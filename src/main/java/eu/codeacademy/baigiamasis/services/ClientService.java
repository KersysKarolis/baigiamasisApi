package eu.codeacademy.baigiamasis.services;

import eu.codeacademy.baigiamasis.converters.ClientConverter;
import eu.codeacademy.baigiamasis.converters.OrderConverter;
import eu.codeacademy.baigiamasis.dto.ClientDTO;
import eu.codeacademy.baigiamasis.dto.PasswordChangeDTO;
import eu.codeacademy.baigiamasis.dto.CreateClientDTO;
import eu.codeacademy.baigiamasis.entities.Client;
import eu.codeacademy.baigiamasis.enumerators.Role;
import eu.codeacademy.baigiamasis.exceptions.ObjectAlreadyExistsException;
import eu.codeacademy.baigiamasis.exceptions.PasswordDoesNotMatchException;
import eu.codeacademy.baigiamasis.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    MessageSource messageSource;
    @Autowired
    ValidatorService validatorService;


    public List<ClientDTO> getAllClients() {
        return ClientConverter.convertClientsToClientsDTO(clientRepository.findAll());
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id).get();
    }

    public ClientDTO addClient(CreateClientDTO createClientDTO) throws ObjectAlreadyExistsException, PasswordDoesNotMatchException {
        if(clientRepository.existsClientByUsername(createClientDTO.getUsername())){
            throw new ObjectAlreadyExistsException(messageSource.getMessage("username.already.exists", null, LocaleContextHolder.getLocale()));
        } else if(!(createClientDTO.getFirstPassword().equals(createClientDTO.getSecondPassword()))){
            throw new PasswordDoesNotMatchException(messageSource.getMessage("password.does.not.match", null, LocaleContextHolder.getLocale()));
        }else {
            Client client = ClientConverter.convertCreateClientDTOToClient(createClientDTO);
            client.setPassword(passwordEncoder.encode(client.getPassword()));
            client.setRole(Role.CLIENT);
            clientRepository.saveAndFlush(client);
            return ClientConverter.convertClientToClientDTO(client);
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

    public void changeClientPassword(PasswordChangeDTO password, Long id) throws PasswordDoesNotMatchException{
        Client client = getClientById(id);
        if(validatorService.isPasswordValid(client.getPassword(), password)) {
            client.setPassword(passwordEncoder.encode(password.getNewPassword()));
           clientRepository.saveAndFlush(client);
        }

    }


}
