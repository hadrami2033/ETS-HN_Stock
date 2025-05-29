package com.etshn.stock.payload;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CachierDto {
    private Long id;
    private BigDecimal solde;
}
