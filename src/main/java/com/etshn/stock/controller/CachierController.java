package com.etshn.stock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etshn.stock.payload.CachierDto;
import com.etshn.stock.service.CachierService;

import jakarta.validation.Valid;

@CrossOrigin(origins = { "http://localhost:3000", "https://ets-hn-stock-front.vercel.app"})
@RestController
@RequestMapping("/etshn/cachier")
public class CachierController {
	@Autowired
	private CachierService cachierService;

	public CachierController(CachierService cachierService) {
		super();
		this.cachierService = cachierService;
	}
	
    @PutMapping("/{id}")
    public ResponseEntity<CachierDto> update(@Valid @RequestBody CachierDto cachierDto, @PathVariable(name = "id") long id){
    	CachierDto response = cachierService.update(cachierDto, id);
       return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping(value = "/{id}")
    public ResponseEntity<CachierDto> getById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(cachierService.get(id));
    }
	
}
