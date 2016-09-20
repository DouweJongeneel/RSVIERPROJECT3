package com.adm.web.controllers;

import com.adm.entities.Klant;
import com.adm.session.KlantFacade;
import com.adm.web.helpers.ArtikelBewerkingen;
import java.io.Serializable;
import javax.annotation.PostConstruct;

import javax.ejb.EJB;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "servletController", eager = true)
@RequestScoped
public class ServletController implements Serializable {

	@EJB
	ArtikelBewerkingen artikelBewerkingen;

	@EJB
	KlantFacade klantFacade;

	public String gotoHome() {
		return "/home";
	}

	
	@PostConstruct 
	public void init(){
		if(SessionController.findBean("klant") == null)
			SessionController.naarSessieVariabele("klant", new Klant());
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
		Klant klant = klantFacade.create((Klant)SessionController.findBean("klant"));
		SessionController.naarSessieVariabele("klant", klant);
		
		klant.setCanEdit(false);
		
		return "klantProfile";
	}
//
	public String gotoCustomerProfile() {
		Klant klant = (Klant) SessionController.findBean("klant");
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
		Klant klant = (Klant)SessionController.findBean("klant");
		
		if(!klant.getTempPassword().equals("********"))
			klant.setPassword(klant.getTempPassword());
		
		klant.setCanEdit(false);
		klantFacade.edit(klant);
		return "klantProfile";
	}


}
