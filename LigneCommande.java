package com.infoMed.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @NoArgsConstructor @AllArgsConstructor @ToString
@Entity
public class LigneCommande implements Serializable {
	
	@Id
	LigneCommandeId pk;
	@NotNull
	private int quantiteMedicament;

}
