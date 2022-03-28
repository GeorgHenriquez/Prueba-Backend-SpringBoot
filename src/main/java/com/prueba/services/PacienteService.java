package com.prueba.services;

import com.prueba.dto.PacienteDTO;

import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public interface PacienteService {
    public int guardarPaciente(PacienteDTO pacienteDTO);
    
    public Map<String, Object> listaPacientes(int page, int perPage, 
                String tipoFiltro, String valorFiltro, String estado);
    
    public PacienteDTO obtenerPacientePorId(int idPaciente);
    
    public PacienteDTO actualizarPaciente(PacienteDTO pacienteDTO, int idPaciente);
    
    public PacienteDTO eliminarPaciente(int idPaciente);
}
