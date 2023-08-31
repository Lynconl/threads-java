package br.com.teste.weon.queue;

import br.com.teste.weon.model.Envio;

import java.util.LinkedList;
import java.util.Queue;

public class TratamentoQueue {

    private static TratamentoQueue instance;
    private final Queue<Envio> queue = new LinkedList<>();

    private TratamentoQueue() {

    }

    public static synchronized TratamentoQueue getInstance() {
        if (instance == null) {
            instance = new TratamentoQueue();
        }

        return instance;
    }

    public synchronized void enqueue(Envio envio) {
        queue.add(envio);
    }

    public synchronized Envio dequeue() {
        if (!queue.isEmpty()) {
            return queue.poll();
        }

        return null;
    }

    public synchronized int size() {
        return queue.size();
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}
