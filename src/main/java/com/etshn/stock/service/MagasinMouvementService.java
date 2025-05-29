package com.etshn.stock.service;

import java.sql.Date;

import com.etshn.stock.payload.MagasinMouvementDto;
import com.etshn.stock.payload.MagasinMouvementResponse;
import com.etshn.stock.payload.ProductInMagasin;

public interface MagasinMouvementService {
	MagasinMouvementDto add(MagasinMouvementDto magasinMouvementDto);

	MagasinMouvementDto get(Long id);
	
	MagasinMouvementDto update(MagasinMouvementDto magasinMouvementDto, Long id);

    void delete(Long id);
    
    MagasinMouvementResponse getByMagasinAndInterval(Long typeId, Long magasinId, String product, Date startDate, Date andDate,int pageNo, int pageSize, String sortBy, String sortDir);

    MagasinMouvementResponse getByMagasinAndProduct(Long magasinId, Long productId,int pageNo, int pageSize, String sortBy, String sortDir);

    MagasinMouvementResponse getByMagasinAndType(Long magasinId, Long typeId,int pageNo, int pageSize, String sortBy, String sortDir);
 
    ProductInMagasin getStockProductInMagasin(Long magasinId, Long productId);
}
