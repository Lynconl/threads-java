package br.com.teste.weon;

import br.com.teste.weon.model.Chat;
import br.com.teste.weon.queue.TratamentoQueue;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TratamentoQueueTest {

    @Test
    public void metodoEnqueueDeveAdicionarItemNaFila() {
        var chat = new Chat("", "", LocalDateTime.now());
        var tratamentoQueue = TratamentoQueue.getInstance();

        tratamentoQueue.enqueue(chat);

        assertEquals(1, tratamentoQueue.size());
    }

    @Test
    public void metodoDequeueDeveRemoverItemDaFila() {
        var chat = new Chat("", "", LocalDateTime.now());
        var tratamentoQueue = TratamentoQueue.getInstance();

        tratamentoQueue.enqueue(chat);
        tratamentoQueue.dequeue();

        assertTrue(tratamentoQueue.isEmpty());
    }
}
