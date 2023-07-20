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
public class CreateEmployeeDTO extends EmployeeDTO{
    @NotNull(message = "Name is mandatory")
    private String username;
    @NotNull(message = "Provide password")
    private String firstPassword;
    @NotNull(message = "Repeat password")
    private String secondPassword;
}
