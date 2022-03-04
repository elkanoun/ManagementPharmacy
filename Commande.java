package com.infoMed.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @NoArgsConstructor @AllArgsConstructor @ToString
@Entity
public class Commande implements Serializable {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long num;
	private Date date;
	private double montant;
	private boolean isValid;
	
	@ManyToOne
	@JoinColumn(name="CODE_FOURNISSEUR")
	private Fournisseur fournisseur;
	
	@OneToMany(mappedBy="pk.commande")
	private Collection<LigneCommande> ligneCommandes;

}
