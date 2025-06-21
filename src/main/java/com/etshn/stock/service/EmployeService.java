package com.etshn.stock.service;

import java.util.List;

import com.etshn.stock.payload.EmployeDto;
import com.etshn.stock.payload.EmployeResponce;

public interface EmployeService {
	EmployeDto add(EmployeDto EmployeDto);

	EmployeDto get(Long clientId);
	
	EmployeDto update(EmployeDto EmployeDto, Long id);

    void delete(Long id);
    
    EmployeDto findByName(String name);

    EmployeDto findByPhone(String phone);
    
    EmployeResponce findAll(int pageNo, int pageSize, String sortBy, String sortDir);

    List<EmployeDto> findAll();
}
