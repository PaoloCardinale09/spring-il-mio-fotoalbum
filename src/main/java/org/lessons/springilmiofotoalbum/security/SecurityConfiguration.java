package org.lessons.springilmiofotoalbum.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    // questo è lo userDetailsService
    @Bean
    DatabaseUserDetailsService userDetailsService() {
        return new DatabaseUserDetailsService();
    }

    // questo è password encoder (che deduce l'algoritmo di encoding da una stringa nella password stessa)
    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        // creo authenticationProvider
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // setto il passwordEncoder
        provider.setPasswordEncoder(passwordEncoder());
        // setto lo UserDetailsService
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // definisco la catena di filtri
        http.authorizeHttpRequests()
                .requestMatchers("/photos").permitAll()

                .requestMatchers("/categories").hasAuthority("ADMIN")
                .requestMatchers("/photos/edit/**").hasAuthority("ADMIN")
                .requestMatchers("/photos/edit/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/photos/**").hasAuthority("ADMIN")
                .requestMatchers("/photos/**").hasAnyAuthority("ADMIN", "USER")
//                 chiedo di essere loggato come ADMIN  per tutti i metodi POST (i metodi post hanno potere di modify)
                .requestMatchers("/**").permitAll()
                .and().formLogin()
                .and().logout();
        // disabilitiamo csrf per invocare postman
        http.csrf().disable();
        return http.build();

    }
}
