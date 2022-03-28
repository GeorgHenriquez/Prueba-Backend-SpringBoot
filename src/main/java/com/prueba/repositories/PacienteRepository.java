package com.prueba.repositories;

import com.prueba.models.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("PacienteRepository")
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
    
    public Paciente findByNumeroIdentificacion(String numeroIdentificacion);
    
}
