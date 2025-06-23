package com.etshn.stock.payload;

import java.sql.Date;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MagasinMouvmentUnitDto {
	private long id;
    
    @NotNull
    private Integer quantity;
    private Date dateCreation;
    private Integer rest;
    
    @NotNull
    private Long unitId;
    private UnitDto unit;
    
    @NotNull
    private Long typeId;
    private TypeDto type;
    
    @NotNull
    private Long magasinId;
    private MagasinDto magasin;

}
