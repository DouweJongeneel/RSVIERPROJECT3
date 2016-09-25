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

		if (System.getProperty("os.name").startsWith("Windows")) {
			SessionController.setBasePath("c:/harrie/");
		} else {
			SessionController.setBasePath("/etc/harrie/");
		}

		if (SessionController.findBean("artikelCategorieLijst") == null) {
			SessionController.naarSessieVariabele("artikelCategorieLijst", artikelFacade.haalCategorieTypesOp());
		}
		if (SessionController.findBean("winkelwagen") == null) {
			SessionController.naarSessieVariabele("winkelwagen", new LinkedHashSet<Bestelartikel>());
		}
		if (SessionController.findBean("klant") == null) {
			SessionController.naarSessieVariabele("klant", new Klant());
		}
	}

	public BootController() {
	}

	@Override
	public String toString() {
		return "";
	}

	public void maakMenu(String sessieVariabele, String menuNaam) {

		model = new DefaultMenuModel();
		DefaultMenuItem home = maakMenuItem("Home", "#{servletController.gotoHome}");

		DefaultMenuItem login = maakMenuItem("Login", "#{sessionController.moveToLogin}", ((Klant) SessionController.findBean("klant")).getEmail() == null);

		DefaultMenuItem logout = maakMenuItem("Logout", "#{sessionController.logout()}", ((Klant) SessionController.findBean("klant")).getEmail() != null);

		DefaultMenuItem register = maakMenuItem("Register", "#{servletController.goToKlantRegister}", ((Klant) SessionController.findBean("klant")).getEmail() == null);
		
		DefaultMenuItem winkelwagen = maakMenuItem("Winkelwagen", "#{bestellingController.gotoWinkelwagen}");

		DefaultMenuItem profile = maakMenuItem("Profiel", "#{servletController.gotoCustomerProfile}", ((Klant) SessionController.findBean("klant")).getEmail() != null);
		
		DefaultSubMenu artikelTypes = new DefaultSubMenu(menuNaam);

		for (String menuItem : (List<String>) SessionController.findBean(sessieVariabele)) {
			DefaultMenuItem item = maakMenuItem(menuItem, "#{articleController.gotoArticleOverview('" + menuItem + "')}");
			artikelTypes.addElement(item);
		}

		DefaultSubMenu admin = new DefaultSubMenu("Admin");
		admin.setRendered(((Klant) SessionController.findBean("klant")).getKlantRol().equals("ROLE_ADMINISTRATOR"));

		DefaultMenuItem nieuwArtikel = maakMenuItem("Nieuw Artikel", "#{articleController.gotoRegisterArticle}");

		DefaultMenuItem userLijst = maakMenuItem("Klantenlijst", "#{servletController.gotoCustomerList}");

		admin.addElement(nieuwArtikel);
		admin.addElement(userLijst);

		model.addElement(home);
		model.addElement(artikelTypes);
		model.addElement(winkelwagen);
		model.addElement(profile);
		model.addElement(admin);
		model.addElement(login);
		model.addElement(logout);
		model.addElement(register);

		SessionController.naarSessieVariabele(menuNaam, model);
	}
	
	private DefaultMenuItem maakMenuItem(String naam, String command){
		DefaultMenuItem menuItem = new DefaultMenuItem(naam);
		menuItem.setCommand(command);
		return menuItem;
	}

		private DefaultMenuItem maakMenuItem(String naam, String command, boolean rendered){
		DefaultMenuItem menuItem = new DefaultMenuItem(naam);
		menuItem.setCommand(command);
		menuItem.setRendered(rendered);
		return menuItem;
	}
	
	public MenuModel getModel() {
		return model;
	}

	public void setModel(MenuModel model) {
		this.model = model;
	}

}
