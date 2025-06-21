package com.etshn.stock.controller;

import java.sql.Date;

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

import com.etshn.stock.payload.DebtClientDto;
import com.etshn.stock.payload.DebtsDto;
import com.etshn.stock.payload.DebtsResponce;
import com.etshn.stock.service.DebtsService;
import com.etshn.stock.utils.AppConstants;

import jakarta.validation.Valid;

@CrossOrigin(origins = { "http://localhost:3000", "https://keen-strength-ets-hn.up.railway.app"})
@RestController
@RequestMapping("/etshn/debts")
public class DebtsController {
	@Autowired
	private DebtsService debtsService;

	public DebtsController(DebtsService debtsService) {
		super();
		this.debtsService = debtsService;
	}
	
    @PostMapping("/add")
    public ResponseEntity<DebtsDto> add(@Valid @RequestBody DebtsDto debtsDto){
    	return new ResponseEntity<>(debtsService.add(debtsDto), HttpStatus.CREATED);
    }
    
    @GetMapping(value = "/{id}")
    public ResponseEntity<DebtsDto> getById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(debtsService.get(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<DebtsDto> update(@Valid @RequestBody DebtsDto debtsDto, @PathVariable(name = "id") long id){
    	DebtsDto response = debtsService.update(debtsDto, id);
       return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") long id){
    	debtsService.delete(id);
       return new ResponseEntity<>("deleted successfully.", HttpStatus.OK);
    }
	
    @GetMapping(value = "/byclientpayed")
    public DebtsResponce findByClient(
    		 @RequestParam(value = "client", required = true) long clientId,
    		 @RequestParam(value = "payed", required = true) int payed,
    		 @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
             @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
             @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
             @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    
    ){
        return debtsService.findByClientAndPayed(clientId, payed, pageNo, pageSize, sortBy, sortDir);
    }
    
    @GetMapping(value = "/byemploye")
    public DebtsResponce findByEmploye(
    		 @RequestParam(value = "employe", required = true) long employeId,
    		 @RequestParam(value = "payed", required = true) int payed,
    		 @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
             @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
             @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
             @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    
    ){
        return debtsService.findByEmployeAndPayed(employeId, payed, pageNo, pageSize, sortBy, sortDir);
    }
    
    @GetMapping(value = "/byinterval")
    public DebtsResponce findByInterval(
    		 @RequestParam(value = "payed", required = true) int payed,
    		 @RequestParam(value = "startDate", required = true) Date startDate,
    		 @RequestParam(value = "endDate", required = true) Date endDate,
    		 @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
             @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
             @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
             @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    
    ){
        return debtsService.findByPayedAndInterval(payed, startDate, endDate, pageNo, pageSize, sortBy, sortDir);
    }
    
    @GetMapping(value = "/clientdebt")
    public ResponseEntity<DebtClientDto> getClientDebts(
   		 @RequestParam(value = "client", required = true) long clientId,
   		 @RequestParam(value = "payed", required = true) int payed
    		){
        return ResponseEntity.ok(debtsService.getClientDebts(clientId, payed));
    }
    
}
