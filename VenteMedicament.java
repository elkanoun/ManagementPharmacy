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
public class VenteMedicament implements Serializable {
	@Id
	VenteMedicamentId pk;
	
	private int quantiteMedicament;

}
