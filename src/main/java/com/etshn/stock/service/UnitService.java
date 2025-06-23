package com.etshn.stock.service;

import com.etshn.stock.payload.UnitDto;
import com.etshn.stock.payload.UnitResponse;

public interface UnitService {
	UnitDto add(UnitDto unitDto);

	UnitDto get(Long id);
	
	UnitDto update(UnitDto unitDto, Long id);

    void delete(Long id);
    
	UnitDto findByProductId(Long productId);
	
	UnitResponse findAll(String nom, int pageNo, int pageSize, String sortBy, String sortDir);

}
