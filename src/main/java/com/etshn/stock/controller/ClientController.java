package com.etshn.stock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.etshn.stock.payload.ClientDto;
import com.etshn.stock.payload.ClientResponce;
import com.etshn.stock.service.ClientService;
import com.etshn.stock.utils.AppConstants;

import jakarta.validation.Valid;

@CrossOrigin(origins = { "http://localhost:3000", "https://ets-hn-stock-front.vercel.app"})
@RestController
@RequestMapping("/etshn/clients")
public class ClientController {
	@Autowired
	private ClientService clientService;

	public ClientController(ClientService clientService) {
		super();
		this.clientService = clientService;
	}
	
    @PostMapping("/add")
    public ResponseEntity<ClientDto> add(@Valid @RequestBody ClientDto clientDto){
    	return new ResponseEntity<>(clientService.add(clientDto), HttpStatus.CREATED);
    }
    
    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientDto> getById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(clientService.get(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> update(@Valid @RequestBody ClientDto clientDto, @PathVariable(name = "id") long id){
    	ClientDto response = clientService.update(clientDto, id);
       return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") long id){
    	clientService.delete(id);
       return new ResponseEntity<>("deleted successfully.", HttpStatus.OK);
    }
    
    @GetMapping(value = "/all")
    public ClientResponce findAll(
    		 @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
             @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
             @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
             @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return clientService.findAll(pageNo, pageSize, sortBy, sortDir);
    }
    
    @GetMapping(value = "/allclients")
    public List<ClientDto> findAllClients(){
        return clientService.findAll();
    }
    
    @GetMapping(value = "/bayname")
    public ResponseEntity<ClientDto> getByName(
   		 @RequestParam(value = "name", required = true) String name
    		){
        return ResponseEntity.ok(clientService.findByName(name) );
    }
    
    @GetMapping(value = "/bayphone")
    public ResponseEntity<ClientDto> getByPhone(
   		 @RequestParam(value = "phone", required = true) String phone
    		){
        return ResponseEntity.ok(clientService.findByPhone(phone) );
    }
	
}
