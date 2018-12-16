package Controller;

import Modele.DAO;
import Modele.DAOException;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author BenjiX34
 */
@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {
    private final String adminLogin = "admin";
    private final String adminPass = "admin";
    private DAO dao;
    
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
        
        String action = request.getParameter("action");
        if(action == null){
            request.getRequestDispatcher("Accueil.jsp").forward(request, response);
        }else{
            dao = (DAO) getServletContext().getAttribute("dao");
            if(action.toLowerCase().equals("connexion")){
                String login = request.getParameter("login");
                String pass = request.getParameter("motDePasse");

                if(login.equals(this.adminLogin) && pass.equals(this.adminPass)){
                    request.getRequestDispatcher("administrateur.html").forward(request, response);

                }else{
                    try{
                        int clientID = Integer.parseInt(pass);
                        HashMap<Integer,String> allIdsAndMails = dao.idEtMailClients();
                        /*
                        Personne test:
                            email:jumboeagle@example.com
                            pass:1
                        */
                        if(allIdsAndMails.get(clientID).equals(login)){
                            System.out.println("CLIENT ACCESS GRANTED!");
                            request.getSession(true).setAttribute("clientID", clientID);
                            request.getRequestDispatcher("newjsp.jsp").forward(request, response);

                        }else{
                           System.out.println("CLIENT ACCESS DENIED!");
                           request.setAttribute("error", "Identifiant ou code incorrect");
                           request.getRequestDispatcher("Accueil.jsp").forward(request, response);
                        }
                    }catch(DAOException | NumberFormatException | NullPointerException e){
                           request.setAttribute("error", "Identifiant ou code incorrect");
                           request.getRequestDispatcher("Accueil.jsp").forward(request, response); 
                    }
                    
                }
            }
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
