/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Modele.DAO;
import Modele.DAOException;
import Modele.Product;
import Modele.PurchaseOrder;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;
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
@WebServlet(name = "AddProduct", urlPatterns = {"/AddProduct"})
public class AddProduct extends HttpServlet {

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
        String message;
        
        try{
            int manuf_ID = Integer.parseInt(request.getParameter("Manuf"));
            float  prix = Float.parseFloat(request.getParameter("Prix"));
            String code = request.getParameter("code");
            int quantity = Integer.parseInt(request.getParameter("Quantite"));
            float balisage = Float.parseFloat(request.getParameter("Balisage"));
            String description = request.getParameter("Description");

            String disponible = quantity > 0 ? "TRUE" : "FALSE";

            if(description.isEmpty()){
                message  = "Veuillez remplir la description de votre produit";
            }else if(prix < 0 || quantity < 0 || balisage < 0){
                message = "On ne peut (évidemment) pas rentrer des nombres négatifs. Veuillez entrer des nombres valides";
            }else{
                HashMap<Integer,Product> allProduits = dao.productsInfos();

                try {
                    dao.addProduct(maxID(allProduits)+1, manuf_ID, code, prix, quantity, balisage, disponible, description);
                    message = "Votre produit a bien été enregistré";
                } catch (DAOException ex) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    message = ex.getMessage();
                }
            }
            
        }catch(NumberFormatException e){
            message = "Veuillez remplir tous les champs avec des valeurs valides";
        }
	Properties resultat = new Properties();
	resultat.put("message", message);

	try (PrintWriter out = response.getWriter()) {
            response.setContentType("application/json;charset=UTF-8");
            Gson gson = new Gson();
            out.println(gson.toJson(resultat));
	}
        
    }
    
    private int maxID(HashMap<Integer, Product> allProduits){
        int max = 0;
        for(int id: allProduits.keySet()){
            if(id>max){
                max = id;
            }
        }
        return max;
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
            Logger.getLogger(AddProduct.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AddProduct.class.getName()).log(Level.SEVERE, null, ex);
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
