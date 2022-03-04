package com.infoMed;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.infoMed.dao.FamilleRepository;
import com.infoMed.dao.MedicamentRepository;
import com.infoMed.dao.SousFamilleRepository;
import com.infoMed.dao.StockRepository;
import com.infoMed.metier.IPharmacieMetier;
import com.infoMed.metier.PharmacieMetierImpl;

@SpringBootApplication
public class GestionPharmacieApplication implements CommandLineRunner {
	@Autowired
	IPharmacieMetier pharmacierMetierImpl=new PharmacieMetierImpl();
	@Autowired
	StockRepository stockRepository;
	@Autowired
	FamilleRepository familleRepository;
	@Autowired
	SousFamilleRepository sousFamilleRepository;
	@Autowired
	MedicamentRepository medicamentRepository;
	

	public static void main(String[] args) {
		SpringApplication.run(GestionPharmacieApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		//publication sw pharmacie
//		String url="http://localhost:8888/";
//		Endpoint.publish(url, new PharmacieService());
//		System.out.println(url);
		
		/*//fournisseurs
		Fournisseur fournisseur1=(new Fournisseur(null, "pharma kmed", "cheikh mfddl", "salé", "0651623839", null));
		Fournisseur fournisseur2=(new Fournisseur(null, "pharma abdo", "hay sbata", "casablanca", "0551493839", null));
		Fournisseur fournisseur3=(new Fournisseur(null, "pharma leila", "hay beni mekada", "tanger", "0591624339", null));
		pharmacierMetierImpl.addFournisseur(fournisseur1);pharmacierMetierImpl.addFournisseur(fournisseur2);pharmacierMetierImpl.addFournisseur(fournisseur3);
		//stocks
		Stock stock1=new Stock(null, "A", 100, null);
		Stock stock2=new Stock(null, "B", 100, null);
		Stock stock3=new Stock(null, "C", 100, null); 
		Stock stock4=new Stock(null, "D", 100, null);
		Stock stock5=new Stock(null, "E", 100, null);
		stockRepository.save(stock1);stockRepository.save(stock2);stockRepository.save(stock3);stockRepository.save(stock4);stockRepository.save(stock5);
		//familles médicaments
		Famille famille1=new Famille(null,"Allergologie",null);
		Famille famille2=new Famille(null,"Anesthésie, réanimation",null);
		Famille famille3=new Famille(null,"Antalgiques",null);
		Famille famille4=new Famille(null,"Anti-inflammatoires ",null);
		Famille famille5=new Famille(null,"Cancérologie et hématologie",null);
		Famille famille6=new Famille(null,"Cardiologie et angéiologie",null);
		Famille famille7=new Famille(null,"Contraception et interruption de grossesse",null);
		Famille famille8=new Famille(null,"Dermatologie ",null);
		Famille famille9=new Famille(null,"Endocrinologie ",null);
		Famille famille10=new Famille(null,"Gastro-Entéro-Hépatologie ",null);
		Famille famille11=new Famille(null,"Gynécologie ",null);
		Famille famille12=new Famille(null,"Hémostase et sang",null);
		Famille famille13=new Famille(null,"Immunologie ",null);
		Famille famille14=new Famille(null,"Infectiologie - Parasitologie ",null);
		Famille famille15=new Famille(null,"Métabolisme et nutrition  ",null);
		Famille famille16=new Famille(null,"Neurologie-psychiatrie",null);
		Famille famille17=new Famille(null,"Ophtalmologie ",null);
		Famille famille18=new Famille(null,"Oto-rhino-laryngologie ",null);
		Famille famille19=new Famille(null,"Pneumologie ",null);
		Famille famille20=new Famille(null,"Produits diagnostiques ou autres produits thérapeutiques",null);
		Famille famille21=new Famille(null,"Rhumatologie ",null);
		Famille famille22=new Famille(null,"Sang et dérivés ",null);
		Famille famille23=new Famille(null,"Souches Homéopathiques",null);
		Famille famille24=new Famille(null,"Stomatologie ",null);
		Famille famille25=new Famille(null,"Toxicologie ",null);
		Famille famille26=new Famille(null,"Urologie néphrologie",null);
		familleRepository.save(famille1);familleRepository.save(famille2);familleRepository.save(famille3);familleRepository.save(famille4);familleRepository.save(famille5);
		familleRepository.save(famille6);familleRepository.save(famille7);familleRepository.save(famille8);familleRepository.save(famille9);familleRepository.save(famille10);
		familleRepository.save(famille11);familleRepository.save(famille12);familleRepository.save(famille13);familleRepository.save(famille14);familleRepository.save(famille15);
		familleRepository.save(famille16);familleRepository.save(famille17);familleRepository.save(famille18);familleRepository.save(famille19);familleRepository.save(famille20);
		familleRepository.save(famille21);familleRepository.save(famille22);familleRepository.save(famille23);familleRepository.save(famille24);familleRepository.save(famille25);
		//sous familles médicaments
		SousFamille sousFamille1=new SousFamille(null, "Adrénaline", famille1, null);
		SousFamille sousFamille2=new SousFamille(null, "Allergènes", famille1, null);
		SousFamille sousFamille3=new SousFamille(null, "Antihistaminiques H1", famille1, null);
		SousFamille sousFamille4=new SousFamille(null, "Autres anti-allergiques", famille1, null);
		SousFamille sousFamille5=new SousFamille(null, "Diluants et solvants pour allergènes", famille1, null);
		SousFamille sousFamille6=new SousFamille(null, "Anesthésie de surface", famille2, null);
		SousFamille sousFamille7=new SousFamille(null, "Anesthésie générale", famille2, null);
		SousFamille sousFamille8=new SousFamille(null, "Anesthésie locale ou régionale", famille2, null);
		SousFamille sousFamille9=new SousFamille(null, "Rachianesthésie", famille2, null);
		SousFamille sousFamille10=new SousFamille(null, "Solutions cardioplégiques", famille2, null);
		SousFamille sousFamille11=new SousFamille(null, "Solutions pour dialyse péritonéale", famille2, null);
		SousFamille sousFamille12=new SousFamille(null, "Solutions pour hémodialyse et hémofiltration", famille2, null);
		SousFamille sousFamille13=new SousFamille(null, "Solutions pour perfusion", famille2, null);
		SousFamille sousFamille14=new SousFamille(null, "Succédanés du plasma", famille2, null);
		SousFamille sousFamille15=new SousFamille(null, "Sympathomimétiques IV", famille2, null);
		
		sousFamilleRepository.save(sousFamille1);sousFamilleRepository.save(sousFamille2);sousFamilleRepository.save(sousFamille3);sousFamilleRepository.save(sousFamille4);
		sousFamilleRepository.save(sousFamille5);sousFamilleRepository.save(sousFamille6);sousFamilleRepository.save(sousFamille7);sousFamilleRepository.save(sousFamille8);
		sousFamilleRepository.save(sousFamille9);sousFamilleRepository.save(sousFamille10);sousFamilleRepository.save(sousFamille11);sousFamilleRepository.save(sousFamille12);
		sousFamilleRepository.save(sousFamille13);sousFamilleRepository.save(sousFamille14);sousFamilleRepository.save(sousFamille15);
		

		//medicaments
		Medicament medicament1=new Medicament(null, "ADRENALINE AGUET SANS SULFITE", 200, 230, null, sousFamille1, null, null, null);
		medicament1.setNotice("ADRENALINE AGUETTANT 1 mg/1 mL Injectable boîte de 10 ampoules de 1 mL");
		Medicament medicament2=new Medicament(null, "ADRENALINE AGUETTANT", 210, 250, null, sousFamille1, null, null, null);
		medicament2.setNotice("ADRENALINE AGUETTANT 1 mg/1 mL Injectable boîte de 10 ampoules de 1 mL");
		Medicament medicament3=new Medicament(null, "ADRENALINE RENAUDIN", 280, 300, null, sousFamille1, null, null, null);
		medicament3.setNotice("ADRENALINE RENAUDIN 1 mg/mL Injectable boîte de 100 ampoules bouteilles de 5 ml");
		Medicament medicament4=new Medicament(null, "ANAHELP", 365, 495, null, sousFamille1, null, null, null);
		medicament4.setNotice("ANAHELP 1 mg/1 ml, solution injectable en seringue préremplie (SC et IM), trousse d'urgence de 1 seringue préremplie de 1 mL");
		Medicament medicament5=new Medicament(null, "ANAPENT", 260, 280, null, sousFamille1, null, null, null);
		medicament5.setNotice("ANAPEN 150 µg/0,3 mL Injectable boîte de 2 seringues préremplies de 0,30 mL");
		Medicament medicament6=new Medicament(null, "EMERADE", 320, 360, null, sousFamille1, null, null, null);
		medicament6.setNotice("EMERADE 150 microgrammes Injectable IM boîte de 2 stylos préremplis de 0,15 mL");
		Medicament medicament7=new Medicament(null, "EPIPEN", 365, 495, null, sousFamille1, null, null, null);
		medicament7.setNotice("EPIPEN 0,15 mg/0,3 mL Injectable boîte de 2 stylos préremplis de 0,30 mL");
		Medicament medicament8=new Medicament(null, "JEXT", 320, 350, null, sousFamille1, null, null, null);
		medicament8.setNotice("JEXT 150 microgrammes, solution injectable en stylo prérempli, boîte de 2 stylos préremplis de 0,15 mL");
		
		medicamentRepository.save(medicament1);medicamentRepository.save(medicament2);medicamentRepository.save(medicament3);medicamentRepository.save(medicament4);
		medicamentRepository.save(medicament5);medicamentRepository.save(medicament6);medicamentRepository.save(medicament7);medicamentRepository.save(medicament8);
        //commandes
		DateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date=new Date();
		Commande commande=new Commande(null, date, String.valueOf(new Date().getHours()), 0, false, fournisseur1,null);
		pharmacierMetierImpl.addCommande(commande);
		pharmacierMetierImpl.validCommandeEffectueeFournisseur(commande, fournisseur1, medicament1, 100);
		pharmacierMetierImpl.validCommandeEffectueeFournisseur(commande, fournisseur1, medicament2, 100);
		pharmacierMetierImpl.validCommandeEffectueeFournisseur(commande, fournisseur1, medicament3, 100);
		pharmacierMetierImpl.validCommandeEffectueeFournisseur(commande, fournisseur1, medicament4, 100);
		pharmacierMetierImpl.validCommandeEffectueeFournisseur(commande, fournisseur1, medicament5, 100);
		pharmacierMetierImpl.validCommandeEffectueeFournisseur(commande, fournisseur1, medicament6, 100);
		pharmacierMetierImpl.validCommandeEffectueeFournisseur(commande, fournisseur1, medicament7, 100);
		pharmacierMetierImpl.validCommandeEffectueeFournisseur(commande, fournisseur1, medicament8, 100);


		//stockmédicaments
		pharmacierMetierImpl.addProduitStock(stock1, medicament1, 100, 10);
		pharmacierMetierImpl.addProduitStock(stock2, medicament2, 200, 15);
		pharmacierMetierImpl.addProduitStock(stock3, medicament3, 150, 15);
		pharmacierMetierImpl.addProduitStock(stock4, medicament4, 200, 7);
		pharmacierMetierImpl.addProduitStock(stock4, medicament5, 300, 17);
		pharmacierMetierImpl.addProduitStock(stock5, medicament6, 200, 5);
		pharmacierMetierImpl.addProduitStock(stock5, medicament7, 300, 9);
		pharmacierMetierImpl.addProduitStock(stock5, medicament8, 200, 8);*/
		





		










		












		
	}

}
