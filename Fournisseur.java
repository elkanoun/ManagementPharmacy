package com.infoMed.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @NoArgsConstructor @AllArgsConstructor @ToString
@Entity
public class Fournisseur implements Serializable {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Size(min=4,max=50)
	private String nom;
	private String adresse;
	private String ville;
	@NotNull
	@Size(min=10,max=14)
	private String tel;
	@NotNull
	@Size(min=14,max=50)
	private String email;
	
	@OneToMany(mappedBy="fournisseur",fetch=FetchType.LAZY)
	private Collection<Commande> commandes;

}
