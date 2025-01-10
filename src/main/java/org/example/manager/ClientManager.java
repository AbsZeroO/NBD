package org.example.manager;

import org.example.model.domain.Client;
import org.example.model.modelMapper.ClientAccountModelMapper;
import org.example.repo.ClientAccountRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ClientManager {
    private final ClientAccountRepository repository = new ClientAccountRepository();

    public boolean add(Client client) {
        return repository.add(ClientAccountModelMapper.toClientAccountCas(client));
    }

    public Client findById(int id) {
        return ClientAccountModelMapper.toClientDomain(repository.findById(id));
    }

    public List<Client> findAll() {
        return repository.findAll()
                .stream()
                .map(ClientAccountModelMapper::toClientDomain)
                .collect(Collectors.toList());
    }

    public boolean update(Client client) {
        return repository.update(ClientAccountModelMapper.toClientAccountCas(client));
    }

    public boolean delete(Client client) {
        return repository.delete(ClientAccountModelMapper.toClientAccountCas(client));
    }

    public ClientAccountRepository getRepository() {
        return repository;
    }
}
