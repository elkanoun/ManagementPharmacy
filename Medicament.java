package com.infoMed.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @NoArgsConstructor @AllArgsConstructor @ToString
@Entity
@XmlRootElement(name="medicament")
public class Medicament implements Serializable {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long code;
	@NotNull
	@Size(min=4,max=100)
	private String libelle;
	private double prixAchat;
	private double prixVente;
	private String notice;
	private String dateExpiration;
	
	@ManyToOne
	@JoinColumn(name="CODE_SOUSFAMILLE")
	private SousFamille sousFamille;
	
	@OneToMany(mappedBy="pk.medicament")
	private Collection<LigneCommande> ligneCommandes;
	
	@OneToMany(mappedBy="pk.medicament")
	private Collection<StockMedicament> stockMedicaments;
	
	@OneToMany(mappedBy="pk.medicament")
	private Collection<VenteMedicament> venteMedicaments;

	

}
