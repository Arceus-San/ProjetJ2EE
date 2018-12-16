/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Modele.DAO;
import Modele.DAOException;
import Modele.DataSourceFactory;
import Modele.PurchaseOrder;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@WebServlet(name = "DeleteProduit_InJSON", urlPatterns = {"/deletePurchaseOrder"})
public class deletePurchaseOrder extends HttpServlet {

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
            throws ServletException, IOException{
        
        String message;
        Properties resultat = new Properties();
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	Date today = new Date();
        
        try{
            DAO dao = (DAO) getServletContext().getAttribute("dao");
            int ID = Integer.parseInt(request.getParameter("code"));
            PurchaseOrder codeClient = dao.PurchaseOrdersInfos().get(ID);
            if(today.before(dateFormat.parse(codeClient.getShippingDate()))){
                message = "Votre commande a bien été effacée";
                dao.modifQuantiteSupprCommande(codeClient.getProductId(), codeClient.getQuantity());
                dao.supprPurchaseOrder(ID);
            }else{
                message = "Votre commande a déjà été expédiée. Vous ne pouvez donc pas l'effacer.";
            }

        }catch(DAOException | ParseException e){
            message = e.getMessage();
        }

        resultat.put("message", message);
        
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
        processRequest(request, response);
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
        processRequest(request, response);
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
