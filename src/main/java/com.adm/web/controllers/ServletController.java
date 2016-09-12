package com.adm.web.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by douwejongeneel on 09/09/16.
 */
@WebServlet(name = "servletController", loadOnStartup = 1, urlPatterns={"/", "/home"})
public class ServletController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userPath = request.getServletPath();

        // if action is called
        if (userPath.equals("/home")) {

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

        if (userPath.equals("TODO")){

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
