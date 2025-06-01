package com.etshn.stock.payload;

import java.math.BigDecimal;
import java.sql.Date;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DebtsDto {
    private long id;
    private String description;
    private BigDecimal amount;
    private BigDecimal amountPayed;
    private Date dateCreation;
    private Date updatedAt;
    private Integer payed;
    
    @NotNull
    private Long clientId;
    private ClientDto client;
}
