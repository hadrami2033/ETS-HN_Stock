package com.etshn.stock.service;

import java.sql.Date;

import com.etshn.stock.payload.MouvementDto;
import com.etshn.stock.payload.MouvementResponse;

public interface MouvementService {
	MouvementDto add(MouvementDto mouvementDto);

	MouvementDto get(Long mouvementId);
	
	MouvementDto update(MouvementDto mouvementDto, Long id);

    void delete(Long id);
    
    MouvementResponse getByType(Long typeId,int pageNo, int pageSize, String sortBy, String sortDir);

    MouvementResponse getByTypeAndInterval(Long typeId, Date startDate, Date andDate,int pageNo, int pageSize, String sortBy, String sortDir);

    MouvementResponse getByTypeAndProduct(Long typeId, Long productId, int pageNo, int pageSize, String sortBy, String sortDir);
}
