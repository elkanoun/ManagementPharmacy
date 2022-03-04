package com.infoMed.web;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.infoMed.entities.Famille;
import com.infoMed.entities.Medicament;
import com.infoMed.entities.SousFamille;
import com.infoMed.entities.Stock;
import com.infoMed.entities.StockMedicament;
import com.infoMed.metier.IPharmacieMetier;
import com.infoMed.metier.PharmacieMetierImpl;

@Controller
public class CatalogueStockController {
	@Autowired
	IPharmacieMetier pharmacieMetierImpl=new PharmacieMetierImpl();
	
	//method a propos api
    @RequestMapping(value="/aPropos")
	public String aproposApi() {
    	return "propos";
    	}
	//method accueil pharmacie
	@RequestMapping(value="")
	public String index() {
		return "pharmacie";
		}
	//method catalogue
	@RequestMapping(value="/catalogueMedicaments")
	public String catalogueMedicaments() {
		return "catalogue";
		}
	//method recherche familles
	@RequestMapping(value="/rechercherFamille")
	public String searchFamille(Model model,String motCle) {
		List<Famille> familles=pharmacieMetierImpl.consulterFamille(motCle);
				
		model.addAttribute("familles", familles);
				
		return "catalogue";
		}
	//method recherche Sousfamilles
	@RequestMapping(value="/rechercherSousFamille")
	public String searchSousFamille(Model model,String motCle) {
		List<SousFamille> sousFamilles=pharmacieMetierImpl.consulterSousFamille(motCle);
					
		model.addAttribute("sousFamilles", sousFamilles);
					
		return "catalogue";
		}
    //method recherche Sousfamilles de famille
	@RequestMapping(value="/showSousFamilles")
	public String searchSousFamilleDeFamille(Model model,Long code) {
		List<SousFamille> sousFamilles=pharmacieMetierImpl.consulterSousFamilleDeFamille(code);
						
		model.addAttribute("sousFamilles", sousFamilles);
						
		return "catalogue";
		}
	//method accueil formulaire famille
	@RequestMapping(value="/formFamille")
	public String formulaireFamille(Model model) {
		model.addAttribute("famille", new Famille());
		return "formFamille";
		
	}
	//method add famille
	@RequestMapping(value="/saveFamille", method=RequestMethod.POST)
	public String ajouterFamille(@Valid Famille famille, BindingResult bindingResult) {
		if(bindingResult.hasErrors())
			return "formFamille";
		pharmacieMetierImpl.addFamille(famille);
		return "catalogue";
	}
	//method edit famille
	@RequestMapping(value="/editFamille",method=RequestMethod.GET)
	public String updateFamille(Model model, Long code) {
		Famille famille=pharmacieMetierImpl.consulterFamilleAvecId(code);
		model.addAttribute("famille", famille);
		return "editFamille";
	}
	//method delete famille
	@RequestMapping(value="/deleteFamille",method=RequestMethod.GET)
	public String supprimerFamille(Long code) {
		pharmacieMetierImpl.deleteFamille(code);
		return "redirect:/catalogue/rechercherFamille?motCle=";
	}
	//method accueil formulaire sousFamille
	@RequestMapping(value="/formSousFamille")
	public String formulaireSousFamille(Model model) {
		model.addAttribute("sousFamille", new SousFamille());
		//model.addAttribute("famille", new Famille());
		List<Famille> familles=pharmacieMetierImpl.listFamilles();
		model.addAttribute("listFamilles", familles);
		return "formSousFamille";	
	}
	//method add sousFamille
	@RequestMapping(value="/saveSousFamille", method=RequestMethod.POST)
	public String ajouterSousFamille(@Valid SousFamille sousFamille, String libelleFamille, BindingResult bindingResult) {
		if(bindingResult.hasErrors())
			return "redirect:/catalogue/formSousFamille";
		Famille famille=pharmacieMetierImpl.consulterFamilleAvecLibelle(libelleFamille);
		sousFamille.setFamille(famille);
		pharmacieMetierImpl.addSousFamille(sousFamille);
		return "redirect:/catalogue/rechercherSousFamille?motCle=";
	}
	//method edit sousFamille
	@RequestMapping(value="/editerSousFamille",method=RequestMethod.GET)
	public String updateSousFamille(Model model, Long code) {
		SousFamille sousFamille=pharmacieMetierImpl.consulterSousFamilleAvecId(code);
		model.addAttribute("sousFamille", sousFamille);
     	String libelleFamille=sousFamille.getFamille().getLibelle();
		model.addAttribute("libFamille", libelleFamille);
		List<Famille> familles=pharmacieMetierImpl.listFamilles();
		model.addAttribute("listFamilles", familles);
		return "editSousfamille";
	}
	//method delete sousFamille
	@RequestMapping(value="/deleteSousFamille",method=RequestMethod.GET)
	public String supprimerSousFamille(Long code) {
		pharmacieMetierImpl.deleteSousFamille(code);
		return "redirect:/catalogue/rechercherSousFamille?motCle=";
	}
	
