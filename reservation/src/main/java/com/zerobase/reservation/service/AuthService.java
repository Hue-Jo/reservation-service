package com.zerobase.reservation.service;

import com.zerobase.reservation.dto.AuthDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService {


    AuthDto.CreateAccount creatAccount(AuthDto.CreateAccount request);

    AuthDto.UpdateAccount updateAccount(String email, AuthDto.UpdateAccount request);

    void deleteAccount(AuthDto.DeleteAccount request);



}
