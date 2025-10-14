package com.projeto.familiaeduca.infrastructure.security;

import com.projeto.familiaeduca.domain.models.Diretor;
import com.projeto.familiaeduca.domain.models.Professor;
import com.projeto.familiaeduca.domain.models.Responsavel;
import com.projeto.familiaeduca.domain.models.Usuario;
import com.projeto.familiaeduca.infrastructure.repository.UsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + email));
        Collection<GrantedAuthority> authorities = getAuthorities(usuario);

        return new org.springframework.security.core.userdetails.User(
                usuario.getEmail(),
                usuario.getSenha(),
                authorities
        );
    }

    private Collection<GrantedAuthority> getAuthorities(Usuario usuario) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        if(usuario instanceof Diretor) {
            authorities.add(new SimpleGrantedAuthority("ROLE_DIRETOR"));
        } else if(usuario instanceof Professor) {
            authorities.add(new SimpleGrantedAuthority("ROLE_PROFESSOR"));
        } else if(usuario instanceof Responsavel) {
            authorities.add(new SimpleGrantedAuthority("ROLE_RESPONSAVEL"));
        }

        return authorities;
    }
}
