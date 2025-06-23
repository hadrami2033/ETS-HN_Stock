package com.etshn.stock.service.impl;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.etshn.stock.entity.Magasin;
import com.etshn.stock.entity.MagasinUnitsMouvments;
import com.etshn.stock.entity.Type;
import com.etshn.stock.entity.Units;
import com.etshn.stock.exception.ResourceNotFoundException;
import com.etshn.stock.payload.MagasinMouvmentUnitDto;
import com.etshn.stock.payload.MagasinMouvmentUnitResponse;
import com.etshn.stock.payload.ProductInMagasin;
import com.etshn.stock.repository.MagasinMouvmentsUnitsRepository;
import com.etshn.stock.repository.MagasinRepository;
import com.etshn.stock.repository.TypeRepository;
import com.etshn.stock.repository.UnitRepository;
import com.etshn.stock.service.MagasinMouvmentsUnitService;

@Service
public class MagasinMouvmentsUnitServiceImpl implements MagasinMouvmentsUnitService {

	private ModelMapper mapper;
	private UnitRepository unitRepository;
	private TypeRepository typeRepository;
	private MagasinRepository magasinRepository;
	private MagasinMouvmentsUnitsRepository magasinMouvmentsUnitsRepository;
	
	
	
	public MagasinMouvmentsUnitServiceImpl(ModelMapper mapper, UnitRepository unitRepository,
			TypeRepository typeRepository, MagasinRepository magasinRepository,
			MagasinMouvmentsUnitsRepository magasinMouvmentsUnitsRepository) {
		super();
		this.mapper = mapper;
		this.unitRepository = unitRepository;
		this.typeRepository = typeRepository;
		this.magasinRepository = magasinRepository;
		this.magasinMouvmentsUnitsRepository = magasinMouvmentsUnitsRepository;
	}
	
	// convert Entity into DTO
    private MagasinMouvmentUnitDto mapToDTO(MagasinUnitsMouvments magasinMouvement){
    	MagasinMouvmentUnitDto mouvementDto = mapper.map(magasinMouvement, MagasinMouvmentUnitDto.class);
        return mouvementDto;
    }
    
    // convert DTO to entity
    private MagasinUnitsMouvments mapToEntity(MagasinMouvmentUnitDto mouvementDto){
    	MagasinUnitsMouvments mouvment = mapper.map(mouvementDto, MagasinUnitsMouvments.class);
        return mouvment;
    }
    
  	@Override
  	public MagasinMouvmentUnitDto add(MagasinMouvmentUnitDto magasinMouvementDto) {
  		Units unit = unitRepository.findById(magasinMouvementDto.getUnitId())
                  .orElseThrow(() -> new ResourceNotFoundException("unit", "id", magasinMouvementDto.getUnitId()));

  		Magasin magasin = magasinRepository.findById(magasinMouvementDto.getMagasinId())
                  .orElseThrow(() -> new ResourceNotFoundException("Magasin", "id", magasinMouvementDto.getMagasinId()));

  		Type type = typeRepository.findById(magasinMouvementDto.getTypeId())
                  .orElseThrow(() -> new ResourceNotFoundException("Type", "id", magasinMouvementDto.getTypeId()));
  		
  		MagasinUnitsMouvments mouvement = mapToEntity(magasinMouvementDto);
  		mouvement.setUnit(unit);
  		mouvement.setType(type);
  		mouvement.setMagasin(magasin);
  		
  		MagasinUnitsMouvments saved = magasinMouvmentsUnitsRepository.save(mouvement);
          return mapToDTO(saved);
  	}

  	@Override
  	public MagasinMouvmentUnitDto get(Long id) {
  		MagasinUnitsMouvments m = magasinMouvmentsUnitsRepository.findById(id)
  				.orElseThrow(() -> new ResourceNotFoundException("MagasinMouvement", "id", id));
  		return mapToDTO(m);
  	}

  	@Override
  	public MagasinMouvmentUnitDto update(MagasinMouvmentUnitDto magasinMouvementDto, Long id) {
  		MagasinUnitsMouvments m = magasinMouvmentsUnitsRepository.findById(id)
  				.orElseThrow(() -> new ResourceNotFoundException("Mouvment", "id", id));
          
  		m.setId(magasinMouvementDto.getId());
          m.setQuantity(magasinMouvementDto.getQuantity());

          MagasinUnitsMouvments updatedMouvment = magasinMouvmentsUnitsRepository.save(m);

  		return mapToDTO(updatedMouvment);
  	}

