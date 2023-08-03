package eu.codeacademy.baigiamasis.controllers;

import eu.codeacademy.baigiamasis.converters.EmployeeConverter;
import eu.codeacademy.baigiamasis.dto.CreateEmployeeDTO;
import eu.codeacademy.baigiamasis.dto.EmployeeDTO;
import eu.codeacademy.baigiamasis.dto.PasswordChangeDTO;
import eu.codeacademy.baigiamasis.exceptions.ObjectAlreadyExistsException;
import eu.codeacademy.baigiamasis.exceptions.PasswordDoesNotMatchException;
import eu.codeacademy.baigiamasis.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/employees")
@PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;
   @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@PageableDefault Pageable pageable, @RequestParam(name = "role", required = false) String role){
            return ResponseEntity.ok().body(employeeService.getAllEmployeesByRole(pageable, role));
    }
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id){
       try{
           return ResponseEntity.ok().body(EmployeeConverter.convertEmployeeToEmployeeDTO(employeeService.getEmployeeById(id)));
       } catch (NoSuchElementException e){
           throw new ResponseStatusException(HttpStatus.NOT_FOUND);
       }
    }
    @PostMapping
    public ResponseEntity<EmployeeDTO> addNewEmployee(@Valid @RequestBody CreateEmployeeDTO createEmployeeDTO){
       try{
           return ResponseEntity.ok().body(employeeService.addNewEmployee(createEmployeeDTO));
       } catch(ObjectAlreadyExistsException | PasswordDoesNotMatchException e){
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
       }
    }
    @PreAuthorize("hasRole('ADMIN')")
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
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@Valid @RequestBody EmployeeDTO employeeDTO){
       try{
           return ResponseEntity.ok().body(employeeService.updateEmployeeById(employeeDTO));
       } catch (NoSuchElementException e){
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
       }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateEmployeePasswordById(@PathVariable Long id, @RequestBody PasswordChangeDTO passwordChangeDTO){
       try{
           employeeService.changeEmployeePassword(passwordChangeDTO, id);
           return ResponseEntity.ok().build();
       } catch(PasswordDoesNotMatchException e){
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
       }
    }
}
