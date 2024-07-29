package com.zerobase.reservation.service;

import com.zerobase.reservation.dto.StoreDto;

import java.util.List;
import java.util.Optional;

public interface StoreService {

    void enrollStore(StoreDto storeDto, String userEmail);

    List<StoreDto> searchAllStores();

    List<String> searchStoreSortedByName();

    Optional<StoreDto> searchStoreDetailInfo(String storeName);
}
