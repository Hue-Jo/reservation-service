package com.zerobase.reservation.controller;

import com.zerobase.reservation.dto.AuthDto;
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
     * 이메일, 이름, 연락처, 비밀번호, 이용자/관리자 여부
     */
    @PostMapping("/create-account")
    public ResponseEntity<String> createAccount(@RequestBody @Valid AuthDto.CreateAccount request) {
        authService.createAccount(request);
        return ResponseEntity.ok("회원가입을 축하합니다!");

    }


    /**
     * 회원정보 수정 (비밀번호)
     * 이메일 입력 후 비밀번호 변경
     */
    @PatchMapping("/update-account")
    public ResponseEntity<String> updateAccount(@RequestBody @Valid AuthDto.UpdateAccount request) {
        authService.updateAccount(request.getUserEmail(), request);
        return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
    }


    /**
     * 회원탈퇴 (계정 삭제)
     * 이메일 입력 후 탈퇴
     */
    @DeleteMapping("/delete-account")
    public ResponseEntity<String> deleteAccount(@RequestBody @Valid AuthDto.DeleteAccount request) {
        authService.deleteAccount(request);
        return ResponseEntity.ok("계정이 성공적으로 삭제되었습니다.");
    }


    /**
     * 로그인
     * 이메일 & 비밀번호 입력
     */
    @PostMapping("/login")
    public ResponseEntity<AuthDto.LoginResponse> login(@RequestBody AuthDto.LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }


    /**
     * 로그아웃
     */

}
