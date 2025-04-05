package com.example.oro2.converter;

import com.example.oro2.dto.ClientDTO;
import com.example.oro2.model.Client;

public class ClientToClientDTO {
    public ClientDTO mapToDTO(Client client){
        return new ClientDTO(client.getFirstName(),
                client.getLastName(),
                client.getEmail());
    }
}
