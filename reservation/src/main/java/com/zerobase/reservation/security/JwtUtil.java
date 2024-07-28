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

    /**
     * JWT 토큰 생성
     */
    public String generateToken(String email, UserRole role) {

        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role", role.name());

        byte[] keyBytes = Base64.getDecoder().decode(jwtProperties.getSecret());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))
                .signWith(SignatureAlgorithm.HS512, keyBytes)
                .compact();
    }

    /**
     * JWT 토큰에서 이메일을 추출
     */
    public String extractEmail(String token) {

        byte[] keyBytes = Base64.getDecoder().decode(jwtProperties.getSecret());


        return Jwts.parser()
                .setSigningKey(keyBytes)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public UserRole extractRole(String token) {

        byte[] keyBytes = Base64.getDecoder().decode(jwtProperties.getSecret());

        Claims claims = Jwts.parser()
                .setSigningKey(keyBytes)
                .parseClaimsJws(token)
                .getBody();
        return UserRole.valueOf(claims.get("role", String.class));
    }

    // JWT 토큰 유효성 검증
    public boolean isTokenValid(String token, String email) {
        final String username = extractEmail(token);
        return (username.equals(email) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {

        byte[] keyBytes = Base64.getDecoder().decode(jwtProperties.getSecret());

        return Jwts.parser()
                .setSigningKey(keyBytes)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }
}