package com.etshn.stock.service;

import java.util.List;

import com.etshn.stock.payload.ClientDto;
import com.etshn.stock.payload.ClientResponce;

public interface ClientService {
	ClientDto add(ClientDto clientDto);

	ClientDto get(Long clientId);
	
	ClientDto update(ClientDto clientDto, Long id);

    void delete(Long id);
    
    ClientDto findByName(String name);

    ClientDto findByPhone(String phone);
    
    ClientResponce findAll(int pageNo, int pageSize, String sortBy, String sortDir);

    List<ClientDto> findAll();
}
