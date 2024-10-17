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
    public void Add(Client client) {
        try {
            transaction.begin();
            entityManager.persist(client);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void Delete(Client client) {
        try {
            transaction.begin();
            entityManager.remove(client);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    // update client or add client to database if not exits
    @Override
    public void Update(Client client) {
        try {
            transaction.begin();
            entityManager.merge(client);
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
