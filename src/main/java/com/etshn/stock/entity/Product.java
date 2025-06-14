package com.etshn.stock.entity;

import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "products" //, uniqueConstraints = {@UniqueConstraint(columnNames = {"trsid"})}
)
public class Product {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String nom;
    private String description;
    private Integer quantiteEnStock = 0;
    private Integer uniteEnStock = 0;
    private BigDecimal prixAchat;
    private BigDecimal prixVente;
    private Date dateCreation;

}
