package org.example.model;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        Client client = new Client("Filip", "Maciaszek", new Address("Lodz", "Radwanska", "5"),
                new Gold());
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("my-persistence-unit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        entityManager.persist(client);

        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();
    }
}