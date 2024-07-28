package com.zerobase.reservation.controller;

import com.zerobase.reservation.dto.StoreDto;
import com.zerobase.reservation.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
     * (매니저) 매장등록
     */
    @PostMapping("/enroll")
    public ResponseEntity<?> enrollStore(@RequestBody @Valid StoreDto storeDto) {

        storeService.enrollStore(storeDto);
        return ResponseEntity.ok("매장 등록이 완료되었습니다.");
    }


    /**
     * 전체매장 검색
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
     * 매장 상세 검색
     */
    @GetMapping("/search/{storeName}/detail")
    public ResponseEntity<?> searchStoreDetail(@PathVariable String storeName) {
        Optional<StoreDto> storeDto = storeService.searchStoreDetailInfo(storeName);
        if (storeDto.isPresent()) {
            return ResponseEntity.ok(storeDto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
