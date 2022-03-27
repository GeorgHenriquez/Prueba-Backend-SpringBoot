package com.prueba.models;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="DAF_TIPOS_IDENTIFICACION")
public class TipoIdentificacion implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "CODIGO_TIPO_IDENTIFICACION")
    private int codigoTipoIdentificacion;;
    
    @Column(name = "NOMBRE_TIPO_IDENTIFICACION")
    private String nombreTipoIdentificacion;

    @Column(name = "ESTADO")
    private String estado;
}
