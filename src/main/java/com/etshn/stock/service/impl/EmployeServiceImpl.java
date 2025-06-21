package com.etshn.stock.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.etshn.stock.entity.Employes;
import com.etshn.stock.exception.ResourceNotFoundException;
import com.etshn.stock.payload.EmployeDto;
import com.etshn.stock.payload.EmployeResponce;
import com.etshn.stock.repository.EmployeRepository;
import com.etshn.stock.service.EmployeService;

@Service
public class EmployeServiceImpl implements EmployeService {
	
	private ModelMapper mapper;
	
	private EmployeRepository employeRepository;	
	
	public EmployeServiceImpl(ModelMapper mapper, EmployeRepository employeRepository) {
		super();
		this.mapper = mapper;
		this.employeRepository = employeRepository;
	}

	// convert Entity into DTO
    private EmployeDto mapToDTO(Employes entity){
    	EmployeDto dto = mapper.map(entity, EmployeDto.class);
        return dto;
    }
    
    // convert DTO to entity
    private Employes mapToEntity(EmployeDto dto){
    	Employes entity = mapper.map(dto, Employes.class);
        return entity;
    }

	@Override
	public EmployeDto add(EmployeDto EmployeDto) {
		Employes saved = employeRepository.save(mapToEntity(EmployeDto));
        return mapToDTO(saved);
	}

	@Override
	public EmployeDto get(Long EmployesId) {
		Employes c = employeRepository.findById(EmployesId)
				.orElseThrow(() -> new ResourceNotFoundException("Employes", "id", EmployesId));
		return mapToDTO(c);
	}

	@Override
	public EmployeDto update(EmployeDto EmployeDto, Long id) {
		Employes c = employeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employes", "id", id));  
        c.setName(EmployeDto.getName());
        c.setPhone(EmployeDto.getPhone());
        Employes updated = employeRepository.save(c);
		return mapToDTO(updated);
	}

	@Override
	public void delete(Long id) {
		Employes c = employeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employes", "id", id));
		employeRepository.delete(c);			
	}

	@Override
	public EmployeDto findByName(String name) {
		Employes c = employeRepository.findByName(name);
		return mapToDTO(c);
	}

	@Override
	public EmployeDto findByPhone(String phone) {
		Employes c = employeRepository.findByPhone(phone);
		return mapToDTO(c);
	}
	
	@Override
	public List<EmployeDto> findAll() {
		List<Employes> Employess = employeRepository.findAll();
        List<EmployeDto> all = Employess.stream().map(m -> mapToDTO(m)).collect(Collectors.toList());
		return all;
	}

	@Override
	public EmployeResponce findAll(int pageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Employes> Employess = employeRepository.findAll(pageable);

        // get content for page object
        List<Employes> list = Employess.getContent();

        List<EmployeDto> content= list.stream().map(m -> mapToDTO(m)).collect(Collectors.toList());

        EmployeResponce response = new EmployeResponce();
        
        response.setContent(content);
        response.setPageNo(Employess.getNumber());
        response.setPageSize(Employess.getSize());
        response.setTotalElements(Employess.getTotalElements());
        response.setTotalPages(Employess.getTotalPages());
        response.setLast(Employess.isLast());
		
		return response;
	}

}
