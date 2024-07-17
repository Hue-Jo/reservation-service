package com.zerobase.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authority")
public class AuthController {

    /**
     * 회원가입
     */
    @PostMapping("/create-account")
    public void createAccount() {

    }


    /**
     *  회원정보 수정
     */
    @PatchMapping("/update-account")
    public void updateAccount() {

    }


    /**
     * 회원탈퇴
     */
    @DeleteMapping("/delete-account")
    public void deleteAccount() {

    }


    /**
     * 로그인
     */
    @PostMapping("/log-in")
    public void logIn() {

    }


    /**
     * 로그아웃
     */
    @GetMapping("/log-out")
    public void logOut() {

    }


}
