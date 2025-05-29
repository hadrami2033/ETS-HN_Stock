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
import com.etshn.stock.entity.MagasinMouvement;
import com.etshn.stock.entity.Product;
import com.etshn.stock.entity.Type;
import com.etshn.stock.exception.ResourceNotFoundException;
import com.etshn.stock.payload.MagasinMouvementDto;
import com.etshn.stock.payload.MagasinMouvementResponse;
import com.etshn.stock.payload.ProductInMagasin;
import com.etshn.stock.repository.MagasinMouvmentsRepository;
import com.etshn.stock.repository.MagasinRepository;
import com.etshn.stock.repository.ProductRepository;
import com.etshn.stock.repository.TypeRepository;
import com.etshn.stock.service.MagasinMouvementService;

@Service
public class MagasinMouvementServiceImpl implements MagasinMouvementService {

	private ModelMapper mapper;
	private ProductRepository productRepository;
	private TypeRepository typeRepository;
	private MagasinRepository magasinRepository;
	private MagasinMouvmentsRepository magasinMouvmentsRepository;

	public MagasinMouvementServiceImpl(ModelMapper mapper, ProductRepository productRepository,
			TypeRepository typeRepository, MagasinRepository magasinRepository,
			MagasinMouvmentsRepository magasinMouvmentsRepository) {
		super();
		this.mapper = mapper;
		this.productRepository = productRepository;
		this.typeRepository = typeRepository;
		this.magasinRepository = magasinRepository;
		this.magasinMouvmentsRepository = magasinMouvmentsRepository;
	}

	// convert Entity into DTO
    private MagasinMouvementDto mapToDTO(MagasinMouvement magasinMouvement){
    	MagasinMouvementDto mouvementDto = mapper.map(magasinMouvement, MagasinMouvementDto.class);
        return mouvementDto;
    }
    
    // convert DTO to entity
    private MagasinMouvement mapToEntity(MagasinMouvementDto mouvementDto){
    	MagasinMouvement mouvment = mapper.map(mouvementDto, MagasinMouvement.class);
        return mouvment;
    }
    
    
    
	@Override
	public MagasinMouvementDto add(MagasinMouvementDto magasinMouvementDto) {
		Product product = productRepository.findById(magasinMouvementDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", magasinMouvementDto.getProductId()));

		Magasin magasin = magasinRepository.findById(magasinMouvementDto.getMagasinId())
                .orElseThrow(() -> new ResourceNotFoundException("Magasin", "id", magasinMouvementDto.getMagasinId()));

		Type type = typeRepository.findById(magasinMouvementDto.getTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Type", "id", magasinMouvementDto.getTypeId()));
		
		MagasinMouvement mouvement = mapToEntity(magasinMouvementDto);
		mouvement.setProduct(product);
		mouvement.setType(type);
		mouvement.setMagasin(magasin);
		
		MagasinMouvement saved = magasinMouvmentsRepository.save(mouvement);
        return mapToDTO(saved);
	}

	@Override
	public MagasinMouvementDto get(Long id) {
		MagasinMouvement m = magasinMouvmentsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("MagasinMouvement", "id", id));
		return mapToDTO(m);
	}

	@Override
	public MagasinMouvementDto update(MagasinMouvementDto magasinMouvementDto, Long id) {
		MagasinMouvement m = magasinMouvmentsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Mouvment", "id", id));
        
		m.setId(magasinMouvementDto.getId());
        m.setQuantity(magasinMouvementDto.getQuantity());

        MagasinMouvement updatedMouvment = magasinMouvmentsRepository.save(m);

		return mapToDTO(updatedMouvment);
	}

	@Override
	public void delete(Long id) {
		MagasinMouvement m = magasinMouvmentsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Mouvment", "id", id));
		magasinMouvmentsRepository.delete(m);			
	}

	@Override
	public MagasinMouvementResponse getByMagasinAndInterval(Long typeId, Long magasinId, String product, Date startDate, Date andDate, int pageNo,
			int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        
        Page<MagasinMouvement> mouvments = magasinMouvmentsRepository.findByTypeIdAndMagasinIdAndProductNomContainingAndDateCreationBetweenOrderByDateCreationDesc(typeId, magasinId, product, startDate, andDate, pageable);
        List<MagasinMouvement> list = mouvments.getContent();

        List<MagasinMouvementDto> content= list.stream().map(m -> mapToDTO(m)).collect(Collectors.toList());
        MagasinMouvementResponse mouvementResponse = new MagasinMouvementResponse();
        
        mouvementResponse.setContent(content);
        mouvementResponse.setPageNo(mouvments.getNumber());
        mouvementResponse.setPageSize(mouvments.getSize());
        mouvementResponse.setTotalElements(mouvments.getTotalElements());
        mouvementResponse.setTotalPages(mouvments.getTotalPages());
        mouvementResponse.setLast(mouvments.isLast());

		return mouvementResponse;
	}

	@Override
	public MagasinMouvementResponse getByMagasinAndProduct(Long magasinId, Long productId, int pageNo, int pageSize,
			String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        
        Page<MagasinMouvement> mouvments = magasinMouvmentsRepository.findByMagasinIdAndProductIdOrderByDateCreationDesc(magasinId, productId, pageable);
        List<MagasinMouvement> list = mouvments.getContent();

        List<MagasinMouvementDto> content= list.stream().map(m -> mapToDTO(m)).collect(Collectors.toList());
        MagasinMouvementResponse mouvementResponse = new MagasinMouvementResponse();
        
        mouvementResponse.setContent(content);
        mouvementResponse.setPageNo(mouvments.getNumber());
        mouvementResponse.setPageSize(mouvments.getSize());
        mouvementResponse.setTotalElements(mouvments.getTotalElements());
        mouvementResponse.setTotalPages(mouvments.getTotalPages());
        mouvementResponse.setLast(mouvments.isLast());

		return mouvementResponse;
	}

	@Override
	public MagasinMouvementResponse getByMagasinAndType(Long magasinId, Long typeId, int pageNo, int pageSize,
			String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        
        Page<MagasinMouvement> mouvments = magasinMouvmentsRepository.findByMagasinIdAndTypeIdOrderByDateCreationDesc(magasinId, typeId, pageable);
        List<MagasinMouvement> list = mouvments.getContent();

        List<MagasinMouvementDto> content= list.stream().map(m -> mapToDTO(m)).collect(Collectors.toList());
        MagasinMouvementResponse mouvementResponse = new MagasinMouvementResponse();
        
        mouvementResponse.setContent(content);
        mouvementResponse.setPageNo(mouvments.getNumber());
        mouvementResponse.setPageSize(mouvments.getSize());
        mouvementResponse.setTotalElements(mouvments.getTotalElements());
        mouvementResponse.setTotalPages(mouvments.getTotalPages());
        mouvementResponse.setLast(mouvments.isLast());

		return mouvementResponse;
	}

	@Override
	public ProductInMagasin getStockProductInMagasin(Long magasinId, Long productId) {
		List<Integer> quantityList = magasinMouvmentsRepository.getStockProductInMagasin(magasinId, productId);
		int q = (quantityList.size() > 0 && quantityList.get(0) != null ) ? quantityList.get(0) : 0;
		ProductInMagasin quantity = new ProductInMagasin(q);
		return quantity;
	}

}
