package org.example.repositories;

import jakarta.persistence.*;
import org.example.model.Rent;
import org.example.model.Vehicle;

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
            Vehicle vehicle1 = entity.getVehicle();
            Vehicle vehicle2 = entityManager.find(Vehicle.class, entity.getVehicle().getId());

            vehicle2.setRented(vehicle1.isRented());
            vehicle2.setArchived(vehicle1.isArchived());
            vehicle2.setBasePrice(vehicle1.getBasePrice());
            vehicle2.setPlateNumber(vehicle1.getPlateNumber());
            vehicle2.setEngineDisplacement(vehicle1.getEngineDisplacement());

            transaction.begin();

            entityManager.merge(entity);
            entityManager.merge(vehicle2);

            transaction.commit();

        } catch (OptimisticLockException e) {
            transaction.rollback();
            System.out.println("Optimistic lock exception: The entity has been modified by another transaction.");
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

    public Rent findRentById(Long id) {
        String jpql = "SELECT r FROM Rent r WHERE r.id = :rentId";

        return entityManager.createQuery(jpql, Rent.class)
                .setParameter("rentId", id)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }
}
