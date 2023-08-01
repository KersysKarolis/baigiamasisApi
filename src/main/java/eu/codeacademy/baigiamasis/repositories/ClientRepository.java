package eu.codeacademy.baigiamasis.repositories;

import eu.codeacademy.baigiamasis.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findClientByUsername(String username);
    Page<Client> findAllByName(Pageable pageable, String name);
}
