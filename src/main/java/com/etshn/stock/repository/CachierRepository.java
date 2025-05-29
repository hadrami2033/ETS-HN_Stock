package com.etshn.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etshn.stock.entity.Cachier;

public interface CachierRepository extends JpaRepository<Cachier, Long> {

}
