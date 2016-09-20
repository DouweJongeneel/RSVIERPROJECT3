package com.adm.web.controllers;

import com.adm.entities.Klant;
import com.adm.session.KlantFacade;
import com.adm.web.helpers.ArtikelBewerkingen;
import java.io.Serializable;
import java.util.Map;
import javax.annotation.PostConstruct;

import javax.ejb.EJB;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean(name = "servletController", eager = true)
@RequestScoped
public class ServletController implements Serializable {

	@EJB
	ArtikelBewerkingen artikelBewerkingen;

	@EJB
	KlantFacade klantFacade;

	@EJB
	Klant klant;
	
	public String gotoHome() {
		return "/home";
	}

	
	@PostConstruct 
	public void init(){
		if(findBean("klant") == null)
			klant = new Klant();
	}
	
	/**
	 * ARTIKEL METHODES
	 */
	public String gotoArticleOverview() {

//		artikelBewerkingen.maakArtikelOverzicht(request);
		// stuur de client naar het artikel overzicht
		return "/artikel/artikelOverzicht";
	}

	public String gotoRegisterArticle() {
//		artikelBewerkingen.maakArtikelRegistratieFormulier(request);
		return "/artikel/artikelRegistratie";
	}

	public String gotoArticle() {

		// Stuur de client door naar de artikelRegistratie view
//		artikelBewerkingen.toonArtikel(request);
		return "/artikel/toonArtikel";
	}

	public String gotoAlterArticle() {

//		artikelBewerkingen.wijzigArtikel(request);
		return "/artikel/artikelWijziging";

	}

	public String gotoDeleteArticle() {
//		artikelBewerkingen.haalArtikelUitVoorraad(request);

		// If the direct is from the product profile page, redirect to profilepage instead of product list.
//			if ((Integer) request.getAttribute("fromProfilePage") == 1) {
//				return = "redirect:/artikel/toon/{artikelId}";
//			}
		// Return to the productlist page
		return "redirect/artikel/";
	}

	/**
	 * Klant methodes
	 *
	 */
	public String moveToKlantRegister() {

		return "klantRegister";
	}

	public String registerCustomer() {
		klant = klantFacade.create(klant);
		naarSessieVariabele("klant", klant);
		return "klantProfile";
	}
//
	public String gotoCustomerProfile() {
		klant = (Klant) findBean("klant");
		klant.setCanEdit(false);

		return "klantProfile";
	}
//
//	public String gotoCustomerList() {
//		klantBewerkingen.showProfile(request, response);
//		return "/klant/klantenLijst";
//	}
//
//	public String gotoToggleCustomerStatus() {
//		klantBewerkingen.tumbleStatusClient(request, response);
//		return "/klant/klantenLijst";
//	}
//
	public String editUser() {
		klant = (Klant)findBean("klant");
		
		if(!klant.getTempPassword().isEmpty())
			klant.setPassword(klant.getTempPassword());
		
		klantFacade.edit(klant);
		return "/klant/klantModify";
	}
//

	//Haal een sessie-object op
	public static Object findBean(String key) {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		return sessionMap.get(key);
	}
	
	//Schrijf een object naar de sessie toe
	public static void naarSessieVariabele(String key, Object value){
		//Set klant als Sessie variabele
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		sessionMap.put(key, value);
	}
}
