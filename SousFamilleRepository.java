package com.infoMed.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.infoMed.entities.SousFamille;

public interface SousFamilleRepository extends JpaRepository<SousFamille, Long> {
    @Query("select sf from SousFamille sf where sf.libelle like :x")
	public List<SousFamille> consulterSousFamillesAvecMotCle(@Param("x")String motCle);
    @Query("select sf from SousFamille sf where sf.famille.code like :x")
	public List<SousFamille> sousFamillesDeFamille(@Param("x")Long code);
    @Query("select sf from SousFamille sf where sf.code like :x")
	public SousFamille findOneSousFamilleAvecId(@Param("x")Long code);
    @Query("select sf from SousFamille sf where sf.libelle like :x")
	public SousFamille findOneSousFamilleAvecLib(@Param("x")String libelle);

}
