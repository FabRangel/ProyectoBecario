/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author fgmrr
 */
public class CheckSessionCookie extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CheckSessionCookie</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CheckSessionCookie at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         HttpSession session = request.getSession(false); 

        if (session == null || session.getAttribute("curp") == null) {
            response.sendRedirect("becario/inicio");
            return;
        }

        Cookie[] cookies = request.getCookies();
        boolean cookieValid = false;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("curp".equals(cookie.getName())) { 
                    cookieValid = true; 
                    break;
                }
            }
        }

        if (!cookieValid) {
            response.sendRedirect("becario/inicio");
            return;
        }

       
        response.setStatus(HttpServletResponse.SC_OK);
    }

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
