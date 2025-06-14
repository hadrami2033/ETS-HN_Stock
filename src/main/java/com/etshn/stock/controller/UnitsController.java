package com.etshn.stock.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.etshn.stock.payload.UnitDto;
import com.etshn.stock.service.UnitService;

import jakarta.validation.Valid;

@CrossOrigin(origins = { "http://localhost:3000", "https://keen-strength-ets-hn.up.railway.app"})
@RestController
@RequestMapping("/etshn/units")
public class UnitsController {
	
	@Autowired
	private UnitService unitService;
	
	@PostMapping("/add")
    public ResponseEntity<UnitDto> add(@Valid @RequestBody UnitDto unitDto){
    	return new ResponseEntity<>(unitService.add(unitDto), HttpStatus.CREATED);
    }
    
    @GetMapping(value = "/{id}")
    public ResponseEntity<UnitDto> getById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(unitService.get(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UnitDto> update(@Valid @RequestBody UnitDto unitDto, @PathVariable(name = "id") long id){
    	UnitDto response = unitService.update(unitDto, id);
       return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") long id){
    	unitService.delete(id);
       return new ResponseEntity<>("deleted successfully.", HttpStatus.OK);
    }
    
    @GetMapping(value = "byproduct/{id}")
    public ResponseEntity<UnitDto> getByProduct(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(unitService.findByProductId(id));
    }
	
}
