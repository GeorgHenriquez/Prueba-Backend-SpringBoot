package com.prueba.exceptions;

import com.prueba.dto.DetalleError;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<DetalleError> resourceNotFoundExceptionHandler(ResourceNotFoundException rnfe, WebRequest webRequest) {
        DetalleError detalleError = new DetalleError(404, false, rnfe.getMessage(), new ArrayList<>());
        return new ResponseEntity<>(detalleError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<DetalleError> apiExceptionHandler(ApiException ae, WebRequest webRequest) {
        DetalleError detalleError = new DetalleError(400, false, ae.getMensaje(), new ArrayList<>());
        return new ResponseEntity<>(detalleError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DetalleError> exceptionHandler(Exception e, WebRequest webRequest) {
        List<String> errorList = new ArrayList<String>();
        errorList.add(e.getMessage());
        DetalleError detalleError = new DetalleError(500, false, "Ha ocurrido un error inesperado.", errorList);
        return new ResponseEntity<>(detalleError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errorList = new ArrayList<String>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            errorList.add(((FieldError) error).getDefaultMessage());
        });
        DetalleError detalleError = new DetalleError(400, false, errorList.get(0), new ArrayList<>());
        return new ResponseEntity<>(detalleError, HttpStatus.BAD_REQUEST);
    }

}
