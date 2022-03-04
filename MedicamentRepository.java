package com.infoMed.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.infoMed.entities.Medicament;

public interface MedicamentRepository extends JpaRepository<Medicament, Long> {
	
	@Query("select m from Medicament m where m.libelle like :x")
	public Page<Medicament> consulterMedicament(@Param("x")String motCle, Pageable pageable);
	
	@Query("select m from Medicament m where m.code like :y")
	public Medicament findOneMedicamentAvecId(@Param("y")Long code);
	
	@Query("select m from Medicament m where m.libelle like :x")
	public Medicament findOneMedicamentAvecLib(@Param("x")String libelle);
	
		
	

}
