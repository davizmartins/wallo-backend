package com.wallo.wallo_api.security;

import com.wallo.wallo_api.model.User;
import com.wallo.wallo_api.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Carrega usuários a partir do banco para o processo de autenticação do Spring Security.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Chamado pelo Spring Security durante a autenticação.
     * O parâmetro "username" corresponde ao email no nosso modelo.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));

        return new UserDetailsImpl(user);
    }
}