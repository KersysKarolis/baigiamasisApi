package eu.codeacademy.baigiamasis.controllers;

import eu.codeacademy.baigiamasis.dto.OrderDTO;
import eu.codeacademy.baigiamasis.services.OrderService;
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
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/orders")
@PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
public class OrderController {
    @Autowired
    OrderService orderService;
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrdersByClientsId(@PageableDefault Pageable pageable, @RequestParam(name = "id") Long id){
        try{
            return ResponseEntity.ok().body(orderService.getAllOrdersByClientId(pageable, id));
        } catch (NullPointerException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id){
        try{
            return ResponseEntity.ok().body(orderService.getOrderById(id));
        } catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE', 'CLIENT')")
    @PostMapping
    public ResponseEntity<OrderDTO> addNewOrder(@Valid @RequestBody OrderDTO orderDTO){
        try {
            return ResponseEntity.ok().body(orderService.addNewOrder(orderDTO));
        } catch  (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        }

    @PutMapping
    public ResponseEntity<OrderDTO> updateOrderById(@Valid @RequestBody OrderDTO orderDTO){
        try{
            return ResponseEntity.ok().body(orderService.updateOrderById(orderDTO));
        } catch(NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable Long id){
        try{
            orderService.deleteOrderById(id);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
