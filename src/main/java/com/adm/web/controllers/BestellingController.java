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
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;

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

	private Bestelling bestelling;

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
		for (Bestelartikel bestelartikel : winkelwagen) {
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

	public String naarBetaalMethode() {
		if (((Klant) SessionController.findBean("klant")).getEmail() == null) {
			return "/pages/klant/login";
		}

		return "/pages/bestelling/betaalmethode";
	}

	public String plaatsBestelling() {
		Klant klant = (Klant) SessionController.findBean("klant");
		int jaar = Calendar.getInstance().get(Calendar.YEAR);

		System.out.println("V");
		System.out.println("Hier");
		System.out.println("V");

		Factuur factuur = new Factuur();
		Betaling betaling = new Betaling();

		bestelling = new Bestelling();
		bestelling.setKlant(klant);
		bestelling.setBestelNummer(jaar + "-" + (1 + bestellingFacade.count()));
		bestelling.setFactuur(factuur);
		bestelling.setBestelartikelCollection((LinkedHashSet<Bestelartikel>) SessionController.findBean("winkelwagen"));
		bestelling = bestellingFacade.create(bestelling);

		for(Bestelartikel best : bestelling.getBestelartikelCollection()){
			best.setBestellingId(bestelling);
		}
		
		factuur = bestelling.getFactuur();
		
		factuur.setBestellingId(bestelling);
		factuur.setBetaling(betaling);
		factuur.setFactureringsDatum(new Date(System.currentTimeMillis()));
		factuur.setFactuurNummer(jaar + "-" + klant.getId() + "-" + bestelling.getId());
		factuur.setKlant(klant);

		betaling.setFactuurId(factuur);
		betaling.setBetaalwijze(betaalwijze);
		betaling.setKlant(klant);

		klant.voegToeAanFactuurCollection(factuur);
		klant.voegToeAanBestellingCollection(bestelling);

		bestellingFacade.edit(bestelling);

		SessionController.naarSessieVariabele("winkelwagen", new LinkedHashSet<Bestelartikel>());

		return "/pages/bestelling/bedankt";
	}

	public BigDecimal getTotaalPrijs(){
		BigDecimal totaal = new BigDecimal("0");
		
		LinkedHashSet<Bestelartikel> winkelwagen = ((LinkedHashSet<Bestelartikel>) SessionController.findBean("winkelwagen"));
		for(Bestelartikel bestart : winkelwagen)
			totaal = bestart.getPrijsId().getPrijs().add(totaal);
		return totaal;
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

	public Bestelling getBestelling() {
		return bestelling;
	}

	public void setBestelling(Bestelling bestelling) {
		this.bestelling = bestelling;
	}

}
