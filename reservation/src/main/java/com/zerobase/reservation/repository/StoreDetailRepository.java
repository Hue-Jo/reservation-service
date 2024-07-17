package com.zerobase.reservation.repository;

import com.zerobase.reservation.entity.StoreDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreDetailRepository extends JpaRepository<StoreDetail, Long> {
}
