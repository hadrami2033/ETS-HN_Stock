package com.etshn.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etshn.stock.entity.Type;


public interface TypeRepository extends JpaRepository<Type, Long>{
	Type findByLabel(String type);
}
