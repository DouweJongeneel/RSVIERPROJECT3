/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adm.web.controllers;

import com.adm.entities.Bestelartikel;
import com.adm.entities.Klant;
import com.adm.session.ArtikelFacade;
import java.util.LinkedHashSet;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author Albert
 *
 * Preps session variables on start
 */
@ManagedBean(eager = true)
@SessionScoped
public class BootController {

	@EJB
	ArtikelFacade artikelFacade;

	private MenuModel model;

	@PostConstruct
	public void init() {
		if (SessionController.findBean("klant") == null) {
			SessionController.naarSessieVariabele("klant", new Klant());
		}
		if (System.getProperty("os.name").startsWith("Windows")) {
			SessionController.setBasePath("c:/harrie/");
		} else {
			SessionController.setBasePath("/etc/harrie/");
		}

		if (SessionController.findBean("artikelCategorieLijst") == null) {
			SessionController.naarSessieVariabele("artikelCategorieLijst", artikelFacade.haalCategorieTypesOp());
		}
		if(SessionController.findBean("winkelwagen") == null)
			SessionController.naarSessieVariabele("winkelwagen", new LinkedHashSet<Bestelartikel>());
		
	}

	public BootController() {
	}

	@Override
	public String toString() {
		return "";
	}

	public void maakMenu(String sessieVariabele, String menuNaam) {

		model = new DefaultMenuModel();
		DefaultMenuItem home = new DefaultMenuItem("Home");
		home.setCommand("#{servletController.gotoHome}");

		DefaultMenuItem login = new DefaultMenuItem("Login");
		login.setCommand("#{sessionController.moveToLogin}");

		DefaultMenuItem register = new DefaultMenuItem("Register");
		register.setCommand("#{servletController.goToKlantRegister}");

		DefaultMenuItem winkelwagen = new DefaultMenuItem("Winkelwagen");
		winkelwagen.setCommand("#{bestellingController.gotoWinkelwagen}");

		
		DefaultSubMenu submenu = new DefaultSubMenu(menuNaam);
		for (String menuItem : (List<String>) SessionController.findBean(sessieVariabele)) {
			DefaultMenuItem item = new DefaultMenuItem(menuItem);
			item.setCommand("#{articleController.gotoArticleOverview('" + menuItem + "')}");
			submenu.addElement(item);
		}

		model.addElement(home);
		model.addElement(login);
		model.addElement(register);
		model.addElement(submenu);
		model.addElement(winkelwagen);

		SessionController.naarSessieVariabele(menuNaam, model);
	}

	public MenuModel getModel() {
		return model;
	}

	public void setModel(MenuModel model) {
		this.model = model;
	}

}
