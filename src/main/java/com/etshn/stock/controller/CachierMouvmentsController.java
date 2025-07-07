package com.etshn.stock.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.etshn.stock.payload.CachierMouvmentsDto;
import com.etshn.stock.payload.CachierMouvmentsResponce;
import com.etshn.stock.service.CachierMouvementsService;
import com.etshn.stock.utils.AppConstants;

import jakarta.validation.Valid;

@CrossOrigin(origins = { "http://localhost:3000", "https://ets-hn-stock-front.vercel.app"})
@RestController
@RequestMapping("/etshn/cachiermouvments")
public class CachierMouvmentsController {
	@Autowired
	private CachierMouvementsService mouvementService;

	public CachierMouvmentsController(CachierMouvementsService mouvementService) {
		super();
		this.mouvementService = mouvementService;
	}
	
    @PostMapping("/add")
    public ResponseEntity<CachierMouvmentsDto> add(@Valid @RequestBody CachierMouvmentsDto mouvementDto){
    	return new ResponseEntity<>(mouvementService.add(mouvementDto), HttpStatus.CREATED);
    }
    
    @GetMapping(value = "/byinterval")
    public CachierMouvmentsResponce findByInterval(
    		 @RequestParam(value = "startDate", required = true) Date startDate,
    		 @RequestParam(value = "endDate", required = true) Date endDate,
    		 @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
             @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
             @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
             @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    
    ){
        return mouvementService.getByInterval(startDate, endDate, pageNo, pageSize, sortBy, sortDir);
    }

}
