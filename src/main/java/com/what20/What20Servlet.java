/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.what20;

/**
 *
 * @author moscac
 */

import javax.servlet.ServletException;

import com.vaadin.server.VaadinServlet;

@SuppressWarnings("serial")
public class What20Servlet extends VaadinServlet {

    @Override
    protected final void servletInitialized() throws ServletException {
        super.servletInitialized();
        getService().addSessionInitListener(new What20SessionInitListener());
    }
    
}
