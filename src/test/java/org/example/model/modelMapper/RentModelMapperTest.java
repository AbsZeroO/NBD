package org.example.model.modelMapper;

import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.truncate.Truncate;
import org.example.model.cassandra.*;
import org.example.model.domain.Client;
import org.example.model.domain.ClientType;
import org.example.repo.RentRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RentModelMapperTest {

    private static RentRepository rentRepository;

    @BeforeAll
    static void setUp() {
        rentRepository = new RentRepository();
    }

    @AfterAll
    public static void teardown() {
        Truncate truncate = QueryBuilder.truncate("rent_a_vehicle", "rents");
        rentRepository.getSession().execute(truncate.build());
        truncate = QueryBuilder.truncate("rent_a_vehicle", "rent_by_client");
        rentRepository.getSession().execute(truncate.build());
        truncate = QueryBuilder.truncate("rent_a_vehicle", "rent_by_vehicle");
        rentRepository.getSession().execute(truncate.build());
    }

    @Test
    void toRentCasByClient() {
        RentCas rentCas = new RentCas(1, 1, 1, null, null, 100.0, false);

        RentCasByClient rentCasByClient = RentModelMapper.toRentCasByClient(rentCas);

        assertEquals(rentCas.getEntityId(), rentCasByClient.getEntityId());
        assertEquals(rentCas.getClientAccountCas(), rentCasByClient.getClientAccountCas());
        assertEquals(rentCas.getVehicleCas(), rentCasByClient.getVehicleCas());
        assertEquals(rentCas.getBeginTime(), rentCasByClient.getBeginTime());
        assertEquals(rentCas.getEndTime(), rentCasByClient.getEndTime());
        assertEquals(rentCas.getRentCost(), rentCasByClient.getRentCost());
        assertEquals(rentCas.isArchived(), rentCasByClient.isArchived());
    }

    @Test
    void toRentCasByVehicle() {
        RentCas rentCas = new RentCas(1, 1, 1, null, null, 100.0, false);

        RentCasByVehicle rentCasByVehicle = RentModelMapper.toRentCasByVehicle(rentCas);

        assertEquals(rentCas.getEntityId(), rentCasByVehicle.getEntityId());
        assertEquals(rentCas.getClientAccountCas(), rentCasByVehicle.getClientAccountCas());
        assertEquals(rentCas.getVehicleCas(), rentCasByVehicle.getVehicleCas());
        assertEquals(rentCas.getBeginTime(), rentCasByVehicle.getBeginTime());
        assertEquals(rentCas.getEndTime(), rentCasByVehicle.getEndTime());
        assertEquals(rentCas.getRentCost(), rentCasByVehicle.getRentCost());
        assertEquals(rentCas.isArchived(), rentCasByVehicle.isArchived());
    }

    @Test
    void fromRentCasByClientOrVehicle() {
        RentCasByClient rentByClient = new RentCasByClient(1, 1, 1, null, null, 100.0, false);

        RentCas rentCas = RentModelMapper.fromRentCasByClientOrVehicle(rentByClient);

        assertEquals(rentByClient.getEntityId(), rentCas.getEntityId());
        assertEquals(rentByClient.getClientAccountCas(), rentCas.getClientAccountCas());
        assertEquals(rentByClient.getVehicleCas(), rentCas.getVehicleCas());
        assertEquals(rentByClient.getBeginTime(), rentCas.getBeginTime());
        assertEquals(rentByClient.getEndTime(), rentCas.getEndTime());
        assertEquals(rentByClient.getRentCost(), rentCas.getRentCost());
        assertEquals(rentByClient.isArchived(), rentCas.isArchived());
    }

    @Test
    void fromRentCasByVehicle() {
        RentCasByVehicle rentByVehicle = new RentCasByVehicle(1, 1, 1, null, null, 100.0, false);

        RentCas rentCas = RentModelMapper.fromRentCasByClientOrVehicle(rentByVehicle);

        assertEquals(rentByVehicle.getEntityId(), rentCas.getEntityId());
        assertEquals(rentByVehicle.getClientAccountCas(), rentCas.getClientAccountCas());
        assertEquals(rentByVehicle.getVehicleCas(), rentCas.getVehicleCas());
        assertEquals(rentByVehicle.getBeginTime(), rentCas.getBeginTime());
        assertEquals(rentByVehicle.getEndTime(), rentCas.getEndTime());
        assertEquals(rentByVehicle.getRentCost(), rentCas.getRentCost());
        assertEquals(rentByVehicle.isArchived(), rentCas.isArchived());
    }

    @Test
    void toDomain() {
        // Tworzymy obiekt RentCas, który będzie przekazywany do repozytorium
        RentCas rentCas = new RentCas();
        rentCas.setEntityId(1);
        rentCas.setClientAccountCas(1001); // przykładowe dane
        rentCas.setVehicleCas(2001); // przykładowe dane
        rentCas.setBeginTime(LocalDateTime.now());
        rentCas.setEndTime(LocalDateTime.now().plusHours(5));
        rentCas.setRentCost(100.0);
        rentCas.setArchived(false);

        rentRepository.add(rentCas);

        RentCas retrievedRent = rentRepository.findById(rentCas.getEntityId());

        assertNotNull(retrievedRent);
        assertEquals(rentCas.getEntityId(), retrievedRent.getEntityId());


        ClientAccountCas clientAccountCas = new ClientAccountCas();
        clientAccountCas.setAddressCas(new AddressCas("nowa", "Nadzieja", "15"));
        clientAccountCas.setEntityId(1001);
        clientAccountCas.setFirstName("John");
        clientAccountCas.setLastName("Doe");
        clientAccountCas.setClientType(ClientType.GOLD);
        clientAccountCas.setArchived(false);

        Client client = ClientAccountModelMapper.toClientDomain(clientAccountCas);

        assertNotNull(client);
        assertEquals("John", client.getFirstName());
        assertEquals("Doe", client.getLastName());
        assertEquals("GOLD", client.getClientType().toString());
    }

}
