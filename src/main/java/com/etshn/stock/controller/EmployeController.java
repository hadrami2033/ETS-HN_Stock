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

import com.etshn.stock.payload.EmployeDto;
import com.etshn.stock.payload.EmployeResponce;
import com.etshn.stock.service.EmployeService;
import com.etshn.stock.utils.AppConstants;

import jakarta.validation.Valid;

@CrossOrigin(origins = { "http://localhost:3000", "https://ets-hn-stock-front.vercel.app"})
@RestController
@RequestMapping("/etshn/employes")
public class EmployeController {
	@Autowired
	private EmployeService employeService;
	
	@PostMapping("/add")
    public ResponseEntity<EmployeDto> add(@Valid @RequestBody EmployeDto EmployeDto){
    	return new ResponseEntity<>(employeService.add(EmployeDto), HttpStatus.CREATED);
    }
    
    @GetMapping(value = "/{id}")
    public ResponseEntity<EmployeDto> getById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(employeService.get(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<EmployeDto> update(@Valid @RequestBody EmployeDto EmployeDto, @PathVariable(name = "id") long id){
    	EmployeDto response = employeService.update(EmployeDto, id);
       return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") long id){
    	employeService.delete(id);
       return new ResponseEntity<>("deleted successfully.", HttpStatus.OK);
    }
    
    @GetMapping(value = "/all")
    public EmployeResponce findAll(
    		 @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
             @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
             @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
             @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return employeService.findAll(pageNo, pageSize, sortBy, sortDir);
    }
    
    @GetMapping(value = "/allemployes")
    public List<EmployeDto> findAllClients(){
        return employeService.findAll();
    }
    
    @GetMapping(value = "/bayname")
    public ResponseEntity<EmployeDto> getByName(
   		 @RequestParam(value = "name", required = true) String name
    		){
        return ResponseEntity.ok(employeService.findByName(name) );
    }
    
    @GetMapping(value = "/bayphone")
    public ResponseEntity<EmployeDto> getByPhone(
   		 @RequestParam(value = "phone", required = true) String phone
    		){
        return ResponseEntity.ok(employeService.findByPhone(phone) );
    }

}
