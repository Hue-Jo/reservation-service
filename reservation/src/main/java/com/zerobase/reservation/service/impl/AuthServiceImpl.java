package com.zerobase.reservation.service.impl;

import com.zerobase.reservation.constant.UserRole;
import com.zerobase.reservation.dto.AuthDto;
import com.zerobase.reservation.entity.User;
import com.zerobase.reservation.exception.error.EmailAlreadyExistException;
import com.zerobase.reservation.exception.error.InvalidPasswordException;
import com.zerobase.reservation.exception.error.UserNotFoundException;
import com.zerobase.reservation.repository.UserRepository;
import com.zerobase.reservation.security.JwtUtil;
import com.zerobase.reservation.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /**
     * 회원가입
     */
    @Override
    public AuthDto.CreateAccount creatAccount(@Valid AuthDto.CreateAccount request) {

        if (userRepository.existsByEmail(request.getUserEmail())) {
            throw new EmailAlreadyExistException("이미 존재하는 이메일입니다.");
        }

        if (request.getPassword() == null || request.getPassword().length() < 8) {
            throw new InvalidPasswordException("8자리 이상의 유효한 비밀번호를 입력하세요");
        }

        User user = User.builder()
                .email(request.getUserEmail())
                .userName(request.getUserName())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNum(request.getPhoneNum())
                .role(UserRole.valueOf(request.getRole()))
                .build();

        userRepository.save(user);
        return request;
    }


    /**
     * 회원정보 수정
     */
    @Override
    public AuthDto.UpdateAccount updateAccount(String email, @Valid AuthDto.UpdateAccount request) {

        // 이메일로 사용자 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 회원입니다."));

        // 새 비밀번호로 업데이트
        String newPassword = request.getNewPassword();
        if (newPassword != null && newPassword.length() >= 8) {
            user.setPassword(passwordEncoder.encode(newPassword));
        } else {
            throw new InvalidPasswordException("8자리 이상의 유효한 비밀번호를 입력하세요");
        }

        userRepository.save(user);

        return AuthDto.UpdateAccount.builder()
                .newPassword(newPassword)
                .build();
    }


    /**
     * 계정 삭제
     */
    @Override
    public void deleteAccount(AuthDto.DeleteAccount request) {

        User user = userRepository.findByEmail(request.getUserEmail())
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 회원입니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.");
        }

        userRepository.delete(user);
    }


    /**
     * 로그인
     */
    @Override
    public AuthDto.LoginResponse login(AuthDto.LoginRequest request) {

        User user = userRepository.findByEmail(request.getUserEmail())
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 회원입니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        return AuthDto.LoginResponse.builder()
                .token(token).message("로그인 되었습니다.")
                .build();
    }


    /**
     * 로그아웃
     */

}
