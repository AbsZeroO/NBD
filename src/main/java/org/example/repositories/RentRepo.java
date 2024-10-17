package org.example.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.model.Client;
import org.example.model.Rent;

import java.util.List;

public class RentRepo implements IRepo<Rent> {

    private final EntityManager entityManager;
    private final EntityTransaction transaction;

    public RentRepo() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("my-persistence-unit");
        this.entityManager = entityManagerFactory.createEntityManager();
        this.transaction = entityManager.getTransaction();
    }


    @Override
    public void Add(Rent entity) {
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
    public void Delete(Rent entity) {
        try {
            transaction.begin();
            entityManager.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void Update(Rent entity) {
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
    public Rent Find(long id) {
        return entityManager.find(Rent.class, id);
    }

    @Override
    public List<Rent> getAll() {
        String jpql = "SELECT r FROM Rent r";
        return entityManager.createQuery(jpql, Rent.class).getResultList();
    }
}
