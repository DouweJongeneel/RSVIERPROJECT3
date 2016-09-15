package com.adm.web.helpers;

import com.adm.entities.Artikel;
import com.adm.entities.Prijs;
import com.adm.entities.PrijsArtikel;
import com.adm.session.ArtikelFacade;
import com.adm.session.PrijsArtikelFacade;
import com.adm.session.PrijsFacade;
import com.adm.web.forms.ArtikelRegistratieFormulier;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by douwejongeneel on 13/09/16.
 */
public class ArtikelBewerkingen {

    @Inject
    private ArtikelFacade artikelFacade;

    @Inject
    private PrijsFacade prijsFacade;

    @Inject
    private PrijsArtikelFacade prijsArtikelFacade;

    public ArtikelBewerkingen() {
    }

    public ArtikelBewerkingen(ArtikelFacade artikelFacade, PrijsFacade prijsFacade, PrijsArtikelFacade prijsArtikelFacade) {
        this.artikelFacade = artikelFacade;
        this.prijsFacade = prijsFacade;
        this.prijsArtikelFacade = prijsArtikelFacade;
    }

    /**
     *  Onderstaande methodes helpen de servletController bij het verwerken van de artikelGegevens
     */

    // Methode voor servletController --> /artikel en /artikel/
    public void maakArtikelOverzicht(HttpServletRequest request) {

        HttpSession session = request.getSession();

        // verkrijg een lijst met alle artikelen
        List<Artikel> artikelLijst = maakArtikelLijst();

        // stop de lijst met alle artikelen in de request
        session.setAttribute("artikelLijst", artikelLijst);

    }

    // Methode voor servletController --> /artikel/registreer
    public void maakArtikelRegistratieFormulier(HttpServletRequest request) {

        HttpSession session = request.getSession();

        session.setAttribute("artikelRegistratieFormulier", new ArtikelRegistratieFormulier());

    }

    // Methode die het artikelRegistratieFormulier verwerkt
    public String verwerkArtikelRegistratie(HttpServletRequest request) {

        HttpSession session = request.getSession();

        ArtikelRegistratieFormulier artikelRegistratieFormulier =
                (ArtikelRegistratieFormulier) session.getAttribute("artikelRegistratieFormulier");

        // Toon fouten wanneer het artikelRegistratieFormulier verkeerd is ingevuld
//        if (errors.hasErrors()) {
//            return "/artikel/artikelRegistratie";
//        }


        // Haal de artikelgegevens uit het artikelRegistratieFormulier
        Artikel artikel = new Artikel(
                hanteerNaamConventie(artikelRegistratieFormulier.getArtikelNaam()),
                artikelRegistratieFormulier.getArtikelPrijs(),
                artikelRegistratieFormulier.getArtikelLevertijd(),
                artikelRegistratieFormulier.isArtikelOpVoorraad());

        artikelFacade.create(artikel); // TODO - wordt het artikelId opgeslagen? hoe werkt dit met entity beans???
        artikelFacade.flush(); // --> wordt het artikel vanuit de persistence context naar de database gesaved en krijgt het een Id.

        Prijs artikelPrijs = new Prijs(artikelRegistratieFormulier.getArtikelPrijs());
        prijsFacade.create(artikelPrijs);
        prijsFacade.flush(); // flush DB voor id
        PrijsArtikel prijsArtikel = new PrijsArtikel(artikelPrijs.getId(), artikel, artikelPrijs);
//        prijsArtikelFacade.flush();

        // Sla de afbeelding op
        slaAfbeeldingOp(artikel.getArtikelId(), artikelRegistratieFormulier.getArtikelAfbeelding());
        artikel.setArtikelAfbeelding(verkrijgArtikelAfbeeldingString(artikel.getArtikelId()));

        // Save flash attribute
        session.setAttribute("artikel", artikel);
        session.setAttribute("id", artikel.getArtikelId());

    }

    // Methode voor servletController --> /artikel/toon/{artikelId}
    public String toonArtikel(HttpServletRequest request, HttpServletResponse response
            /*@PathVariable Long id, Model model*/) throws Exception {
        // Verkrijg artikelgegevens en prijs
        Artikel artikel = artikelFacade.find((Long)artikelId);
        stopDeActuelePrijsInHetArtikel(artikel);

        // Stop artikel met prijs en plaatje in model
        request.setAttribute("afbeelding", verkrijgArtikelAfbeeldingString(artikelId));
        request.setAttribute("artikel", artikel);

        return "artikel/toonArtikel";
    }

    // Methode voor servletController --> /artikel/wijzig/{artikelId}
    public String wijzigArtikel(HttpServletRequest request, HttpServletResponse response
            /*@PathVariable Long id, Model model */) {

        // Verkrijg het artikel en de actuele prijs
        Artikel teWijzigenArtikel = artikelFacade.find(artikelId);
        stopDeActuelePrijsInHetArtikel(teWijzigenArtikel);

        artikelRegistratieFormulier artikelWijzigingsFormulier = new artikelRegistratieFormulier(
                teWijzigenArtikel.getArtikelNaam(),
                teWijzigenArtikel.getArtikelPrijs(),
                teWijzigenArtikel.getVerwachteLevertijd(),
                teWijzigenArtikel.isInAssortiment());

        request.setAttribute("artikelRegistratieFormulier", artikelWijzigingsFormulier);
        request.setAttribute("artikel", teWijzigenArtikel);
        request.setAttribute("plaatje", verkrijgArtikelAfbeeldingString(artikelId));

        return "artikel/artikelWijziging";
    }

