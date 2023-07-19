package eu.codeacademy.baigiamasis.services;

import eu.codeacademy.baigiamasis.converters.ClientConverter;
import eu.codeacademy.baigiamasis.converters.OrderConverter;
import eu.codeacademy.baigiamasis.dto.ClientDTO;
import eu.codeacademy.baigiamasis.dto.CreateClientDTO;
import eu.codeacademy.baigiamasis.entities.Client;
import eu.codeacademy.baigiamasis.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public List<ClientDTO> getAllClients(){
        return ClientConverter.convertClientsToClientsDTO(clientRepository.findAll());
    }
    public ClientDTO getClientById(Long id ){
        return ClientConverter.convertClientToClientDTO(clientRepository.findById(id).get());
    }
    public ClientDTO addClient (CreateClientDTO createClientDTO){
        if(createClientDTO.getFirstPassword() != null && createClientDTO.getUsername() != null) {
            Client client = ClientConverter.convertCreateClientDTOToClient(createClientDTO);
            client.setPassword(passwordEncoder.encode(client.getPassword()));
            clientRepository.saveAndFlush(client);
            return ClientConverter.convertClientToClientDTO(client);
        } else {
            throw new NullPointerException("Username and password fields must be filled");
        }

    }
    public void deleteClientById(Long id){
        clientRepository.deleteById(id);
    }
    public ClientDTO updateClientById(ClientDTO clientDTO){
        Client client = clientRepository.findById(clientDTO.getId()).get();
        if(clientDTO != null){
            client.setName(clientDTO.getName());
            client.setSurname(clientDTO.getSurname());
            client.setPhoneNumber(clientDTO.getPhone());
            client.setEmail(clientDTO.getEmail());
            if(clientDTO.getOrderList() != null && !clientDTO.getOrderList().isEmpty()){
                client.setOrderList(OrderConverter.convertOrdersDTOToOrders(clientDTO.getOrderList()));
            }
            clientRepository.saveAndFlush(client);
        }
        return ClientConverter.convertClientToClientDTO(client);
    }
}
