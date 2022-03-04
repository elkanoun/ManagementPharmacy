package com.infoMed.metier;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;

import com.infoMed.entities.Commande;
import com.infoMed.entities.Famille;
import com.infoMed.entities.Fournisseur;
import com.infoMed.entities.LigneCommande;
import com.infoMed.entities.Medicament;
import com.infoMed.entities.SousFamille;
import com.infoMed.entities.Stock;
import com.infoMed.entities.StockMedicament;
import com.infoMed.entities.Vente;
import com.infoMed.entities.VenteMedicament;

public interface IPharmacieMetier {
	//metier gestion fournisseurs
	public Optional<Fournisseur> searchFournisseurById(Long id);
	public List<Fournisseur> listFournisseurs();
	public Page<Fournisseur> searchFournisseurs(int p, int s, String motCle);
	public Fournisseur addFournisseur(Fournisseur fournisseur);
	public Fournisseur updateFournisseur(Fournisseur fournisseur);
	public boolean deleteFournisseur(Long id);
	public List<Fournisseur> searchFournisseur(String mc);
	public Fournisseur searchFournisseurByNom(String nom);
	//metier gestion commandes
	public Commande searchCommandeById(Long id);
	public List<Commande> listCommandes();
	public Commande addCommande(Commande commande);
	public List<Commande> listCommandesNonValides();
	public boolean deleteCommande(Long id);
	public List<Commande> listCommandesValides();
	public LigneCommande validCommandeEffectueeFournisseur(Commande commande, String libelleMedicament, int quantiteMedicament);
	public Commande finaliserCommandeEffectueeFournisseur(Commande commandeFinalisee, List<LigneCommande> ligneCommandes);
	public List<LigneCommande> listLignesCommandesDeCmd(Long num);
	public List<LigneCommande> listLignesCommandes();
	public LigneCommande consulterLigneCommandeAvecCmdEtMedic(Long numCmd, Long codeMedic);
	public Commande traiterMajLigneCommande(Long numCmd, Long codedMedic, Long codeNewMedic, int quantiteMedicament);
	public LigneCommande addLigneCommande(LigneCommande ligneCommande);





	//metier gestion catalogue et stock et alertes
	public List<Famille> listFamilles();
	public List<SousFamille> listSousFamilles();
	public List<Medicament> listMedicaments();
	public List<Stock> listStocks();
	public List<StockMedicament> listStocksMedicaments();
	public List<Medicament> listProductsAlertStock();
	public Optional<Famille> searchFamilleById(Long id);
	public Optional<SousFamille> searchSousFamilleById(Long id);
	public Optional<Stock> searchStockById(Long id);
	public Page<Stock> consulterStock(String motCle,int p,int s);
	public Optional<Medicament> searchMedicamentById(Long id);
	public Page<Medicament> consulterMedicament(String motCle,int p,int s);
	public Page<StockMedicament> consulterMedicamentsStock(String mc,int p,int s);
	public StockMedicament consulterMedicamentsStockLibMedic(String libMedic);
	public StockMedicament addProduitStock(Stock stock,Medicament medicament,int quantite,int alertQuantite);
	public StockMedicament updateProduitStock(Stock stock, Medicament medicament,int quantite,int alertQuantite);
	public boolean deleteProduitStock(String libMedic);
	public Famille addFamille(Famille famille);
	public SousFamille addSousFamille(SousFamille sousFamille);
	public Medicament addMedicament(Medicament medicament);
	public List<Famille >consulterFamille(String motCle);
	public List<SousFamille> consulterSousFamille(String motCle);
	public List<SousFamille> consulterSousFamilleDeFamille(Long code);
	public Famille consulterFamilleAvecId(Long code);
	public SousFamille consulterSousFamilleAvecId(Long code);
	public boolean deleteFamille(Long code);
	public boolean deleteSousFamille(Long code);
	public Famille consulterFamilleAvecLibelle(String libelle);
	public boolean isAlertMedicamentStock(int quantite,int alertQuantite);
	public Medicament consulterMedicamentAvecId(Long code);
	public SousFamille consulterSousFamilleAvecLib(String libelle);
	public Medicament consulterMedicamentAvecLibelle(String libelle);
	public List<LigneCommande> consulterLignesCommandesMedicament(String motCle);
	public Stock addStock(Stock stock);
	public Stock searchStockByNom(String nom);
	public Stock updateStock(@Valid Stock stock);
	public void deleteStock(Long id);
	//public List<StockMedicament> rechercherStocksMediByNomStock(String nom);





    
	//gestion medicaments

	//metier gestion ventes
	public Optional<Vente> searchVenteById(Long id);
	public List<VenteMedicament> listVentes();
	public VenteMedicament addVente(Vente vente,Medicament medicament,int quantite);
	public VenteMedicament updateVente(Vente vente,Medicament medicament,int quantite);
	public boolean deleteVente(Long id);
	




	


	
	

}
