package com.zerobase.reservation.controller;

import com.zerobase.reservation.dto.AuthDto;
import com.zerobase.reservation.exception.error.EmailAlreadyExistException;
import com.zerobase.reservation.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/authority")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;

    /**
     * 회원가입
     */
    @PostMapping("/create-account")
    public ResponseEntity<String> createAccount(@RequestBody @Valid AuthDto.CreateAccount request) {
        authService.creatAccount(request);
        return ResponseEntity.ok("회원가입을 축하합니다!");

    }


    /**
     * 회원정보 수정 (비밀번호)
     */
    @PatchMapping("/update-account")
    public ResponseEntity<String> updateAccount(@RequestBody @Valid AuthDto.UpdateAccount request) {
        authService.updateAccount(request.getUserEmail(), request);
        return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
    }


    /**
     * 회원탈퇴
     */
    @DeleteMapping("/delete-account")
    public ResponseEntity<String> deleteAccount(@RequestBody @Valid AuthDto.DeleteAccount request) {
        authService.deleteAccount(request);
        return ResponseEntity.ok("계정이 성공적으로 삭제되었습니다.");
    }


    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<AuthDto.LoginResponse> login(@RequestBody AuthDto.LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }


    /**
     * 로그아웃
     */

}
