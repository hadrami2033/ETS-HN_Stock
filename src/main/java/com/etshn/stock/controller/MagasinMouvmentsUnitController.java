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


import com.etshn.stock.payload.MagasinMouvmentUnitDto;
import com.etshn.stock.payload.MagasinMouvmentUnitResponse;
import com.etshn.stock.payload.ProductInMagasin;
import com.etshn.stock.service.MagasinMouvmentsUnitService;
import com.etshn.stock.utils.AppConstants;

import jakarta.validation.Valid;

@CrossOrigin(origins = { "http://localhost:3000", "https://ets-hn-stock-front.vercel.app"})
@RestController
@RequestMapping("/etshn/magasinunitsmouvments")
public class MagasinMouvmentsUnitController {
	@Autowired
	private MagasinMouvmentsUnitService mouvementService;

	public MagasinMouvmentsUnitController(MagasinMouvmentsUnitService mouvementService) {
		super();
		this.mouvementService = mouvementService;
	}
	

    @PostMapping("/add")
    public ResponseEntity<MagasinMouvmentUnitDto> add(@Valid @RequestBody MagasinMouvmentUnitDto mouvementDto){
    	return new ResponseEntity<>(mouvementService.add(mouvementDto), HttpStatus.CREATED);
    }
    
    @GetMapping(value = "/{id}")
    public ResponseEntity<MagasinMouvmentUnitDto> getById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(mouvementService.get(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<MagasinMouvmentUnitDto> update(@Valid @RequestBody MagasinMouvmentUnitDto mouvementDto, @PathVariable(name = "id") long id){
    	MagasinMouvmentUnitDto response = mouvementService.update(mouvementDto, id);
       return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") long id){
    	mouvementService.delete(id);
       return new ResponseEntity<>("deleted successfully.", HttpStatus.OK);
    }
    
    @GetMapping(value = "/bymagasintype")
    public MagasinMouvmentUnitResponse findByMagasinType(
    		 @RequestParam(value = "magasin", required = true) long magasinId,
    		 @RequestParam(value = "type", required = true) long typeId,
    		 @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
             @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
             @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
             @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    
    ){
        return mouvementService.getByMagasinAndType(magasinId, typeId, pageNo, pageSize, sortBy, sortDir);
    }
    
    @GetMapping(value = "/bymagasinunit")
    public MagasinMouvmentUnitResponse findByMagasinAndProduct(
    		 @RequestParam(value = "magasin", required = true) long magasinId,
    		 @RequestParam(value = "unit", required = true) long unitId,
    		 @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
             @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
             @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
             @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    
    ){
        return mouvementService.getByMagasinAndUnit(magasinId, unitId, pageNo, pageSize, sortBy, sortDir);
    }
    
    @GetMapping(value = "/bymagasinunitinterval")
    public MagasinMouvmentUnitResponse getByMagasinAndInterval(
  		 	@RequestParam(value = "type", required = true) long typeId,
   		 	@RequestParam(value = "magasin", required = true) long magasinId,
  		 	@RequestParam(value = "unit", required = true) String unit,
   		 	@RequestParam(value = "startDate", required = true) Date startDate,
   		 	@RequestParam(value = "endDate", required = true) Date endDate,
   		 	@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
   		 	@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    
    ){
        return mouvementService.getByMagasinAndInterval(typeId, magasinId, unit, startDate, endDate , pageNo, pageSize, sortBy, sortDir);
    }
    
    @GetMapping(value = "/stockunit")
    public ResponseEntity<ProductInMagasin> getYearSoldeByType(
    		@RequestParam(value = "magasin", required = true) long magasinId,
    		@RequestParam(value = "unit", required = true) long unitId ){
        return ResponseEntity.ok(mouvementService.getStockUnitInMagasin(magasinId, unitId));
    }

}
