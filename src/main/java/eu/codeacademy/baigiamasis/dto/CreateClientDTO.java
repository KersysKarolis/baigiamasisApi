package eu.codeacademy.baigiamasis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateClientDTO extends ClientDTO{
    private String firstPassword;
    private String secondPassword;
}
