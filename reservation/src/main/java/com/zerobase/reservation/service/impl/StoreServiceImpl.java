package com.zerobase.reservation.service.impl;

import com.zerobase.reservation.constant.UserRole;
import com.zerobase.reservation.dto.StoreDto;
import com.zerobase.reservation.entity.Store;
import com.zerobase.reservation.entity.User;
import com.zerobase.reservation.exception.error.UserNotFoundException;
import com.zerobase.reservation.repository.StoreRepository;
import com.zerobase.reservation.repository.UserRepository;
import com.zerobase.reservation.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    /**
     * 매장등록 (매장관리자 ONLY)
     * 상호명, 위치, 전화번호, 오픈시간, 마감시간
     */
    @Override
    public void enrollStore(StoreDto storeDto, String userEmail) {

        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 회원입니다."));

        log.info("현재 로그인된 사용자: {}", currentUser.getRole()); // 현재 사용자 역할 로그

        if (currentUser.getRole() != UserRole.MANAGER) {
            throw new AccessDeniedException("매장 관리자만 매장을 등록할 수 있습니다.");
        }

        Store store = Store.builder()
                .storeName(storeDto.getStoreName())
                .location(storeDto.getLocation())
                .phoneNum(storeDto.getPhoneNum())
                .openTime(storeDto.getOpenTime())
                .closeTime(storeDto.getCloseTime())
                .build();

        storeRepository.save(store);
    }


    /**
     * 전체 매장 리스트 검색
     */
    @Override
    public List<StoreDto> searchAllStores() {
        return storeRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    /**
     * 전체매장 이름 오름차순 검색
     */
    @Override
    public List<String> searchStoreSortedByName() {
        return storeRepository.findAll(Sort.by(Sort.Order.asc("storeName")))
                .stream()
                .map(Store::getStoreName)
                .collect(Collectors.toList());
    }


    /**
     * 특정매장 상세정보 검색
     */
    @Override
    public Optional<StoreDto> searchStoreDetailInfo(String storeName) {
        return storeRepository.findByStoreName(storeName)
                .map(this::convertToDto);
    }


    private StoreDto convertToDto(Store store) {
        return StoreDto.builder()
                .storeName(store.getStoreName())
                .location(store.getLocation())
                .phoneNum(store.getPhoneNum())
                .openTime(store.getOpenTime())
                .closeTime(store.getCloseTime())
                .build();
    }
}
