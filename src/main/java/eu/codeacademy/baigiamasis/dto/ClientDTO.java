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
public class ClientDTO {
    private Long id;
    @NotNull(message = "Enter client name")
    private String name;
    @NotNull(message = "Enter client surname")
    private String surname;
    @NotNull(message = "Enter client username")
    private String username;
    @NotNull(message = "Enter client email")
    private String email;
    @NotNull(message = "Enter client phone")
    private Integer phone;
    private List<OrderDTO> orderList;
}