	/*gestion informatios stocks*/
	//method recherche stock
	@RequestMapping(value="/rechercherStock",method=RequestMethod.GET)
	public String searchStock(Model model,
			@RequestParam(name="page", defaultValue="0") int p,
            @RequestParam(name="size", defaultValue="3") int s,
            @RequestParam(name="motCle", defaultValue="") String mc) {
		Page<Stock> pageStocks=pharmacieMetierImpl.consulterStock(mc, p, s);
		model.addAttribute("listStocks", pageStocks.getContent());
		int[] pages=new int[pageStocks.getTotalPages()];
		model.addAttribute("pages", pages);
		model.addAttribute("pageCourante", p);
		model.addAttribute("size", s);
		model.addAttribute("motCle", mc);
		
		return "catalogue";
		}
	//methode formulaire ajout stock
	@RequestMapping(value="/formStock",method=RequestMethod.GET)
	public String formAjoutStock(Model model) {
		model.addAttribute(new Stock());
		return "formStock";
	}
	//méthode ajout stock
	@RequestMapping(value="/saveStock",method=RequestMethod.POST)
	public String ajouterStock(@Valid Stock stock, BindingResult bindingResult) {
		if(bindingResult.hasErrors())
			return "formStock";
		pharmacieMetierImpl.addStock(stock);
		return "catalogue";
	}
	//méthode formulaire maj stock
	@RequestMapping(value="/formUpdateStock",method=RequestMethod.GET)
	public String formModifierStock(Model model, String libelle) {
		Stock stock=pharmacieMetierImpl.searchStockByNom(libelle);
		model.addAttribute("stock", stock);
		return "formMajStock";
	}
	//méthode maj stock
	@RequestMapping(value="/updateStock",method=RequestMethod.POST)
	public String modifierStock(@Valid Stock stock, BindingResult bindingResult) {
		if(bindingResult.hasErrors())
			return "formMajStock";
		pharmacieMetierImpl.updateStock(stock);
		return "catalogue";
	}
	//méthode supprimer stock
	@RequestMapping(value="/deleteStock",method=RequestMethod.GET)
	public String supprimerStock(Long id) {
		pharmacieMetierImpl.deleteStock(id);
		return "catalogue";
	}
	
	/*gestion stocks médicaments*/
	//méthode  recherche stocks médicaments d'un médicament
	@RequestMapping(value="/searchStocksMédicamentsDeMedic")
	public String accueil(Model model,
			@RequestParam(name="page",defaultValue="0")int p, 
			@RequestParam(name="size",defaultValue="4")int s, 
			@RequestParam(name="motCle",defaultValue="4")String mc) {
		
		Page<StockMedicament> pageStockMedicaments=pharmacieMetierImpl.consulterMedicamentsStock(mc,p,s);
		model.addAttribute("stockMedicaments", pageStockMedicaments.getContent());
		int[] pages=new int[pageStockMedicaments.getTotalPages()];
		model.addAttribute("pages", pages);
		model.addAttribute("pageCourante", p);
		model.addAttribute("size", s);
		model.addAttribute("motCle", mc);

		return "catalogue";
	}
	//méthode formulaire ajout stock médicament
	@RequestMapping(value="/ajouterStockMedicament")
	public String addStockMedic(Model model) {
		model.addAttribute("stockMedicament", new StockMedicament());
		model.addAttribute("listMedicaments", pharmacieMetierImpl.listMedicaments());
		model.addAttribute("listStocks", pharmacieMetierImpl.listStocks());
		
		return "formAjoutStockMedic";
	}
	//méthde save stock médicament
	@RequestMapping(value="/saveStockMedicament",method=RequestMethod.POST)
	public String enregistrerStockMedicament(String libelleMedicament, String libelleStock, int quantiteMedicament, int alertQuantite) {
		Stock stock=pharmacieMetierImpl.searchStockByNom(libelleStock);
		Medicament medicament=pharmacieMetierImpl.consulterMedicamentAvecLibelle(libelleMedicament);
		pharmacieMetierImpl.addProduitStock(stock, medicament, quantiteMedicament, alertQuantite);
		
		return "catalogue";
	}
	//méthode update stockMedicament
	@RequestMapping(value="/editStockMedicament")
	public String updateStockMedicament(Model model, String libMedic) {
		StockMedicament sm=pharmacieMetierImpl.consulterMedicamentsStockLibMedic(libMedic);
			model.addAttribute("stockMedicament", sm);
		
		return "formEditStockMedicament";
	}
	//méthode delete stock médicament
	@RequestMapping(value="/deleteStockMedicament",method=RequestMethod.GET)
	public String supprimerStockMedicament(String libMedic) {
		pharmacieMetierImpl.deleteProduitStock(libMedic);
		return "catalogue";
	}
	
    
}
