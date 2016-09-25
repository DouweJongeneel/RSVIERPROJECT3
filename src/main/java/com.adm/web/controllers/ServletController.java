package com.adm.web.controllers;

import com.adm.entities.Adres;
import com.adm.entities.Klant;
import com.adm.session.KlantFacade;
import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "servletController", eager = true)
@RequestScoped
public class ServletController implements Serializable {

	@EJB
	KlantFacade klantFacade;

	private Klant klant = new Klant();
	private Adres adres = new Adres();

	public String massaKlantWijzigingenOpslaan() {
		List<Klant> list = (List<Klant>) SessionController.findBean("klantenLijst");
		for (Klant klant : list) {
			klantFacade.edit(klant);
		}
		return "/protected/klant/klantenLijst?faces-redirect=true";
	}

	public String gotoHome() {
		return "/pages/home";
	}

	/**
	 * Klant methodes
	 *
	 */
	public String goToKlantRegister() {

		return "/pages/klant/klantRegister";
	}

	public String registerCustomer() {
		klant.setKlantRol(klant.isAdminRechten() ? "ROLE_ADMINISTRATOR" : "ROLE_USER");
		klant = klantFacade.create(klant);
		SessionController.naarSessieVariabele("klant", klant);

		klant.setCanEdit(false);

		return "/pages/klant/klantProfile";
	}

	public String gotoCustomerProfile() {
		return "/pages/klant/klantProfile";
	}

	public String gotoCustomerList() {
		SessionController.naarSessieVariabele("klantenLijst", klantFacade.findAll());
		return "/protected/klant/klantenLijst";
	}

	public String editUser() {
		Klant klant = (Klant) SessionController.findBean("klant");
		System.out.println(klant.getTempPassword());
		if (!klant.getTempPassword().equals("********")) {
			klant.setPassword(klant.getTempPassword());
		}

		klant.setCanEdit(false);
		klantFacade.edit(klant);
		return "/pages/klant/klantProfile?faces-redirect=true";
	}

	public Klant getKlant() {
		return klant;
	}

	public void setKlant(Klant klant) {
		this.klant = klant;
	}

	public Adres getAdres() {
		return adres;
	}

	public void setAdres(Adres adres) {
		this.adres = adres;
	}

}
