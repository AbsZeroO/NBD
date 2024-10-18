package org.example.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.model.Client;
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
    public void Delete(Vehicle entity) {
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
    public void Update(Vehicle entity) {
        try {
            Vehicle vehicle2 = entityManager.find(Vehicle.class, entity.getId());

            vehicle2.setRented(entity.isRented());
            vehicle2.setArchived(entity.isArchived());
            vehicle2.setBasePrice(entity.getBasePrice());
            vehicle2.setPlateNumber(entity.getPlateNumber());
            vehicle2.setEngineDisplacement(entity.getEngineDisplacement());

            transaction.begin();

            entityManager.merge(vehicle2);

            transaction.commit();


        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }

    }

    @Override
    public Vehicle Find(long id) {
        return entityManager.find(Vehicle.class, id);
    }

    @Override
    public List<Vehicle> getAll() {
        String jpql = "SELECT v FROM Vehicle v";
        return entityManager.createQuery(jpql, Vehicle.class).getResultList();
    }
}
