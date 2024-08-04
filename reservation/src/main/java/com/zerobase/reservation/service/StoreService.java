package com.zerobase.reservation.service;

import com.zerobase.reservation.dto.StoreDto;

import java.util.List;
import java.util.Optional;

public interface StoreService {

    // 매장등록 (매장관리자 ONLY)
    void enrollStore(StoreDto storeDto, String userEmail);

    // 전체 매장 리스트 검색
    List<StoreDto> searchAllStores();

    // 전체매장 이름 오름차순 검색
    List<String> searchStoreSortedByName();

    // 특정매장 상세정보 검색
    Optional<StoreDto> searchStoreDetailInfo(String storeName);
}
