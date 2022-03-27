package com.prueba.repositories;

import com.prueba.models.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("Usuario")
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    public Optional<Usuario> findByUsername(String username);
    
    public boolean existsByUsername(String username);
    
}
