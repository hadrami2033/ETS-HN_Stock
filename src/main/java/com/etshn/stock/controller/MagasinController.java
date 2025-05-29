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

import com.etshn.stock.payload.MagasinDto;
import com.etshn.stock.service.MagasinService;

import jakarta.validation.Valid;


@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:2024"})
@RestController
@RequestMapping("/etshn/magasins")
public class MagasinController {
	@Autowired
	private MagasinService magasinService;

	public MagasinController(MagasinService magasinService) {
		super();
		this.magasinService = magasinService;
	}
	
    @PostMapping("/add")
    public ResponseEntity<MagasinDto> add(@Valid @RequestBody MagasinDto magasinDto){
    	return new ResponseEntity<>(magasinService.add(magasinDto), HttpStatus.CREATED);
    }
    
    @GetMapping(value = "/{id}")
    public ResponseEntity<MagasinDto> getById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(magasinService.get(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<MagasinDto> update(@Valid @RequestBody MagasinDto magasinDto, @PathVariable(name = "id") long id){
    	MagasinDto response = magasinService.update(magasinDto, id);
       return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") long id){
    	magasinService.delete(id);
       return new ResponseEntity<>("deleted successfully.", HttpStatus.OK);
    }
    
    @GetMapping(value = "/all")
    public List<MagasinDto> findAll(){
        return magasinService.findAll();
    }
    
    @GetMapping(value = "/bayname")
    public ResponseEntity<MagasinDto> getByName(
   		 @RequestParam(value = "name", required = true) String name
    		){
        return ResponseEntity.ok(magasinService.findByLabel(name) );
    }
}
