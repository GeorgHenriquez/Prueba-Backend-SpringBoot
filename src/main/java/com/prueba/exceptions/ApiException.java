package com.prueba.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ApiException extends RuntimeException {

    private HttpStatus estado;
    private String mensaje;

    public ApiException(HttpStatus estado, String mensaje) {
        super();
        this.estado = estado;
        this.mensaje = mensaje;
    }

    public HttpStatus getEstado() {
        return estado;
    }

    public void setEstado(HttpStatus estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
