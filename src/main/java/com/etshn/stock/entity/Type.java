package com.etshn.stock.entity;

import jakarta.persistence.*;
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
        name = "types" //, uniqueConstraints = {@UniqueConstraint(columnNames = {"trsid"})}
)
public class Type {
    @Id
    private Long id;
    
    @Column(name = "label", nullable = false)
    private String label;

	public Type(String label) {
		super();
		this.label = label;
	}
    
    
    
}
