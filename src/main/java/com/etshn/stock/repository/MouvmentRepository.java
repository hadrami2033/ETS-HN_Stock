package com.etshn.stock.repository;

import java.sql.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.etshn.stock.entity.Mouvment;

public interface MouvmentRepository extends JpaRepository<Mouvment, Long>{
	Page<Mouvment> findByTypeIdOrderByDateCreationDesc(Long typeId, Pageable pageable);
	Page<Mouvment> findByTypeIdAndDateCreationBetweenOrderByDateCreationDesc(Long typeId, Date startDate, Date andDate, Pageable pageable);
	Page<Mouvment> findByTypeIdAndProductIdOrderByDateCreationDesc(Long typeId, Long productId, Pageable pageable);
}
