package eu.codeacademy.baigiamasis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class OrderDTO {
    private Long id;
    @NotNull(message= "Order number field must be filled")
    private Integer orderNumber;
    @NotNull(message = "Client id must be provided")
    private Long clientDtoId;
    @NotNull(message = "Employee id must be provided")
    private Long employeeDtoId;
}
