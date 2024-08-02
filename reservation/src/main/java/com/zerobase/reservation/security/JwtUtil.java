package com.zerobase.reservation.security;

import com.zerobase.reservation.constant.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtProperties jwtProperties;

    private byte[] getKeyBytes() {
        return Base64.getDecoder().decode(jwtProperties.getSecret());
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getKeyBytes())
                .parseClaimsJws(token)
                .getBody();
    }


    //토큰 생성
    public String generateToken(String email, UserRole role) {

        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role", role.name());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))
                .signWith(SignatureAlgorithm.HS512, getKeyBytes())
                .compact();
    }


    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }


    public UserRole extractRole(String token) {
        return UserRole.valueOf(getClaims(token).get("role", String.class));
    }


    // JWT 토큰 유효성 검증
    public boolean isTokenValid(String token, String email) {
        final String username = extractEmail(token);
        return (username.equals(email) && !isTokenExpired(token));
    }


    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }
}