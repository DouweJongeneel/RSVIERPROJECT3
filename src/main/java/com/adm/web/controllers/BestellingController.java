/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adm.web.controllers;

import com.adm.entities.Artikel;
import com.adm.entities.Bestelartikel;
import com.adm.entities.Prijs;
import com.adm.session.BestellingFacade;
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

	private int aantal;
	private Bestelartikel bestelArtikel;

	public String gotoWinkelwagen() {
		return "/pages/winkelwagen";
	}

	public String maakBestelArtikel(Artikel artikel, Prijs prijs, int aantal) {
		LinkedHashSet<Bestelartikel> winkelwagen = ((LinkedHashSet<Bestelartikel>) SessionController.findBean("winkelwagen"));
		for (Bestelartikel bestelartikel : winkelwagen) {
			if (bestelartikel.getArtikelId().equals(artikel)) {
				bestelArtikel.setAantal(aantal + bestelartikel.getAantal());
			} else {
				((LinkedHashSet<Bestelartikel>) SessionController.findBean("winkelwagen")).add(new Bestelartikel(prijs, artikel, aantal));
			}
		}
		return "/pages/winkelwagen";
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

}
