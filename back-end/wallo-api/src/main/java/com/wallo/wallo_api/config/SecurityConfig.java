package com.wallo.wallo_api.config;

import com.wallo.wallo_api.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuração central do Spring Security.
 * Define quais rotas são públicas, a política de sessão (stateless para JWT)
 * e o algoritmo de criptografia de senhas.
 */
@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Codificador de senhas usando BCrypt.
     * Toda senha é criptografada antes de ser salva e comparada por hash no login.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /** Expõe o AuthenticationManager para ser usado no processo de login. */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
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
                        // Documentação Swagger/OpenAPI pública
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                        // Qualquer outra rota exige usuário autenticado
                        .anyRequest().authenticated()
                )

                // API stateless: não guarda sessão em memória, cada request traz o token
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // Registra o filtro JWT antes do filtro padrão de usuário/senha
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}