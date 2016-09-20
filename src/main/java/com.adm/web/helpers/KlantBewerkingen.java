package com.adm.web.helpers;

import com.adm.session.KlantFacade;
import com.adm.entities.Klant;

import java.util.*;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


//TODO ClientController -> Exception handling in general

@Stateless
public class KlantBewerkingen {

	@EJB
	private KlantFacade klantDAO;

//    private String pictureFolder = "/tmp/harrie/uploads/data/profilePictures/"; // Unix-Based

//    @RequestMapping(value = "/register", method = POST)
	public Klant processRegistration(HttpServletRequest request){

		Klant klant = (Klant) (request.getSession().getAttribute("klant"));
		return klantDAO.create(klant);
}

	/**
	 * CLIENT PROFILE PAGE *
	 */
//    @RequestMapping(value = "/profile", method = GET)
	public void showProfile(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		Klant klant = (Klant) session.getAttribute("klant");

		klant = klantDAO.find(klant.getId());

		session.setAttribute("klant", klant);

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
	public void showClients(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();

		List<Klant> klantenLijst = klantDAO.findAll();

		//TODO Laad de profielpictures in
		
		session.setAttribute("klantenList", klantenLijst);
//        return "klant/klantenLijst";
	}

	/**
	 * TUMBLE CLIENT STATUS METHOD *
	 */
//    @RequestMapping(value = "/tumble/{id}", method = GET)
//    @Secured({"ROLE_ADMIN"})
	public void tumbleStatusClient(HttpServletRequest request, HttpServletResponse response
	) {

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

		klant.setDatumGewijzigd(new Date().toString());

		klantDAO.edit(klant);

	}

	/**
	 * SELECT CLIENT METHOD *
	 */
//    @Secured({"ROLE_ADMIN"})
	public void selectClient(HttpServletRequest request) {
		long id = Long.parseLong(request.getQueryString());
		HttpSession session = request.getSession();

		Klant klant = klantDAO.find(id);

		session.setAttribute("klant", klant);
	}

	/**
	 * METHOD FOR GETTING PROFILE PICTURE FROM SERVER *
	 */
	private static String getProfilePicture(long id) {
		
		//TODO

		

		return null;
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
