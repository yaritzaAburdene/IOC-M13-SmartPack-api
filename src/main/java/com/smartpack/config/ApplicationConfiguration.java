package com.smartpack.config;

import com.smartpack.repositories.UsuariRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Classe ApplicationConfiguration
 */
@Configuration
public class ApplicationConfiguration {
    private final UsuariRepository userRepository;

    /**
     * Constructor ApplicationConfiguration
     * 
     * @param userRepository UsuariRepository
     */
    public ApplicationConfiguration(UsuariRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Obté l'usuari per l'autenticació
     * 
     * @return
     */
    @Bean
    UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuari no trovat"));
    }

    /**
     * Crea una nova encriptació
     * 
     * @return BCryptPasswordEncoder
     */
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Obté a autenticació Manager
     * 
     * @param config AuthenticationConfiguration
     * @return AuthenticationManager
     * @throws Exception Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Obté el provider que fa la validació amb les credencials amb la base de dades
     * 
     * @return DaoAuthenticationProvider
     */
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
}
