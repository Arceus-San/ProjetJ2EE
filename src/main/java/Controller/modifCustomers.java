/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Modele.DAO;
import Modele.DAOException;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author guillaume
 */
//@WebServlet(name = "modifCustomers", urlPatterns = {"/modifCustomers"})
public class modifCustomers extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, DAOException {
            DAO dao = (DAO) getServletContext().getAttribute("dao");
            int ID = Integer.parseInt(request.getParameter("ID"));
            String name= request.getParameter("Name");
            String addressline1= request.getParameter("Adress1");
            String addressline2= request.getParameter("Adress2");
            String city= request.getParameter("City");
            String state= request.getParameter("State");
            String phone= request.getParameter("Phone");
            String fax= request.getParameter("Fax");
            String email= request.getParameter("Email");
            int credit = Integer.parseInt(request.getParameter("Credit"));
            
            System.out.print(ID+" ");
            System.out.print(name+" ");
            System.out.print(addressline1+" ");
            System.out.print(addressline2+" ");
            System.out.print(city+" ");
            System.out.print(state+" ");
            System.out.print(phone+" ");
            System.out.print(fax+" ");
            System.out.print(email+" ");
            System.out.print(credit+" ");
            Properties resultat = new Properties();
            
            dao.modifCustomer(ID, name, addressline1, addressline2, city, state, phone, fax, email, credit);
                       
            resultat.put("message", "Vos informations ont bien été mises à jour");
            
            try (PrintWriter out = response.getWriter()) {
                response.setContentType("application/json;charset=UTF-8");
                Gson gson = new Gson();
                out.println(gson.toJson(resultat));
            }
            

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (DAOException ex) {
            Logger.getLogger(modifCustomers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (DAOException ex) {
            Logger.getLogger(modifCustomers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
