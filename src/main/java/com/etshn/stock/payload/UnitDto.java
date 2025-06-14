package com.etshn.stock.payload;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UnitDto {
	private long id;
    private String nom;
    private Integer quantite;
    private BigDecimal prixVente;
    
    @NotNull
    private Long productId;
    private ProductDto product;
}
