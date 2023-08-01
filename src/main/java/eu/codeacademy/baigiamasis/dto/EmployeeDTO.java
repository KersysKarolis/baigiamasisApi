package eu.codeacademy.baigiamasis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeDTO {
    private Long id;
    @NotNull(message = "Enter employee name")
    private String name;
    @NotNull(message = "Enter employee surname")
    private String surname;
    @NotNull(message = "Enter employee email")
    private String email;
    private List<OrderDTO> orderDTOList;
}
