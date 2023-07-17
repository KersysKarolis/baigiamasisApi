package eu.codeacademy.baigiamasis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class OrderDTO {
    private Long id;
    private Integer orderNumber;
    private UserDTO userDTO;
    private EmployeeDTO employeeDTO;
}
