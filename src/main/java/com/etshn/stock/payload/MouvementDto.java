package com.etshn.stock.payload;

import java.math.BigDecimal;
import java.sql.Date;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MouvementDto {
	private long id;
    
    @NotNull
    private Integer quantity;
    
    @NotNull
    private BigDecimal amount;
    
    
    private String description;;
    
    private Date dateCreation;

    private Date updatedAt;
        
    @NotNull
    private Long userId;
    private RegisterDto user;
    
    private Long productId;
    private ProductDto product;
    
    private Long unitId;
    private UnitDto unit;
    
    private Long invoiceId;
    private InvoiceDto invoice;

    @NotNull
    private Long typeId;
    private TypeDto type;
    
    private Long clientId;
    private ClientDto client;
}
