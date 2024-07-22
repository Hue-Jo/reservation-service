package com.zerobase.reservation.controller;

import com.zerobase.reservation.dto.AuthDto;
import com.zerobase.reservation.exception.InvalidPasswordException;
import com.zerobase.reservation.exception.UserNotFoundException;
import com.zerobase.reservation.response.ResponseMessage;
import com.zerobase.reservation.security.JwtTokenProvider;
import com.zerobase.reservation.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/authority")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 회원가입
     */
    @PostMapping("/create-account")
    public ResponseEntity<?> createAccount(@RequestBody @Valid AuthDto.CreateAccount request) {
        authService.creatAccount(request);
        return ResponseEntity.ok("회원가입을 축하합니다!");
    }


    /**
     * 회원정보 수정 (비밀번호)
     */
    @PatchMapping("/update-account")
    public ResponseEntity<?> updateAccount(@RequestBody @Valid AuthDto.UpdateAccount request) {
        authService.updateAccount(request.getEmail(), request);
        return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
    }


    /**
     * 회원탈퇴
     */
    @DeleteMapping("/delete-account")
    public ResponseEntity<String> deleteAccount(@RequestBody @Valid AuthDto.DeleteAccount request) {
        authService.deleteAccount(request);
        return ResponseEntity.status(HttpStatus.OK).body("계정이 성공적으로 삭제되었습니다.");
    }


    /**
     * 로그인
     */
    @PostMapping("/log-in")
    public ResponseEntity<?> logIn(@RequestBody @Valid AuthDto.LogIn request) {
        try {
            String token = authService.logIn(request);
            ResponseMessage responseMessage = ResponseMessage.success("로그인되었습니다.");
            responseMessage.setBody(token);
            return ResponseEntity.ok(responseMessage);
        } catch (UserNotFoundException | InvalidPasswordException exception) {
            ResponseMessage responseMessage = ResponseMessage.fail(exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
        }
    }


    /**
     * 로그아웃
     */

}
