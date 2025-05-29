package com.etshn.stock.service;

import java.util.List;

import com.etshn.stock.payload.MagasinDto;

public interface MagasinService {
	MagasinDto add(MagasinDto magasinDto);

	MagasinDto get(Long id);
	
	MagasinDto update(MagasinDto magasinDto, Long id);

    void delete(Long id);
    
    MagasinDto findByLabel(String label);
    
    List<MagasinDto> findAll();
}
