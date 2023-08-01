package eu.codeacademy.baigiamasis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClientPasswordChangeDTO {
   @NotNull(message = "Enter old password")
    private String oldPassword;
    @NotNull(message = "Enter new password")
    private String newPassword;
    @NotNull(message = "Repeat new password")
    private String repeatNewPassword;
}
