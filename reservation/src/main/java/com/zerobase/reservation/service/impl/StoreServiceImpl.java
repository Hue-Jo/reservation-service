package com.zerobase.reservation.service.impl;

import com.zerobase.reservation.dto.StoreDto;
import com.zerobase.reservation.entity.Store;
import com.zerobase.reservation.exception.StoreNotExistException;
import com.zerobase.reservation.repository.StoreRepository;
import com.zerobase.reservation.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;


    // 매장 등록 (MANAGER only)
    @Override
    public void enrollStore(StoreDto storeDto) {
        Store store = Store.builder()
                .storeName(storeDto.getStoreName())
                .location(storeDto.getStoreLocation())
                .phoneNum(storeDto.getStorePhoneNum())
                .openTime(storeDto.getStoreOpenTime())
                .closeTime(storeDto.getStoreCloseTime())
                .build();
        storeRepository.save(store);
    }


    // 전체 매장 검색
    @Override
    public List<Store> searchAllStores() {
        return storeRepository.findAll();
    }


    // 매장명 오름차순 검색
    @Override
    public List<StoreDto> searchStoreSortedByName() {
        List<Store> stores = storeRepository.findAllByOrderByStoreNameAsc();
        return stores.stream()
                .map(store -> StoreDto.builder()
                        .storeLocation(store.getLocation())
                        .storePhoneNum(store.getPhoneNum())
                        .storeOpenTime(store.getOpenTime())
                        .storeCloseTime(store.getCloseTime())
                        .build())
                .collect(Collectors.toList());
    }


    // 매장 상세정보 검색
    @Override
    public StoreDto searchStoreDetailInfo(String storeName) {

        Store store = storeRepository.findByStoreName(storeName);
        if (store != null) {
            return StoreDto.builder()
                    .storeLocation(store.getLocation())
                    .storePhoneNum(store.getPhoneNum())
                    .storeOpenTime(store.getOpenTime())
                    .storeCloseTime(store.getCloseTime())
                    .build();
        } else {
            throw new StoreNotExistException(storeName);
        }
    }


}
