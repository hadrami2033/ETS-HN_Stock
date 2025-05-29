package com.etshn.stock.payload;

import java.sql.Date;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MagasinDto {
    private Long id;
    @NotEmpty
    private String label;
    private Date dateCreation;

}
