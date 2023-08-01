package eu.codeacademy.baigiamasis.services;

import eu.codeacademy.baigiamasis.converters.EmployeeConverter;
import eu.codeacademy.baigiamasis.converters.OrderConverter;
import eu.codeacademy.baigiamasis.dto.CreateEmployeeDTO;
import eu.codeacademy.baigiamasis.dto.EmployeeDTO;
import eu.codeacademy.baigiamasis.entities.Employee;
import eu.codeacademy.baigiamasis.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public List<EmployeeDTO> getAllEmployeesByRole(Pageable pageable, String role){
        Page<Employee> employeePage = null;
        if(role != null){
            employeePage = employeeRepository.findAllByRole(pageable, role);
        }else {
            employeePage = employeeRepository.findAll(pageable);
        }
        return EmployeeConverter.convertEmployeesToEmployeesDTO(employeePage);
    }
    public EmployeeDTO getEmployeeById(Long id){
        return EmployeeConverter.convertEmployeeToEmployeeDTO(employeeRepository.findById(id).get());
    }
    public EmployeeDTO addNewEmployee(CreateEmployeeDTO employeeDTO){
        Employee employee = null;
        if(employeeDTO.getFirstPassword().equals(employeeDTO.getSecondPassword())){
            employee = EmployeeConverter.convertCreateEmployeeDTOToEmployee(employeeDTO);
            employee.setPassword(passwordEncoder.encode(employee.getPassword()));
            employeeRepository.saveAndFlush(employee);
            return EmployeeConverter.convertEmployeeToEmployeeDTO(employee);
        } else{
            throw new InputMismatchException("Passwords must match");
        }

    }
    public EmployeeDTO updateEmployeeById(EmployeeDTO employeeDTO){
        Employee employee = EmployeeConverter.convertEmployeeDTOToEmployee(getEmployeeById(employeeDTO.getId()));
        employee.setId(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        employee.setSurname(employeeDTO.getSurname());
        employee.setEmail(employeeDTO.getEmail());
        if(employeeDTO.getOrderDTOList() != null) {
            employee.setOrderList(OrderConverter.convertOrdersDTOToOrders(employeeDTO.getOrderDTOList()));
        }
        employeeRepository.saveAndFlush(employee);
        return EmployeeConverter.convertEmployeeToEmployeeDTO(employee);
    }
    public void deleteEmployeeById(Long id){
        employeeRepository.deleteById(id);
    }
}
