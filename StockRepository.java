package com.infoMed.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.infoMed.entities.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {
    @Query("select s from Stock s where s.libelle like :x")
	Page<Stock> ConsulterStock(@Param("x") String motCle, Pageable pageable);
    @Query("select s from Stock s where s.libelle like :x")
	Stock chercherStockByNom(@Param("x")String nom);

}
