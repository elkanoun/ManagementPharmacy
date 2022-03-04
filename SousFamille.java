package com.infoMed.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @NoArgsConstructor @AllArgsConstructor @ToString
@Entity
@XmlRootElement(name="sousFamille")
public class SousFamille implements Serializable {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long code;
	private String libelle;
	
	@ManyToOne
	@JoinColumn(name="CODE_FAMILLE")
	private Famille famille;
	
	@OneToMany(mappedBy="sousFamille", fetch=FetchType.LAZY)
	private Collection<Medicament> medicaments;

}
