package com.prueba.dto;

import lombok.Data;

@Data
public class JWTAuthResponseDTO {
    
    private String token;
    private String tipoToken = "Bearer";

    public JWTAuthResponseDTO(String token) {
        this.token = token;
    }
    
}
