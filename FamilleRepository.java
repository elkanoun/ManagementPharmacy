package com.infoMed.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.infoMed.entities.Famille;

public interface FamilleRepository extends JpaRepository<Famille, Long> {
	@Query("select f from Famille f where f.libelle like :x")
	public List<Famille> consulterFamillesAvecMotCle(@Param("x")String motCle);
    @Query("select f from Famille f where f.code like :x")
	public Famille findOne(@Param("x")Long code);
    @Query("select f from Famille f where f.libelle like :x")
	public Famille findOneAvecLibelle(@Param("x")String libelle);

}
