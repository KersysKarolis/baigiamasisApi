package eu.codeacademy.baigiamasis.converters;

import eu.codeacademy.baigiamasis.dto.CreateEmployeeDTO;
import eu.codeacademy.baigiamasis.dto.EmployeeDTO;
import eu.codeacademy.baigiamasis.entities.Employee;

import java.util.ArrayList;
import java.util.List;

public abstract class EmployeeConverter {
    public static Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = null;
        if (employeeDTO != null) {
            employee = new Employee();
            employee.setId(employeeDTO.getId());
            employee.setEmail(employeeDTO.getEmail());
            employee.setName(employeeDTO.getName());
            employee.setSurname(employeeDTO.getSurname());
            employee.setOrderList(OrderConverter.convertOrdersDTOToOrders(employeeDTO.getOrderDTOList()));
        }
        return employee;
    }

    public static EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = null;
        if (employee != null) {
            employeeDTO = new EmployeeDTO();
            employeeDTO.setId(employee.getId());
            employeeDTO.setName(employee.getName());
            employeeDTO.setEmail(employee.getEmail());
            employeeDTO.setSurname(employee.getSurname());
            employeeDTO.setOrderDTOList(OrderConverter.convertOrdersToOrdersDTO(employee.getOrderList()));
        }
        return employeeDTO;
    }
    public static List<EmployeeDTO> convertEmployeesToEmployeesDTO(Iterable<Employee> employeeList){
        List<EmployeeDTO> employeeDTOS = null;
        if(employeeList != null){
            employeeDTOS = new ArrayList<>();
            for(Employee e: employeeList){
                employeeDTOS.add(convertEmployeeToEmployeeDTO(e));
            }
        }
        return employeeDTOS;
    }
    public static Employee convertCreateEmployeeDTOToEmployee(CreateEmployeeDTO employeeDTO){
        Employee employee = null;
        if(employeeDTO != null){
            employee = new Employee();
            employee.setName(employeeDTO.getName());
            employee.setSurname(employeeDTO.getSurname());
            employee.setId(employeeDTO.getId());
            employee.setEmail(employeeDTO.getEmail());
            employee.setUsername(employeeDTO.getUsername());
            employee.setPassword(employeeDTO.getFirstPassword());
            employee.setOrderList(OrderConverter.convertOrdersDTOToOrders(employeeDTO.getOrderDTOList()));
        }
        return employee;
    }
}
