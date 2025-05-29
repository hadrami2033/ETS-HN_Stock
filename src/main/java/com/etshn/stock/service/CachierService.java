package com.etshn.stock.service;

import com.etshn.stock.payload.CachierDto;

public interface CachierService {
	CachierDto update(CachierDto cachierDto, Long id);
	
	CachierDto get(Long id);

}
