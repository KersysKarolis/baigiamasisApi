package eu.codeacademy.baigiamasis.converters;

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
            employee.setUsername(employeeDTO.getUsername());
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
            employeeDTO.setUsername(employee.getUsername());
            employeeDTO.setOrderDTOList(OrderConverter.convertOrdersToOrdersDTO(employee.getOrderList()));
        }
        return employeeDTO;
    }
    public static List<EmployeeDTO> convertEmployeesToEmployeesDTO(List<Employee> employeeList){
        List<EmployeeDTO> employeeDTOS = null;
        if(employeeList != null && !employeeList.isEmpty()){
            employeeDTOS = new ArrayList<>();
            for(Employee e: employeeList){
                employeeDTOS.add(convertEmployeeToEmployeeDTO(e));
            }
        }
        return employeeDTOS; 
    }
}
