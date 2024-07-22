package com.zerobase.reservation.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.zerobase.reservation.entity.User;
import com.zerobase.reservation.exception.UserNotFoundException;
import com.zerobase.reservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${spring.jwt.secret}")
    private String secretKey;
    private static final long TOKEN_EXPIRED_TIME = 1000 * 60 * 60; // 1시간

    public String generateToken(String email) {
        Date now = new Date(); // 토큰 생성시간
        Date expiredDate = new Date(now.getTime() + TOKEN_EXPIRED_TIME); // 토큰 만료시간

        return JWT.create()
                .withSubject(email) // 주체
                .withIssuedAt(now)            // 생성시간
                .withExpiresAt(expiredDate)   // 만료시간
                .sign(Algorithm.HMAC512(secretKey.getBytes()));

    }

}
