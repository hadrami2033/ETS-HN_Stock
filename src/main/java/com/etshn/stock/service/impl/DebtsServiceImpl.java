package com.etshn.stock.service.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.etshn.stock.entity.Client;
import com.etshn.stock.entity.Debts;
import com.etshn.stock.exception.ResourceNotFoundException;
import com.etshn.stock.payload.DebtClientDto;
import com.etshn.stock.payload.DebtsDto;
import com.etshn.stock.payload.DebtsResponce;
import com.etshn.stock.repository.ClientRepository;
import com.etshn.stock.repository.DebtsRepository;
import com.etshn.stock.service.DebtsService;

@Service
public class DebtsServiceImpl implements DebtsService {

	private ModelMapper mapper;
	
	private DebtsRepository debtsRepository;
	
	private ClientRepository clientRepository;

	
	
	
	public DebtsServiceImpl(ModelMapper mapper, DebtsRepository debtsRepository, ClientRepository clientRepository) {
		super();
		mapper.getConfiguration().setAmbiguityIgnored(true);
		this.mapper = mapper;
		this.debtsRepository = debtsRepository;
		this.clientRepository = clientRepository;
	}
	
	// convert Entity into DTO
    private DebtsDto mapToDTO(Debts entity){
    	DebtsDto dto = mapper.map(entity, DebtsDto.class);
        return dto;
    }
    
	/*
	 * private ClientDto mapClientToDTO(Client entity){ ClientDto dto =
	 * mapper.map(entity, ClientDto.class); return dto; }
	 */
    
    // convert DTO to entity
    private Debts mapToEntity(DebtsDto dto){
    	Debts entity = mapper.map(dto, Debts.class);
        return entity;
    }

	@Override
	public DebtsDto add(DebtsDto debtsDto) {		
		Client client = clientRepository.findById(debtsDto.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client", "id", debtsDto.getClientId()));
		//debtsDto.setClient(mapClientToDTO(client));
		Debts debts = mapToEntity(debtsDto);
		debts.setClient(client);
		
		Debts saved = debtsRepository.save(debts);
        return mapToDTO(saved);
	}

	@Override
	public DebtsDto get(Long debttId) {
		Debts d = debtsRepository.findById(debttId)
				.orElseThrow(() -> new ResourceNotFoundException("debt", "id", debttId));
		return mapToDTO(d);
	}

	@Override
	public DebtsDto update(DebtsDto debtsDto, Long id) {
		Debts d = debtsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Debts", "id", id));
        
        d.setPayed(debtsDto.getPayed());
        d.setDescription(debtsDto.getDescription());
        d.setAmount(debtsDto.getAmount());

        Debts updated = debtsRepository.save(d);

		return mapToDTO(updated);
	}

	@Override
	public void delete(Long id) {
		Debts d = debtsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Debts", "id", id));
		debtsRepository.delete(d);			
	}

	@Override
	public DebtsResponce findByClientAndPayed(Long clientId, Integer payed, int pageNo, int pageSize, String sortBy,
			String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Debts> debts = debtsRepository.findByClientIdAndPayedOrderByDateCreationDesc(clientId, payed, pageable);

        // get content for page object
        List<Debts> list = debts.getContent();

        List<DebtsDto> content= list.stream().map(m -> mapToDTO(m)).collect(Collectors.toList());

        DebtsResponce response = new DebtsResponce();
        
        response.setContent(content);
        response.setPageNo(debts.getNumber());
        response.setPageSize(debts.getSize());
        response.setTotalElements(debts.getTotalElements());
        response.setTotalPages(debts.getTotalPages());
        response.setLast(debts.isLast());
		
		return response;	
	}

	@Override
	public DebtsResponce findByPayedAndInterval(Integer payed, Date startDate, Date andDate, int pageNo, int pageSize,
			String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Debts> debts = debtsRepository.findByPayedAndDateCreationBetweenOrderByDateCreationDesc(payed, startDate, andDate, pageable);

        // get content for page object
        List<Debts> list = debts.getContent();

        List<DebtsDto> content= list.stream().map(m -> mapToDTO(m)).collect(Collectors.toList());

        DebtsResponce response = new DebtsResponce();
        
        response.setContent(content);
        response.setPageNo(debts.getNumber());
        response.setPageSize(debts.getSize());
        response.setTotalElements(debts.getTotalElements());
        response.setTotalPages(debts.getTotalPages());
        response.setLast(debts.isLast());
		
		return response;	
	}

	@Override
	public DebtClientDto getClientDebts(Long clientId, Integer payed) {
		List<BigDecimal> debtList = debtsRepository.getClientDebts(clientId, payed);
		BigDecimal debt = (debtList.size() > 0 && debtList.get(0) != null ) ? debtList.get(0) : BigDecimal.valueOf(0);
		DebtClientDto clientDebt = new DebtClientDto(debt);

		return clientDebt;
	}

}
