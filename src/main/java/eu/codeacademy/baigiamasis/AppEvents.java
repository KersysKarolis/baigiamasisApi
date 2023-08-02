package eu.codeacademy.baigiamasis;

import eu.codeacademy.baigiamasis.entities.Employee;
import eu.codeacademy.baigiamasis.enumerators.Role;
import eu.codeacademy.baigiamasis.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AppEvents {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @EventListener(ApplicationReadyEvent.class)
    public void startApp(){
        if(employeeRepository.findAll().isEmpty()){
            Employee employee = new Employee();
            employee.setUsername("admin");
            employee.setPassword(passwordEncoder.encode("admin"));
            employee.setRole(Role.ADMIN);
            employeeRepository.saveAndFlush(employee);
        }

    }
}
