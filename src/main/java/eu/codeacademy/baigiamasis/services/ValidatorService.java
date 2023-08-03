package eu.codeacademy.baigiamasis.services;

import eu.codeacademy.baigiamasis.dto.PasswordChangeDTO;
import eu.codeacademy.baigiamasis.exceptions.PasswordDoesNotMatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class ValidatorService {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    MessageSource messageSource;
    public boolean isPasswordValid(String existingPassword , PasswordChangeDTO passwordChangeDTO) throws PasswordDoesNotMatchException{
        if (!(passwordEncoder.matches(passwordChangeDTO.getOldPassword(), existingPassword))) {
            throw new PasswordDoesNotMatchException(messageSource.getMessage("incorrect.password", null, LocaleContextHolder.getLocale()));

        } else if (!(passwordChangeDTO.getNewPassword().equals(passwordChangeDTO.getRepeatNewPassword()))) {
            throw new PasswordDoesNotMatchException(messageSource.getMessage("new.password.does.not.match", null, LocaleContextHolder.getLocale()));
        }else{
            return true;
        }
    }
}
