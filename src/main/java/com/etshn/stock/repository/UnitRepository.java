package com.etshn.stock.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.etshn.stock.entity.Units;

public interface UnitRepository extends JpaRepository<Units, Long>{
	Units findByProductId(Long productId);
	Page<Units> findByNomContainingOrderByProductDateCreationDesc(String nom, Pageable pageable);

}
