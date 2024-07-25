package com.zerobase.reservation.service;

import com.zerobase.reservation.dto.AuthDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService {

    // 계정 생성
    AuthDto.CreateAccount creatAccount(AuthDto.CreateAccount request);

    // 계정 업데이트
    AuthDto.UpdateAccount updateAccount(String email, AuthDto.UpdateAccount request);

    // 계정 삭제
    void deleteAccount(AuthDto.DeleteAccount request);

    // 로그인

}
