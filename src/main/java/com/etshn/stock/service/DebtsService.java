package com.etshn.stock.service;

import java.sql.Date;

import com.etshn.stock.payload.DebtClientDto;
import com.etshn.stock.payload.DebtsDto;
import com.etshn.stock.payload.DebtsResponce;

public interface DebtsService {
	
	DebtsDto add(DebtsDto debtsDto);

	DebtsDto get(Long debttId);
	
	DebtsDto update(DebtsDto debtsDto, Long id);

    void delete(Long id);
    
	DebtsResponce findByClientAndPayed(Long clientId, Integer payed,int pageNo, int pageSize, String sortBy, String sortDir);

	DebtsResponce findByEmployeAndPayed(Long employeId, Integer payed,int pageNo, int pageSize, String sortBy, String sortDir);

	DebtsResponce findByPayedAndInterval(Integer payed, Date startDate, Date andDate, int pageNo, int pageSize, String sortBy, String sortDir);

	DebtClientDto getClientDebts(Long clientId, Integer payed);
	
	DebtClientDto getClientPaidDebts(Long clientId, Integer payed);

}
