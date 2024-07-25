package com.zerobase.reservation.service.impl;

import com.zerobase.reservation.constant.UserRole;
import com.zerobase.reservation.dto.AuthDto;
import com.zerobase.reservation.entity.User;
import com.zerobase.reservation.exception.error.EmailAlreadyExistException;
import com.zerobase.reservation.exception.error.InvalidPasswordException;
import com.zerobase.reservation.exception.error.UserNotFoundException;
import com.zerobase.reservation.repository.UserRepository;
import com.zerobase.reservation.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * 회원가입
     */
    @Override
    public AuthDto.CreateAccount creatAccount(@Valid AuthDto.CreateAccount request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistException("이미 존재하는 이메일입니다.");
        }

        User user = User.builder()
                .email(request.getEmail())
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
    @Transactional
    public AuthDto.UpdateAccount updateAccount(String email, @Valid AuthDto.UpdateAccount request) {

        // 이메일로 사용자 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 회원입니다."));

        // 새 비밀번호로 업데이트
        String newPassword = request.getNewPassword();
        if (newPassword == null || newPassword.isEmpty() || newPassword.length() < 8) {
            throw new InvalidPasswordException("8자리 이상의 유효한 비밀번호를 입력하세요");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return AuthDto.UpdateAccount.builder()
                .newPassword(newPassword)
                .build();
    }


    /**
     * 계정 삭제
     */
    @Override
    @Transactional
    public void deleteAccount(AuthDto.DeleteAccount request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 회원입니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.");
        }

        userRepository.delete(user);
    }

}
