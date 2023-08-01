package eu.codeacademy.baigiamasis.converters;

import eu.codeacademy.baigiamasis.dto.ClientDTO;
import eu.codeacademy.baigiamasis.dto.CreateClientDTO;
import eu.codeacademy.baigiamasis.entities.Client;

import java.util.ArrayList;
import java.util.List;

public abstract class ClientConverter {
    public static ClientDTO convertClientToClientDTO(Client client) {
        ClientDTO clientDTO = null;
        if (client != null) {
            clientDTO = new ClientDTO();
            clientDTO.setId(client.getId());
            clientDTO.setName(client.getName());
            clientDTO.setSurname(client.getSurname());
            clientDTO.setUsername(client.getUsername());
            clientDTO.setEmail(client.getEmail());
            clientDTO.setPhone(client.getPhoneNumber());
            clientDTO.setOrderList(OrderConverter.convertOrdersToOrdersDTO(client.getOrderList()));
        }
        return clientDTO;
    }

    public static Client convertClientDTOToClient(ClientDTO clientDTO) {
        Client client = null;
        if (clientDTO != null) {
            client = new Client();
            client.setId(clientDTO.getId());
            client.setName(clientDTO.getName());
            client.setSurname(clientDTO.getSurname());
            client.setUsername(clientDTO.getUsername());
            client.setEmail(clientDTO.getEmail());
            client.setOrderList(OrderConverter.convertOrdersDTOToOrders(clientDTO.getOrderList()));
        }
        return client;
    }

    public static List<ClientDTO> convertClientsToClientsDTO(Iterable<Client> usersList) {
        List<ClientDTO> clientDTOList = null;
        if (usersList != null) {
            clientDTOList = new ArrayList<>();
            for (Client e : usersList) {
                clientDTOList.add(convertClientToClientDTO(e));
            }
        }
        return clientDTOList;

    }

    public static Client convertCreateClientDTOToClient(CreateClientDTO createClientDTO) {
        Client client = null;
        if(createClientDTO != null && createClientDTO.getFirstPassword().equals(createClientDTO.getSecondPassword())){
            client = new Client();
            client.setName(createClientDTO.getName());
            client.setEmail(createClientDTO.getEmail());
            client.setSurname(createClientDTO.getSurname());
            if(createClientDTO.getOrderList() != null) {
                client.setOrderList(OrderConverter.convertOrdersDTOToOrders(createClientDTO.getOrderList()));
            }
            client.setUsername(createClientDTO.getUsername());
            client.setPhoneNumber(createClientDTO.getPhone());
            client.setPassword(createClientDTO.getFirstPassword());
        }
        return client;
    }
}
