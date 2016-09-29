/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adm.web.controllers;

import com.adm.entities.Artikel;
import com.adm.entities.Prijs;
import com.adm.session.ArtikelFacade;
import com.adm.web.helpers.FileUploadHelper;
import java.io.Serializable;
import java.math.BigDecimal;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Albert
 */
@ManagedBean(name = "articleController", eager = true)
@RequestScoped
public class ArticleController implements Serializable {

	@EJB
	ArtikelFacade artikelFacade;

	private String artikelNaam;
	private int levertijd;
	private BigDecimal prijs;
	private String artikelType;
	private Artikel artikel;
	private int aantal;
	
	/**
	 * ARTIKEL METHODES
	 */
	public String gotoArticleOverview(String type) {
		SessionController.naarSessieVariabele("itemOverzicht", artikelFacade.withNamedQuery("Artikel.findByArtikelType", new String[]{"artikelType"}, new String[]{type}));
		return "/pages/artikel/artikelOverzicht?faces-redirect=true";
	}

	public String gotoRegisterArticle() {
		return "/protected/artikel/artikelRegistratie";
	}

	public String gotoArticle(Artikel artikel) {
		SessionController.naarSessieVariabele("artikel", artikel);
		return "/pages/artikel/toonArtikel";
	}

	public String gotoAlterArticle() {

//		artikelBewerkingen.wijzigArtikel(request);
		return "/protected/artikel/artikelWijziging";
	}

	public String schrijfArtikelNaarDatabase() {
		Artikel artikel = artikelFacade.create(toArtikel());

		FileUploadHelper.hernoemEnVerplaatsAfbeelding(artikel.getArtikelId(), "artikel");

		return "/protected/artikel/artikelRegustratie";
	}

	public List getArtikelen() {
		List<Artikel> artikelen = artikelFacade.withNamedQuery("Artikel.findByArtikelType", new String[]{"artikelType"}, new String[]{"%"});
		return artikelen;
	}

	public Artikel toArtikel() {
		Artikel artikel = new Artikel(artikelNaam, levertijd, artikelType);
		Prijs prijsObject = new Prijs(prijs);
		prijsObject.setArtikel(artikel);

		artikel.addPrijsAanCollectie(prijsObject);
		return artikel;
	}

	/**
	 *
	 * Getters en Setters
	 *
	 */
	public String getArtikelNaam() {
		return artikelNaam;
	}

	public void setArtikelNaam(String artikelNaam) {
		this.artikelNaam = artikelNaam;
	}

	public int getLevertijd() {
		return levertijd;
	}

	public void setLevertijd(int levertijd) {
		this.levertijd = levertijd;
	}

	public BigDecimal getPrice() {
		return prijs;
	}

	public void setPrice(BigDecimal prijs) {
		this.prijs = prijs;
	}

	public String getArtikelType() {
		return artikelType;
	}

	public void setArtikelType(String artikelType) {
		this.artikelType = artikelType;
	}

	public BigDecimal getPrijs() {
		return prijs;
	}

	public void setPrijs(BigDecimal prijs) {
		this.prijs = prijs;
	}

	public Artikel getArtikel() {
		return artikel;
	}

	public void setArtikel(Artikel artikel) {
		this.artikel = artikel;
	}

	public int getAantal() {
		return aantal;
	}

	public void setAantal(int aantal) {
		this.aantal = aantal;
	}

}
