package com.etshn.stock.service;

import com.etshn.stock.payload.ProductDto;
import com.etshn.stock.payload.ProductResponce;

public interface ProductService {
	ProductDto add(ProductDto productDto);

	ProductDto get(Long productId);
	
	ProductDto update(ProductDto productDto, Long id);

    void delete(Long id);
    
	ProductDto findByNom(String nom);
	
	ProductResponce findAllDisponible(String nom, int pageNo, int pageSize, String sortBy, String sortDir);
	
	ProductResponce findAllIndisponible(String nom, int pageNo, int pageSize, String sortBy, String sortDir);
	
	ProductResponce findAll(String nom, int pageNo, int pageSize, String sortBy, String sortDir);


}
