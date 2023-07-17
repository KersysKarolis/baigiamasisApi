package eu.codeacademy.baigiamasis.repositories;

import eu.codeacademy.baigiamasis.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
