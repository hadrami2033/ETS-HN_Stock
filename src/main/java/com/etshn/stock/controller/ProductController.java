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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.etshn.stock.payload.ProductDto;
import com.etshn.stock.payload.ProductResponce;
import com.etshn.stock.service.ProductService;
import com.etshn.stock.utils.AppConstants;

import jakarta.validation.Valid;

@CrossOrigin(origins = { "http://localhost:3000", "https://keen-strength-ets-hn.up.railway.app"})
@RestController
@RequestMapping("/etshn/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;

	public ProductController(ProductService productService) {
		super();
		this.productService = productService;
	}
	
    @PostMapping("/add")
    public ResponseEntity<ProductDto> add(@Valid @RequestBody ProductDto productDto){
    	return new ResponseEntity<>(productService.add(productDto), HttpStatus.CREATED);
    }
    
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(productService.get(id));
    }
    
    @GetMapping(value = "/byname/{name}")
    public ResponseEntity<ProductDto> getByName(@PathVariable(name = "name") String name){
        return ResponseEntity.ok(productService.findByNom(name));
    }
    
    @GetMapping(value = "/alldispo")
    public ProductResponce findAllDispo(
   		 	 @RequestParam(value = "nom", required = true) String nom,
    		 @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
             @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
             @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
             @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    
    ){
        return productService.findAllDisponible(nom, pageNo, pageSize, sortBy, sortDir);
    }
    
    @GetMapping(value = "/allindispo")
    public ProductResponce findAllIndispo(
   		 	 @RequestParam(value = "nom", required = true) String nom,
    		 @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
             @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
             @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
             @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    
    ){
        return productService.findAllIndisponible(nom, pageNo, pageSize, sortBy, sortDir);
    }
    
    @GetMapping(value = "/all")
    public ProductResponce findAll(
   		 	 @RequestParam(value = "nom", required = true) String nom,
    		 @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
             @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
             @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
             @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    
    ){
        return productService.findAll(nom, pageNo, pageSize, sortBy, sortDir);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(@Valid @RequestBody ProductDto productDto, @PathVariable(name = "id") long id){
    	ProductDto productResponse = productService.update(productDto, id);
       return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") long id){
    	productService.delete(id);
       return new ResponseEntity<>("deleted successfully.", HttpStatus.OK);
    }
    
    
}
