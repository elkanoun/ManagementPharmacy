package com.infoMed.web;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.infoMed.entities.Commande;
import com.infoMed.entities.Fournisseur;
import com.infoMed.entities.LigneCommande;
import com.infoMed.entities.Medicament;
import com.infoMed.entities.SousFamille;
import com.infoMed.entities.StockMedicament;
import com.infoMed.metier.IPharmacieMetier;
import com.infoMed.metier.PharmacieMetierImpl;

@Controller
@RequestMapping("/commandes")
public class CommandeController {
	@Autowired
	IPharmacieMetier pharmacieMetierImpl=new PharmacieMetierImpl();
	//method recherche medicaments
	@RequestMapping(value="/rechercherMedicament")
	public String searchMedicament(Model model,
			                       @RequestParam(name="page", defaultValue="0")int p,
			                       @RequestParam(name="size", defaultValue="4")int s,
//			                       @RequestParam(name="page", defaultValue="0")int p2,
//			                       @RequestParam(name="size", defaultValue="3")int s2,
			                       @RequestParam(name="motCle", defaultValue="")String mc) {
	    Page<Medicament> pageMedicaments=pharmacieMetierImpl.consulterMedicament(mc,p, s);
		model.addAttribute("medicaments", pageMedicaments.getContent());
		int[] pages=new int[pageMedicaments.getTotalPages()];
		model.addAttribute("pages", pages);
		model.addAttribute("pageCourante", p);
		model.addAttribute("size", s);
		model.addAttribute("motCle", mc);

		/*Page<StockMedicament> pageStockMedicaments=pharmacieMetierImpl.consulterMedicamentsStock(mc,p,s);
		model.addAttribute("stockMedicaments", pageStockMedicaments.getContent());
		int[] pages2=new int[pageStockMedicaments.getTotalPages()];
		model.addAttribute("pages2", pages2);*/
//		model.addAttribute("pageCourante2", p2);
//		model.addAttribute("size2", s2);

					
		   return "pharmacie";
				}
	//method serach stocksMedicaments de médicament
	@RequestMapping(value="/stocksMedicamentRecherche")
	public String stocksMedicamentSearched(Model model,
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
		
		return "pharmacie";
	}
	//method accueil commande medicaments
	@RequestMapping(value="/commanderMedicament")
	public String commander(Model model, Long code) {
		Medicament medicamentChoisi=pharmacieMetierImpl.consulterMedicamentAvecId(code);
		model.addAttribute("medicamentChoisi", medicamentChoisi);
		model.addAttribute("listFournisseurs", pharmacieMetierImpl.listFournisseurs());
		List<Medicament> medicaments=pharmacieMetierImpl.listMedicaments();
		model.addAttribute("listMedicaments", medicaments);
         model.addAttribute("commande", new Commande());
			
		return "commande";
		}
	//formulaire ajout médicament
	@RequestMapping(value="/formMedicament",method=RequestMethod.GET)
		public String formulaireMedicament(Model model) {
		List<SousFamille> sousFamilles=pharmacieMetierImpl.listSousFamilles();
		model.addAttribute("listSousFamilles", sousFamilles);
						
		model.addAttribute("medicament", new Medicament());
					
		return "formMedicament";
		}
	//ajout médicament
	@RequestMapping(value="/saveMedicament",method=RequestMethod.POST)
	public String saveMedicament(Model model,@ Valid Medicament medicament, String libSousFamille, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "formMedicament";
		}
		SousFamille sousFamille=pharmacieMetierImpl.consulterSousFamilleAvecLib(libSousFamille);
		medicament.setSousFamille(sousFamille);
		pharmacieMetierImpl.addMedicament(medicament);
		return "redirect:/commandes/commanderMedicament?code="+medicament.getCode();
		}
	//formulaire ajout fournisseur
	@RequestMapping(value="/formFournisseur",method=RequestMethod.GET)
	public String formulaireFournisseur(Model model) {
		model.addAttribute("fournisseur", new Fournisseur());
				
		return "formFournisseur";
		}
	//ajout fournisseur
	@RequestMapping(value="/saveFournisseur",method=RequestMethod.POST)
	public String ajouterFournisseur(@Valid Fournisseur fournisseur,
					                     BindingResult bindingResult) {
		 if(bindingResult.hasErrors()) 
			 return "fournisseur";
		 pharmacieMetierImpl.addFournisseur(fournisseur);
				
		 return "redirect:/commandes/formRechercheFournisseur?motCle=";
		}

	//formulaire recherche fournisseur
	@RequestMapping(value="/formRechercheFournisseur")
	public String formSearchFournisseur() {
		
		return "fournisseurs";
		}
	//method recherche fournisseur
	@RequestMapping(value="/rechercherFournisseur")
	public String searchFournisseur(Model model,
			@RequestParam(name="page", defaultValue="0") int p,
            @RequestParam(name="size", defaultValue="3") int s,
            @RequestParam(name="motCle", defaultValue="") String mc) {
		Page<Fournisseur> pageFournisseurs=pharmacieMetierImpl.searchFournisseurs(p, s, mc);
		model.addAttribute("listFournisseurs", pageFournisseurs.getContent());
		int[] pages=new int[pageFournisseurs.getTotalPages()];
		model.addAttribute("pages", pages);
		model.addAttribute("pageCourante", p);
		model.addAttribute("size", s);
		model.addAttribute("motCle", mc);
				
		 return "fournisseurs";
		 }
	//method delete fournisseur
	@RequestMapping(value="/deleteFournisseur",method=RequestMethod.GET)
	public String supprimerFournisseur(Long id) {
		 pharmacieMetierImpl.deleteFournisseur(id);
			
	     return "redirect:/commandes/rechercherFournisseur?motCle=";
	    }
	//formulaire update fournisseur
	@RequestMapping(value="/formUpdateFournisseur",method=RequestMethod.GET)
	public String formulaireUpdateFournisseur(Model model, String nom) {
		Fournisseur fournisseur=pharmacieMetierImpl.searchFournisseurByNom(nom);
		model.addAttribute("fournisseur", fournisseur);
			
		return "editFournisseur";
		}
	//method traitements validation commande médicament effectué
	@RequestMapping(value="/processCommanderMedicament",method=RequestMethod.POST)
	public String traiterCommandeMedicament(Model model, Commande commande, String libelleMedicament, String nomFournisseur, int quantiteMedicament) {
		Fournisseur fournisseur=pharmacieMetierImpl.searchFournisseurByNom(nomFournisseur);
	    commande.setDate(new Date());
	    commande.setFournisseur(fournisseur);
	    
	    pharmacieMetierImpl.addCommande(commande);
		LigneCommande ligneCommandeValid = pharmacieMetierImpl.validCommandeEffectueeFournisseur(commande, libelleMedicament, quantiteMedicament);
        model.addAttribute("ligneCommandeValid", ligneCommandeValid);
        
		return "confirmationCommande";
	}
	//method accueil  reCommande medicaments
	@RequestMapping(value="/reCommanderMedicament")
	public String reCommander(Model model, Long idCommande) {
		 Commande commandeEnCours=pharmacieMetierImpl.searchCommandeById(idCommande);
		 model.addAttribute("commandeEnCours", commandeEnCours);
		 model.addAttribute("listMedicaments", pharmacieMetierImpl.listMedicaments());

		 
				
		 return "formReCommande";
		 }
	//method traitements reCommande médicament 
	@RequestMapping(value="/processReCommanderMedicament",method=RequestMethod.POST)
	public String reTraiterCommandeMedicament(Model model, Commande commandeEnCours, String libelleMedicament, int quantiteMedicament) {
		 pharmacieMetierImpl.validCommandeEffectueeFournisseur(commandeEnCours, libelleMedicament, quantiteMedicament);
		 Commande commande=pharmacieMetierImpl.searchCommandeById(commandeEnCours.getNum());
		 model.addAttribute("commande", commande);
	     List<LigneCommande> lignesCommandes = pharmacieMetierImpl.listLignesCommandesDeCmd(commandeEnCours.getNum());
	     model.addAttribute("lignesCommandes", lignesCommandes);
	        
		 return "confirmationCommande";
		}
	//method traitements finaliser commande
	@RequestMapping(value="/finaliserCommande",method=RequestMethod.GET)
	public String checkoutCommande(Model model, Long idCommande) {
		Commande commande=pharmacieMetierImpl.searchCommandeById(idCommande);
		List<LigneCommande> lignesCommandes=pharmacieMetierImpl.listLignesCommandesDeCmd(idCommande);
        model.addAttribute("lignesCommandes", lignesCommandes);
		Commande commandeValidee=pharmacieMetierImpl.finaliserCommandeEffectueeFournisseur(commande, lignesCommandes);
        model.addAttribute("commandeValide", commandeValidee);

		return "confirmationCommandeValide";
		
	}
	
	 /*management commandes*/
	//method accueil gestion commandes
	@RequestMapping(value="/gererCommandes")
	public String formGererCommandes() {
		
		return "commandesManagement";
	}
	//method search commandes d'un médicament
	@RequestMapping(value="/searchCommandes")
	public String rechercherCommandesMedicament(Model model, String motCle) {
		List<LigneCommande> lignesCommandes=pharmacieMetierImpl.consulterLignesCommandesMedicament(motCle);
		model.addAttribute("lignesCommandes", lignesCommandes);
		
		return "commandesManagement";
	}
	//method lignesCommandes d'une commande
	@RequestMapping(value="/showLignesCommandesCmd")
	public String rechercherLignesCommandesCmd(Model model, Long num) {
		model.addAttribute("cmdSearched", pharmacieMetierImpl.searchCommandeById(num));
		model.addAttribute("lignesCommandesCmd", pharmacieMetierImpl.listLignesCommandesDeCmd(num));
		
		return "commandesManagement";
	}
	//method accueil formulaire update commande
	@RequestMapping(value="/editCommande")
	public String formUpdateCommande(Model model, Long num) {
		model.addAttribute("commande", pharmacieMetierImpl.searchCommandeById(num));
		model.addAttribute("listFournisseurs", pharmacieMetierImpl.listFournisseurs());
		
		return "formMajCommande";
	}
	//method save commande aprés maj
	@RequestMapping(value="/saveCommande", method=RequestMethod.POST)
	public String addCommande(Model model, @Valid Commande commande, String nomFournisseur, BindingResult bindingResult) throws ParseException {
		if(bindingResult.hasErrors()) {
			return "formMajCommande";
		}
		Commande cmd=pharmacieMetierImpl.searchCommandeById(commande.getNum());
		commande.setDate(cmd.getDate());
		Fournisseur fournisseur=pharmacieMetierImpl.searchFournisseurByNom(nomFournisseur);
		commande.setFournisseur(fournisseur);
		pharmacieMetierImpl.addCommande(commande);
		return "redirect:/commandes/searchCommandes?motCle=";
	}
	//method delete commande
	@RequestMapping(value="/deleteCommande")
	public String supprimerCommandeEtLignes(Long num) {
		return "redirect:/commandes/searchCommandes?motCle=";
	}
	//method accueil formulaire update ligneCommande
	@RequestMapping(value="/editLigneCommande")
	public String formUpdateLigneCommande(Model model, Long numCmd, Long codeMedic) {
		LigneCommande ligneCommande=pharmacieMetierImpl.consulterLigneCommandeAvecCmdEtMedic(numCmd, codeMedic);
		model.addAttribute("ligneCommande", ligneCommande);
	    model.addAttribute("listMedicaments", pharmacieMetierImpl.listMedicaments());
	    
		return "formMajlignesCommande";
		
	}
	//method save ligneCommande aprés maj
	@RequestMapping(value="/saveLigneCommandeCmd", method=RequestMethod.POST)
	public String updateLigneCommande(Long num, String libOldMedicament, String libSelectedMedicament, int quantiteMedicament) {
		Medicament medicamentOld=pharmacieMetierImpl.consulterMedicamentAvecLibelle(libOldMedicament);
		Medicament medicamentSelected=pharmacieMetierImpl.consulterMedicamentAvecLibelle(libSelectedMedicament);
		pharmacieMetierImpl.traiterMajLigneCommande(num, medicamentOld.getCode(), medicamentSelected.getCode(), quantiteMedicament);
			
		return "redirect:/commandes/editCommande?num="+num;
			
	}

	/*management medicaments*/
	//method update medicament
	@RequestMapping(value="/editMedicament",method=RequestMethod.GET)
	public String updateMedicament(Model model, Long code) {
		model.addAttribute("medicament", pharmacieMetierImpl.consulterMedicamentAvecId(code));
		model.addAttribute("listSousFamilles", pharmacieMetierImpl.listSousFamilles());
		return "editMedicament";
	}
	//update médicament
	@RequestMapping(value="/mergeMedicament",method=RequestMethod.POST)
	public String updateMedicament(Model model,@ Valid Medicament medicament, String libSousFamille, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "editMedicament";
			}
		SousFamille sousFamille=pharmacieMetierImpl.consulterSousFamilleAvecLib(libSousFamille);
		medicament.setSousFamille(sousFamille);
		pharmacieMetierImpl.addMedicament(medicament);
		return "redirect:/commandes/rechercherMedicament?motCle="+medicament.getLibelle();
	}
	
	/*module vente médicaments*/
	//method vente médicament
	@RequestMapping(value="/vendreMedicament",method=RequestMethod.GET)
	public String passerVente(Model model, Long codeMedicament, Long codeStock) {
		//liste stocks medicament si insuffisant
		//option ajouter autre medicament+verification disponiblité stocks..
		
		
		return "formVente";
	}
	
	


}