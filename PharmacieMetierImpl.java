package com.infoMed.metier;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infoMed.dao.CommandeRepository;
import com.infoMed.dao.FamilleRepository;
import com.infoMed.dao.FournisseurRepository;
import com.infoMed.dao.LigneCommandeRepository;
import com.infoMed.dao.MedicamentRepository;
import com.infoMed.dao.SousFamilleRepository;
import com.infoMed.dao.StockMedicamentRepository;
import com.infoMed.dao.StockRepository;
import com.infoMed.dao.VenteMedicamentRepository;
import com.infoMed.dao.VenteRepository;
import com.infoMed.entities.Commande;
import com.infoMed.entities.Famille;
import com.infoMed.entities.Fournisseur;
import com.infoMed.entities.LigneCommande;
import com.infoMed.entities.LigneCommandeId;
import com.infoMed.entities.Medicament;
import com.infoMed.entities.SousFamille;
import com.infoMed.entities.Stock;
import com.infoMed.entities.StockMedicament;
import com.infoMed.entities.StockMedicamentId;
import com.infoMed.entities.Vente;
import com.infoMed.entities.VenteMedicament;
import com.infoMed.entities.VenteMedicamentId;

@Service
@Transactional
public class PharmacieMetierImpl implements IPharmacieMetier {
	@Autowired
	private FamilleRepository familleRepository;
	@Autowired
	private SousFamilleRepository sousFamilleRepository;
	@Autowired
	private FournisseurRepository fournisseurRepository;
	@Autowired
	private CommandeRepository commandeRepository;
	@Autowired
	private MedicamentRepository medicamentRepository;
	@Autowired
	private LigneCommandeRepository ligneCommandeRepository;
	@Autowired
	private StockRepository stockRepository;
	@Autowired
	private StockMedicamentRepository stockMedicamentRepository;
	@Autowired
	private VenteRepository venteRepository;
	@Autowired
	private VenteMedicamentRepository venteMedicamentRepository;
	
