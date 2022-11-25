package com.kevsz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kevsz.model.SaleDetail;

public interface SaleDetailRepository extends JpaRepository<SaleDetail, Integer> {
  
}
