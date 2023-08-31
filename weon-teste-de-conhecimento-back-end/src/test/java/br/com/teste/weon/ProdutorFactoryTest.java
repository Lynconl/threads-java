package br.com.teste.weon;

import br.com.teste.weon.factory.ProdutorFactory;
import br.com.teste.weon.produtores.ProdutorChat;
import br.com.teste.weon.produtores.ProdutorEmail;
import br.com.teste.weon.produtores.ProdutorVoz;
import br.com.teste.weon.produtores.TipoProdutor;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ProdutorFactoryTest {

    @Test
    public void deveRetornarProdutorVozQuandoOTipoForVoz() {
        var produtor = ProdutorFactory.criarProdutor(TipoProdutor.VOZ);

        assertTrue(produtor instanceof ProdutorVoz);
    }

    @Test
    public void deveRetornarProdutorChatQuandoOTipoForChat() {
        var produtor = ProdutorFactory.criarProdutor(TipoProdutor.CHAT);

        assertTrue(produtor instanceof ProdutorChat);
    }

    @Test
    public void deveRetornarProdutorEmailQuandoOTipoForEmail() {
        var produtor = ProdutorFactory.criarProdutor(TipoProdutor.EMAIL);

        assertTrue(produtor instanceof ProdutorEmail);
    }
}
