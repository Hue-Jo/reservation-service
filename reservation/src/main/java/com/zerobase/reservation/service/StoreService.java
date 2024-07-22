package com.zerobase.reservation.service;

import com.zerobase.reservation.dto.StoreDto;
import com.zerobase.reservation.entity.Store;

import java.util.List;

public interface StoreService {

    void enrollStore(StoreDto storeDto);


    List<Store> searchAllStores();

    StoreDto searchStoreDetailInfo(String storeName);

    List<StoreDto> searchStoreSortedByName();

}
