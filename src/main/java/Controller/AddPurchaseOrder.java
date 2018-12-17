/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Modele.DAO;
import Modele.DAOException;
import Modele.DataSourceFactory;
import Modele.Product;
import Modele.PurchaseOrder;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
@WebServlet(name = "AddPurchaseOrder_InJSON", urlPatterns = {"/AddPurchaseOrder"})
public class AddPurchaseOrder extends HttpServlet {

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
                
	int product_ID = Integer.parseInt(request.getParameter("code2"));
        int customerID = (int)request.getSession(true).getAttribute("clientID");
        String quantity = request.getParameter("Quantite");
        String companie = request.getParameter("Companie");
        
        HashMap<Integer, PurchaseOrder> allCommandes = dao.PurchaseOrdersInfos();
               
        String message;
        
        Calendar c = Calendar.getInstance();
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	Date today = new Date();
        Date shippingDate;
        
        c.setTime(today);

        c.add(Calendar.DATE, 5);

        shippingDate = c.getTime();
        
        if(companie.isEmpty() || quantity.isEmpty()){
            message = "Veuillez remplir tous les champs";
        }else if(Integer.parseInt(quantity) < 0){
            message = "Veuillez rentrer une quantité valide";
        }else{
            try {
                dao.modifQuantite(product_ID, Integer.parseInt(quantity));
                dao.addPurchaseOrder(maxID(allCommandes)+1, customerID, product_ID, Integer.parseInt(quantity), Math.round(new Random().nextFloat()*10000f)/100f, dateFormat.format(today), dateFormat.format(shippingDate), companie);
                message = "Votre commande a bien été enregistré";
            } catch (NumberFormatException ex) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                message = ex.getMessage();
            }
        }
	Properties resultat = new Properties();
	resultat.put("message", message);

	try (PrintWriter out = response.getWriter()) {
            response.setContentType("application/json;charset=UTF-8");
            Gson gson = new Gson();
            out.println(gson.toJson(resultat));
	}
        
    }


    private int maxID(HashMap<Integer, PurchaseOrder> allCommandes){
        int max = 0;
        
        for(int id : allCommandes.keySet()){
            if(id > max){
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
            Logger.getLogger(AddPurchaseOrder.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AddPurchaseOrder.class.getName()).log(Level.SEVERE, null, ex);
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

