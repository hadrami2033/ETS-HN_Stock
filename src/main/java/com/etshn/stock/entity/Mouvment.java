package com.etshn.stock.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "mouvements" //, uniqueConstraints = {@UniqueConstraint(columnNames = {"trsid"})}
)
public class Mouvment {
	  	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    private Integer quantity;
	    private String description;
	    private Date dateCreation;
	    private BigDecimal amount;
	    private Date updatedAt;
	    @ManyToOne
	    @JoinColumn(name = "product_id")
	    private Product product;
	    
	    @ManyToOne
	    @JoinColumn(name = "unit_id")
	    private Units unit;

	    @ManyToOne
	    @JoinColumn(name = "user_id")
	    private User user;
	    
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "type_id")
	    private Type type;
	    
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "client_id")
	    private Client client;

	    
}
