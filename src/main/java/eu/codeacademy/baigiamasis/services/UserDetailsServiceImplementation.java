package eu.codeacademy.baigiamasis.services;

import eu.codeacademy.baigiamasis.entities.Client;
import eu.codeacademy.baigiamasis.entities.Employee;
import eu.codeacademy.baigiamasis.enumerators.Role;
import eu.codeacademy.baigiamasis.repositories.ClientRepository;
import eu.codeacademy.baigiamasis.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class UserDetailsServiceImplementation implements UserDetailsService {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    MessageSource messageSource;
    @Override
    public UserDetails loadUserByUsername(String username) {
        Client client = clientRepository.findClientByUsername(username);

        if(client != null){
            return new User(client.getUsername(), client.getPassword(), buildSimpleGrantedAuthorities(client.getRole()));
        } else {
            Employee employee = employeeRepository.findByUsername(username);
            if(employee != null) {
                return new User(employee.getUsername(), employee.getPassword(), buildSimpleGrantedAuthorities(employee.getRole()));
            }
        }
        throw new UsernameNotFoundException(messageSource.getMessage("username.not.found", null, LocaleContextHolder.getLocale()));
    }
    private static List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(Role role) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));

        return authorities;
    }
}
