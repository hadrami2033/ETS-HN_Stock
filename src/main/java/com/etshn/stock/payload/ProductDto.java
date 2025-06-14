package com.etshn.stock.payload;

import java.math.BigDecimal;
import java.sql.Date;
import lombok.Data;

@Data
public class ProductDto {
	private long id;
    private String nom;
    private String description;
    private Integer quantiteEnStock;
    private Integer uniteEnStock;
    private BigDecimal prixAchat;
    private BigDecimal prixVente;
    private Date dateCreation;
}
