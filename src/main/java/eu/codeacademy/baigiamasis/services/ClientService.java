package eu.codeacademy.baigiamasis.services;

import eu.codeacademy.baigiamasis.converters.ClientConverter;
import eu.codeacademy.baigiamasis.converters.OrderConverter;
import eu.codeacademy.baigiamasis.dto.ClientDTO;
import eu.codeacademy.baigiamasis.dto.ClientPasswordChangeDTO;
import eu.codeacademy.baigiamasis.dto.CreateClientDTO;
import eu.codeacademy.baigiamasis.entities.Client;
import eu.codeacademy.baigiamasis.enumerators.Role;
import eu.codeacademy.baigiamasis.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public List<ClientDTO> getAllClients() {
        return ClientConverter.convertClientsToClientsDTO(clientRepository.findAll());
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id).get();
    }

    public ClientDTO addClient(CreateClientDTO createClientDTO) {
        if (createClientDTO.getFirstPassword().equals(createClientDTO.getSecondPassword())) {
            Client client = ClientConverter.convertCreateClientDTOToClient(createClientDTO);
            client.setPassword(passwordEncoder.encode(client.getPassword()));
            clientRepository.saveAndFlush(client);
            return ClientConverter.convertClientToClientDTO(client);
        } else {
            throw new NullPointerException("Username and password fields must be filled");
        }


    }

    public void deleteClientById(Long id) {
        clientRepository.deleteById(id);
    }
    public List<ClientDTO> findAllClientsByName(Pageable pageable, String name){
        Page<Client> clientPage = null;
        if(name != null){
            clientPage = clientRepository.findAllByName(pageable, name);
        } else {
            clientPage = clientRepository.findAll(pageable);
        }
        return ClientConverter.convertClientsToClientsDTO(clientPage);
    }

    public ClientDTO updateClientById(ClientDTO clientDTO) {
        Client client = getClientById(clientDTO.getId());
        if (clientDTO != null) {
            client.setName(clientDTO.getName());
            client.setSurname(clientDTO.getSurname());
            client.setPhoneNumber(clientDTO.getPhone());
            client.setEmail(clientDTO.getEmail());
            if (clientDTO.getOrderList() != null && !clientDTO.getOrderList().isEmpty()) {
                client.setOrderList(OrderConverter.convertOrdersDTOToOrders(clientDTO.getOrderList()));
            }
            clientRepository.saveAndFlush(client);
        }
        return ClientConverter.convertClientToClientDTO(client);
    }

    public void changeClientPassword(ClientPasswordChangeDTO password, Long id) {
        Client client = getClientById(id);
        if (!(passwordEncoder.matches(password.getOldPassword(), client.getPassword()))) {
            throw new InputMismatchException("Incorrect password provided");

        } else if (password.getNewPassword().equals(password.getRepeatNewPassword())) {
            throw new InputMismatchException("New passwords does not match");
        } else {
            client.setPassword(passwordEncoder.encode(password.getNewPassword()));
        }

    }

    public void changeClientRole(String role, Long id) {
        Client client = getClientById(id);
        if (role.equals("ADMIN")) {
            client.setRole(Role.ADMIN);
        } else if (role.equals("EMPLOYEE")) {
            client.setRole(Role.EMPLOYEE);
        } else if (role.equals("CLIENT")) {
            client.setRole(Role.CLIENT);
        } else {
            throw new NoSuchElementException("No such role exists");
        }
    }
}
