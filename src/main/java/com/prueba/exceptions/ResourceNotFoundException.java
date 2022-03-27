package com.prueba.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    
    private String nombreRecurso;
    private String nombreCampo;
    private int valor;

    public ResourceNotFoundException(String nombreRecurso, String nombreCampo, int valor) {
        super(String.format("%s no encontrado con %s : '%s'", nombreRecurso, nombreCampo, valor) );
        this.nombreRecurso = nombreRecurso;
        this.nombreCampo = nombreCampo;
        this.valor = valor;
    }

    public String getNombreRecurso() {
        return nombreRecurso;
    }

    public void setNombreRecurso(String nombreRecurso) {
        this.nombreRecurso = nombreRecurso;
    }

    public String getNombreCampo() {
        return nombreCampo;
    }

    public void setNombreCampo(String fieldName) {
        this.nombreCampo = fieldName;
    }

    public int getValor() {
        return valor;
    }

    public void setFieldValue(int valor) {
        this.valor = valor;
    }
    
    
    
}
