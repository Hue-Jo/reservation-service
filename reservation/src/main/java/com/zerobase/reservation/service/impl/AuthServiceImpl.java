package com.zerobase.reservation.service.impl;

import com.zerobase.reservation.repository.UserRepository;
import com.zerobase.reservation.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
}
