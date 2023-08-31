package br.com.teste.weon.dao;

import br.com.teste.weon.model.Email;

import javax.persistence.EntityManager;
import java.util.List;

public class EmailDAO implements DAO<Email> {

    private EntityManager em;

    public EmailDAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public Email get(long id) {
        return em.find(Email.class, id);
    }

    @Override
    public List<Email> getAll() {
        return em.createQuery("SELECT e FROM Email e", Email.class)
                .getResultList();
    }

    @Override
    public void save(Email email) {
        em.persist(email);
    }

    @Override
    public void update(Email email) {
        em.merge(email);
    }

    @Override
    public void delete(Email email) {
        try {
            var managedEmail = get(email.getId());

            if (managedEmail != null) {
                em.remove(managedEmail);
                em.getTransaction().commit();
            }
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw new RuntimeException("Erro ao remover email: " + email.getId(), e);
        }
    }
}
