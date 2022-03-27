package com.prueba.controllers;

import com.prueba.dto.PacienteDTO;
import com.prueba.services.PacienteService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping()
    public ResponseEntity<Object> getPacientes(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "perPage", defaultValue = "5", required = false) int perPage,
            @RequestParam(value = "tipoFiltro", defaultValue = "", required = false) String tipoFiltro,
            @RequestParam(value = "valorFiltro", defaultValue = "", required = false) String valorFiltro,
            @RequestParam(value = "estado", defaultValue = "S", required = false) String estado) {
        
        Map<String, Object> response = new HashMap<String, Object>();
        Map<String, Object> data = pacienteService.listaPacientes(page, perPage,
                tipoFiltro, valorFiltro, estado);
        response.put("code", 200);
        response.put("success", true);
        response.put("message", "OK");
        response.put("data", data);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @GetMapping("/{idPaciente}")
    public ResponseEntity<Object> getPaciente(@PathVariable(name = "idPaciente") int idPaciente) {
        Map<String, Object> response = new HashMap<String, Object>();
        PacienteDTO pacienteDTO = pacienteService.obtenerPacientePorId(idPaciente);
        response.put("code", 200);
        response.put("success", true);
        response.put("message", "OK");
        response.put("data", pacienteDTO);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Object> savePaciente(@Valid @RequestBody PacienteDTO pacienteDTO) {
        Map<String, Object> response = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<>();

        int idPaciente = pacienteService.guardarPaciente(pacienteDTO);
        data.put("idPaciente", idPaciente);

        response.put("code", 200);
        response.put("success", true);
        response.put("message", "Paciente creado.");
        response.put("data", data);
        return new ResponseEntity<Object>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{idPaciente}")
    public ResponseEntity<Object> updatePaciente(@Valid @RequestBody PacienteDTO pacienteDTO, @PathVariable(name = "idPaciente") int idPaciente) {
        Map<String, Object> response = new HashMap<String, Object>();

        PacienteDTO _pacienteDTO = pacienteService.actualizarPaciente(pacienteDTO, idPaciente);
        response.put("code", 200);
        response.put("success", true);
        response.put("message", "Paciente actualizado.");
        response.put("data", _pacienteDTO);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
        
    }

    @DeleteMapping("/{idPaciente}")
    public ResponseEntity<Object> deletePaciente(@PathVariable(name = "idPaciente") int idPaciente) {
        Map<String, Object> response = new HashMap<String, Object>();

        PacienteDTO _pacienteDTO = pacienteService.eliminarPaciente(idPaciente);

        response.put("code", 200);
        response.put("success", true);
        response.put("message", "Paciente eliminado.");
        response.put("data", _pacienteDTO);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }
}
