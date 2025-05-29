package com.etshn.stock.entity;

import java.math.BigDecimal;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "cachier" //, uniqueConstraints = {@UniqueConstraint(columnNames = {"trsid"})}
)
public class Cachier {
	@Id
    private Long id;
    private BigDecimal solde;
}
