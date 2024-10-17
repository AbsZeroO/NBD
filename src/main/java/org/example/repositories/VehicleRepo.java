package org.example.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.model.Vehicle;

import java.util.List;

public class VehicleRepo implements IRepo<Vehicle> {
    private final EntityManager entityManager;
    private final EntityTransaction transaction;

    public VehicleRepo() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("my-persistence-unit");
        this.entityManager = entityManagerFactory.createEntityManager();
        this.transaction = entityManager.getTransaction();
    }



    @Override
    public void Add(Vehicle entity) {

    }

    @Override
    public void Delete(Vehicle entity) {

    }

    @Override
    public void Update(Vehicle entity) {

    }

    @Override
    public Vehicle Find(long id) {
        return null;
    }

    @Override
    public List<Vehicle> getAll() {
        return null;
    }
}
