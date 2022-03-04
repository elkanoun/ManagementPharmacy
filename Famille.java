package com.infoMed.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@XmlRootElement(name="famille")
public class Famille implements Serializable{
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long code;
	@NotNull
	@Size(min=4, max=50)
	private String libelle;
	
	@OneToMany(mappedBy="famille",fetch=FetchType.LAZY)
	private Collection<SousFamille> sousFamilles;
	
	

}
