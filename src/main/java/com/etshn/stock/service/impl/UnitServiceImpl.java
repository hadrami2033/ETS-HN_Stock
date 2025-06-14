package com.etshn.stock.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.etshn.stock.entity.Product;
import com.etshn.stock.entity.Units;
import com.etshn.stock.exception.ResourceNotFoundException;
import com.etshn.stock.payload.UnitDto;
import com.etshn.stock.repository.ProductRepository;
import com.etshn.stock.repository.UnitRepository;
import com.etshn.stock.service.UnitService;

@Service
public class UnitServiceImpl implements UnitService {
	
    private UnitRepository unitRepository;    
	private ModelMapper mapper;
    private ProductRepository productRepository;    

    
	public UnitServiceImpl(UnitRepository unitRepository, ModelMapper mapper, ProductRepository productRepository) {
		super();
		this.unitRepository = unitRepository;
		this.mapper = mapper;
		this.productRepository = productRepository;
		mapper.getConfiguration().setAmbiguityIgnored(true);
	}
	
	// convert Entity into DTO
    private UnitDto mapToDTO(Units unit){
    	UnitDto unitDto = mapper.map(unit, UnitDto.class);
        return unitDto;
    }
    
    // convert DTO to entity
    private Units mapToEntity(UnitDto unitDto){
    	Units unit = mapper.map(unitDto, Units.class);
        return unit;
    }
	

	@Override
	public UnitDto add(UnitDto unitDto) {
		Product product = productRepository.findById(unitDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", unitDto.getProductId()));
		Units unit = mapToEntity(unitDto);
		unit.setProduct(product);
		Units saved = unitRepository.save(unit);

		return mapToDTO(saved);
	}

	@Override
	public UnitDto get(Long id) {
		Units u = unitRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("unit", "id", id));
		return mapToDTO(u);
	}

	@Override
	public UnitDto update(UnitDto unitDto, Long id) {
		Units u = unitRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("unit", "id", id));
		u.setId(unitDto.getId());
        u.setNom(unitDto.getNom());
        u.setPrixVente(unitDto.getPrixVente());
        u.setQuantite(unitDto.getQuantite());
        
		Units saved = unitRepository.save(u);

		return mapToDTO(saved);
	}

	@Override
	public void delete(Long id) {
		Units u = unitRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("unit", "id", id));
		unitRepository.delete(u);
	}

	@Override
	public UnitDto findByProductId(Long productId) {
		Units u = unitRepository.findByProductId(productId);
		return mapToDTO(u);
	}

}
