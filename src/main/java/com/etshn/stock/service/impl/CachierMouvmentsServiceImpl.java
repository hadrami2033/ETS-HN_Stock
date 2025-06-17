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

import com.etshn.stock.entity.CachierMouvments;
import com.etshn.stock.payload.CachierMouvmentsDto;
import com.etshn.stock.payload.CachierMouvmentsResponce;
import com.etshn.stock.repository.CachierMouvmentsRepository;
import com.etshn.stock.service.CachierMouvementsService;

@Service
public class CachierMouvmentsServiceImpl implements CachierMouvementsService{

	private ModelMapper mapper;
	
	private CachierMouvmentsRepository mouvmentRepository;
	
	public CachierMouvmentsServiceImpl(ModelMapper mapper, CachierMouvmentsRepository mouvmentRepository) {
		super();
		this.mapper = mapper;
		this.mouvmentRepository = mouvmentRepository;
	}
	
	// convert Entity into DTO
    private CachierMouvmentsDto mapToDTO(CachierMouvments mouvment){
    	CachierMouvmentsDto mouvementDto = mapper.map(mouvment, CachierMouvmentsDto.class);
        return mouvementDto;
    }
    
    // convert DTO to entity
    private CachierMouvments mapToEntity(CachierMouvmentsDto mouvementDto){
    	CachierMouvments mouvment = mapper.map(mouvementDto, CachierMouvments.class);
        return mouvment;
    }

	@Override
	public CachierMouvmentsDto add(CachierMouvmentsDto mouvmentDto) {
		CachierMouvments saved = mouvmentRepository.save(mapToEntity(mouvmentDto));
        return mapToDTO(saved);
	}
	
	@Override
	public CachierMouvmentsResponce getByInterval(Date startDate, Date andDate, int pageNo, int pageSize, String sortBy,
			String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<CachierMouvments> mouvments = mouvmentRepository.findByDateCreationBetweenOrderByDateCreationDesc(startDate, andDate, pageable);

        // get content for page object
        List<CachierMouvments> list = mouvments.getContent();

        List<CachierMouvmentsDto> content= list.stream().map(m -> mapToDTO(m)).collect(Collectors.toList());

        CachierMouvmentsResponce mouvementResponse = new CachierMouvmentsResponce();
        
        mouvementResponse.setContent(content);
        mouvementResponse.setPageNo(mouvments.getNumber());
        mouvementResponse.setPageSize(mouvments.getSize());
        mouvementResponse.setTotalElements(mouvments.getTotalElements());
        mouvementResponse.setTotalPages(mouvments.getTotalPages());
        mouvementResponse.setLast(mouvments.isLast());
		
		return mouvementResponse;

	}

}
