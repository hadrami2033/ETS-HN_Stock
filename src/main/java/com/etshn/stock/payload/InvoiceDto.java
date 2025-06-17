package com.etshn.stock.payload;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Data;

@Data
public class InvoiceDto {
    private long id;
    private BigDecimal amount;
    private BigDecimal amountPartiel;
    private Date dateCreation;
    private String typePaiement;
    private String client;
}
