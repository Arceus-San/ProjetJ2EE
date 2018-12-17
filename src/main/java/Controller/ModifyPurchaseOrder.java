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
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
 * @author benjamin
 */
@WebServlet(name = "ModifyPurchaseOrder", urlPatterns = {"/ModifyPurchaseOrder"})
public class ModifyPurchaseOrder extends HttpServlet {

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
            throws ServletException, IOException {
        
        DAO dao = (DAO)getServletContext().getAttribute("dao");
        String message;
        
        try{
            //int id,int qt, float shippingcost, String sales,String shippingdate,String transporteur
            //id_commande, quantity, shipping_cost, sales_date, shipping_date, freight_company
            int id_commande = Integer.parseInt(request.getParameter("id_commande"));
            int product_code = Integer.parseInt(request.getParameter("product_id"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            float shipping_cost = Float.parseFloat(request.getParameter("shipping_cost"));
            String sales_date = request.getParameter("sales_date");
            String shipping_date = request.getParameter("shipping_date");
            String freight_company = request.getParameter("freight_company");
            
            Calendar c = Calendar.getInstance();

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date today = new Date();
            Date shipping_date_obj = dateFormat.parse(shipping_date);
            
            Product boughtProduct = (Product) dao.productsInfos().get(product_code);
            PurchaseOrder oldCode = dao.PurchaseOrdersInfos().get(id_commande);
            
            int newDelta = quantity-oldCode.getQuantity();
            
            if(quantity<0){
                message = "Non, on ne peut pas acheter un nombre négatif de produits. Désolé.";
            }else if(today.after(shipping_date_obj)){
                message = "Il est trop tard pour ça. La commande a déjà été expédiée";
            }else if(quantity>boughtProduct.getQuantity()){
                message = "Il n'y a que "+boughtProduct.getQuantity()+" produits encore disponibles."
                           +"<br>"+"Vous ne pouvez donc pas en acheter "+quantity;
            }else{
                c.setTime(today);
                c.add(Calendar.DATE, 5);
                shipping_date_obj = c.getTime();
                
                System.out.println(newDelta);
                dao.modifQuantite(product_code, newDelta);
                dao.modifPurchaseOrder(id_commande, quantity, shipping_cost, dateFormat.format(today), dateFormat.format(shipping_date_obj), freight_company);
                
                message = "La quantité et la nouvelle date de livraison ont bien été mis à jour";
            }
        }catch(NumberFormatException e){
            message = "Veuillez rentrer un nombre valide. S'il vous plait.";
        } catch (DAOException | ParseException ex) {
            message = "Une erreur est survenue lors de la modification";
        }
        
        /*System.out.println(id_commande);
        System.out.println(quantity);
        System.out.println(shipping_cost);
        System.out.println(sales_date);
        System.out.println(shipping_date);
        System.out.println(freight_company);*/
        
        Properties resultat = new Properties();
        resultat.put("message", message);
        
        try (PrintWriter out = response.getWriter()) {
            response.setContentType("application/json;charset=UTF-8");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
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
