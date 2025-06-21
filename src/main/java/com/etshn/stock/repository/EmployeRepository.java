package com.etshn.stock.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.etshn.stock.entity.Employes;

public interface EmployeRepository extends JpaRepository<Employes, Long> {
	Employes findByName(String name);
	Employes findByPhone(String phone);
	Page<Employes> findAll(Pageable pageable);
	List<Employes> findAll();
}
