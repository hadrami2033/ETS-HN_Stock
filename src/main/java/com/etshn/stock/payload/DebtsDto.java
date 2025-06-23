package com.etshn.stock.payload;

import java.math.BigDecimal;
import java.sql.Date;

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
    
    private Long clientId;
    private ClientDto client;
    
    private Long employeId;
    private EmployeDto employe;
}
