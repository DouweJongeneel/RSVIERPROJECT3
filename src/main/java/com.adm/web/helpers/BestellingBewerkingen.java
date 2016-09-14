//package com.adm.web.controllers;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.List;
//
//import javax.servlet.http.HttpSession;
//
//import com.adm.session.ArtikelFacade;
//import com.adm.session.BestellingFacade;
//import com.adm.entities.Artikel;
//import com.adm.entities.Bestelartikel;
//import com.adm.entities.Bestelling;
//import com.adm.entities.Betaalwijze;
//import com.adm.entities.Betaling;
//import com.adm.entities.Factuur;
//import com.adm.entities.Klant;
//import com.adm.entities.Prijs;
//import com.adm.entities.Prijsartikel;
//import javax.inject.Inject;
//import javax.servlet.http.HttpServletRequest;
//
//public class BestellingBewerkingen {
//
//	private BestellingFacade bestellingDAO;
//	private ArtikelFacade artikelDAO;
//
//
//	@Inject
//	public void setBestellingService(BestellingFacade bs){
//		bestellingDAO = bs;
//	}
//
//	@Inject
//	public void setArtikelDAO(ArtikelFacade as){
//		artikelDAO = as;
//	}
//
//
//	/*
//	 * Bestelling maken
//	 *
//	 */
//
////	@RequestMapping(value = "/bestelling", method = RequestMethod.GET)
//	public String listBestelling(Klant klant, HashSet<Bestelartikel> winkelwagen, HttpServletRequest request) {
//
//		HttpSession session = request.getSession();
//
//		//TODO 
////		List<Bestelling> bestellingen = bestellingDAO.findBestellingByKlantId(klant.getId());
////		session.setAttribute("bestellingList", bestellingen);
//		return "bestelling/bestellingLijst";
//	}
//
////	@RequestMapping(value = "/bestelling/catalogus", method = RequestMethod.GET)
//	public void viewCatalogus(HttpServletRequest request) {
//
//		HttpSession session = request.getSession();
//
//		//Zet een List<Bestelartikel> in het model
//		session.setAttribute("artikelen", setCatalogusPrijs(artikelDAO.findAll()));
//	}
//
//	/*
//	 *
//	 * Individuele bestellingen
//	 *
//	 */
////	@RequestMapping(value = "/bestelling/select/{bestelId}", method = RequestMethod.GET)
//	public void listBestelling(long bestelId, HttpServletRequest request) {
//
//		HttpSession session = request.getSession();
//		
//		List<Bestelartikel> bestelArtikelen = null; //bestellingDAO.findByBestellingId(bestelId); //TODO fix met named query
//		Bestelling bestelling = bestellingDAO.find(bestelId);
//
//		BigDecimal totaal = totaalPrijsBestelling(bestelArtikelen.iterator());
//
//		session.setAttribute("bestelArtikelen", bestelArtikelen);
//		session.setAttribute("factuur", bestelling.getFactuur());
//		session.setAttribute("totaalPrijsExBtw", totaal);
//		session.setAttribute("totaalPrijsIncBtw", totaal.multiply(new BigDecimal("1.21")));
//
//	}
//
////	@RequestMapping(value = "/bestelling/bevestigen", method = RequestMethod.GET)
//	public void kiesBetaalmethode(HttpServletRequest request) {
//
//		HttpSession session = request.getSession();
//		
//		@SuppressWarnings("unchecked")
//		HashSet<Bestelartikel> winkelwagen = (HashSet<Bestelartikel>)session.getAttribute("winkelwagen");
//		Klant klant = (Klant)session.getAttribute("klant");
//
//		Bestelling bestelling = new Bestelling();
////		bestelling.setBestelNummer(Calendar.getInstance().get(Calendar.YEAR) + "-" + bestellingDAO.getCount()); TODO bestelling teller aanmaken
//		
//		bestelling.setKlantId(klant);
//		bestelling.setDatumAanmaak(new Date().toString());
//		bestelling.setFactuur(new Factuur());
//
//		ArrayList<Bestelartikel> list = new ArrayList<Bestelartikel>();
//		list.addAll(winkelwagen);
//
//
//		Betaalwijze betaalWijze = new Betaalwijze();
//
//		session.setAttribute("nieuweBestelling", bestelling);
//		session.setAttribute("artikelLijst", list);
//		session.setAttribute("betaalWijze", betaalWijze);
//		session.setAttribute("totaalPrijs", totaalPrijsBestelling(winkelwagen.iterator()));
//
//	}
//
//	/* Bestelling naar database schrijven */
////	@RequestMapping(value = "/bestelling/bevestigen", method = RequestMethod.POST)
//	public void bestellingGeplaatst(HttpServletRequest request) {
//
//		HttpSession session = request.getSession();
//
//		Bestelling nieuweBestelling = (Bestelling)session.getAttribute("nieuweBestelling");
//
//		bestellingDAO.edit(nieuweBestelling);
//
//		@SuppressWarnings("unchecked")
//		HashSet<Bestelartikel> winkelwagen = (HashSet<Bestelartikel>) session.getAttribute("winkelwagen");
//
//		Iterator<Bestelartikel> it = winkelwagen.iterator();
//
//		while(it.hasNext()){
//			it.next().setBestellingId(nieuweBestelling);
//		}
//
//		nieuweBestelling.setBestelartikelCollection(winkelwagen);
//
//		Factuur fact = maakFactuur(nieuweBestelling);
//		Betaling bet = new Betaling();
//
//		bet.setBetaalDatum(new java.sql.Date(System.currentTimeMillis()));
////		bet.setBetaalwijze(betaalWijze); //TODO betaalwijze toevoegen
//		bet.setFactuurId(fact);
//		bet.setKlantId(nieuweBestelling.getKlantId());
//
//		fact.setBetaling(bet);
//
//		nieuweBestelling.setFactuur(maakFactuur(nieuweBestelling));
//
//	}
//
//
//
//	/* Extra methoden voor verwerken data */
//
//
//	/*
//	 *
//	 * Bouw factuur
//	 * Factuurnummer is bestelnummer met het aantal facturen in de bestelling + 1
//	 *
//	 */
//	private Factuur maakFactuur(Bestelling bestelling){
//
//		Factuur factuur = new Factuur();
//
//		factuur.setBestellingId(bestelling);
//		factuur.setFactureringsDatum(new java.sql.Date(System.currentTimeMillis()));
//		factuur.setFactuurNummer(bestelling.getBestelNummer());
//
//		return factuur;
//	}
//
//
//	/*
//	 * Rekent de totaalprijs van de 
//	 * bestelling uit 
//	 * 
//	 */
//
//	private BigDecimal totaalPrijsBestelling(Iterator<Bestelartikel> bestelArtikelen){
//
//		BigDecimal totaal = new BigDecimal("0");
//
//		while(bestelArtikelen.hasNext()){
//
//			Bestelartikel BA = bestelArtikelen.next();
//			BigDecimal prijs = BA.getPrijsId().getPrijs();
//			BigDecimal aantal = new BigDecimal(BA.getAantal());
//			totaal = totaal.add( prijs.multiply(aantal) );
//
//		}
//
//		return totaal;
//	}
//
//	/*
//	 * Loopt alle artikelen door om
//	 * de laatste prijsnotatie in de
//	 * artikelPrijs te zetten voor
//	 * de catalogus weergave
//	 * 
//	 */
//	private List<Bestelartikel> setCatalogusPrijs(List<Artikel> artikelen){
//		
//		List<Bestelartikel> artikelenLijst = new ArrayList<Bestelartikel>();
//		Iterator<Artikel> it = artikelen.iterator();
//		Artikel art;
//		Prijsartikel pr = new Prijsartikel();
//		
//		while(it.hasNext()){
//
//			art = it.next();
//			Iterator<Prijsartikel> prijsIt = art.getPrijsartikelCollection().iterator();
//
//			while(prijsIt.hasNext())
//				pr = prijsIt.next();
//			art.setPrijs(pr.getPrijs());
//			artikelenLijst.add(new Bestelartikel(pr.getPrijs(), art, 0));
//		}
//		return artikelenLijst;
//	}
//}