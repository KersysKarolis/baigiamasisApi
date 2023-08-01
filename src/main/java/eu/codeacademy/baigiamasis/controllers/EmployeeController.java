package eu.codeacademy.baigiamasis.controllers;

import eu.codeacademy.baigiamasis.dto.CreateEmployeeDTO;
import eu.codeacademy.baigiamasis.dto.EmployeeDTO;
import eu.codeacademy.baigiamasis.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;
   @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(){
        try{
            return ResponseEntity.ok().body(employeeService.getAllEmployees());
        } catch (NullPointerException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id){
       try{
           return ResponseEntity.ok().body(employeeService.getEmployeeById(id));
       } catch (NoSuchElementException e){
           throw new ResponseStatusException(HttpStatus.NOT_FOUND);
       }
    }
    @PostMapping
    public ResponseEntity<EmployeeDTO> addNewEmployee(@Valid @RequestBody CreateEmployeeDTO createEmployeeDTO){
       try{
           return ResponseEntity.ok().body(employeeService.addNewEmployee(createEmployeeDTO));
       } catch(InputMismatchException e){
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
       }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable Long id){
       try{
           employeeService.deleteEmployeeById(id);
           return ResponseEntity.ok().build();
       } catch(NoSuchElementException e){
           throw new ResponseStatusException(HttpStatus.NOT_FOUND);
       }
    }
    @PutMapping
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@RequestBody EmployeeDTO employeeDTO){
       try{
           return ResponseEntity.ok().body(employeeService.updateEmployeeById(employeeDTO));
       } catch (NullPointerException e){
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
       }
    }
}