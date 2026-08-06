package com.wallo.wallo_api.security;

import com.wallo.wallo_api.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Adapta a entidade User para o contrato UserDetails do Spring Security.
 * Mantém a entidade de domínio livre de dependências do framework de segurança.
 */
public class UserDetailsImpl implements UserDetails {

    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    /** Expõe a entidade original para quem precisar (ex.: pegar o id do usuário logado). */
    public User getUser() {
        return user;
    }

    /** Papéis/permissões do usuário, no formato esperado pelo Spring Security. */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole().name()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /** O Spring Security trata o "username" como o identificador de login: aqui, o email. */
    @Override
    public String getUsername() {
        return user.getEmail();
    }
}
