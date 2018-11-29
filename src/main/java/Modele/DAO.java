package Modele;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Arceus-San
 */
public class DAO {
    
    protected final DataSource myDataSource;

	/**
	 *
	 * @param dataSource la source de données à utiliser
	 */
	public DAO(DataSource dataSource) {
		this.myDataSource = dataSource;
	}
        
        
        
        public HashMap<Integer,String> listeClients() throws DAOException{
            String sql ="SELECT CUSTOMER_ID,EMAIL FROM CUSTOMER";
            HashMap<Integer,String> clients = new HashMap<>();
            
             try (Connection connection = myDataSource.getConnection();
		PreparedStatement stmt = connection.prepareStatement(sql)) {
		try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) { 
			int id =rs.getInt("CUSTOMER_ID");
                        String mel = rs.getString("EMAIL");				
			clients.put(id,mel);
                    }
		}
                return clients;
            }
            
            catch (SQLException ex) {
		Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
		throw new DAOException(ex.getMessage());
            }    
        }
        
        /*
        Entrée : 
        Renvoie une HashMap avec comme clé un product_code et comme valeur correspondante le montant total correspondant à cet état
        */
        public HashMap mapProductCode() throws DAOException{
            String sql ="SELECT PRODUCT_CODE, SUM(SHIPPING_COST+PURCHASE_COST * QUANTITY) AS TOTAL FROM CUSTOMER INNER JOIN PURCHASE_ORDER USING(CUSTOMER_ID) INNER JOIN PRODUCT USING (PRODUCT_ID) GROUP BY PRODUCT_CODE";
            HashMap<String,Float> prodcode = new HashMap<>();
            
             try (Connection connection = myDataSource.getConnection();
		PreparedStatement stmt = connection.prepareStatement(sql)) 
             {
		try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) { 
			String id =rs.getString("PRODUCT_CODE");
                        float mel = rs.getFloat("TOTAL");
                        prodcode.put(id, mel);
                    }
		}
                return prodcode;
            }
            
            catch (SQLException ex) {
		Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
		throw new DAOException(ex.getMessage());
            }    
        }
        
        /*
        Entrée : les dates sur lesquelles on travaille
        Renvoie une HashMap avec comme clé un état et comme valeur correspondante le montant total dépensé correspondant
        */
        public HashMap mapState(String date1, String date2) throws DAOException{
            String sql ="SELECT STATE, SUM(PURCHASE_COST * QUANTITY+SHIPPING_COST) AS TOTAL FROM CUSTOMER INNER JOIN PURCHASE_ORDER USING (CUSTOMER_ID) INNER JOIN PRODUCT ON PRODUCT.PRODUCT_ID=PURCHASE_ORDER.PRODUCT_ID AND (PURCHASE_ORDER.SHIPPING_DATE BETWEEN ? AND ?) GROUP BY STATE";
            HashMap<String,Float> state = new HashMap<>();
            
             try (Connection connection = myDataSource.getConnection();
		PreparedStatement stmt = connection.prepareStatement(sql)){
                stmt.setString(1, date1);
                stmt.setString(2, date2);
		try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) { 
			String id =rs.getString("STATE");
                        float mel = rs.getFloat("TOTAL");
                        state.put(id, mel);
                    }
		}
                return state;
            }
            
            catch (SQLException ex) {
		Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
		throw new DAOException(ex.getMessage());
            }    
        }
        
        /*
        Entrée : les dates sur lesquelles on travaille
        Renvoie une HashMap avec comme clé un nom de client et comme valeur correspondante le montant total qu'il a dépensé
        */
        public HashMap mapCustomer(String date1, String date2) throws DAOException{
            String sql="SELECT NAME, SUM(SHIPPING_COST+PURCHASE_COST * QUANTITY) AS TOTAL FROM CUSTOMER INNER JOIN PURCHASE_ORDER USING (CUSTOMER_ID) INNER JOIN PRODUCT ON PRODUCT.PRODUCT_ID=PURCHASE_ORDER.PRODUCT_ID AND (PURCHASE_ORDER.SHIPPING_DATE BETWEEN ? AND ?) GROUP BY NAME";
            HashMap<String,Float> client = new HashMap<>();
            
             try (Connection connection = myDataSource.getConnection();
		PreparedStatement stmt = connection.prepareStatement(sql)){
                stmt.setString(1, date1);
                stmt.setString(2, date2);
		try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) { 
			String id =rs.getString("NAME");
                        float mel = rs.getFloat("TOTAL");
                        client.put(id, mel);
                    }
		}
                return client;
            }
            
            catch (SQLException ex) {
		Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
		throw new DAOException(ex.getMessage());
            }    
        }
                
                
        public int supprProduct(String prodid) throws DAOException{
            String sql ="DELETE FROM PRODUCT WHERE PRODUCT_ID=?";
            int maj=0;
            
            try (Connection connection = myDataSource.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setString(1, prodid);

			maj = stmt.executeUpdate();
                        return maj;
			}
			
		catch (SQLException ex) {
			Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
			throw new DAOException(ex.getMessage());
		}
            
        }
        
        public int addProduct(int prodid, int manid, String prodcode, float purchasecost, int qtdispo, float markup, String dispo,String desc) throws DAOException{
            String sql ="INSERT INTO PRODUCT VALUES (?,?,?,?,?,?,?,?)";
            int maj=0;
            
            try (Connection connection = myDataSource.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql)) {

                        
			stmt.setInt(1,prodid);
                        stmt.setInt(2, manid);
                        stmt.setString(3, prodcode);
                        stmt.setFloat(4, purchasecost);
                        stmt.setInt(5, qtdispo);
                        stmt.setFloat(6, markup);
                        stmt.setString(7, dispo);
                        stmt.setString(8, desc);
                        maj = stmt.executeUpdate();
                        return maj;
			
		}  catch (SQLException ex) {
			Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
			throw new DAOException(ex.getMessage());
		}
            
        }
        
        /*
        Modifie le produit en focntion de son id et des valeurs renseignées, seules les clés primaires et étrangères ne sont pas (et ne peuvent pas) être modifiées
        */
        public int modifProduct(int prodid,float purchasecost, int qtdispo, float markup, String dispo,String desc) throws DAOException{
            String sql ="UPDATE PRODUCT SET PURCHASE_COST=?, QUANTITY_ON_HAND=?, MARKUP=?, AVAILABLE=?, DESCRIPTION=? WHERE PRODUCT_ID=?";
            int maj=0;
            
            try (Connection connection = myDataSource.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql)) {

                        stmt.setFloat(1, purchasecost);
                        stmt.setInt(2, qtdispo);
                        stmt.setFloat(3, markup);
                        stmt.setString(4, dispo);
                        stmt.setString(5, desc);
                        stmt.setInt(6,prodid);
                        maj = stmt.executeUpdate();
                        return maj;
			
		}  catch (SQLException ex) {
			Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
			throw new DAOException(ex.getMessage());
		}
            
        }
        
        /*public int modifPurchCost(int prodid, float purchasecost) throws DAOException{
            String sql ="UPDATE PRODUCT SET PURCHASE_COST=? WHERE PRODUCT_ID=?";
            int maj=0;
            
            try (Connection connection = myDataSource.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql)) {

                        stmt.setFloat(1,purchasecost);
                        stmt.setInt(2,prodid);
                        maj = stmt.executeUpdate();
                        return maj;
			
		}  catch (SQLException ex) {
			Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
			throw new DAOException(ex.getMessage());
		}
            
        }
        
        public int modifMarkup(int prodid, float markup) throws DAOException{
            String sql ="UPDATE PRODUCT SET MARKUP=? WHERE PRODUCT_ID=?";
            int maj=0;
            
            try (Connection connection = myDataSource.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql)) {

                        stmt.setFloat(1,markup);
                        stmt.setInt(2,prodid);
                        maj = stmt.executeUpdate();
                        return maj;
			
		}  catch (SQLException ex) {
			Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
			throw new DAOException(ex.getMessage());
		}
            
        }*/
        
        /*
        Récupère toutes les infos des produits dans la table PRODUCT au moment de la requete
        Elle met dans une HashMap en clé l'id du produit et en valeur correspondante un Product qui contient toutes ses informations (product_code,cost,description,ect...)
        */
        public HashMap mapProductInfo() throws DAOException{
            String sql="SELECT * FROM PRODUCT";
            HashMap<Integer,Product> produits = new HashMap<>();
            
             try (Connection connection = myDataSource.getConnection();
		PreparedStatement stmt = connection.prepareStatement(sql)){
		try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
			int id =rs.getInt("PRODUCT_ID");
                        int man = rs.getInt("MANUFACTURER_ID");
                        String code = rs.getString("PRODUCT_CODE");
                        float cost = rs.getFloat("PURCHASE_COST");
                        int qt = rs.getInt("QUANTITY_ON_HAND");
                        float markup = rs.getFloat("MARKUP");
                        String dispo = rs.getString("AVAILABLE");
                        String desc = rs.getString("DESCRIPTION");
                        Product p= new Product(id,man,code,cost,qt,markup,dispo,desc);
                        produits.put(id, p);
                    }
		}
                return produits;
            }
            
            catch (SQLException ex) {
		Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
		throw new DAOException(ex.getMessage());
            }    
        }
        
        /*
        Récupère toutes les infos des clients dans la table CUSTOMER au moment de la requete
        Elle met dans une HashMap en clé l'id du client et en valeur correspondante un CustomerEntity qui contient toutes ses informations (name,adressline,credit_limit,ect...)
        */
        public HashMap<Integer,CustomerEntity> mapCustomerInfo() throws DAOException{
            String sql ="SELECT * FROM CUSTOMER";
            HashMap<Integer,CustomerEntity> clients = new HashMap<>();
            
             try (Connection connection = myDataSource.getConnection();
		PreparedStatement stmt = connection.prepareStatement(sql)){
		try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
			int id = rs.getInt("CUSTOMER_ID");
                        String code = rs.getString("DISCOUNT_CODE");
                        int zip = rs.getInt("ZIP");
                        String name = rs.getString("NAME");
                        String adress1 = rs.getString("ADDRESSLINE1");
                        String adress2 = rs.getString("ADDRESSLINE2");
                        String ville = rs.getString("CITY");
                        String etat = rs.getString("STATE");
                        String tel = rs.getString("PHONE");
                        String fax = rs.getString("FAX");
                        String mail = rs.getString("EMAIL");
                        int credit = rs.getInt("CREDIT_LIMIT");
                        CustomerEntity p= new CustomerEntity(id,code,zip,name,adress1,adress2,ville,etat,tel,fax,mail,credit);
                        clients.put(id, p);
                    }
		}
                return clients;
            }
            
            catch (SQLException ex) {
		Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
		throw new DAOException(ex.getMessage());
            }
        }
    
}
