package br.com.teste.weon.runnables;

import br.com.teste.weon.produtores.Produtor;
import br.com.teste.weon.util.JPAUtil;

import java.util.concurrent.CountDownLatch;

public class ProdutorRunnable implements Runnable {

    private final CountDownLatch startLatch;
    private final Produtor produtor;
    private final long duracaoThreadEmSegundos;

    public ProdutorRunnable(Produtor produtor, CountDownLatch startLatch, long duracaoThreadEmSegundos) {
        this.produtor = produtor;
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

            System.out.println("[Produtor] Thread: " + Thread.currentThread().getId() + " inicializada.");

            while ((System.currentTimeMillis() - startTime) < duracaoThreadEmMilissegundo) {
                produtor.produzir(em);
                Thread.sleep(100);
            }

            em.getTransaction().commit();
        } catch (InterruptedException e) {
            throw new RuntimeException("Erro inesperado ao rodar a thread: " + Thread.currentThread().getId(), e);
        }

        System.out.println("[Produtor] Thread: " + Thread.currentThread().getId() + " finalizada.");
    }
}
