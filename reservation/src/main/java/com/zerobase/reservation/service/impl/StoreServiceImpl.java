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
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;


    /**
     * 매장등록 (MANAGER ONLY)
     */
    @Override
    public void enrollStore(StoreDto storeDto) {

        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 회원입니다."));

        if (currentUser.getRole() != UserRole.MANAGER) {
            throw new AccessDeniedException("매장 관리자만 매장을 등록할 수 있습니다.");
        }

        Store store = Store.builder()
                .storeName(storeDto.getStoreName())
                .location(storeDto.getStoreLocation())
                .phoneNum(storeDto.getStorePhoneNum())
                .openTime(storeDto.getStoreOpenTime())
                .closeTime(storeDto.getStoreCloseTime())
                .build();

        storeRepository.save(store);
    }


    /**
     * 전체 매장 검색
     */
    @Override
    public List<StoreDto> searchAllStores() {
        List<Store> stores = storeRepository.findAll();
        return stores.stream()
                .map(store -> StoreDto.builder()
                        .storeName(store.getStoreName())
                        .storeLocation(store.getLocation())
                        .storePhoneNum(store.getPhoneNum())
                        .storeOpenTime(store.getOpenTime())
                        .storeCloseTime(store.getCloseTime())
                        .build())
                .collect(Collectors.toList());

    }


    /**
     * 매장 이름 오름차순 검색
     */
    @Override
    public List<String> searchStoreSortedByName() {
        List<Store> stores = storeRepository.findAll(
                Sort.by(Sort.Order.asc("storeName")));
        return stores.stream()
                .map(Store::getStoreName)
                .collect(Collectors.toList());
    }


    /**
     * 매장 상세정보 검색
     */
    @Override
    public Optional<StoreDto> searchStoreDetailInfo(String storeName) {
        Optional<Store> storeDetail = storeRepository.findByStoreName(storeName);
        return storeDetail.map(store -> StoreDto.builder()
                .storeName(store.getStoreName())
                .storeLocation(store.getLocation())
                .storePhoneNum(store.getPhoneNum())
                .storeOpenTime(store.getOpenTime())
                .storeCloseTime(store.getCloseTime())
                .build());
    }

}
