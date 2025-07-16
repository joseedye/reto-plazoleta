package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.infrastructure.security.AccesoDenegadoPersonalizadoHandler;
import com.pragma.powerup.infrastructure.security.JwtAuthenticationFilter;
import com.pragma.powerup.infrastructure.security.JwtProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    public final JwtProvider jwtProvider;

    public SecurityConfig(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           AccesoDenegadoPersonalizadoHandler accessDeniedHandler) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .antMatchers("/api/v1/login/**").permitAll()  // Login público
                        .antMatchers("/api/v1/usuario/cliente").permitAll()
                        .anyRequest().authenticated()                 // El resto necesita autenticación
                )
                .exceptionHandling(exception ->
                        exception.accessDeniedHandler(accessDeniedHandler)  // Aquí agregas tu handler personalizado
                )
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtProvider);
    }

}
