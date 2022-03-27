package com.prueba.repositories;

import com.prueba.models.TipoIdentificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("TipoIdentificacionRepository")
public interface TipoIdentificacionRepository extends JpaRepository<TipoIdentificacion, Integer> {
    
}
