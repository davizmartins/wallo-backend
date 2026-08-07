package com.wallo.wallo_api.service;

import com.wallo.wallo_api.dto.auth.AuthResponse;
import com.wallo.wallo_api.dto.auth.LoginRequest;
import com.wallo.wallo_api.dto.auth.RegisterRequest;
import com.wallo.wallo_api.model.User;
import com.wallo.wallo_api.model.UserRole;
import com.wallo.wallo_api.repository.UserRepository;
import com.wallo.wallo_api.security.JwtService;
import com.wallo.wallo_api.security.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Regras de negócio de autenticação: cadastro e login de usuários.
 */
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Registra um novo usuário e já devolve um token para login automático.
     */
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password())); // criptografa antes de salvar
        user.setRole(UserRole.ROLE_USER);

        userRepository.save(user);

        String token = jwtService.generateToken(new UserDetailsImpl(user));
        return new AuthResponse(token);
    }

    /**
     * Autentica um usuário existente e devolve um token válido.
     */
    public AuthResponse login(LoginRequest request) {
        // Delega ao Spring Security a verificação de email + senha
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);
        return new AuthResponse(token);
    }
}