  	@Override
  	public void delete(Long id) {
  		MagasinUnitsMouvments m = magasinMouvmentsUnitsRepository.findById(id)
  				.orElseThrow(() -> new ResourceNotFoundException("Mouvment", "id", id));
  		magasinMouvmentsUnitsRepository.delete(m);			
  	}

  	@Override
  	public MagasinMouvmentUnitResponse getByMagasinAndInterval(Long typeId, Long magasinId, String unit, Date startDate, Date andDate, int pageNo,
  			int pageSize, String sortBy, String sortDir) {
  		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                  : Sort.by(sortBy).descending();

          // create Pageable instance
          Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
          
          Page<MagasinUnitsMouvments> mouvments = magasinMouvmentsUnitsRepository.findByTypeIdAndMagasinIdAndUnitNomContainingAndDateCreationBetweenOrderByDateCreationDesc(typeId, magasinId, unit, startDate, andDate, pageable);
          List<MagasinUnitsMouvments> list = mouvments.getContent();

          List<MagasinMouvmentUnitDto> content= list.stream().map(m -> mapToDTO(m)).collect(Collectors.toList());
          MagasinMouvmentUnitResponse mouvementResponse = new MagasinMouvmentUnitResponse();
          
          mouvementResponse.setContent(content);
          mouvementResponse.setPageNo(mouvments.getNumber());
          mouvementResponse.setPageSize(mouvments.getSize());
          mouvementResponse.setTotalElements(mouvments.getTotalElements());
          mouvementResponse.setTotalPages(mouvments.getTotalPages());
          mouvementResponse.setLast(mouvments.isLast());

  		return mouvementResponse;
  	}

  	@Override
  	public MagasinMouvmentUnitResponse getByMagasinAndUnit(Long magasinId, Long unitId, int pageNo, int pageSize,
  			String sortBy, String sortDir) {
  		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                  : Sort.by(sortBy).descending();

          // create Pageable instance
          Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
          
          Page<MagasinUnitsMouvments> mouvments = magasinMouvmentsUnitsRepository.findByMagasinIdAndUnitIdOrderByDateCreationDesc(magasinId, unitId, pageable);
          List<MagasinUnitsMouvments> list = mouvments.getContent();

          List<MagasinMouvmentUnitDto> content= list.stream().map(m -> mapToDTO(m)).collect(Collectors.toList());
          MagasinMouvmentUnitResponse mouvementResponse = new MagasinMouvmentUnitResponse();
          
          mouvementResponse.setContent(content);
          mouvementResponse.setPageNo(mouvments.getNumber());
          mouvementResponse.setPageSize(mouvments.getSize());
          mouvementResponse.setTotalElements(mouvments.getTotalElements());
          mouvementResponse.setTotalPages(mouvments.getTotalPages());
          mouvementResponse.setLast(mouvments.isLast());

  		return mouvementResponse;
  	}

  	@Override
  	public MagasinMouvmentUnitResponse getByMagasinAndType(Long magasinId, Long typeId, int pageNo, int pageSize,
  			String sortBy, String sortDir) {
  		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                  : Sort.by(sortBy).descending();

          // create Pageable instance
          Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
          
          Page<MagasinUnitsMouvments> mouvments = magasinMouvmentsUnitsRepository.findByMagasinIdAndTypeIdOrderByDateCreationDesc(magasinId, typeId, pageable);
          List<MagasinUnitsMouvments> list = mouvments.getContent();

          List<MagasinMouvmentUnitDto> content= list.stream().map(m -> mapToDTO(m)).collect(Collectors.toList());
          MagasinMouvmentUnitResponse mouvementResponse = new MagasinMouvmentUnitResponse();
          
          mouvementResponse.setContent(content);
          mouvementResponse.setPageNo(mouvments.getNumber());
          mouvementResponse.setPageSize(mouvments.getSize());
          mouvementResponse.setTotalElements(mouvments.getTotalElements());
          mouvementResponse.setTotalPages(mouvments.getTotalPages());
          mouvementResponse.setLast(mouvments.isLast());

  		return mouvementResponse;
  	}

  	@Override
  	public ProductInMagasin getStockUnitInMagasin(Long magasinId, Long unitId) {
  		List<Integer> quantityList = magasinMouvmentsUnitsRepository.getStockUnitInMagasin(magasinId, unitId);
  		int q = (quantityList.size() > 0 && quantityList.get(0) != null ) ? quantityList.get(0) : 0;
  		ProductInMagasin quantity = new ProductInMagasin(q);
  		return quantity;
  	}
	
}
