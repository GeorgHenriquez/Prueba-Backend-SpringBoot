package com.prueba.services.impl;

import com.prueba.dto.PacienteDTO;
import com.prueba.exceptions.ApiException;
import com.prueba.exceptions.ResourceNotFoundException;
import com.prueba.models.Paciente;
import com.prueba.repositories.PacienteRepository;
import com.prueba.repositories.TipoIdentificacionRepository;
import com.prueba.services.PacienteService;
import com.prueba.utils.Validator;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.modelmapper.ModelMapper;

@Service("PacienteService")
public class PacienteServiceImpl implements PacienteService {
    
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PacienteRepository pacienteRepository;
    
    @Autowired
    private TipoIdentificacionRepository tipoIdentificacionRepository;
    
    @PersistenceContext
    EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }
    
    @SuppressWarnings("unchecked")
    public Map<String, Object> listaPacientes(int page, int perPage,
            String tipoFiltro, String valorFiltro, String estado) {

        if (Validator.isEmpty(page) || Validator.isEmpty(perPage))
            throw new ApiException("Los campos page y perPage son obligatorio.");
        else if (page < 1 || perPage < 1)
            throw new ApiException("El campo page o perPage no es válido.");

        String[] strFiltros = {"numero_identificacion", "nombre_completo", "email"};
        if (!Validator.isEmpty(tipoFiltro) && !Arrays.stream(strFiltros).anyMatch(tipoFiltro::equalsIgnoreCase))
            throw new ApiException("El campo tipo filtro no es válido.");
        if (!Validator.isEmpty(tipoFiltro) && Validator.isEmpty(valorFiltro))
            throw new ApiException("El campo valor filtro es obligatorio.");

        String[] strEstados = {"S", "N"};
        if (!Validator.isEmpty(estado) && !Arrays.stream(strEstados).anyMatch(estado::equalsIgnoreCase)) {
            throw new ApiException("El campo estado no es válido.");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT pa.*, ti.nombre_tipo_identificacion");
        sb.append(" FROM mgm_pacientes pa, daf_tipos_identificacion ti");
        sb.append(" WHERE pa.codigo_tipo_identificacion = ti.codigo_tipo_identificacion"
                + " AND pa.estado = :estado");

        if (tipoFiltro.equals("numero_identificacion"))
            sb.append(" AND pa.numero_identificacion LIKE :numeroIdentificacion");
        else if (tipoFiltro.equals("nombre_completo"))
            sb.append(" AND pa.nombre_completo LIKE :nombreCompleto");
        else if (tipoFiltro.equals("email"))
            sb.append(" AND pa.email LIKE :email");

        Query query = em.createNativeQuery(sb.toString(), Paciente.class);
        query.setFirstResult((page * perPage) - perPage);
        query.setMaxResults(perPage);
        query.setParameter("estado", estado);

        if (tipoFiltro.equalsIgnoreCase("numero_identificacion"))
            query.setParameter("numeroIdentificacion", "%"+valorFiltro+"%");
        else if (tipoFiltro.equalsIgnoreCase("nombre_completo"))
            query.setParameter("nombreCompleto", "%"+valorFiltro+"%");
        else if (tipoFiltro.equalsIgnoreCase("email"))
            query.setParameter("email", "%"+valorFiltro+"%");

        List<Paciente> resultado = (List<Paciente>) query.getResultList();
        List<PacienteDTO> pacientes = resultado.stream().map(paciente -> entityToDTO(paciente)).collect(Collectors.toList());
        
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("rows", pacientes);
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
        UserDetails usuario = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        String numeroIdentificacion = pacienteDTO.getNumeroIdentificacion();
        int codigoTipoId = pacienteDTO.getCodigoTipoIdentificacion();
        
        if (Objects.isNull(numeroIdentificacion))
            throw new ApiException("Campo número de identificación es requerido.");
        if (codigoTipoId == 0)
            throw new ApiException("Campo código de tipo de identificación es requerido.");
        
        tipoIdentificacionRepository.findById(codigoTipoId).
                orElseThrow(() -> new ResourceNotFoundException("Tipo Identificación", "id", codigoTipoId));
        
        Paciente pacientePorNumeroId = pacienteRepository.findByNumeroIdentificacion(pacienteDTO.getNumeroIdentificacion());
        if (!Objects.isNull(pacientePorNumeroId))
            throw new ApiException("Ya existe un usuario con este número de identificación.");
        Paciente paciente = DtoToEntity(pacienteDTO);
        paciente.setNombreCompleto(pacienteDTO.armarNombreCompleto());
        paciente.setUsuarioIngreso(usuario.getUsername());
        Paciente nuevoPaciente = pacienteRepository.save(paciente);
        return nuevoPaciente.getIdPaciente();
    }
    
    @Override
    public PacienteDTO actualizarPaciente(PacienteDTO pacienteDTO, int idPaciente) {
        UserDetails usuario = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        int codigoIdentificacion = pacienteDTO.getCodigoTipoIdentificacion();
        String numeroIdentificacion = pacienteDTO.getNumeroIdentificacion();
        if (codigoIdentificacion != 0 || !Objects.isNull(numeroIdentificacion)) {
            throw new ApiException("Los campos de código y número de identificación no se pueden modificar.");
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
        paciente.setFechaModificacion(new Timestamp(System.currentTimeMillis()));
        paciente.setUsuarioModificacion(usuario.getUsername());
        
        Paciente pacienteActualizado = pacienteRepository.save(paciente);
        return entityToDTO(pacienteActualizado);
        
    }
    
    @Override
    public PacienteDTO eliminarPaciente(int idPaciente) {
        UserDetails usuario = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        Paciente paciente = pacienteRepository.findById(idPaciente).
                orElseThrow(() -> new ResourceNotFoundException("Paciente", "id", idPaciente));
        paciente.setEstado("N");
        paciente.setFechaModificacion(new Timestamp(System.currentTimeMillis()));
        paciente.setUsuarioModificacion(usuario.getUsername());
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
