package com.crosskey.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProspectDTO {
		
		private String id;
	    private String customerName;
	    private double totalLoan;
	    private double interest;
	    private int years;
	    private double result;
}
