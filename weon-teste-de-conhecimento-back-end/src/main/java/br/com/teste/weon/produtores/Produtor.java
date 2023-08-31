package br.com.teste.weon.produtores;

import javax.persistence.EntityManager;

public interface Produtor {

    void produzir(EntityManager em);

    int getQtdeProduzidos();
}
