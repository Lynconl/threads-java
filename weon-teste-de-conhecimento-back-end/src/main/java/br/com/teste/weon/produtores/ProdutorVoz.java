package br.com.teste.weon.produtores;

import br.com.teste.weon.dao.VozDAO;
import br.com.teste.weon.model.Voz;
import br.com.teste.weon.queue.TratamentoQueue;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

public class ProdutorVoz implements Produtor {

    private final TratamentoQueue tratamentoQueue = TratamentoQueue.getInstance();
    private int qtdeProduzidos = 0;

    @Override
    public void produzir(EntityManager em) {
        var dataHora = LocalDateTime.now();
        var voz = new Voz("Telefone origem " + qtdeProduzidos, "Telefone destino " + qtdeProduzidos, dataHora);
        var vozDAO = new VozDAO(em);

        vozDAO.save(voz);
        tratamentoQueue.enqueue(voz);
        qtdeProduzidos++;

        System.out.println("[Produtor] Produziu: " + voz.getClass().getSimpleName());
    }

    @Override
    public int getQtdeProduzidos() {
        return qtdeProduzidos;
    }
}
