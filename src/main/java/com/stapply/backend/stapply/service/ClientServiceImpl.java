package com.stapply.backend.stapply.service;

import com.stapply.backend.stapply.models.Client;
import com.stapply.backend.stapply.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService{
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void create(Client client) {
        clientRepository.save(client);
    }

    @Override
    public List<Client> readAll() {
        var result = clientRepository.findAll();
        return result;
    }

    @Override
    public Client read(int id) {
        if(!clientRepository.existsById(id))
            return null;

        var result = clientRepository.findById(id).get();
        return result;
    }

    @Override
    public boolean update(Client client, int id) {
        if(!clientRepository.existsById(id))
            return false;

        client.setId(id);
        clientRepository.save(client);
        return true;
    }

    @Override
    public boolean delete(int id) {
        if(!clientRepository.existsById(id))
            return false;

        clientRepository.deleteById(id);
        return true;
    }
}
