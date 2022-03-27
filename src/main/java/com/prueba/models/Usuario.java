package com.prueba.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="USUARIOS")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "CODIGO_USUARIO")
    private int codigoUsuario;
    
    @Column(name = "USERNAME")
    private String username;
    
    @Column(name = "USER_PASSWORD")
    private String userPassword;
    
    
}
