package com.crosskey.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.sun.istack.internal.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "propspect")
public class Prospect {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
    @NotNull
    @Size(min = 3)
    private String customerName;
    @NotNull
    private double totalLoan;
    @NotNull
    private double interest;
    @NotNull
    private int years;
    
    private double result;
}
