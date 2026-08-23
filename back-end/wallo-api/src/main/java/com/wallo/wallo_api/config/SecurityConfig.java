package com.wallo.wallo_api.config;

import com.wallo.wallo_api.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.Customizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
import java.util.ArrayList;

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
                .cors(Customizer.withDefaults())

                // Define as regras de autorização por rota
                .authorizeHttpRequests(auth -> auth
                        // Rotas de autenticação são públicas (cadastro/login)
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers( "/error").permitAll()
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

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Origens permitidas: localhost (dev) e o domínio de produção (variável de ambiente)
        List<String> origins = new ArrayList<>(List.of(
                "http://localhost:5173",
                "http://127.0.0.1:5173"
        ));

        // Adiciona a URL de produção se a variável estiver definida
        String frontendUrl = System.getenv("FRONTEND_URL");
        if (frontendUrl != null && !frontendUrl.isBlank()) {
            origins.add(frontendUrl);
        }

        configuration.setAllowedOrigins(origins);
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
