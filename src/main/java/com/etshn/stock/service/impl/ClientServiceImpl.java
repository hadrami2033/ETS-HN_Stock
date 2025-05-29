package com.etshn.stock.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.etshn.stock.entity.Client;
import com.etshn.stock.exception.ResourceNotFoundException;
import com.etshn.stock.payload.ClientDto;
import com.etshn.stock.payload.ClientResponce;
import com.etshn.stock.repository.ClientRepository;
import com.etshn.stock.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

	private ModelMapper mapper;
	
	private ClientRepository clientRepository;	
	
	public ClientServiceImpl(ModelMapper mapper, ClientRepository clientRepository) {
		super();
		this.mapper = mapper;
		this.clientRepository = clientRepository;
	}
	
	// convert Entity into DTO
    private ClientDto mapToDTO(Client entity){
    	ClientDto dto = mapper.map(entity, ClientDto.class);
        return dto;
    }
    
    // convert DTO to entity
    private Client mapToEntity(ClientDto dto){
    	Client entity = mapper.map(dto, Client.class);
        return entity;
    }

	@Override
	public ClientDto add(ClientDto clientDto) {
		Client saved = clientRepository.save(mapToEntity(clientDto));
        return mapToDTO(saved);
	}

	@Override
	public ClientDto get(Long clientId) {
		Client c = clientRepository.findById(clientId)
				.orElseThrow(() -> new ResourceNotFoundException("client", "id", clientId));
		return mapToDTO(c);
	}

	@Override
	public ClientDto update(ClientDto clientDto, Long id) {
		Client c = clientRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Client", "id", id));  
        c.setName(clientDto.getName());
        c.setPhone(clientDto.getPhone());
        Client updated = clientRepository.save(c);
		return mapToDTO(updated);
	}

	@Override
	public void delete(Long id) {
		Client c = clientRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Client", "id", id));
		clientRepository.delete(c);			
	}

	@Override
	public ClientDto findByName(String name) {
		Client c = clientRepository.findByName(name);
		return mapToDTO(c);
	}

	@Override
	public ClientDto findByPhone(String phone) {
		Client c = clientRepository.findByPhone(phone);
		return mapToDTO(c);
	}
	
	@Override
	public List<ClientDto> findAll() {
		List<Client> clients = clientRepository.findAll();
        List<ClientDto> all = clients.stream().map(m -> mapToDTO(m)).collect(Collectors.toList());
		return all;
	}

	@Override
	public ClientResponce findAll(int pageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Client> clients = clientRepository.findAll(pageable);

        // get content for page object
        List<Client> list = clients.getContent();

        List<ClientDto> content= list.stream().map(m -> mapToDTO(m)).collect(Collectors.toList());

        ClientResponce response = new ClientResponce();
        
        response.setContent(content);
        response.setPageNo(clients.getNumber());
        response.setPageSize(clients.getSize());
        response.setTotalElements(clients.getTotalElements());
        response.setTotalPages(clients.getTotalPages());
        response.setLast(clients.isLast());
		
		return response;
	}

}
