package org.example.managers;

import org.example.exceptions.ClientException;
import org.example.mappers.ClientMapper;
import org.example.model.Client;
import org.example.repositories.ClientMgdRepository;


import java.util.List;
import java.util.stream.Collectors;

public class ClientManager {

    private final ClientMgdRepository clientRepo;

    public ClientManager(ClientMgdRepository clientRepo) {
        this.clientRepo = clientRepo;
    }

    public void registerClient(Client client) {
        clientRepo.add(ClientMapper.clientToMongo(client));
    }

    public void deleateClient(Client client){
        clientRepo.delete(client.getId());
    }

    public Client getClient(int id) throws ClientException {
        return ClientMapper.clientFromMongo(clientRepo.findById(id));
    }
    public void updateInfo(Client client) {
        clientRepo.update(ClientMapper.clientToMongo(client));
    }

    public List<Client> getAllClients(){
        return clientRepo.findAll()
                .stream()
                .map(ClientMapper::clientFromMongo)
                .collect(Collectors.toList());
    }

}
