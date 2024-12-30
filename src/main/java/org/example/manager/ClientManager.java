package org.example.manager;

import org.example.model.domain.Client;
import org.example.model.modelMapper.ClientAccountModelMapper;
import org.example.repo.ClientAccountRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ClientManager {
    private final ClientAccountRepository repository = new ClientAccountRepository();

    public void add(Client client) {
        repository.add(ClientAccountModelMapper.toClientAccountCas(client));
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

    public void update(Client client) {
        repository.update(ClientAccountModelMapper.toClientAccountCas(client));
    }

    public void delete(Client client) {
        repository.delete(ClientAccountModelMapper.toClientAccountCas(client));
    }

}
