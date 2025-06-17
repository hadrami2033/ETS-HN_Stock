package com.etshn.stock.payload;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Data;

@Data
public class CachierMouvmentsDto {
    private Long id;
    private BigDecimal amount;
    private Date dateCreation;
    private String description;
}
