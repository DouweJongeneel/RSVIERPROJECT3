package com.adm.web.controllers;

import com.adm.entities.Klant;
import com.adm.session.KlantFacade;
import java.io.Serializable;

import javax.ejb.EJB;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "servletController", eager = true)
@RequestScoped
public class ServletController implements Serializable {

	@EJB
	KlantFacade klantFacade;

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
		Klant klant = klantFacade.create((Klant) SessionController.findBean("klant"));
		SessionController.naarSessieVariabele("klant", klant);

		klant.setCanEdit(false);

		return "/pages/klant/klantProfile";
	}
//

	public String gotoCustomerProfile() {
		Klant klant = (Klant) SessionController.findBean("klant");
		klant.setCanEdit(false);
		return "/pages/klant/klantProfile";
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
		Klant klant = (Klant) SessionController.findBean("klant");

		if (!klant.getTempPassword().equals("********")) {
			klant.setPassword(klant.getTempPassword());
		}

		klant.setCanEdit(false);
		klantFacade.edit(klant);
		return "/pages/klant/klantProfile";
	}
}
