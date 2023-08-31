package br.com.teste.weon.util;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("banco");

    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }
}
