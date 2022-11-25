package com.kevsz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kevsz.model.Sale;

public interface SaleRepository  extends JpaRepository<Sale, Integer>{
  
}
