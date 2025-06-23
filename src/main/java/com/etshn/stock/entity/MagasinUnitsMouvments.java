package com.etshn.stock.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
        name = "magasinunitsmouvment" //, uniqueConstraints = {@UniqueConstraint(columnNames = {"trsid"})}
)
public class MagasinUnitsMouvments {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateCreation;
    private Integer quantity;
    private Integer rest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private Type type;
    
    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Units unit;
    
    @ManyToOne
    @JoinColumn(name = "magasin_id")
    private Magasin magasin;

}
