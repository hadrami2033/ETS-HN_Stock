package com.etshn.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etshn.stock.entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

}
