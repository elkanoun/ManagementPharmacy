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
public class LigneCommandeId implements Serializable {
	
	@ManyToOne
	private Medicament medicament;
	
	@ManyToOne
	private Commande commande;

}
