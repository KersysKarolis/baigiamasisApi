package eu.codeacademy.baigiamasis.services;

import eu.codeacademy.baigiamasis.entities.Client;
import eu.codeacademy.baigiamasis.enumerators.Role;
import eu.codeacademy.baigiamasis.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientRepository.findClientByUsername(username);
        if(client != null){
            return new User(client.getUsername(), client.getPassword(), buildSimpleGrantedAuthorities(client.getRole()));
        } else{
            throw new UsernameNotFoundException("Client with given username not found: " + username);
        }
    }
    private static List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(Role role) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));

        return authorities;
    }
}
