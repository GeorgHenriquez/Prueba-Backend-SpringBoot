package com.prueba.controllers;

import com.prueba.dto.UsuarioDTO;
import com.prueba.security.JwtTokenProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/autenticacion/login")
public class AuthController {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    @PostMapping
    public ResponseEntity<Object> login(@RequestBody UsuarioDTO usuarioDTO) {
        Map<String, Object> response = new HashMap<String, Object>();
        
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuarioDTO.getUsername(), usuarioDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenProvider.generarToken(authentication);

            response.put("code", 200);
            response.put("success", true);
            response.put("message", "Usuario autenticado.");
            response.put("token", token);
            return new ResponseEntity<Object>(response, HttpStatus.OK);
        } catch (Exception ae) {
            response.put("code", 401);
            response.put("success", false);
            response.put("message", "Las credenciales de autenticaci??n no son v??lidas.");
            response.put("errorData", new ArrayList<>());
            return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
        }
        
            
    } 
    
}
