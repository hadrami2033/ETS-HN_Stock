package com.etshn.stock.service;

import java.sql.Date;

import com.etshn.stock.payload.MagasinMouvmentUnitDto;
import com.etshn.stock.payload.MagasinMouvmentUnitResponse;
import com.etshn.stock.payload.ProductInMagasin;

public interface MagasinMouvmentsUnitService {
	MagasinMouvmentUnitDto add(MagasinMouvmentUnitDto magasinMouvementDto);

	MagasinMouvmentUnitDto get(Long id);
	
	MagasinMouvmentUnitDto update(MagasinMouvmentUnitDto magasinMouvementDto, Long id);

    void delete(Long id);
    
    MagasinMouvmentUnitResponse getByMagasinAndInterval(Long typeId, Long magasinId, String unit, Date startDate, Date andDate,int pageNo, int pageSize, String sortBy, String sortDir);

    MagasinMouvmentUnitResponse getByMagasinAndUnit(Long magasinId, Long unitId,int pageNo, int pageSize, String sortBy, String sortDir);

    MagasinMouvmentUnitResponse getByMagasinAndType(Long magasinId, Long typeId,int pageNo, int pageSize, String sortBy, String sortDir);
 
    ProductInMagasin getStockUnitInMagasin(Long magasinId, Long unitId);
}
