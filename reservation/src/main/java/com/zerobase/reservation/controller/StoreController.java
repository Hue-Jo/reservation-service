package com.zerobase.reservation.controller;

import com.zerobase.reservation.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    /**
     * (매니저) 매장등록
     */
    @PostMapping("/enroll")
    public void enrollStore() {
    }


    /**
     * (매니저) 매장 상세정보 등록
     */
    @PostMapping("/enroll/detail-info")
    public void enrollStoreDetailInfo() {

    }


    /**
     * 전체매장 검색
     */
    @GetMapping("/search/all")
    public void searchAllStores() {

    }


    /**
     * 매장 상세 검색
     */
    @GetMapping("/search/detail")
    public void searchStoreDetail() {

    }



}
