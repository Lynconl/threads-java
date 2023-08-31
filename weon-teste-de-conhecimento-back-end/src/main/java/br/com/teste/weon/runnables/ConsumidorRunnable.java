package br.com.teste.weon.runnables;

import br.com.teste.weon.dao.ChatDAO;
import br.com.teste.weon.dao.EmailDAO;
import br.com.teste.weon.dao.VozDAO;
import br.com.teste.weon.model.Chat;
import br.com.teste.weon.model.Email;
import br.com.teste.weon.model.Envio;
import br.com.teste.weon.model.Voz;
import br.com.teste.weon.queue.TratamentoQueue;
import br.com.teste.weon.util.JPAUtil;

import javax.persistence.EntityManager;
import java.util.concurrent.CountDownLatch;

public class ConsumidorRunnable implements Runnable {

    private final CountDownLatch startLatch;
    private final long duracaoThreadEmSegundos;
    private final TratamentoQueue tratamentoQueue = TratamentoQueue.getInstance();
    private int qtdeConsumida = 0;

    public ConsumidorRunnable(CountDownLatch startLatch, long duracaoThreadEmSegundos) {
        this.startLatch = startLatch;
        this.duracaoThreadEmSegundos = duracaoThreadEmSegundos;
    }

    @Override
    public void run() {
        startLatch.countDown();

        var em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            long startTime = System.currentTimeMillis();
            long duracaoThreadEmMilissegundo = duracaoThreadEmSegundos * 1000;

            System.out.println("[Consumidor] Thread: " + Thread.currentThread().getId() + " inicializada.");

            while ((System.currentTimeMillis() - startTime) < duracaoThreadEmMilissegundo) {
                var envioASerRemovido = tratamentoQueue.dequeue();

                if (envioASerRemovido != null) {
                    consumir(em, envioASerRemovido);
                    qtdeConsumida++;
                    Thread.sleep(100);
                } else {
                    // Caso a fila esteja vazia, esperar 1 segundo antes de tentar novamente.
                    Thread.sleep(1000);
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Erro inesperado ao rodar a thread: " + Thread.currentThread().getId(), e);
        }

        System.out.println("[Consumidor] Thread: " + Thread.currentThread().getId() + " finalizada.");
    }

    void consumir(EntityManager em, Envio envio) {
        if (envio instanceof Voz voz) {
            var vozDAO = new VozDAO(em);
            vozDAO.delete(voz);
            System.out.println("[Consumidor] Consumiu: " + voz.getClass().getSimpleName());
        } else if (envio instanceof Chat chat) {
            var chatDAO = new ChatDAO(em);
            chatDAO.delete(chat);
            System.out.println("[Consumidor] Consumiu: " + chat.getClass().getSimpleName());
        } else if (envio instanceof Email email) {
            var emailDAO = new EmailDAO(em);
            emailDAO.delete(email);
            System.out.println("[Consumidor] Consumiu: " + email.getClass().getSimpleName());
        } else {
            System.out.println("[Consumidor] Consumiu um envio inesperado: " + envio.getClass().getSimpleName());
        }
    }

    public int getQuantidadeConsumida() {
        return qtdeConsumida;
    }
}
