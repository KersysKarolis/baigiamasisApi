package eu.codeacademy.baigiamasis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeDTO {
    private Long id;

    private String name;
    private String surname;
    private String email;
    private List<OrderDTO> orderDTOList;
}
