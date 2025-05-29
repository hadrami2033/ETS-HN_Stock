package com.etshn.stock.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.etshn.stock.entity.Cachier;
import com.etshn.stock.exception.ResourceNotFoundException;
import com.etshn.stock.payload.CachierDto;
import com.etshn.stock.repository.CachierRepository;
import com.etshn.stock.service.CachierService;

@Service
public class CachierServiceImpl implements CachierService {
	
	private ModelMapper mapper;
	
	private CachierRepository cachierRepository;

	public CachierServiceImpl(ModelMapper mapper, CachierRepository cachierRepository) {
		super();
		this.mapper = mapper;
		this.cachierRepository = cachierRepository;
	}
	
	// convert Entity into DTO
    private CachierDto mapToDTO(Cachier entity){
    	CachierDto dto = mapper.map(entity, CachierDto.class);
        return dto;
    }

	@Override
	public CachierDto update(CachierDto cachierDto, Long id) {
		Cachier c = cachierRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cachier", "id", id));  
        c.setSolde(cachierDto.getSolde());
        Cachier updated = cachierRepository.save(c);
		return mapToDTO(updated);
	}
	
	@Override
	public CachierDto get(Long id) {
		Cachier c = cachierRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("cachier", "id", id));
		return mapToDTO(c);
	}

}
