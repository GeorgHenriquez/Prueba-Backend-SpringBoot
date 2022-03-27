package com.prueba.security;

import com.prueba.models.Usuario;
import com.prueba.repositories.UsuarioRepository;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    private final Set<GrantedAuthority> authorities = new HashSet<>();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado " + username));

        return new User(usuario.getUsername(), usuario.getUserPassword(), getAuthorities());
    }

    private Collection<? extends GrantedAuthority> getAuthorities() {
        authorities.add(new SimpleGrantedAuthority("USER"));
        return authorities;
    }

}
