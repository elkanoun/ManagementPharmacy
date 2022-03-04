package com.infoMed.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infoMed.entities.Vente;

public interface VenteRepository extends JpaRepository<Vente, Long> {

}
