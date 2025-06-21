package com.etshn.stock.payload;

import java.math.BigDecimal;
import java.sql.Date;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmployeDto {
	  private Long id;
	  @NotNull
	  private String name;
	  @NotEmpty
	  private String phone;
	  private Date dateCreation;
	  private BigDecimal salair;

}
