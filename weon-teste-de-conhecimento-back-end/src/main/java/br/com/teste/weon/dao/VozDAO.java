package br.com.teste.weon.dao;

import br.com.teste.weon.model.Voz;

import javax.persistence.EntityManager;
import java.util.List;

public class VozDAO implements DAO<Voz> {

    private EntityManager em;

    public VozDAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public Voz get(long id) {
        return em.find(Voz.class, id);
    }

    @Override
    public List<Voz> getAll() {
        return em.createQuery("SELECT v FROM Voz v", Voz.class)
                .getResultList();
    }

    @Override
    public void save(Voz voz) {
        em.persist(voz);
    }

    @Override
    public void update(Voz voz) {
        em.merge(voz);
    }

    @Override
    public void delete(Voz voz) {
        try {
            var managedVoz = get(voz.getId());

            if (managedVoz != null) {
                em.remove(managedVoz);
                em.getTransaction().commit();
            }
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw new RuntimeException("Erro ao remover voz: " + voz.getId(), e);
        }
    }
}
