package repositories;

import org.example.mgd.AddressMgd;
import org.example.mgd.ClientAccountMgd;
import org.example.mgd.ClientTypeMgd;
import org.example.model.ClientType;
import org.example.repositories.ClientMgdRepository;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientMdgRepositoryTest {
    ClientMgdRepository clientMgdRepository = new ClientMgdRepository();
    @AfterEach
    public void before() {
        clientMgdRepository.getMongodb().getCollection("clients").drop();
        clientMgdRepository.getMongodb().getCollection("vehicles").drop();
    }

    @After
    public void after() throws Exception {
        clientMgdRepository.close();
    }

    @Test
    public void addTest() {
        ClientAccountMgd client = new ClientAccountMgd(1, "Maciek", "Walaszek",
                new AddressMgd("Łódź", "Radwańska", "40"), ClientType.GOLD, false);

        clientMgdRepository.Add(client);
        assertEquals(clientMgdRepository.getAll().size(), 1);

    }

}
