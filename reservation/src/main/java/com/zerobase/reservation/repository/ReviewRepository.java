package com.zerobase.reservation.repository;

import com.zerobase.reservation.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByStore_StoreName(String storeName);


}
