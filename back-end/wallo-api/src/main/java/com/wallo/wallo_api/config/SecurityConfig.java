package com.wallo.wallo_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuração central do Spring Security.
 * Define quais rotas são públicas, a política de sessão (stateless para JWT)
 * e o algoritmo de criptografia de senhas.
 */
@Configuration
public class SecurityConfig {

    /**
     * Codificador de senhas usando BCrypt.
     * Toda senha é criptografada antes de ser salva e comparada por hash no login.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Cadeia de filtros de segurança: define o comportamento das requisições.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desabilita CSRF: não se aplica a APIs REST stateless com token
                .csrf(AbstractHttpConfigurer::disable)

                // Define as regras de autorização por rota
                .authorizeHttpRequests(auth -> auth
                        // Rotas de autenticação são públicas (cadastro/login)
                        .requestMatchers("/auth/**").permitAll()
                        // Qualquer outra rota exige usuário autenticado
                        .anyRequest().authenticated()
                )

                // API stateless: não guarda sessão em memória, cada request traz o token
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }
}