package br.com.teste.weon;

import br.com.teste.weon.factory.ProdutorFactory;
import br.com.teste.weon.produtores.Produtor;
import br.com.teste.weon.produtores.TipoProdutor;
import br.com.teste.weon.queue.TratamentoQueue;
import br.com.teste.weon.runnables.ConsumidorRunnable;
import br.com.teste.weon.runnables.ProdutorRunnable;
import br.com.teste.weon.util.Propriedades;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        var properties = Propriedades.getInstance();
        var quantidadeProdutores = Integer.parseInt(properties.get("quantidade.produtores"));
        var quantidadeConsumidores = Integer.parseInt(properties.get("quantidade.consumidores"));
        var duracaoThreadProdutoresEmSegundos = Long.parseLong(properties.get("produtores.segundos.producao"));
        var duracaoThreadConsumidoresEmSegundos = Long.parseLong(properties.get("consumidores.segundos.producao"));
        var totalThreads = quantidadeProdutores + quantidadeConsumidores;
        var startLatch = new CountDownLatch(totalThreads);

        System.out.println("[Main] Iniciando criação das threads");
        System.out.println("[Main] Threads dos produtores terão duração de: " + duracaoThreadProdutoresEmSegundos + " segundos");
        System.out.println("[Main] Threads dos consumidores terão duração de: " + duracaoThreadConsumidoresEmSegundos + " segundos");

        var executorProdutores = Executors.newScheduledThreadPool(quantidadeProdutores);
        var executorConsumidores = Executors.newScheduledThreadPool(quantidadeConsumidores);

        var produtores = criarProdutores(executorProdutores, startLatch, quantidadeProdutores, duracaoThreadProdutoresEmSegundos);
        var consumidores = criarConsumidores(executorConsumidores, startLatch, quantidadeConsumidores, duracaoThreadConsumidoresEmSegundos);

        try {
            executorProdutores.awaitTermination(duracaoThreadProdutoresEmSegundos, TimeUnit.SECONDS);
            executorConsumidores.awaitTermination(duracaoThreadConsumidoresEmSegundos, TimeUnit.SECONDS);
            startLatch.countDown();

            System.out.println("[Main] Todas as threads foram finalizadas");

            var totalProduzidos = obterTotalProduzidos(produtores);
            var totalConsumidos = obterTotalConsumidos(consumidores);

            System.out.println("[Main] Total produzidos: " + totalProduzidos);
            System.out.println("[Main] Total consumidos: " + totalConsumidos);

            var fila = TratamentoQueue.getInstance();

            if (!fila.isEmpty()) {
                System.out.println("[Main] Sobrou objetos na fila: " + fila.size());
            }

            System.exit(0);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static List<Produtor> criarProdutores(ScheduledExecutorService executorProdutores,
                                          CountDownLatch startLatch,
                                          int quantidadeProdutores,
                                          long duracaoThreadEmSegundos) {
        var produtores = new ArrayList<Produtor>();

        for (int i = 0; i < quantidadeProdutores; i++) {
            //Algo simples para dar uma variedade nos produtores. Cada thread será responsável por produzir um tipo de produtor.
            var tipoProdutor = i == 0 ? TipoProdutor.VOZ : i == 1 ? TipoProdutor.EMAIL : TipoProdutor.CHAT;

            var produtor = ProdutorFactory.criarProdutor(tipoProdutor);
            produtores.add(produtor);
            executorProdutores.submit(new ProdutorRunnable(produtor, startLatch, duracaoThreadEmSegundos));
        }

        return produtores;
    }

    private static List<ConsumidorRunnable> criarConsumidores(ScheduledExecutorService executorConsumidores,
                                                              CountDownLatch startLatch,
                                                              int quantidadeConsumidores,
                                                              long duracaoThreadConsumidoresEmSegundos) {
        var consumidores = new ArrayList<ConsumidorRunnable>();

        for (int i = 0; i < quantidadeConsumidores; i++) {
            var consumidorRunnable = new ConsumidorRunnable(startLatch, duracaoThreadConsumidoresEmSegundos);
            consumidores.add(consumidorRunnable);
            executorConsumidores.submit(consumidorRunnable);
        }

        return consumidores;
    }

    private static int obterTotalProduzidos(List<Produtor> produtores) {
        int total = 0;

        for (Produtor produtor : produtores) {
            total += produtor.getQtdeProduzidos();
        }

        return total;
    }

    private static int obterTotalConsumidos(List<ConsumidorRunnable> consumidores) {
        int total = 0;

        for (ConsumidorRunnable consumidorRunnable : consumidores) {
            total += consumidorRunnable.getQuantidadeConsumida();
        }

        return total;
    }
}