package com.prueba.security;

import com.prueba.exceptions.ApiException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    
    @Value("${app.jwt-secret}")
    private String jwtSecret;
    
    public String generarToken(Authentication authentication) {
        String username = authentication.getName();
        
        String token = Jwts.builder().setSubject(username).signWith(SignatureAlgorithm.HS256, jwtSecret).compact();
        return token;
    }
    
    public String obtenerUsername(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
    
    public boolean validarToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Token no valido");
        }
    }
    
}
