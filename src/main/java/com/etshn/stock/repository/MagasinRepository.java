package com.etshn.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etshn.stock.entity.Magasin;

public interface MagasinRepository extends JpaRepository<Magasin, Long>{
	Magasin findByLabel(String label);
}
