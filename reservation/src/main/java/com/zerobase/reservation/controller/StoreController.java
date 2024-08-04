package com.zerobase.reservation.controller;

import com.zerobase.reservation.dto.StoreDto;
import com.zerobase.reservation.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;


    /**
     * 매장등록 (매장관리자 ONLY)
     * 상호명, 위치, 전화번호, 오픈시간, 마감시간
     */
    @PostMapping("/enroll")
    public ResponseEntity<String> enrollStore(@RequestBody @Valid StoreDto storeDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        storeService.enrollStore(storeDto, userEmail);
        return ResponseEntity.ok("매장 등록이 완료되었습니다.");
    }


    /**
     * 전체 매장 리스트 검색
     */
    @GetMapping("/search/all")
    public ResponseEntity<List<StoreDto>> searchAllStores() {

        List<StoreDto> allStores = storeService.searchAllStores();
        return ResponseEntity.ok(allStores);
    }


    /**
     * 전체매장 이름 오름차순 검색
     */
    @GetMapping("/search/name-order")
    public ResponseEntity<List<String>> searchStoreNameAsc() {

        List<String> storeNames = storeService.searchStoreSortedByName();
        return ResponseEntity.ok(storeNames);
    }


    /**
     * 특정매장 상세정보 검색
     */
    @GetMapping("/search/{storeName}/detail")
    public ResponseEntity<?> searchStoreDetail(@PathVariable String storeName) {

        Optional<StoreDto> storeDto = storeService.searchStoreDetailInfo(storeName);
        return storeDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
