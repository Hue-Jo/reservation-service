package com.zerobase.reservation.service;

import com.zerobase.reservation.dto.AuthDto;

public interface AuthService{


    // 회원가입
    void createAccount(AuthDto.CreateAccount request);

    // 회원정보 수정 (비밀번호)
    AuthDto.UpdateAccount updateAccount(String email, AuthDto.UpdateAccount request);

    // 회원 탈퇴
    void deleteAccount(AuthDto.DeleteAccount request);

    // 로그인
    AuthDto.LoginResponse login(AuthDto.LoginRequest request);
}
