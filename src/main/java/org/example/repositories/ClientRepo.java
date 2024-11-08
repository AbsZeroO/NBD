package org.example.repositories;

import org.example.model.Client;

import jakarta.persistence.*;
import java.util.List;


public class ClientRepo implements IRepo<Client> {
    private final EntityManager entityManager;
    private final EntityTransaction transaction;

    public ClientRepo() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("my-persistence-unit");
        this.entityManager = entityManagerFactory.createEntityManager();
        this.transaction = entityManager.getTransaction();
    }

    @Override
    public void Add(Client entity) {
        try {
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void Delete(Client entity) {
        try {
            transaction.begin();
            entityManager.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }


    public void Unredister(Client entity) {
        entity.setArchived(true);
        Update(entity);
    }

    // update client or add client to database if not exits
    @Override
    public void Update(Client entity) {
        try {
            transaction.begin();
            entityManager.merge(entity);
            transaction.commit();
        } catch(Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }

    }

    @Override
    public Client Find(long id) {
        return entityManager.find(Client.class, id);
    }

    @Override
    public List<Client> getAll() {
        String jpql = "SELECT c FROM Client c";
        return entityManager.createQuery(jpql, Client.class).getResultList();
    }
}
