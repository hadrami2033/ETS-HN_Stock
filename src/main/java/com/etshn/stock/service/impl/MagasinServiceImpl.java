package com.etshn.stock.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.etshn.stock.entity.Magasin;
import com.etshn.stock.exception.ResourceNotFoundException;
import com.etshn.stock.payload.MagasinDto;
import com.etshn.stock.repository.MagasinRepository;
import com.etshn.stock.service.MagasinService;

@Service
public class MagasinServiceImpl implements MagasinService {
	private ModelMapper mapper;
	
	private MagasinRepository magasinRepository;		
	
	public MagasinServiceImpl(ModelMapper mapper, MagasinRepository magasinRepository) {
		super();
		this.mapper = mapper;
		this.magasinRepository = magasinRepository;
	}
	
	// convert Entity into DTO
    private MagasinDto mapToDTO(Magasin entity){
    	MagasinDto dto = mapper.map(entity, MagasinDto.class);
        return dto;
    }
    
    // convert DTO to entity
    private Magasin mapToEntity(MagasinDto dto){
    	Magasin entity = mapper.map(dto, Magasin.class);
        return entity;
    }

	@Override
	public MagasinDto add(MagasinDto magasinDto) {
		Magasin saved = magasinRepository.save(mapToEntity(magasinDto));
        return mapToDTO(saved);
	}

	@Override
	public MagasinDto get(Long id) {
		Magasin m = magasinRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("magasin", "id", id));
		return mapToDTO(m);
	}

	@Override
	public MagasinDto update(MagasinDto magasinDto, Long id) {
		Magasin m = magasinRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("magasin", "id", id));
        m.setLabel(magasinDto.getLabel());
        Magasin updated = magasinRepository.save(m);
		return mapToDTO(updated);
	}

	@Override
	public void delete(Long id) {
		Magasin m = magasinRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("magasin", "id", id));		
		magasinRepository.delete(m);			
	}

	@Override
	public MagasinDto findByLabel(String label) {
		Magasin m = magasinRepository.findByLabel(label);
		return mapToDTO(m);
	}

	@Override
	public List<MagasinDto> findAll() {
		List<Magasin> magasins = magasinRepository.findAll();
        List<MagasinDto> all = magasins.stream().map(m -> mapToDTO(m)).collect(Collectors.toList());
		return all;
	}

}
