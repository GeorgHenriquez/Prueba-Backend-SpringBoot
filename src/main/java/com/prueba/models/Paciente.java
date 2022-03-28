package com.prueba.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="MGM_PACIENTES ", uniqueConstraints = {@UniqueConstraint(columnNames = {"NUMERO_IDENTIFICACION"})})
public class Paciente implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQUENCE_PACIENT")
    @SequenceGenerator(name="SEQUENCE_PACIENT", sequenceName="SEQUENCE_PACIENT", allocationSize=1)
    @Column(name = "ID_PACIENTE")
    private int idPaciente;
    
    @JsonBackReference
    @JoinColumn(name = "CODIGO_TIPO_IDENTIFICACION", insertable = false, updatable = false)
    @ManyToOne(targetEntity = TipoIdentificacion.class, fetch = FetchType.EAGER)
    private TipoIdentificacion tipoIdentificacion;
    
    @Column(name = "CODIGO_TIPO_IDENTIFICACION")
    private int codigoTipoIdentificacion;
    
    @Column(name = "NUMERO_IDENTIFICACION", nullable = false)
    private String numeroIdentificacion;

    @Column(name = "PRIMER_NOMBRE", nullable = false)
    private String primerNombre;

    @Column(name = "SEGUNDO_NOMBRE")
    private String segundoNombre;

    @Column(name = "PRIMER_APELLIDO", nullable = false)
    private String primerApellido;

    @Column(name = "SEGUNDO_APELLIDO")
    private String segundoApellido;

    @Column(name = "NOMBRE_COMPLETO", nullable = false)
    private String nombreCompleto;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "ESTADO", nullable = false)
    private String estado;

    @Column(name = "FECHA_INGRESO", nullable = false)
    private Timestamp fechaIngreso;

    @Column(name = "USUARIO_INGRESO", nullable = false)
    private String usuarioIngreso;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;
    
    @Column(name = "USUARIO_MODIFICACION")
    private String usuarioModificacion;
}