    // Methode die de wijziging van het artikel verwerkt
    public String verwerkArtikelWijziging( HttpServletRequest request, HttpServletResponse response
            /* @PathVariable Long id, Artikel artikel,
                                            @Valid ArtikelRegisterForm artikelForm,
                                            Errors errors, RedirectAttributes model */) throws Exception {

        if (errors.hasErrors()) {
            return "/artikel/artikelWijziging";
        }

        // Vergelijk de prijs voor en na de wijziging en geef aan of de prijs gewijzigd is
        // TODO - impl

        // Zet de gegevens uit het artikel formulier in het artikel
        artikel.setArtikelNaam(artikelWijzigingsFormulier.getArtikelNaam());
        artikel.setArtikelPrijs(artikelWijzigingsFormulier.getArtikelPrijs());
        artikel.setVerwachteLevertijd(artikelWijzigingsFormulier.getArtikelLevertijd());
        artikel.setInAssortiment(artikelWijzigingsFormulier.isArtikelOpVoorraad());

        // Sla het artikel op in de database
        artikel = artikelFacade.edit(artikel);

        // Wanneer de prijs gewijzigd is wordt er een nieuwe prijs opgeslagen in de database
        if (prijsIsGewijzigd) {
            prijsFacade.create(new Prijs(artikelForm.getArtikelPrijs(), artikel));
        }

        // Sla de afbeelding op wanneer deze gewijzigd is
        if (!artikelForm.getArtikelAfbeelding().getOriginalFilename().isEmpty()) {
            slaAfbeeldingOp(artikel.getArtikelId(), artikelWijzigingsFormulier.getArtikelAfbeelding());
        }

       request.setAttribute("artikel", artikel);

        return "redirect:/artikel/toon/{artikelId}"; // TODO - ?
    }

    // Methode voor servletController --> /artikel/verwijder/{artikelId}
    public String verwerkArtikelIsUitVoorraad(HttpServletRequest request, HttpServletResponse response
            /*@PathVariable Long id, RedirectAttributes model,
                                           @RequestParam(value="fromProfile", defaultValue="0") int fromProfilePage*/){
        Artikel artikel = artikelFacade.find(artikelId);

        if (artikel.getInAssortiment()){
            artikel.setInAssortiment(false);
        }
        else {
            artikel.setInAssortiment(true);
        }

        artikelFacade.edit(artikel);

        request.setAttribute("artikel", artikel);


        // If the direct is from the product profile page, redirect to profilepage instead of product list.
        if (fromProfilePage == 1) {
            return "redirect:/artikel/toon/{artikelId}";
        }

        // Return to the productlist page
        return "redirect:/artikel/";
    }



    /**
     * Ondersteunende methodes
     */

    public List<Artikel> maakArtikelLijst() {
        List<Artikel> artikelList = artikelFacade.findAll();

        // Iterate door artikelList en haal voor elk artikel de actuele prijs en afbeelding op
        for (int i = 0; i < artikelList.size(); i++) {

            Artikel tempArtikel = artikelList.get(i);
            tempArtikel.setArtikelAfbeelding(verkrijgArtikelAfbeeldingString(tempArtikel.getArtikelId()));
            stopDeActuelePrijsInHetArtikel(tempArtikel);

        }
        return artikelList;
    }
    // TODO - opslaan van artikel met facelets
    public void slaAfbeeldingOp(Long id, File afbeelding) {
        try {
            afbeelding.transferTo(new File("/data/productPictures/" +
                    id + ".jpg"));
        }
        catch (IOException ex) {
            // TODO
        }
    }

    @SuppressWarnings("restriction")
    public static String verkrijgArtikelAfbeeldingString(Long id) {
        byte[] array = null;

        try {
            array = Files.readAllBytes(new File("/tmp/harrie/uploads/data/productPictures/"
                    + id + ".jpg").toPath());
        }
        catch (IOException ex) {
            // TODO - IOException handling
        }

        return Base64.encode(array);
    }

    public static void stopDeActuelePrijsInHetArtikel(Artikel artikel) { // TODO - Controleer of de prijs actueel is

        ArrayList<PrijsArtikel> tempPrijsList = (ArrayList<PrijsArtikel>) artikel.getPrijsArtikelCollection();
        artikel.setActuelePrijs(((tempPrijsList.get(tempPrijsList.size()-1)).getPrijs()).getPrijs());
    }

    public static String hanteerNaamConventie(String string) {
        StringBuilder builder = new StringBuilder();
        int indexToUpperCase = 0;

        // Start with a lowercase string
        String s = string.toLowerCase();
        // Split on whitespace
        String[] tokens = s.split("[\\s]");
        // Iterate over each wordt and capitalize the first character
        for(int i = 0; i < tokens.length; i++) {
            if (i > 0) {
                builder.append(' ');
                indexToUpperCase = builder.length();
            }
            builder.append(tokens[i]);
            builder.setCharAt(indexToUpperCase, Character.toUpperCase(tokens[i].charAt(0)));
        }
        return builder.toString();
    }

}