	/*metier gestion catalogue, stock, et alertes*/
	//method liste familles
	@Override
	public List<Famille> listFamilles() {
		return familleRepository.findAll();
	}
	//method liste sousFamilles
	@Override
	public List<SousFamille> listSousFamilles() {
		return sousFamilleRepository.findAll();
		}
	//method search famille avec mot cle
	@Override
	public List<Famille> consulterFamille(String motCle) {
		List<Famille> familles=familleRepository.consulterFamillesAvecMotCle("%"+motCle+"%");
		return familles;
		}
	//method search sous famille avec mot cle
	@Override
	public List<SousFamille> consulterSousFamille(String motCle) {
		List<SousFamille> sousFamilles=sousFamilleRepository.consulterSousFamillesAvecMotCle("%"+motCle+"%");
		return sousFamilles;
		}
	//method list stocks
	@Override
	public List<Stock> listStocks() {
		return stockRepository.findAll();
		}
	//method list stocskMedicaments
	@Override
	public List<StockMedicament> listStocksMedicaments() {
		return stockMedicamentRepository.findAll();
		}
	//rechercher famille avec id
	@Override
	public Optional<Famille> searchFamilleById(Long id) {
		return familleRepository.findById(id);
		}
	//rechercher sousFamille avec id
	@Override
	public Optional<SousFamille> searchSousFamilleById(Long id) {
		return sousFamilleRepository.findById(id);
		}
	//rechercher stock avec id
	@Override
	public Optional<Stock> searchStockById(Long id) {
		return stockRepository.findById(id);
		}
	//rechercher stock par motClé
	@Override
	public Page<Stock> consulterStock(String motCle, int p, int s) {
		Page<Stock> pagesStocks=stockRepository.ConsulterStock("%"+motCle+"%", PageRequest.of(p, s));
		return pagesStocks;
	}
	//rechercher médicament avec id
	@Override
	public Optional<Medicament> searchMedicamentById(Long id) {
		return medicamentRepository.findById(id);
		}
	//list medicaments
	@Override
	public List<Medicament> listMedicaments() {
		return medicamentRepository.findAll();
	}
	//rechercher médicaments avec motClé
	@Override
	public Page<Medicament> consulterMedicament(String motCle,int p, int s) {
		Page<Medicament> pageMedicaments=medicamentRepository.consulterMedicament("%"+motCle+"%", PageRequest.of(p, s));
		if(pageMedicaments==null) throw new RuntimeException("médicament introuvable");
		return pageMedicaments;
	}
	//rechercher stocks médicament avec motCle médic
	public Page<StockMedicament> consulterMedicamentsStock(String motCle,int p,int s) {
		Page<StockMedicament> stockMedicaments=stockMedicamentRepository.consulterMedicament("%"+motCle+"%", PageRequest.of(p, s));
		if(stockMedicaments==null) throw new RuntimeException("médicament introuvable dans aucun stock!");
		return stockMedicaments;
	}
	//rechercher stocks médicament avec lib médic
	@Override
	public StockMedicament consulterMedicamentsStockLibMedic(String libMedic) {
		 StockMedicament stockMedicament=stockMedicamentRepository.consulterStocksMedicLibMedic(libMedic);
		 return stockMedicament;
	}
    //ajouter produit au stock
	@Override
	public StockMedicament addProduitStock(Stock stock, Medicament medicament,int quantite,int alertQuantite) {
		
		//rechercher médic au stock #ToFinish avec stockMedicament sans pagination
		Page<StockMedicament> pagesStocksMedicaments=consulterMedicamentsStock(medicament.getLibelle(), 0, 4);
		List<StockMedicament> stocksMedicaments=pagesStocksMedicaments.getContent();
		for (StockMedicament stockMedicament : stocksMedicaments) {
			int oldQuantite=stockMedicament.getQuantite();
			quantite=quantite+oldQuantite;
		}
		
		StockMedicamentId stockMedicamentId=new StockMedicamentId(stock, medicament);
		boolean isAlert=isAlertMedicamentStock(quantite, alertQuantite);
		StockMedicament stockMedicament=new StockMedicament(stockMedicamentId, quantite, alertQuantite, isAlert);
		return stockMedicamentRepository.save(stockMedicament);
		
	}
    //update produit d'un stock
	@Override
	public StockMedicament updateProduitStock(Stock stock, Medicament medicament,int quantite,int alertQuantite) {
		StockMedicamentId stockMedicamentId=new StockMedicamentId(stock, medicament);
		boolean isAlert=isAlertMedicamentStock(quantite, alertQuantite);
		StockMedicament stockMedicament=new StockMedicament(stockMedicamentId, quantite, alertQuantite, isAlert);
		return stockMedicamentRepository.save(stockMedicament);
	}
    //supprimer produit d'un stock
	@Override
	public boolean deleteProduitStock(String libMedic) {
		StockMedicament stockMedicament=stockMedicamentRepository.consulterStocksMedicLibMedic(libMedic);
		stockMedicamentRepository.deleteProduitFromStock(libMedic);

		return true;

	}
    //list produis stocks en Alerte
	@Override
	public List<Medicament> listProductsAlertStock() {
		// TODO Auto-generated method stub
		return null;
	}
	//method add famille
	@Override
	public Famille addFamille(Famille famille) {
		return familleRepository.save(famille);
		}
	//method add souFamille
	@Override
	public SousFamille addSousFamille(SousFamille sousFamille) {
		return sousFamilleRepository.save(sousFamille);
		}
	//method add medicament
    @Override
	public Medicament addMedicament(Medicament medicament) {
		return medicamentRepository.save(medicament);
		}
  //search sousFamilles de famille
  	@Override
  	public List<SousFamille> consulterSousFamilleDeFamille(Long code) {
  		List<SousFamille> sousFamilles=sousFamilleRepository.sousFamillesDeFamille(code);
  		return sousFamilles;
  	}
  	//rechercher sous famille avec id
  	@Override
  	public SousFamille consulterSousFamilleAvecId(Long code) {
  		return sousFamilleRepository.findOneSousFamilleAvecId(code);
  	}
  	//rechercher famille avec id
  	@Override
  	public Famille consulterFamilleAvecId(Long code) {
  		return familleRepository.findOne(code);
  		
  	}
  	//supprimer famille (aprés suppression de leur sousFamilles)
  	@Override
  	public boolean deleteFamille(Long code) {
		List<SousFamille> sousFamilles=sousFamilleRepository.sousFamillesDeFamille(code);
		for (SousFamille sousFamille : sousFamilles) {
			sousFamilleRepository.deleteById(sousFamille.getCode());
			}
		
  		familleRepository.deleteById(code);
  		return true;
  	}
  	//supprimer sous famille
  	@Override
  	public boolean deleteSousFamille(Long code) {
  		sousFamilleRepository.deleteById(code);
  		return true;
  	}
	  /*metier gestion fournisseurs*/
	//list fournisseurs
	@Override
	public List<Fournisseur> listFournisseurs() {
		return fournisseurRepository.findAll();
	}
    //ajouter fournisseur
	@Override
	public Fournisseur addFournisseur(Fournisseur fournisseur) {
		fournisseurRepository.save(fournisseur);
		return fournisseur;
	}
    //update fournisseur
	@Override
	public Fournisseur updateFournisseur(Fournisseur fournisseur) {
		fournisseurRepository.save(fournisseur);
		return fournisseur;
	}
    //supprimer fournisseur
	@Override
	public boolean deleteFournisseur(Long id) {
		fournisseurRepository.deleteById(id);
		return true;
	}
    //rechercher fournisseur avec id
	@Override
	public Optional<Fournisseur> searchFournisseurById(Long id) {
		return fournisseurRepository.findById(id);
	}
    //rechercher fournisseur avec motClé
	@Override
	public List<Fournisseur> searchFournisseur(String motCle) {
		List<Fournisseur> fournisseurs=fournisseurRepository.consulterFournisseur("%"+motCle+"%");
		return fournisseurs;
	}
	//methode rechercher fournisseurs avec motCle en pagination
	@Override
	public Page<Fournisseur> searchFournisseurs(int p, int s, String motCle) {
		Page<Fournisseur> pageFournisseurs=fournisseurRepository.chercherFournisseurs("%"+motCle+"%",PageRequest.of(p, s));
		return pageFournisseurs;
	}
	//method search fournisseur avec nom
	@Override
	public Fournisseur searchFournisseurByNom(String nom) {
		return fournisseurRepository.consulterFournisseurAvecNom(nom);
	}
	//rechercher famille avec libelle
	@Override
	public Famille consulterFamilleAvecLibelle(String libelle) {
		return familleRepository.findOneAvecLibelle(libelle);
	}
	
	
	  /*metier gestion commandes*/
	//rechercher commande avec id
	@Override
	public Commande searchCommandeById(Long id) {
		return commandeRepository.findOneCommandeById(id);
	}
	//liste commandes
	@Override
	public List<Commande> listCommandes() {
		return commandeRepository.findAll();
	}
    //ajouter + modifier  commande
	@Override
	public Commande addCommande(Commande commande) {
		commandeRepository.save(commande);
		return commande;
	}
    //list commandes non validées
	@Override
	public List<Commande> listCommandesNonValides() {
		//List<Commande> commandes=commandeRepository.isNotValid();
		return null;
	}
    //supprimer commande
	@Override
	public boolean deleteCommande(Long id) {
		commandeRepository.deleteById(id);
		return true;
	}
    //list commandes validées
	@Override
	public List<Commande> listCommandesValides() {
		//List<Commande> commandes=commandeRepository.isValid();
		return null;
	}
    //valider commande avec un fournisseur
	@Override
	public LigneCommande validCommandeEffectueeFournisseur(Commande commande, String libelleMedicament, int quantiteMedicament) {
		Medicament medicament=consulterMedicamentAvecLibelle(libelleMedicament);
		LigneCommandeId ligneCommandeId=new LigneCommandeId(medicament, commande);
		LigneCommande ligneCommande=new LigneCommande(ligneCommandeId, quantiteMedicament);

		return ligneCommandeRepository.save(ligneCommande);
	}
	//list lignesCommandes d'une commande 
	@Override
	public List<LigneCommande> listLignesCommandesDeCmd(Long num) {
	    return ligneCommandeRepository.listLignesCommandesByNumCommande(num);
		}
	//method finaliser traitements commande
	@Override
	public Commande finaliserCommandeEffectueeFournisseur(Commande commandeFinalisee, List<LigneCommande> ligneCommandes) {
		for (LigneCommande ligneCommande : ligneCommandes) {
			double total=commandeFinalisee.getMontant();
			double montantLigneCmd=ligneCommande.getPk().getMedicament().getPrixAchat()*ligneCommande.getQuantiteMedicament();
			commandeFinalisee.setMontant(total+montantLigneCmd); 
		}
		commandeFinalisee.setValid(true);
		//déstribution stocks
		/*for (LigneCommande ligneCommande : ligneCommandes) {
			Stock stock=stockRepository.chercherStockByNom(ligneCommande.getPk().getMedicament().getSousFamille().getFamille().getLibelle());
			List<StockMedicament> stockMedicaments=rechercherStocksMediByNomStock(stock.getLibelle()); //probléme dans cette métohode_toDo
			int total=0;
			for (StockMedicament stockMedicament : stockMedicaments) {
				total=total+stockMedicament.getQuantite();
				
				if(stockMedicament.getAlertQuantite()-(ligneCommande.getQuantiteMedicament()+total)>=0) {
					StockMedicamentId stockMedicamentId=new StockMedicamentId(stock, ligneCommande.getPk().getMedicament());
					StockMedicament stockMedic=new StockMedicament(stockMedicamentId, ligneCommande.getQuantiteMedicament(), 10, false);
					stockMedicamentRepository.save(stockMedic);
				}else {
					int quantiteMedicament2=stockMedicament.getAlertQuantite()-(ligneCommande.getQuantiteMedicament()+total);
					int quantiteMedicament1=ligneCommande.getQuantiteMedicament()-quantiteMedicament2;
					StockMedicamentId stockMedicamentId=new StockMedicamentId(stock, ligneCommande.getPk().getMedicament());
					StockMedicament stockMedic=new StockMedicament(stockMedicamentId, quantiteMedicament1, 10, false);
					stockMedicamentRepository.save(stockMedic);
					
					Stock stock2=stockRepository.chercherStockByNom("Divers");
					StockMedicamentId stockMedicamentId2=new StockMedicamentId(stock2, ligneCommande.getPk().getMedicament());
					StockMedicament stockMedic2=new StockMedicament(stockMedicamentId, quantiteMedicament2, 10, false);
					stockMedicamentRepository.save(stockMedic2);
				}
				
			}
			
		}*/
		
			return commandeRepository.save(commandeFinalisee);
		}
	//method alert stock medicament
	@Override
	public boolean isAlertMedicamentStock(int quantite, int alertQuantite) {
		if(quantite<=alertQuantite)
			return true;
			return false;
		}
	//method search medicament avec id
	@Override
	public Medicament consulterMedicamentAvecId(Long code) {
		return medicamentRepository.findOneMedicamentAvecId(code);
		}
	//search one famille avec libelle
	@Override
	public SousFamille consulterSousFamilleAvecLib(String libelle) {
		return sousFamilleRepository.findOneSousFamilleAvecLib(libelle);
		}
	//method search medicament avec libelle
	@Override
	public Medicament consulterMedicamentAvecLibelle(String libelle) {
		return medicamentRepository.findOneMedicamentAvecLib(libelle);
		}
	//method list lignesCommandes
	@Override
	public List<LigneCommande> listLignesCommandes() {
	   return ligneCommandeRepository.findAll();
		}
	//method search commandes d'un medicament
	@Override
	public List<LigneCommande> consulterLignesCommandesMedicament(String motCle) {
		return ligneCommandeRepository.listLignesCommandesByMedicament("%"+motCle+"%");
	}
	//method formulaire accueil update ligneCommande d'une commande
	@Override
	public LigneCommande consulterLigneCommandeAvecCmdEtMedic(Long numCmd, Long codeMedic) {
		return ligneCommandeRepository.searchLigneCommandeByCmdAndMedic(numCmd, codeMedic);
	}
	//traiter maj ligneCommande, and montant commande
	@Override
	public Commande traiterMajLigneCommande(Long numCmd, Long codeOldMedic, Long codeNewMedic, int quantiteMedicament) {
		LigneCommande oldLigneCommande=consulterLigneCommandeAvecCmdEtMedic(codeOldMedic, numCmd);
		if(codeOldMedic!=codeNewMedic) {
			List<LigneCommande> ligneCommandes=ligneCommandeRepository.listLignesCommandesByNumCommande(numCmd);
			ligneCommandes.remove(oldLigneCommande);
			//deleteLignesCommandeCmdByNumCmd(numCmd);
		}
		Commande commande=searchCommandeById(numCmd);
		Medicament medicament=consulterMedicamentAvecId(codeNewMedic);
		LigneCommandeId ligneCommandeId=new LigneCommandeId(medicament, commande);
		LigneCommande ligneCommande=new LigneCommande(ligneCommandeId, quantiteMedicament);
		addLigneCommande(ligneCommande);
		commande.setMontant(0);
		addCommande(commande);
		List<LigneCommande> ligneCommandes=listLignesCommandesDeCmd(numCmd);
		Commande commandeMaj=finaliserCommandeEffectueeFournisseur(commande, ligneCommandes);

		return commandeMaj;
	}
	//method add ligneCommande
	@Override
	public LigneCommande addLigneCommande(LigneCommande ligneCommande) {
		return ligneCommandeRepository.save(ligneCommande);
	}
	//ajouter stock
	@Override
	public Stock addStock(Stock stock) {
		return stockRepository.save(stock);
	}
	//méthode recherche stock avec nom
	@Override
	public Stock searchStockByNom(String nom) {
		return stockRepository.chercherStockByNom(nom);
	}
	//maj stock
	@Override
	public Stock updateStock(@Valid Stock stock) {
		return stockRepository.save(stock);
	}
	//delete stock
	@Override
	public void deleteStock(Long id) {
		stockRepository.deleteById(id);
	}
	//recherche stocksMedicaments avec nom stock
//	@Override
//	public List<StockMedicament> rechercherStocksMediByNomStock(String nom) {
//		return stockMedicamentRepository.searchStocksMedicByNomStock(nom);
//	}
	
	  
	
