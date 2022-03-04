package com.infoMed.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.infoMed.entities.Commande;

public interface CommandeRepository extends JpaRepository<Commande, Long> {
    @Query("select c from Commande c where c.num like :x")
	public Commande findOneCommandeById(@Param("x")Long id);
	//List<Commande> isValid();
	//List<Commande> isNotValid();

}
