package com.zerobase.reservation.controller;

import com.zerobase.reservation.dto.StoreDto;
import com.zerobase.reservation.entity.Store;
import com.zerobase.reservation.exception.StoreNotExistException;
import com.zerobase.reservation.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    /**
     * (매니저) 매장등록
     */
    @PostMapping("/enroll")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> enrollStore(@RequestBody @Valid StoreDto storeDto) {
        storeService.enrollStore(storeDto);
        return ResponseEntity.ok("매장 등록이 완료되었습니다.");
    }



    /**
     * 전체매장 검색
     */
    @GetMapping("/search/all")
    public ResponseEntity<List<Store>> searchAllStores() {

        List<Store> storeNames = storeService.searchAllStores();
        return ResponseEntity.ok(storeNames);
    }

    @GetMapping("/search/name-asc")
    public ResponseEntity<List<StoreDto>> searchStoreNameAsc() {
        List<StoreDto> storeDtos = storeService.searchStoreSortedByName();
        return ResponseEntity.ok(storeDtos);
    }


    /**
     * 매장 상세 검색
     */
    @GetMapping("/search/detail")
    public ResponseEntity<StoreDto> searchStoreDetail(@RequestParam String storeName) {
        try {
            StoreDto storeDto = storeService.searchStoreDetailInfo(storeName);
            return ResponseEntity.ok(storeDto);
        } catch (StoreNotExistException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @ExceptionHandler(StoreNotExistException.class)
    public ResponseEntity<String> handleStoreNotFoundException(StoreNotExistException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
