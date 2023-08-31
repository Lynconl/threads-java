package br.com.teste.weon.factory;

import br.com.teste.weon.produtores.*;

public class ProdutorFactory {

    public static Produtor criarProdutor(TipoProdutor tipoProdutor) {
        return switch (tipoProdutor) {
            case VOZ -> new ProdutorVoz();
            case EMAIL -> new ProdutorEmail();
            case CHAT -> new ProdutorChat();
        };
    }
}
