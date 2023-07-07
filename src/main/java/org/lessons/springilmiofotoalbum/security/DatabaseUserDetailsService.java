package org.lessons.springilmiofotoalbum.security;

import org.lessons.springilmiofotoalbum.model.User;
import org.lessons.springilmiofotoalbum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // devo recuperare uno User da DB a partire dalla stringa username
        Optional<User> result = userRepository.findByEmail(username);
        if (result.isPresent()) {
            //devo costruire uno UserDetails a partire da quello User
            return new DatabaseUserDetails(result.get());
        } else {
            // se non trovo user con quella email tiro un' eccezione
            throw new UsernameNotFoundException("User with email " + username + " not found");
        }

    }
}
