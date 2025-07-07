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

import com.etshn.stock.payload.MagasinMouvementDto;
import com.etshn.stock.payload.MagasinMouvementResponse;
import com.etshn.stock.payload.ProductInMagasin;
import com.etshn.stock.service.MagasinMouvementService;
import com.etshn.stock.utils.AppConstants;

import jakarta.validation.Valid;

@CrossOrigin(origins = { "http://localhost:3000", "https://ets-hn-stock-front.vercel.app"})
@RestController
@RequestMapping("/etshn/magasinmouvments")
public class MagasinMouvementController {
	@Autowired
	private MagasinMouvementService mouvementService;

	public MagasinMouvementController(MagasinMouvementService mouvementService) {
		super();
		this.mouvementService = mouvementService;
	}
	
    @PostMapping("/add")
    public ResponseEntity<MagasinMouvementDto> add(@Valid @RequestBody MagasinMouvementDto mouvementDto){
    	return new ResponseEntity<>(mouvementService.add(mouvementDto), HttpStatus.CREATED);
    }
    
    @GetMapping(value = "/{id}")
    public ResponseEntity<MagasinMouvementDto> getById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(mouvementService.get(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<MagasinMouvementDto> update(@Valid @RequestBody MagasinMouvementDto mouvementDto, @PathVariable(name = "id") long id){
    	MagasinMouvementDto response = mouvementService.update(mouvementDto, id);
       return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") long id){
    	mouvementService.delete(id);
       return new ResponseEntity<>("deleted successfully.", HttpStatus.OK);
    }
    
    @GetMapping(value = "/bymagasintype")
    public MagasinMouvementResponse findByMagasinType(
    		 @RequestParam(value = "magasin", required = true) long magasinId,
    		 @RequestParam(value = "type", required = true) long typeId,
    		 @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
             @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
             @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
             @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    
    ){
        return mouvementService.getByMagasinAndType(magasinId, typeId, pageNo, pageSize, sortBy, sortDir);
    }
    
    @GetMapping(value = "/bymagasinproduct")
    public MagasinMouvementResponse findByMagasinAndProduct(
    		 @RequestParam(value = "magasin", required = true) long magasinId,
    		 @RequestParam(value = "product", required = true) long productId,
    		 @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
             @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
             @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
             @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    
    ){
        return mouvementService.getByMagasinAndProduct(magasinId, productId, pageNo, pageSize, sortBy, sortDir);
    }
    
    @GetMapping(value = "/bymagasinproductinterval")
    public MagasinMouvementResponse getByMagasinAndInterval(
  		 	@RequestParam(value = "type", required = true) long typeId,
   		 	@RequestParam(value = "magasin", required = true) long magasinId,
  		 	@RequestParam(value = "product", required = true) String product,
   		 	@RequestParam(value = "startDate", required = true) Date startDate,
   		 	@RequestParam(value = "endDate", required = true) Date endDate,
   		 	@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
   		 	@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    
    ){
        return mouvementService.getByMagasinAndInterval(typeId, magasinId, product, startDate, endDate , pageNo, pageSize, sortBy, sortDir);
    }
    
    @GetMapping(value = "/stockproduct")
    public ResponseEntity<ProductInMagasin> getYearSoldeByType(
    		@RequestParam(value = "magasin", required = true) long magasinId,
    		@RequestParam(value = "product", required = true) long productId ){
        return ResponseEntity.ok(mouvementService.getStockProductInMagasin(magasinId, productId));
    }
}
