package br.com.teste.weon.dao;

import br.com.teste.weon.model.Chat;

import javax.persistence.EntityManager;
import java.util.List;

public class ChatDAO implements DAO<Chat> {

    private EntityManager em;

    public ChatDAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public Chat get(long id) {
        return em.find(Chat.class, id);
    }

    @Override
    public List<Chat> getAll() {
        return em.createQuery("SELECT c FROM Chat c", Chat.class)
                .getResultList();
    }

    @Override
    public void save(Chat chat) {
        em.persist(chat);
    }

    @Override
    public void update(Chat chat) {
        em.merge(chat);
    }

    @Override
    public void delete(Chat chat) {
        try {
            var managedChat = get(chat.getId());

            if (managedChat != null) {
                em.remove(managedChat);
                em.getTransaction().commit();
            }
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw new RuntimeException("Erro ao remover chat: " + chat.getId(), e);
        }
    }
}
