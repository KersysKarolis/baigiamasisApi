package eu.codeacademy.baigiamasis.repositories;

import eu.codeacademy.baigiamasis.entities.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findAllByRole (Pageable pageable, String role);
}
