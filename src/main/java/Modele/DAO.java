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
        
        
        
        public List<CustomerEntity> listeClients() throws DAOException{
            String sql ="SELECT CUSTOMER_ID,EMAIL FROM CUSTOMER";
            List<CustomerEntity> clients = new ArrayList<>();
            
             try (Connection connection = myDataSource.getConnection();
		PreparedStatement stmt = connection.prepareStatement(sql)) {
		try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) { 
			String id =rs.getString("CUSTOMER_ID");
                        String mel = rs.getString("EMAIL");
                        int code  = Integer.parseInt(id);				
			clients.add(new CustomerEntity(mel,code));
                    }
		}
                return clients;
            }
            
            catch (SQLException ex) {
		Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
		throw new DAOException(ex.getMessage());
            }    
        }
        
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
                
                
        public void supprCode(String code) throws DAOException{
            String sql ="DELETE FROM DISCOUNT_CODE WHERE DISCOUNT_CODE=?";
            
            try (Connection connection = myDataSource.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setString(1, code);

			stmt.executeUpdate();
			}
			
		catch (SQLException ex) {
			Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
			throw new DAOException(ex.getMessage());
		}
            
        }
        
        public void addCode(String code, float val) throws DAOException{
            String sql ="iNSERT INTO DISCOUNT_CODE (DISCOUNT_CODE,RATE) VALUES (?,?)";
            
            try (Connection connection = myDataSource.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setString(1, code);
                        stmt.setFloat(2, val);

			stmt.executeUpdate();
			
		}  catch (SQLException ex) {
			Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
			throw new DAOException(ex.getMessage());
		}
            
        }
    
}
