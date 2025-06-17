package com.etshn.stock.repository;

import java.sql.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.etshn.stock.entity.CachierMouvments;

public interface CachierMouvmentsRepository extends JpaRepository<CachierMouvments, Long> {
	Page<CachierMouvments> findByDateCreationBetweenOrderByDateCreationDesc(Date startDate, Date andDate, Pageable pageable);
}
