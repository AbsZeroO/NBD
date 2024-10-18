package org.example.managers;

import org.example.exceptions.ClientException;
import org.example.model.Client;
import org.example.repositories.ClientRepo;

import java.util.List;

public class ClientManager {
    private ClientRepo clientRepo;

    public ClientManager(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
    }

    public void registerClient(Client client) {
        clientRepo.Add(client);
    }

    public void unregisterClient(Client client){
        clientRepo.Unredister(client);
        //clientRepo.Delete(client);
    }
    public Client getClient(Long id) throws ClientException {
        Client client = clientRepo.Find(id);
        if(client == null) {
            throw new ClientException("Client does not exists!");
        } else {
            return client;
        }
    }
    public void edit(Client client) {
        clientRepo.Update(client);
    }
    public List<Client> getAllClients(){
        return clientRepo.getAll();
    }
}
