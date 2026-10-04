package com.vetturno.security;

import com.vetturno.model.Rol;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private final String secret;
    private final long expirationMs;

    public JwtService(@Value("${jwt.secret}") String secret,
                      @Value("${jwt.expiration-ms}") long expirationMs) {
        this.secret = secret;
        this.expirationMs = expirationMs;
    }

    public String generarToken(String email, Rol rol) {
        Date ahora = new Date();
        Date expira = new Date(ahora.getTime() + expirationMs);

        return Jwts.builder()
                .subject(email)
                .claim("rol", rol.name())
                .issuedAt(ahora)
                .expiration(expira)
                .signWith(getSigningKey())
                .compact();
    }

    public String extraerEmail(String token) {
        return extraerClaims(token).getSubject();
    }

    public boolean esValido(String token, UserDetails userDetails) {
        Claims claims = extraerClaims(token);
        return claims.getSubject().equals(userDetails.getUsername())
                && claims.getExpiration().after(new Date());
    }

    private Claims extraerClaims(String token) {
        // El parser verifica la firma antes de devolver los claims. Si el token fue
        // alterado o expiró, JJWT lanza una excepción y el filtro no autentica la petición.
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
