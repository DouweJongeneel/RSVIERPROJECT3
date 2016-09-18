package com.adm.web.helpers;

import com.adm.session.KlantFacade;
import com.adm.entities.Adres;
import com.adm.entities.Adrestype;
//import com.adm.entities.Bestelartikel;
import com.adm.entities.Klant;
import com.adm.entities.Klantadresadrestype;
import com.sun.org.apache.xml.internal.security.utils.Base64;
//import org.apache.commons.io.FilenameUtils;

import javax.validation.Valid;
import java.io.File;
import java.nio.file.Files;
import java.util.*;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Milan_Verheij on 15-08-16.
 *
 * Client controller
 *
 */
//TODO ClientController -> Exception handling in general
public class KlantBewerkingen {

	@EJB
	private KlantFacade klantDAO;

	private static String pictureFolder = "C:/harrie/uploads/data/profilePictures/"; // Windows
//    private String pictureFolder = "/tmp/harrie/uploads/data/profilePictures/"; // Unix-Based

	/**
	 * CLIENT REGISTER METHOD (GET) *
	 */

	/**
	 * CLIENT REGISTER METHOD (POST) *
	 */
//    @RequestMapping(value = "/register", method = POST)
	public Klant processRegistration(
			HttpServletRequest request, HttpServletResponse response){

		HttpSession session = request.getSession();
		
				
		// Save client to repository
		Klant nieuweKlant = new Klant(
				(String)session.getAttribute("voornaam"),
				(String)session.getAttribute("tussenvoegsel"),
				(String)session.getAttribute("achternaam"),
				(String)session.getAttribute("email"),
				(String)session.getAttribute("password")
		);
		return klantDAO.create(nieuweKlant);
}

	/**
	 * CLIENT PROFILE PAGE *
	 */
//    @RequestMapping(value = "/profile", method = GET)
	public void showProfile(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		Klant klant = (Klant) session.getAttribute("klant");
		// Find client and set the profilePicture TODO: Prob wat dubbel her en der, streamlinen
		klant = klantDAO.find(klant.getId());
//        klant.setClientProfilePicture(getProfilePicture(klant.getId())); //TODO Foto upload fixen
		session.setAttribute("klant", klant);

		// Initialize address data (lazy loading)
//        Hibernate.initialize(klant.getAdresGegevens()); TODO: initialize onnodig? Anders JEE compatible versie zoeken
		// Fix language settings for Adress Type
		Iterator<Klantadresadrestype> adresIterator = klant.getKlantadresadrestypeCollection().iterator();

		Klantadresadrestype klantadresadrestype = null;
		while (adresIterator.hasNext()) {
			klantadresadrestype = adresIterator.next();

			Adres adres = klantadresadrestype.getAdres();
			Adrestype adresType = adres.getTypeId();
			adres.setAdresTypeString();
		}

		// Add Attributes in map
		session.setAttribute("adresMap", klant.getKlantadresadrestypeCollection());
		session.setAttribute("klant", klant);

//        return "klant/klantProfile";
	}

	/**
	 * CLIENT LIST METHOD *
	 */
//    @RequestMapping(value = "/klanten", method = GET)
//    @Secured({"ROLE_ADMIN"})
	public void showClients(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		List<Klant> klantenLijst = klantDAO.findAll();

		ArrayList<String> plaatjesList = new ArrayList<String>();

		// Laad de profielpictures in
		for (int i = 0; i < klantenLijst.size(); i++) {
			byte[] array = Files.readAllBytes(new File(pictureFolder
					+ klantenLijst.get(i).getId() + ".jpg").toPath());

			plaatjesList.add((i), Base64.encode(array));
		}

		session.setAttribute("plaatjesList", plaatjesList);
		session.setAttribute("klantenList", klantenLijst);
//        return "klant/klantenLijst";
	}

	/**
	 * TUMBLE CLIENT STATUS METHOD *
	 */
//    @RequestMapping(value = "/tumble/{id}", method = GET)
//    @Secured({"ROLE_ADMIN"})
	public void tumbleStatusClient(HttpServletRequest request, HttpServletResponse response
	) throws Exception {

		HttpSession session = request.getSession();

		Klant klant = (Klant) session.getAttribute("tumbledKlant");

		// Find the persisting client
		klant = klantDAO.find(klant.getId());

		// Tumble status with respect to the current status
		if (klant.getKlantActief().charAt(0) == '0') {
			klant.setKlantActief("1");
		} else {
			klant.setKlantActief("0");
		}

		// Fill in the date modified
		klant.setDatumGewijzigd(new Date().toString());

		// Persist the new clientinfo
		klantDAO.edit(klant);

//        // If the direct is from the profile page, redirect to the profilepage instead of the client list. //TODO fix redirect
//        if (fromProfilePage == 1) {
//            klant.setClientProfilePicture(getProfilePicture(klant.getId()));
//            model.addAttribute(klant);
//            return showProfile(model, klant);
//        }
//
//        // Return to the client list
//        return showClients(model);
	}

	/**
	 * SELECT CLIENT METHOD *
	 */
//    @RequestMapping(value = "/select/{id}", method = GET)
//    @Secured({"ROLE_ADMIN"})
	public void selectClient(HttpServletRequest request) throws Exception {

		long id = Long.parseLong(request.getQueryString());
		HttpSession session = request.getSession();
		// Find the persisting client
		Klant klant = klantDAO.find(id);

		// Read in an the correct profile picture and encode in Base64 and add to client
//        klant.setClientProfilePicture(getProfilePicture(klant.getId())); //TODO plaatjes
		// Add the client and picture attributes to the model
		session.setAttribute("klant", klant);

		// Show the profile
//        return showProfile(model, klant);
	}

	/**
	 * MODIFY CLIENT (GET) METHOD *
	 */
//    @RequestMapping(value = "/modify/{id}", method = GET)
	public void modifyClient(HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();
		long id = Long.parseLong(request.getQueryString());

		//TODO
		
		
		Klant klant = klantDAO.find(id);

		session.setAttribute("klant", klant);

	}

	/**
	 * MODIFY CLIENT (POST) METHOD *
	 */

	/**
	 * METHOD FOR GETTING PROFILE PICTURE FROM SERVER *
	 */
	private static String getProfilePicture(long id) throws Exception {
		byte[] profilePictureArray = Files.readAllBytes(new File(pictureFolder
				+ id + ".jpg").toPath());

		String profileString = Base64.encode(profilePictureArray);

		return profileString;
	}

	/* TODO plaatjes
    /** METHOD FOR SAVING PROFILE PICTURE TO SERVER 
    private static void saveProfilePicture(long id, MultipartFile profilePicture) throws Exception {
        // Save profilePicture to a file
        String profielFotoNaam = profilePicture.getOriginalFilename();
        profilePicture.transferTo(new File("/data/profilePictures/"
                + id + "." + (FilenameUtils.getExtension(profielFotoNaam))));
    }
	 */
}
