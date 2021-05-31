/*
 *  <copyright 
 *  notice="oco-source" 
 *  pids="5655-CE3" 
 *  years="2015,2017" 
 *  crc="" > 
 *  IBM Confidential 
 *   
 *  OCO Source Materials 
 *   
 *  5655-CE3 
 *   
 *  (C) Copyright IBM Corp. 2015, 2017 
 *   
 *  The source code for the program is not published 
 *  or otherwise divested of its trade secrets, 
 *  irrespective of what has been deposited with the 
 *  U.S. Copyright Office. 
 *  </copyright> 
 */
package web;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebFilter(urlPatterns = "/*", asyncSupported = true)
public class MainFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        boolean requireSecure = false;
        boolean requireAuth = true;

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        Principal principal = httpServletRequest.getUserPrincipal();
        String userName = principal.getName();
        System.out.println(userName);
        
        if (requireSecure && !request.isSecure()) {
            servletResponse.setStatus(HttpServletResponse.SC_FOUND);
            return;
        }

        if (requireAuth) {
            // Authenticate the user. If not authenticated a 401 is returned to client (no data).
            if (!((HttpServletRequest) request).authenticate((HttpServletResponse) response)) {
                return;
            }

            // Validate the user's authorization under the zosConnectAccess role.
            request.getServletContext().declareRoles("role1");
            if (!((HttpServletRequest) request).isUserInRole("role1")) {
                servletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "AuthorizationFailed");
                return;
            }
        }

        chain.doFilter(servletRequest, servletResponse);
    }

    /** {@inheritDoc} */
    @Override
    public void init(FilterConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
    }
    
    @Override
    public void destroy() {
    	System.out.println("Main Filter destroied.");
    }

}
