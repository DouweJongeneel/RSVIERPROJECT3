package com.adm.web.controllers;

import com.adm.web.helpers.ArtikelBewerkingen;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by douwejongeneel on 09/09/16.
 */

@WebServlet(name = "servletController", loadOnStartup = 1, urlPatterns={"/", "/home",
        "/artikel", "/artikel/", "/artikel/registreer", "/artikel/toon/{artikelId}", "/artikel/wijzig/{artikelId}", "/artikel/verwijder/{artikelId}"})
public class ServletController extends HttpServlet {

    @EJB
    ArtikelBewerkingen artikelBewerkingen;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userPath = request.getServletPath();

        // if action is called
        if (userPath.equals("/home")) {

        }

        /**
         *  ARTIKEL METHODES
         */
        else if (userPath.equals("/artikel") || userPath.equals("/artikel/")) {

            artikelBewerkingen.maakArtikelOverzicht(request);

            // stuur de client naar het artikel overzicht
            userPath = "/artikel/artikelOverzicht";

        }

        else if (userPath.equals("/artikel/registreer")) {

            artikelBewerkingen.maakArtikelRegistratieFormulier(request);

            // Stuur de client door naar de artikelRegistratie view
            userPath = "/artikel/artikelRegistratie";
        }
        else if (userPath.equals("/artikel/toon/{artikelId}")) {

            artikelBewerkingen.toonArtikel(request);

            userPath = "/artikel/toonArtikel";
        }
        else if (userPath.equals("/artikel/Wijzig/{artikelId}")) {

            artikelBewerkingen.wijzigArtikel(request);

            userPath = "/artikel/artikelWijziging";
        }
        else if (userPath.equals("/artikel/verwijder/{artikelId}")) {
            artikelBewerkingen.haalArtikelUitVoorraad(request);

            // If the direct is from the product profile page, redirect to profilepage instead of product list.
            if ((Integer)request.getAttribute("fromProfilePage") == 1) {
                userPath = "redirect:/artikel/toon/{artikelId}";
            }

            // Return to the productlist page
            userPath = "redirect:/artikel/";
        }

        // For all unhandled request redirect the user to the home page
        else {
            userPath = "/home";
        }

        // use requestDispatcher to forward request internally and handle possible exceptions
        forwardRequestInternally(request, response, userPath);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userPath = request.getServletPath();

        if (userPath.equals("/artikel/verwerkRegistratie")){ // TODO

            artikelBewerkingen.verwerkArtikelRegistratie(request);

            // Toon de client het gecreeerde artikel
            userPath = "redirect:/artikel/toon/{artikelId}";

        }
        else if(userPath.equals("/artikel/artikelWijziging")) {

            artikelBewerkingen.verwerkArtikelWijziging(request);

            // Toon het resultaat van de wijziging
            userPath = "redirect:/artikel/toon/{artikelId}";

        }

        // use requestDispatcher to forward request internally and handle possible exceptions
        forwardRequestInternally(request, response, userPath);

    }

    /*
    Utility Methods
     */

    public void forwardRequestInternally(HttpServletRequest request, HttpServletResponse response, String userPath) {

        String url = "WEB-INF/views" + userPath + ".xhtml";

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace(); // TODO - eligently handle exceptions
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
