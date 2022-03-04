package com.infoMed.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.infoMed.metier.IPharmacieMetier;
import com.infoMed.metier.PharmacieMetierImpl;

@Controller
@RequestMapping("ventes")
public class VenteController {
	@Autowired
	IPharmacieMetier pharmacieMetierImpl=new PharmacieMetierImpl();


}
