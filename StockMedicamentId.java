package com.infoMed.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class StockMedicamentId implements Serializable {
	
	@ManyToOne
	private Stock stock;
	
	@ManyToOne
	private Medicament medicament;

}
