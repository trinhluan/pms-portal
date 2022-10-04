
package com.example.pmswebportal.security;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JwtTokenProvider  {

    private final String JWT_SECRET = "PMS Web Portal";

    private Long tokenExpirationHour = 24L;

    public String generateJwtTokenUrl(String email) {
        LocalDateTime current = LocalDateTime.now();
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(Date.from(current.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(current.plusHours(tokenExpirationHour).atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public String generateJwtTokenLogin(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }


    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        // try {
        //     Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
        //     return true;
        // } catch (Exception e) {
            
        // } 
        return true;
    }
}
