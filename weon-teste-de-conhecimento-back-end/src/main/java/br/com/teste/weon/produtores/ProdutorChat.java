package br.com.teste.weon.produtores;

import br.com.teste.weon.dao.ChatDAO;
import br.com.teste.weon.model.Chat;
import br.com.teste.weon.queue.TratamentoQueue;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

public class ProdutorChat implements Produtor {

    private final TratamentoQueue tratamentoQueue = TratamentoQueue.getInstance();
    private int qtdeProduzidos = 0;

    @Override
    public void produzir(EntityManager em) {
        var dataHora = LocalDateTime.now();
        var chat = new Chat("Nome origem " + qtdeProduzidos, "Nome destino " + qtdeProduzidos, dataHora);
        var chatDAO = new ChatDAO(em);

        chatDAO.save(chat);
        tratamentoQueue.enqueue(chat);
        qtdeProduzidos++;

        System.out.println("[Produtor] Produziu: " + chat.getClass().getSimpleName());
    }

    @Override
    public int getQtdeProduzidos() {
        return qtdeProduzidos;
    }
}
