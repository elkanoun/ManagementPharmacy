package com.infoMed.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @NoArgsConstructor @AllArgsConstructor @ToString
@Entity
public class StockMedicament implements Serializable {
	@Id 
	StockMedicamentId pk;
	
	private int quantite;
	private int alertQuantite;
	private boolean isAlert;

}
