/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adm.filters;

import com.adm.entities.Klant;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"*.xhtml"})
public class LoginFilter implements Filter {

	public LoginFilter() {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {

			HttpServletRequest reqt = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			HttpSession ses = reqt.getSession(false);

			String reqURI = reqt.getRequestURI();

			System.out.println(reqURI);
			
			
			if(reqURI.equals("/rsvierproject3/pages/klant/klantProfile.xhtml")){
				chain.doFilter(request, response);
			}else
			
			// Alles pages/ en resources doorlaten, en /rsvierproject/ wegens toegang met welcome page in de web.xml
			if (reqURI.indexOf("pages/") >= 0 || reqURI.equals("/rsvierproject3/") || reqURI.indexOf("javax.faces.resource") >= 0) {
				chain.doFilter(request, response);

				// Als de sessie null is (dus vanuit een nieuwe browser direct naar een protected page, ga naar login
			} else if (reqURI.indexOf("protected/") >= 0 && ses == null) {
				resp.sendRedirect(reqt.getContextPath() + "/pages/klant/login.xhtml");
			//Als de klant attribuut niet null is kijk of de klantrol klopt
			} else if (ses != null) {
				if (getKlantRol(ses)) {
					chain.doFilter(request, response);
				} else {
					resp.sendRedirect(reqt.getContextPath() + "/pages/klant/login.xhtml");
				}
			}else {
					resp.sendRedirect(reqt.getContextPath() + "/pages/home.xhtml");
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean getKlantRol(HttpSession ses) {
		return ((Klant) ses.getAttribute("klant")).getKlantRol().equals("ROLE_ADMINISTRATOR");
	}

	@Override
	public void destroy() {

	}
}
