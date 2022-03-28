package com.prueba.dto;

import com.prueba.models.TipoIdentificacion;
import java.sql.Timestamp;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PacienteDTO {

    private int idPaciente;
    
    private int codigoTipoIdentificacion;
    
    private String numeroIdentificacion;

    @NotEmpty(message = "Campo nombre no puede ser vacio.")
    private String primerNombre;

    private String segundoNombre;

    @NotEmpty(message = "Campo apellido no puede ser vacio.")
    private String primerApellido;

    private String segundoApellido;

    private String nombreCompleto;

    @NotEmpty(message = "Campo email no puede ser vacio.")
    @Email(message = "Campo email no válido.")
    private String email;

    @NotEmpty(message = "Campo estado puede ser vacio.")
    private String estado;
 
    private Timestamp fechaIngreso;

    private String usuarioIngreso;

    private Timestamp fechaModificacion;

    private String usuarioModificacion;
    
    private TipoIdentificacion tipoIdentificacion; 

    public PacienteDTO() {
        this.fechaIngreso = new Timestamp(System.currentTimeMillis());
        this.nombreCompleto = this.armarNombreCompleto();
    }
    
    
    public String armarNombreCompleto() {
        String nombreCompleto = "";
        nombreCompleto += this.primerNombre + " ";
        if (this.segundoNombre != null) {
            nombreCompleto += this.segundoNombre + " ";
        }
        nombreCompleto += this.primerApellido + " ";
        if (this.segundoApellido != null) {
            nombreCompleto += this.segundoApellido;
        }
        return nombreCompleto;
    }
}
