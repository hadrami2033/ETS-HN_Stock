package com.etshn.stock.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.etshn.stock.entity.Product;
import com.etshn.stock.exception.ResourceNotFoundException;
import com.etshn.stock.payload.ProductDto;
import com.etshn.stock.payload.ProductResponce;
import com.etshn.stock.repository.ProductRepository;
import com.etshn.stock.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

	private ModelMapper mapper;
	
    private ProductRepository productRepository;    
    
	public ProductServiceImpl(ModelMapper mapper, ProductRepository productRepository) {
		super();
		this.mapper = mapper;
		this.productRepository = productRepository;
	}
	
	// convert Entity into DTO
    private ProductDto mapToDTO(Product product){
    	ProductDto productDto = mapper.map(product, ProductDto.class);
        return productDto;
    }
    
    // convert DTO to entity
    private Product mapToEntity(ProductDto productDto){
    	Product product = mapper.map(productDto, Product.class);
        return product;
    }

	@Override
	public ProductDto add(ProductDto productDto) {
		Product saved = productRepository.save(mapToEntity(productDto));
        return mapToDTO(saved);
	}

	@Override
	public ProductDto get(Long productId) {
		Product p = productRepository.findById(productId)
			.orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
		return mapToDTO(p);
	}

	@Override
	public ProductDto update(ProductDto productDto, Long id) {
		Product p = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        
		p.setId(productDto.getId());
        p.setDescription(productDto.getDescription());
        p.setNom(productDto.getNom());
        p.setPrixAchat(productDto.getPrixAchat());
        p.setPrixVente(productDto.getPrixVente());
        p.setQuantiteEnStock(productDto.getQuantiteEnStock());
        p.setDateCreation(productDto.getDateCreation());

        Product updatedProduct = productRepository.save(p);

		return mapToDTO(updatedProduct);
	}

	@Override
	public void delete(Long id) {
		Product p = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
      	productRepository.delete(p);
	}

	@Override
	public ProductDto findByNom(String nom) {
		Product p = productRepository.findByNom(nom);
     	return mapToDTO(p);
	}

	@Override
	public ProductResponce findAllDisponible(String nom, int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        //Page<Operation> operations = operationRepository.findAll(pageable);
        Page<Product> products = productRepository.findByNomContainingAndQuantiteEnStockGreaterThanOrderByDateCreationDesc(nom, 0, pageable);

        // get content for page object
        List<Product> listOfOperations = products.getContent();

        List<ProductDto> content= listOfOperations.stream().map(product -> mapToDTO(product)).collect(Collectors.toList());

        ProductResponce productResponse = new ProductResponce();
        
        productResponse.setContent(content);
        productResponse.setPageNo(products.getNumber());
        productResponse.setPageSize(products.getSize());
        productResponse.setTotalElements(products.getTotalElements());
        productResponse.setTotalPages(products.getTotalPages());
        productResponse.setLast(products.isLast());
		
		return productResponse;
	}
	
	@Override
	public ProductResponce findAllIndisponible(String nom, int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        //Page<Operation> operations = operationRepository.findAll(pageable);
        Page<Product> products = productRepository.findByNomContainingAndQuantiteEnStockEqualsOrderByDateCreationDesc(nom, 0, pageable);

        // get content for page object
        List<Product> listOfOperations = products.getContent();

        List<ProductDto> content= listOfOperations.stream().map(product -> mapToDTO(product)).collect(Collectors.toList());

        ProductResponce productResponse = new ProductResponce();
        
        productResponse.setContent(content);
        productResponse.setPageNo(products.getNumber());
        productResponse.setPageSize(products.getSize());
        productResponse.setTotalElements(products.getTotalElements());
        productResponse.setTotalPages(products.getTotalPages());
        productResponse.setLast(products.isLast());
		
		return productResponse;
	}
	
	@Override
	public ProductResponce findAll(String nom, int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        //Page<Operation> operations = operationRepository.findAll(pageable);
        Page<Product> products = productRepository.findByNomContainingOrderByDateCreationDesc(nom, pageable);

        // get content for page object
        List<Product> listOfOperations = products.getContent();

        List<ProductDto> content= listOfOperations.stream().map(product -> mapToDTO(product)).collect(Collectors.toList());

        ProductResponce productResponse = new ProductResponce();
        
        productResponse.setContent(content);
        productResponse.setPageNo(products.getNumber());
        productResponse.setPageSize(products.getSize());
        productResponse.setTotalElements(products.getTotalElements());
        productResponse.setTotalPages(products.getTotalPages());
        productResponse.setLast(products.isLast());
		
		return productResponse;
	}


}
