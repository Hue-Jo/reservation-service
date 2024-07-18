package com.zerobase.reservation.service.impl;

import com.zerobase.reservation.repository.StoreRepository;
import com.zerobase.reservation.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
}
