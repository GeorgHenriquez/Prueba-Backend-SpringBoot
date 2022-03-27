package com.prueba.services.impl;

import com.prueba.dto.PacienteDTO;
import com.prueba.exceptions.ApiException;
import com.prueba.exceptions.ResourceNotFoundException;
import com.prueba.models.Paciente;
import com.prueba.repositories.PacienteRepository;
import com.prueba.repositories.TipoIdentificacionRepository;
import com.prueba.services.PacienteService;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service("PacienteService")
public class PacienteServiceImpl implements PacienteService {
    
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PacienteRepository pacienteRepository;
    
    @Autowired
    private TipoIdentificacionRepository tipoIdentificacionRepository;

    @Override
    public Map<String, Object> listaPacientes(int page, int perPage, 
            String tipoFiltro, String valorFiltro, String estado) {
        Map<String, Object> data = new HashMap<String, Object>();
        Pageable pageable = PageRequest.of(page, perPage, Sort.by("idPaciente"));
        Page<Paciente> pacientes = pacienteRepository.findAll(pageable);

        List<Paciente> listaPacientes = pacientes.getContent();
        List<PacienteDTO> _listaPacientes = listaPacientes.stream().map(paciente -> entityToDTO(paciente)).collect(Collectors.toList());

        data.put("rows", _listaPacientes);
        data.put("totalRows", pacienteRepository.count());
        return data;
    }

    @Override
    public PacienteDTO obtenerPacientePorId(int idPaciente) {
        Paciente paciente = pacienteRepository.findById(idPaciente).
                orElseThrow(() -> new ResourceNotFoundException("Paciente", "id", idPaciente));
        return entityToDTO(paciente);
    }

    @Override
    public int guardarPaciente(PacienteDTO pacienteDTO) {
        tipoIdentificacionRepository.findById(pacienteDTO.getCodigoTipoIdentificacion()).
                orElseThrow(() -> new ResourceNotFoundException("Tipo Identificación", "id", pacienteDTO.getCodigoTipoIdentificacion()));
        
        Paciente paciente = DtoToEntity(pacienteDTO);
        paciente.setNombreCompleto(pacienteDTO.armarNombreCompleto());
        Paciente nuevoPaciente = pacienteRepository.save(paciente);
        return nuevoPaciente.getIdPaciente();
    }
    
    @Override
    public PacienteDTO actualizarPaciente(PacienteDTO pacienteDTO, int idPaciente) {
        int codigoIdentificacion = pacienteDTO.getCodigoTipoIdentificacion();
        String numeroIdentificacion = pacienteDTO.getNumeroIdentificacion();
        if (codigoIdentificacion != 0 || !Objects.isNull(numeroIdentificacion)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Los campos de código y número de identificación no se pueden modificar.");
        }
        System.out.print("ADONDE");
        Paciente paciente = pacienteRepository.findById(idPaciente).
                orElseThrow(() -> new ResourceNotFoundException("Paciente", "id", idPaciente));
        
        paciente.setPrimerNombre(pacienteDTO.getPrimerNombre());
        paciente.setSegundoNombre(pacienteDTO.getSegundoNombre());
        paciente.setPrimerApellido(pacienteDTO.getPrimerApellido());
        paciente.setSegundoApellido(pacienteDTO.getSegundoApellido());
        paciente.setNombreCompleto(pacienteDTO.armarNombreCompleto());
        paciente.setEmail(pacienteDTO.getEmail());
        paciente.setEstado(pacienteDTO.getEstado());
        paciente.setFechaModificacion(new Date(System.currentTimeMillis()));
        paciente.setUsuarioModificacion("1");
        
        Paciente pacienteActualizado = pacienteRepository.save(paciente);
        return entityToDTO(pacienteActualizado);
        
    }
    
    @Override
    public PacienteDTO eliminarPaciente(int idPaciente) {
        Paciente paciente = pacienteRepository.findById(idPaciente).
                orElseThrow(() -> new ResourceNotFoundException("Paciente", "id", idPaciente));
        paciente.setEstado("N");
        paciente.setFechaModificacion(new Date(System.currentTimeMillis()));
        paciente.setUsuarioModificacion("1");
        Paciente pacienteActualizado = pacienteRepository.save(paciente);
        return entityToDTO(pacienteActualizado);
    }
    
    //convertir entidad a DTO
    private PacienteDTO entityToDTO(Paciente paciente) {
        PacienteDTO pacienteDTO = modelMapper.map(paciente, PacienteDTO.class);
        return pacienteDTO;
    }

    //convertir DTO a Entidad
    private Paciente DtoToEntity(PacienteDTO pacienteDTO) {
        Paciente paciente = modelMapper.map(pacienteDTO, Paciente.class);
        return paciente;
    }
    
}
