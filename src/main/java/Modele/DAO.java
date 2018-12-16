package Modele;


import java.sql.Connection;
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
        
        
        
        public HashMap<Integer,String> idEtMailClients() throws DAOException{
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
        public HashMap totalForProductCode(String date1, String date2) throws DAOException{
            String sql ="SELECT PRODUCT_CODE, SUM(SHIPPING_COST+PURCHASE_COST * QUANTITY) AS TOTAL FROM CUSTOMER INNER JOIN PURCHASE_ORDER USING(CUSTOMER_ID) INNER JOIN PRODUCT ON PRODUCT.PRODUCT_ID=PURCHASE_ORDER.PRODUCT_ID AND (PURCHASE_ORDER.SHIPPING_DATE BETWEEN ? AND ?) GROUP BY PRODUCT_CODE";
            HashMap<String,Float> prodcode = new HashMap<>();
            
             try (Connection connection = myDataSource.getConnection();
		PreparedStatement stmt = connection.prepareStatement(sql)) 
             {
                stmt.setString(1, date1);
                stmt.setString(2, date2);
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
        public HashMap totalForState(String date1, String date2) throws DAOException{
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
        public HashMap totalForCustomer(String date1, String date2) throws DAOException{
            String sql="SELECT NAME, SUM(SHIPPING_COST+PURCHASE_COST * QUANTITY) AS TOTAL FROM CUSTOMER INNER JOIN PURCHASE_ORDER USING (CUSTOMER_ID) INNER JOIN PRODUCT ON PRODUCT.PRODUCT_ID=PURCHASE_ORDER.PRODUCT_ID AND (PURCHASE_ORDER.SALES_DATE BETWEEN ? AND ?) GROUP BY NAME";
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
        public HashMap productsInfos() throws DAOException{
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
        public HashMap<Integer,CustomerEntity> CustomersInfos() throws DAOException{
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
        
        /*
        Modifie le produit en focntion de son id et des valeurs renseignées, seules les clés primaires et étrangères ne sont pas (et ne peuvent pas) être modifiées
        */
        public int modifCustomer(int id, String name, String addressline1, String addressline2, String city, String state, String phone, String fax, String email,int credit_limit) throws DAOException{
            String sql ="UPDATE CUSTOMER SET NAME=?, ADDRESSLINE1=?, ADDRESSLINE2=?, CITY=?, STATE=?, PHONE=?, FAX=?, EMAIL=?, CREDIT_LIMIT=? WHERE CUSTOMER_ID=?";
            int maj=0;
            
            try (Connection connection = myDataSource.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql)) {

                        stmt.setString(1, name);
                        stmt.setString(2, addressline1);
                        stmt.setString(3, addressline2);
                        stmt.setString(4, city);
                        stmt.setString(5, state);
                        stmt.setString(6,phone);
                        stmt.setString(7,fax);
                        stmt.setString(8,email);
                        stmt.setInt(9,credit_limit);
                        stmt.setInt(10,id);
                        maj = stmt.executeUpdate();
                        return maj;
			
		}  catch (SQLException ex) {
			Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
			throw new DAOException(ex.getMessage());
		}
            
        }
        
        /*
        Renvoie une HashMap des commandes (sous forme de List<Integer> contenant le numéro des commandes) passées par les clients (représentés par leurs id)
        */
        public HashMap<Integer,List<Integer>> listePurchase() throws DAOException{
            String sql ="SELECT ORDER_NUM,CUSTOMER_ID FROM PURCHASE_ORDER";
            HashMap<Integer,List<Integer>> commandes = new HashMap<>();
            
             try (Connection connection = myDataSource.getConnection();
		PreparedStatement stmt = connection.prepareStatement(sql)) {
		try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) { 
			int id =rs.getInt("CUSTOMER_ID");
                        int commande = rs.getInt("ORDER_NUM");
                        if(commandes.keySet().contains(id)){
                            List<Integer> l = commandes.get(id);
                            l.add(commande);
                            commandes.put(id,l);
                        }else{
                            List<Integer> l = new ArrayList<>();
                            l.add(commande);
                            commandes.put(id,l);
                        }
                    }
		}
                return commandes;
            }
            
            catch (SQLException ex) {
		Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
		throw new DAOException(ex.getMessage());
            }    
        }
    
        /*
        Récupère toutes les infos des bons de commande dans la table PURCHASE_ORDER au moment de la requete
        Elle met dans une HashMap en clé l'order_num correspondant un PurchaseOrder qui contient toutes ses informations (quantity,shipping_cost,ect...)
        */
        public HashMap<Integer,PurchaseOrder> PurchaseOrdersInfos() throws DAOException{
            String sql ="SELECT * FROM PURCHASE_ORDER";
            HashMap<Integer,PurchaseOrder> purord = new HashMap<>();
            
             try (Connection connection = myDataSource.getConnection();
		PreparedStatement stmt = connection.prepareStatement(sql)){
		try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
			int id = rs.getInt("ORDER_NUM");
                        int customid = rs.getInt("CUSTOMER_ID");
                        int prodid = rs.getInt("PRODUCT_ID");
                        int qt = rs.getInt("QUANTITY");
                        float shippingcost = rs.getFloat("SHIPPING_COST");
                        String sales = rs.getString("SALES_DATE");
                        String shippingdate = rs.getString("SHIPPING_DATE");
                        String transporteur = rs.getString("FREIGHT_COMPANY");

                        PurchaseOrder p= new PurchaseOrder(id,customid,prodid,qt,shippingcost,sales,shippingdate,transporteur);
                        purord.put(id, p);
                    }
		}
                return purord;
            }
            
            catch (SQLException ex) {
		Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
		throw new DAOException(ex.getMessage());
            }
        }
        
    public int addPurchaseOrder(int id, int customid, int prodid, int qt, float shippingcost, String sales,String shippingdate,String transporteur) throws DAOException{
            String sql ="INSERT INTO PURCHASE_ORDER VALUES (?,?,?,?,?,?,?,?)";
            int maj=0;
            
            try (Connection connection = myDataSource.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql)) {

                        
			stmt.setInt(1,id);
                        stmt.setInt(2, customid);
                        stmt.setInt(3, prodid);
                        stmt.setInt(4, qt);
                        stmt.setFloat(5, shippingcost);
                        stmt.setString(6, sales);
                        stmt.setString(7, shippingdate);
                        stmt.setString(8, transporteur);
                        maj = stmt.executeUpdate();
                        return maj;
			
		}  catch (SQLException ex) {
			Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
			throw new DAOException(ex.getMessage());
		}
            
        }
    
    public int supprPurchaseOrder(int ordernum) throws DAOException{
            String sql ="DELETE FROM PURCHASE_ORDER WHERE ORDER_NUM=?";
            int maj=0;
            
            try (Connection connection = myDataSource.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setInt(1, ordernum);

			maj = stmt.executeUpdate();
                        return maj;
			}
			
		catch (SQLException ex) {
			Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
			throw new DAOException(ex.getMessage());
		}
            
        }
    
    
    /*
    Modifie la commande en focntion de son numéro et des valeurs renseignées, seules les clés primaires et étrangères ne sont pas (et ne peuvent pas) être modifiées
    */
    public int modifPurchaseOrder(int id,int qt, float shippingcost, String sales,String shippingdate,String transporteur) throws DAOException{
        String sql ="UPDATE PURCHASE_ORDER SET QUANTITY=?, SHIPPING_COST=?, SALES_DATE=?, SHIPPING_DATE=?, FREIGHT_COMPANY=? WHERE ORDER_NUM=?";
        int maj=0;

        try (Connection connection = myDataSource.getConnection();
                    PreparedStatement stmt = connection.prepareStatement(sql)) {

                    stmt.setInt(1, qt);
                    stmt.setFloat(2, shippingcost);
                    stmt.setString(3, sales);
                    stmt.setString(4, shippingdate);
                    stmt.setString(5, transporteur);
                    stmt.setInt(6,id);
                    maj = stmt.executeUpdate();
                    return maj;

            }  catch (SQLException ex) {
                    Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
                    throw new DAOException(ex.getMessage());
            }

    }
    
    /*
    Modifie la commande en focntion de son numéro et des valeurs renseignées, seules les clés primaires et étrangères ne sont pas (et ne peuvent pas) être modifiées
    */
    public int modifQuantite(int id,int qt_commande) throws DAOException{
        String sql ="UPDATE PRODUCT SET QUANTITY_ON_HAND=? WHERE PRODUCT_ID=?";
        String sql2="SELECT QUANTITY_ON_HAND FROM PRODUCT WHERE PRODUCT_ID=?";
        String sql3="UPDATE PRODUCT SET AVAILABLE=? WHERE PRODUCT_ID=?";
        int maj=-1;
        int newvalue=-1;
        int f=-1;
        
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt= connection.prepareStatement(sql);
                PreparedStatement stmt2= connection.prepareStatement(sql2);
                PreparedStatement stmt3= connection.prepareStatement(sql3)) {
                    
                    stmt2.setInt(1,id);
                    try (ResultSet rs = stmt2.executeQuery()) {
                    while (rs.next()) { 
			int qt =rs.getInt("QUANTITY_ON_HAND");
                        maj=qt;
                    }
                    
                    newvalue=maj-qt_commande;
                    stmt.setInt(1,newvalue);
                    stmt.setInt(2,id);
                    f+= stmt.executeUpdate();
                    if(newvalue==0){
                        stmt3.setBoolean(1, false);
                        stmt3.setInt(2,id);
                        f+= stmt3.executeUpdate();
                    }
		}
                return f;

            }  catch (SQLException ex) {
                    Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
                    throw new DAOException(ex.getMessage());
            }

    }
    
    /*
    Modifie la commande en focntion de son numéro et des valeurs renseignées, seules les clés primaires et étrangères ne sont pas (et ne peuvent pas) être modifiées
    */
    public int modifQuantiteSupprCommande(int id,int qt_commande) throws DAOException{
        String sql ="UPDATE PRODUCT SET QUANTITY_ON_HAND=? WHERE PRODUCT_ID=?";
        String sql2="SELECT QUANTITY_ON_HAND FROM PRODUCT WHERE PRODUCT_ID=?";
        String sql3="UPDATE PRODUCT SET AVAILABLE=? WHERE PRODUCT_ID=?";
        int maj=-1;
        int newvalue=-1;
        int f=-1;
        
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt= connection.prepareStatement(sql);
                PreparedStatement stmt2= connection.prepareStatement(sql2);
                PreparedStatement stmt3= connection.prepareStatement(sql3)) {
                    
                    stmt2.setInt(1,id);
                    try (ResultSet rs = stmt2.executeQuery()) {
                    while (rs.next()) { 
			int qt =rs.getInt("QUANTITY_ON_HAND");
                        maj=qt;
                    }
                    
                    newvalue=maj+qt_commande;
                    stmt.setInt(1,newvalue);
                    stmt.setInt(2,id);
                    f+= stmt.executeUpdate();
                    if(newvalue>0){
                        stmt3.setBoolean(1, true);
                        stmt3.setInt(2,id);
                        f+= stmt3.executeUpdate();
                    }
		}
                return f;

            }  catch (SQLException ex) {
                    Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
                    throw new DAOException(ex.getMessage());
            }

    }
        
        
}
