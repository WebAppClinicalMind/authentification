package com.example.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.login.security.JwtUtils;

@Service
public class JwtService {
    @Autowired
    private JwtUtils jwtUtils;

    public String generateToken(String username) {
        return jwtUtils.generateJwtToken(username);
    }

    public boolean validateToken(String token) {
        return jwtUtils.validateJwtToken(token);
    }

    public String getUsername(String token) {
        return jwtUtils.getUsernameFromJwtToken(token);
    }
}
