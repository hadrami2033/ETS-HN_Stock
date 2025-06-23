package com.etshn.stock.repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.etshn.stock.entity.Debts;

public interface DebtsRepository extends JpaRepository<Debts, Long> {
	Page<Debts> findByClientIdAndPayedOrderByDateCreationDesc(Long clientId, Integer payed, Pageable pageable);
	Page<Debts> findByEmployeIdAndPayedOrderByDateCreationDesc(Long id, Integer payed, Pageable pageable);
	Page<Debts> findByPayedAndDateCreationBetweenOrderByDateCreationDesc(Integer payed, Date startDate, Date andDate, Pageable pageable);
	@Query("select sum(d.amount) from Debts d where d.client.id = ?1 and d.payed = ?2")
    List<BigDecimal> getClientDebts(Long clientId, Integer payed);
	@Query("select sum(d.amountPayed) from Debts d where d.client.id = ?1 and d.payed = ?2")
    List<BigDecimal> getClientPaidsDebts(Long clientId, Integer payed);

}
