/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adm.web.controllers;

import com.adm.entities.Artikel;
import com.adm.entities.Bestelartikel;
import com.adm.entities.Bestelling;
import com.adm.entities.Betaling;
import com.adm.entities.Factuur;
import com.adm.entities.Klant;
import com.adm.entities.Prijs;
import com.adm.session.BestellingFacade;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Albert
 */
@ManagedBean(name = "bestellingController", eager = true)
@RequestScoped
public class BestellingController {

	@EJB
	BestellingFacade bestellingFacade;

	private int aantal;
	private Bestelartikel bestelArtikel;

	private final String[] betaalWijzen = new String[]{"Contant", "PIN", "IDeal", "PayPal", "Creditcard", "Natura"};
	private String betaalwijze;
	
	public String gotoWinkelwagen() {
		return "/pages/winkelwagen";
	}

	public String maakBestelArtikel(Artikel artikel, Prijs prijs, int aantal) {
		LinkedHashSet<Bestelartikel> winkelwagen = ((LinkedHashSet<Bestelartikel>) SessionController.findBean("winkelwagen"));
		boolean gevonden = false;
		for (Bestelartikel bestelartikel : winkelwagen){
			if (bestelartikel.getArtikelId().equals(artikel)) {
				gevonden = true;
				bestelartikel.setAantal(aantal + bestelartikel.getAantal());
			}
		}
		
		if (!gevonden) {
			((LinkedHashSet<Bestelartikel>) SessionController.findBean("winkelwagen")).add(new Bestelartikel(prijs, artikel, aantal));
		}

		return "/pages/winkelwagen";
	}

	public String naarBetaalMethode(){
		if(SessionController.findBean("klant") == null)
			return "/pages/klant/login";
		
		Klant klant = (Klant)SessionController.findBean("klant");
		
		Factuur factuur = new Factuur();
		Betaling betaling = new Betaling();
		Bestelling bestelling = new Bestelling();
		
		
		int jaar = Calendar.getInstance().get(Calendar.YEAR);
		
		bestelling.setBestelNummer(jaar + "-" + bestellingFacade.count());
		bestelling.setKlant(klant);
		bestelling.setDatumAanmaak(new Date());
		bestelling.setFactuur(factuur);
		bestelling.setBestelartikelCollection((List<Bestelartikel>)SessionController.findBean("winkelwagen"));

		factuur.setBestellingId(bestelling);
		factuur.setBetaling(betaling);
		factuur.setFactureringsDatum(new Date(System.currentTimeMillis()));
		factuur.setFactuurNummer(jaar + "-" + klant.getId() + "-" + bestelling.getId());
		
		betaling.setKlant(klant);
		betaling.setFactuurId(factuur);
		
		return "/pages/bestelling/betaalmethode";
	}
	
	public int getAantal() {
		return aantal;
	}

	public void setAantal(int aantal) {
		this.aantal = aantal;
	}

	public Bestelartikel getBestelArtikel() {
		return bestelArtikel;
	}

	public void setBestelArtikel(Bestelartikel bestelArtikel) {
		this.bestelArtikel = bestelArtikel;
	}

	public String[] getBetaalWijzen() {
		return betaalWijzen;
	}

	public String getBetaalwijze() {
		return betaalwijze;
	}

	public void setBetaalwijze(String betaalwijze) {
		this.betaalwijze = betaalwijze;
	}

	
}