      /*métier gestion ventes*/
	//list ventes
	@Override
	public List<VenteMedicament> listVentes() {
		return venteMedicamentRepository.findAll();
	}
    //ajouter vente d'un médicament
	@Override
	public VenteMedicament addVente(Vente vente,Medicament medicament,int quantite) {
		venteRepository.save(vente);
		VenteMedicamentId venteMedicamentId=new VenteMedicamentId(vente, medicament);
		VenteMedicament venteMedicament=new VenteMedicament(venteMedicamentId, quantite);
		return venteMedicamentRepository.save(venteMedicament);
	}
    //update vente d'un médicament
	@Override
	public VenteMedicament updateVente(Vente vente,Medicament medicament,int quantite) {
		venteRepository.save(vente);
		VenteMedicamentId venteMedicamentId=new VenteMedicamentId(vente, medicament);
		VenteMedicament venteMedicament=new VenteMedicament(venteMedicamentId, quantite);
		return venteMedicamentRepository.save(venteMedicament);
	}
    //supprimer vente
	@Override
	public boolean deleteVente(Long id) {
		 venteRepository.deleteById(id);
		 return true;
			}
    //rechercher vente avec id
	@Override
	public Optional<Vente> searchVenteById(Long id) {
		return venteRepository.findById(id);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	

	
	

}
