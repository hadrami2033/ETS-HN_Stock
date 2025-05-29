package com.etshn.stock.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.etshn.stock.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
	Client findByName(String name);
	Client findByPhone(String phone);
	Page<Client> findAll(Pageable pageable);
	List<Client> findAll();
}
