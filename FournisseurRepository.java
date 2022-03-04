package com.infoMed.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.infoMed.entities.Fournisseur;
import java.lang.String;
import java.util.List;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {
	@Query("select f from Fournisseur f where f.nom like :x")
	public List<Fournisseur> consulterFournisseur(@Param("x")String motCle);
	@Query("select f from Fournisseur f where f.nom like :x")
	public Fournisseur consulterFournisseurAvecNom(@Param("x")String nom);
	@Query("select f from Fournisseur f where f.nom like :x")
	public Page<Fournisseur> chercherFournisseurs(@Param("x")String motCle,Pageable pageable);

}
