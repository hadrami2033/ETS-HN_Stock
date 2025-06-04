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

import com.etshn.stock.payload.MouvementDto;
import com.etshn.stock.payload.MouvementResponse;
import com.etshn.stock.service.MouvementService;
import com.etshn.stock.utils.AppConstants;

import jakarta.validation.Valid;

@CrossOrigin(origins = { "http://localhost:3000", "https://keen-strength-ets-hn.up.railway.app"})
@RestController
@RequestMapping("/etshn/mouvments")
public class MouvementController {
	
	@Autowired
	private MouvementService mouvementService;

	public MouvementController(MouvementService mouvementService) {
		super();
		this.mouvementService = mouvementService;
	}
	
    @PostMapping("/add")
    public ResponseEntity<MouvementDto> add(@Valid @RequestBody MouvementDto mouvementDto){
    	return new ResponseEntity<>(mouvementService.add(mouvementDto), HttpStatus.CREATED);
    }
    
    @GetMapping(value = "/{id}")
    public ResponseEntity<MouvementDto> getById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(mouvementService.get(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<MouvementDto> update(@Valid @RequestBody MouvementDto mouvementDto, @PathVariable(name = "id") long id){
    	MouvementDto response = mouvementService.update(mouvementDto, id);
       return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") long id){
    	mouvementService.delete(id);
       return new ResponseEntity<>("deleted successfully.", HttpStatus.OK);
    }
    
    @GetMapping(value = "/bytype")
    public MouvementResponse findByType(
    		 @RequestParam(value = "type", required = true) long typeId,
    		 @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
             @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
             @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
             @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    
    ){
        return mouvementService.getByType(typeId, pageNo, pageSize, sortBy, sortDir);
    }
    
    @GetMapping(value = "/byproduct")
    public MouvementResponse findByProduct(
    		 @RequestParam(value = "type", required = true) long typeId,
    		 @RequestParam(value = "product", required = true) long productId,
    		 @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
             @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
             @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
             @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    
    ){
        return mouvementService.getByTypeAndProduct(typeId, productId, pageNo, pageSize, sortBy, sortDir);
    }
    
    @GetMapping(value = "/byinterval")
    public MouvementResponse findByInterval(
    		 @RequestParam(value = "type", required = true) long typeId,
    		 @RequestParam(value = "startDate", required = true) Date startDate,
    		 @RequestParam(value = "endDate", required = true) Date endDate,
    		 @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
             @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
             @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
             @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    
    ){
        return mouvementService.getByTypeAndInterval(typeId, startDate, endDate, pageNo, pageSize, sortBy, sortDir);
    }
}
