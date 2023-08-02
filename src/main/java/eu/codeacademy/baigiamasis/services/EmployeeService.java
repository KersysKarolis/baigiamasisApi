package eu.codeacademy.baigiamasis.services;

import eu.codeacademy.baigiamasis.converters.EmployeeConverter;
import eu.codeacademy.baigiamasis.converters.OrderConverter;
import eu.codeacademy.baigiamasis.dto.PasswordChangeDTO;
import eu.codeacademy.baigiamasis.dto.CreateEmployeeDTO;
import eu.codeacademy.baigiamasis.dto.EmployeeDTO;
import eu.codeacademy.baigiamasis.entities.Employee;
import eu.codeacademy.baigiamasis.exceptions.ObjectAlreadyExistsException;
import eu.codeacademy.baigiamasis.exceptions.PasswordDoesNotMatchException;
import eu.codeacademy.baigiamasis.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    MessageSource messageSource;

    public List<EmployeeDTO> getAllEmployeesByRole(Pageable pageable, String role){
        Page<Employee> employeePage = null;
        if(role != null){
            employeePage = employeeRepository.findAllByRole(pageable, role);
        }else {
            employeePage = employeeRepository.findAll(pageable);
        }
        return EmployeeConverter.convertEmployeesToEmployeesDTO(employeePage);
    }
    public Employee getEmployeeById(Long id){
        return employeeRepository.findById(id).get();
    }
    public EmployeeDTO addNewEmployee(CreateEmployeeDTO employeeDTO)throws ObjectAlreadyExistsException, PasswordDoesNotMatchException{
        Employee employee = null;
        if(employeeRepository.existsEmployeeByUsername(employeeDTO.getUsername())) {
            throw new ObjectAlreadyExistsException(messageSource.getMessage("username.already.exists", null, LocaleContextHolder.getLocale()));
        }else if(!(employeeDTO.getFirstPassword().equals(employeeDTO.getSecondPassword()))){
            throw new PasswordDoesNotMatchException(messageSource.getMessage("password.does.not.match", null, LocaleContextHolder.getLocale()));
        }else{
            employee = EmployeeConverter.convertCreateEmployeeDTOToEmployee(employeeDTO);
            employee.setPassword(passwordEncoder.encode(employee.getPassword()));
            employeeRepository.saveAndFlush(employee);
            return EmployeeConverter.convertEmployeeToEmployeeDTO(employee);
        }
    }
    public EmployeeDTO updateEmployeeById(EmployeeDTO employeeDTO){
        Employee employee = getEmployeeById(employeeDTO.getId());
        if(employee!= null) {
            employee.setId(employeeDTO.getId());
            employee.setName(employeeDTO.getName());
            employee.setSurname(employeeDTO.getSurname());
            employee.setEmail(employeeDTO.getEmail());
            if (employeeDTO.getOrderDTOList() != null) {
                employee.setOrderList(OrderConverter.convertOrdersDTOToOrders(employeeDTO.getOrderDTOList()));
            }
            employeeRepository.saveAndFlush(employee);
            return EmployeeConverter.convertEmployeeToEmployeeDTO(employee);
        } else {
            throw new NoSuchElementException(messageSource.getMessage("employee.by.id.not.found", null, LocaleContextHolder.getLocale()));
        }
    }
    public void deleteEmployeeById(Long id){
        employeeRepository.deleteById(id);
    }
    public void changeEmployeePassword(PasswordChangeDTO password, Long id) throws PasswordDoesNotMatchException{
       Employee employee = getEmployeeById(id);
        if (!(passwordEncoder.matches(password.getOldPassword(), employee.getPassword()))) {
            throw new PasswordDoesNotMatchException(messageSource.getMessage("incorrect.password", null, LocaleContextHolder.getLocale()));

        } else if (password.getNewPassword().equals(password.getRepeatNewPassword())) {
            throw new PasswordDoesNotMatchException(messageSource.getMessage("new.password.does.not.match", null, LocaleContextHolder.getLocale()));
        } else {
            employee.setPassword(passwordEncoder.encode(password.getNewPassword()));
            employeeRepository.saveAndFlush(employee);
        }

    }
}
