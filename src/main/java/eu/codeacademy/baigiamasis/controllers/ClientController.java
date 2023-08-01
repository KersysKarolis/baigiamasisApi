package eu.codeacademy.baigiamasis.controllers;

import eu.codeacademy.baigiamasis.converters.ClientConverter;
import eu.codeacademy.baigiamasis.dto.ClientDTO;
import eu.codeacademy.baigiamasis.dto.CreateClientDTO;
import eu.codeacademy.baigiamasis.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/clients")
@PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
public class ClientController {
    @Autowired
    ClientService clientService;
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClients(@PageableDefault Pageable pageable, @RequestParam(name = "name", required = false) String name) {
        try {
            return ResponseEntity.ok().body(clientService.findAllClientsByName(pageable, name));
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> findClientById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(ClientConverter.convertClientToClientDTO(clientService.getClientById(id)));
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE', 'CLIENT')")
    @PostMapping
    public ResponseEntity<ClientDTO> addNewClient(@Valid @RequestBody CreateClientDTO createClientDTO) {
        try {
            return ResponseEntity.ok().body(clientService.addClient(createClientDTO));
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<ClientDTO> updateClientById(@RequestBody ClientDTO clientDTO) {
        try {
            return ResponseEntity.ok().body(clientService.updateClientById(clientDTO));
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClientById(@PathVariable Long id) {
        try {
            clientService.deleteClientById(id);
            return ResponseEntity.ok().build();
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
