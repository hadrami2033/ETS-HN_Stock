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

import com.etshn.stock.entity.Mouvment;
import com.etshn.stock.entity.Product;
import com.etshn.stock.entity.Type;
import com.etshn.stock.entity.Units;
import com.etshn.stock.entity.User;
import com.etshn.stock.exception.ResourceNotFoundException;
import com.etshn.stock.payload.MouvementDto;
import com.etshn.stock.payload.MouvementResponse;
import com.etshn.stock.repository.MouvmentRepository;
import com.etshn.stock.repository.ProductRepository;
import com.etshn.stock.repository.TypeRepository;
import com.etshn.stock.repository.UnitRepository;
import com.etshn.stock.repository.UserRepository;
import com.etshn.stock.service.MouvementService;

@Service
public class MouvementServiceImpl implements MouvementService{

	private ModelMapper mapper;
	
	private MouvmentRepository mouvmentRepository;
	
	private ProductRepository productRepository;
	
	private UnitRepository unitRepository;
	
	private UserRepository userRepository;
	
	private TypeRepository typeRepository;



	public MouvementServiceImpl(ModelMapper mapper, MouvmentRepository mouvmentRepository,
			ProductRepository productRepository, UserRepository userRepository, TypeRepository typeRepository, UnitRepository unitRepository) {
		super();
		mapper.getConfiguration().setAmbiguityIgnored(true);
		this.mapper = mapper;
		this.mouvmentRepository = mouvmentRepository;
		this.productRepository = productRepository;
		this.userRepository = userRepository;
		this.typeRepository = typeRepository;
		this.unitRepository = unitRepository;
	}

	// convert Entity into DTO
    private MouvementDto mapToDTO(Mouvment mouvment){
    	MouvementDto mouvementDto = mapper.map(mouvment, MouvementDto.class);
        return mouvementDto;
    }
    
    // convert DTO to entity
    private Mouvment mapToEntity(MouvementDto mouvementDto){
    	Mouvment mouvment = mapper.map(mouvementDto, Mouvment.class);
        return mouvment;
    }
    
	@Override
	public MouvementDto add(MouvementDto mouvementDto) {
		Product product = null;
		Units unit = null;
		if(mouvementDto.getProductId() != null)
		product = productRepository.findById(mouvementDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", mouvementDto.getProductId()));
		if(mouvementDto.getUnitId() != null)
		unit = unitRepository.findById(mouvementDto.getUnitId())
                .orElseThrow(() -> new ResourceNotFoundException("Unit", "id", mouvementDto.getUnitId()));
		
		User user = userRepository.findById(mouvementDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", mouvementDto.getUserId()));
		
		Type type = typeRepository.findById(mouvementDto.getTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Type", "id", mouvementDto.getTypeId()));
		
		//Client client = clientRepository.findById(mouvementDto.getClientId())
         //       .orElseThrow(() -> new ResourceNotFoundException("Client", "id", mouvementDto.getClientId()));
		
		Mouvment mouvement = mapToEntity(mouvementDto);
		if(product != null)
		mouvement.setProduct(product);
		if(unit != null)
		mouvement.setUnit(unit);
		mouvement.setType(type);
		mouvement.setUser(user);
		//mouvement.setClient(client);
		

		Mouvment saved = mouvmentRepository.save(mouvement);
        return mapToDTO(saved);
	}

	@Override
	public MouvementDto get(Long mouvementId) {
		Mouvment m = mouvmentRepository.findById(mouvementId)
				.orElseThrow(() -> new ResourceNotFoundException("mouvment", "id", mouvementId));
		return mapToDTO(m);
	}

	@Override
	public MouvementDto update(MouvementDto mouvementDto, Long id) {
		Mouvment m = mouvmentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Mouvment", "id", id));
        
		m.setId(mouvementDto.getId());
        m.setQuantity(mouvementDto.getQuantity());
        m.setDescription(mouvementDto.getDescription());
        m.setUpdatedAt(mouvementDto.getUpdatedAt());

        Mouvment updatedMouvment = mouvmentRepository.save(m);

		return mapToDTO(updatedMouvment);
	}

	@Override
	public void delete(Long id) {
		Mouvment m = mouvmentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Mouvment", "id", id));
		mouvmentRepository.delete(m);		
	}

	@Override
	public MouvementResponse getByType(Long typeId, int pageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Mouvment> mouvments = mouvmentRepository.findByTypeIdOrderByDateCreationDesc(typeId, pageable);

        // get content for page object
        List<Mouvment> list = mouvments.getContent();

        List<MouvementDto> content= list.stream().map(m -> mapToDTO(m)).collect(Collectors.toList());

        MouvementResponse mouvementResponse = new MouvementResponse();
        
        mouvementResponse.setContent(content);
        mouvementResponse.setPageNo(mouvments.getNumber());
        mouvementResponse.setPageSize(mouvments.getSize());
        mouvementResponse.setTotalElements(mouvments.getTotalElements());
        mouvementResponse.setTotalPages(mouvments.getTotalPages());
        mouvementResponse.setLast(mouvments.isLast());
		
		return mouvementResponse;
	}

	@Override
	public MouvementResponse getByTypeAndInterval(Long typeId, Date startDate, Date andDate, int pageNo, int pageSize,
			String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Mouvment> mouvments = mouvmentRepository.findByTypeIdAndDateCreationBetweenOrderByDateCreationDesc(typeId, startDate, andDate, pageable);

        // get content for page object
        List<Mouvment> list = mouvments.getContent();

        List<MouvementDto> content= list.stream().map(m -> mapToDTO(m)).collect(Collectors.toList());

        MouvementResponse mouvementResponse = new MouvementResponse();
        
        mouvementResponse.setContent(content);
        mouvementResponse.setPageNo(mouvments.getNumber());
        mouvementResponse.setPageSize(mouvments.getSize());
        mouvementResponse.setTotalElements(mouvments.getTotalElements());
        mouvementResponse.setTotalPages(mouvments.getTotalPages());
        mouvementResponse.setLast(mouvments.isLast());
		
		return mouvementResponse;
	}

	@Override
	public MouvementResponse getByTypeAndProduct(Long typeId, Long productId, int pageNo, int pageSize, String sortBy,
			String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Mouvment> mouvments = mouvmentRepository.findByTypeIdAndProductIdOrderByDateCreationDesc(typeId, productId, pageable);

        // get content for page object
        List<Mouvment> list = mouvments.getContent();

        List<MouvementDto> content= list.stream().map(m -> mapToDTO(m)).collect(Collectors.toList());

        MouvementResponse mouvementResponse = new MouvementResponse();
        
        mouvementResponse.setContent(content);
        mouvementResponse.setPageNo(mouvments.getNumber());
        mouvementResponse.setPageSize(mouvments.getSize());
        mouvementResponse.setTotalElements(mouvments.getTotalElements());
        mouvementResponse.setTotalPages(mouvments.getTotalPages());
        mouvementResponse.setLast(mouvments.isLast());
		
		return mouvementResponse;
	}

}
