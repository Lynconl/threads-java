package br.com.teste.weon.produtores;

import br.com.teste.weon.dao.EmailDAO;
import br.com.teste.weon.model.Email;
import br.com.teste.weon.queue.TratamentoQueue;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

public class ProdutorEmail implements Produtor {

    private final TratamentoQueue tratamentoQueue = TratamentoQueue.getInstance();
    private int qtdeProduzidos = 0;

    @Override
    public void produzir(EntityManager em) {
        var dataHora = LocalDateTime.now();
        var email = new Email("Email origem " + qtdeProduzidos, "Email destino " + qtdeProduzidos, dataHora);
        var emailDAO = new EmailDAO(em);

        emailDAO.save(email);
        tratamentoQueue.enqueue(email);
        qtdeProduzidos++;

        System.out.println("[Produtor] Produziu: " + email.getClass().getSimpleName());
    }

    @Override
    public int getQtdeProduzidos() {
        return qtdeProduzidos;
    }
}
