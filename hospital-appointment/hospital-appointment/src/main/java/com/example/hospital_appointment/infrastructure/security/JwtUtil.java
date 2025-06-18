package com.example.hospital_appointment.infrastructure.security;

import com.example.hospital_appointment.domain.Enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private final String secret = "U2FsdGVkX1+6I5AkYho2mgJx5qozM0d/dG6Udo9td5I=";

    public String generateToken(String email, Role role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10h
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(secret.getBytes())
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkToken(String token, String requiredRole) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret.getBytes())
                    .parseClaimsJws(token)
                    .getBody();

            String role = (String) claims.get("role");
            return role != null && role.equalsIgnoreCase(requiredRole);
        } catch (Exception e) {
            return false;
        }
    }

    public String generateTemporaryToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 15 * 60 * 1000);
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", Role.OTP_VERIFIED);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
    }

}

