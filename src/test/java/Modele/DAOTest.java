/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.sql.DataSource;
import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Maxime
 */
public class DAOTest {
    
    private DAO myDAO; // L'objet à tester
    private DataSource myDataSource; // La source de données à utiliser
    private static Connection myConnection ;
    
    @Before
    public void setUp() throws SQLException, IOException, SqlToolError  {
        myDataSource = DataSourceFactory.getDataSource();
        myConnection = myDataSource.getConnection();
	// On crée le schema de la base de test
	executeSQLScript(myConnection, "schema.sql");
	// On y met des données
	executeSQLScript(myConnection, "bigtestdata.sql");
	myDAO = new DAO(myDataSource);
    }
    
    @After
    public void tearDown() throws IOException, SqlToolError, SQLException {
        myConnection.close(); // La base de données de test est détruite ici
        myDAO = null; // Pas vraiment utile
    }

    private void executeSQLScript(Connection connexion, String filename)  throws IOException, SqlToolError, SQLException {
        // On initialise la base avec le contenu d'un fichier de test
        String sqlFilePath = DAOTest.class.getResource(filename).getFile();
        SqlFile sqlFile = new SqlFile(new File(sqlFilePath));

        sqlFile.setConnection(connexion);
        sqlFile.execute();
        sqlFile.closeReader();		
    }

    /**
     * Test of idEtMailClients method, of class DAO.
     * @throws Modele.DAOException
     */
    @Test
    public void testidEtMailClients() throws DAOException {
        HashMap<Integer,String> clients = myDAO.idEtMailClients();
        assertEquals(13, clients.keySet().size());
    }
    
    /**
     * Test of listePurchase method, of class DAO.
     * @throws Modele.DAOException
     */
    @Test
    public void testListePurchase() throws DAOException {
        HashMap<Integer,List<Integer>> commandes = myDAO.listePurchase();
        assertEquals(12, commandes.keySet().size());
    }
    
    /**
     * Test of testtotalForProductCode method, of class DAO.
     * @throws Modele.DAOException
     */
    @Test
    public void testtotalForProductCode() throws DAOException {
        String d1="2011-01-01";
        String d2="2011-12-31";
        HashMap<String,Float> clients = myDAO.totalForProductCode(d1,d2);
        assertEquals(9987.5f,clients.get("BK"),0.0f);
        assertEquals(3065.05f,clients.get("CB"),0.0f);
    }
    
    /**
     * Test of testtotalForProductCodeWhenNoPurchase method, of class DAO.
     * @throws Modele.DAOException
     */
    @Test
    public void testtotalForProductCodeWhenNoPurchase() throws DAOException {
        String d1="2011-01-01";
        String d2="2011-02-01";
        HashMap<String,Float> clients = myDAO.totalForProductCode(d1,d2);
        assertEquals(null,clients.get("NY"));
    }
    
    /**
     * Test of testtotalForStateWhenNoPurchase method, of class DAO.
     * @throws Modele.DAOException
     */
    @Test
    public void testtotalForStateWhenNoPurchase() throws DAOException {
        String d1="2011-01-01";
        String d2="2011-01-01";
        HashMap<String,Float> clients = myDAO.totalForState(d1,d2);
        assertEquals(null,clients.get("NY"));
    }
    
    /**
     * Test of testtotalForState method, of class DAO.
     * @throws Modele.DAOException
     */
    @Test
    public void testtotalForState() throws DAOException {
        String d1="2011-01-01";
        String d2="2011-12-31";
        HashMap<String,Float> clients = myDAO.totalForState(d1,d2);
        assertEquals(135888.8f,clients.get("CA"),0.0f);
        assertEquals(160895.66f,clients.get("FL"),0.0f);
        assertEquals(874.5f,clients.get("GA"),0.0f);
        assertEquals(212334.74f,clients.get("MI"),0.0f);
        assertEquals(117045.99f,clients.get("NY"),0.0f);
    }
    
    /**
     * Test of testtotalForCustomerWhenNoPurchase method, of class DAO.
     * @throws Modele.DAOException
     */
    @Test
    public void testtotalForCustomerWhenNoPurchase() throws DAOException {
        String d1="2011-01-01";
        String d2="2011-01-01";
        HashMap<String,Float> clients = myDAO.totalForCustomer(d1,d2);
        assertEquals(null,clients.get("NY"));
    }
    
    /**
     * Test of testtotalForCustomer method, of class DAO.
     * @throws Modele.DAOException
     */
    @Test
    public void testtotalForCustomer() throws DAOException {
        String d1="2011-01-01";
        String d2="2011-12-31";
        HashMap<String,Float> clients = myDAO.totalForCustomer(d1,d2);
        assertEquals(9987.5f,clients.get("Big Car Parts"),0.0f);
        assertEquals(15853.f,clients.get("Jumbo Eagle Corp"),0.0f);
        assertEquals(200295.99f,clients.get("Zed Motor Co"),0.0f);
    }
    
    /**
     * Test of addProduct method, of class DAO.
     * @throws Modele.DAOException
     */
    @Test
    public void testaddProduct() throws DAOException {
        int maj = myDAO.addProduct(10000, 19978451, "MS", 15.00f, 100, 10.0f, "TRUE", "Boite de sushis");
        assertEquals(1,maj);
    }
    
    /**
     * Test of supprProduct method, of class DAO.
     * @throws Modele.DAOException
     */
    @Test
    public void testsupprProduct() throws DAOException {
        myDAO.addProduct(10000, 19978451, "MS", 15.00f, 100, 10.0f, "TRUE", "Boite de sushis");
        int maj = myDAO.supprProduct("10000");
        assertEquals(1,maj);
    }
    
    /**
     * Test of modifProduct method, of class DAO.
     * @throws Modele.DAOException
     */
    @Test
    public void testmodifProduct() throws DAOException {
        myDAO.addProduct(10001, 19978451, "MS", 15.00f, 100, 10.0f, "TRUE", "Boite de sushis");
        int maj = myDAO.modifProduct(10001,15.00f, 0, 10.0f, "FALSE", "Plat de makis");
        assertEquals(1,maj);
    }
    
    /**
     * Test of modifPurchCost method, of class DAO.
     * @throws Modele.DAOException
     */
    /*@Test
    public void testmodifPurchCost() throws DAOException {
        int maj = myDAO.modifPurchCost(10001,200.00f);
        System.out.println("oui");
        assertEquals(1,maj);
    }*/
    
    /**
     * Test of modifmodifMarkup method, of class DAO.
     * @throws Modele.DAOException
     */
    /*@Test
    public void testmodifMarkup() throws DAOException {
        int maj = myDAO.modifMarkup(10001,20.00f);
        assertEquals(1,maj);
    }*/
    
    /**
     * Test of productsInfos method, of class DAO.
     * @throws Modele.DAOException
     */
    @Test
    public void testproductsInfos() throws DAOException {
        HashMap<Integer,Product> produits = myDAO.productsInfos();
        assertEquals(30,produits.keySet().size());
    }
    
    /**
     * Test of productsInfosValue method, of class DAO.
     * @throws Modele.DAOException
     */
    @Test
    public void testproductsInfosValue() throws DAOException {
        myDAO.addProduct(10001, 19978451, "MS", 15.00f, 100, 10.0f, "TRUE", "Boite de sushis");
        HashMap<Integer,Product> produits = myDAO.productsInfos();
        assertEquals("Boite de sushis",produits.get(10001).getDescription());
    }
    
    /**
     * Test of CustomersInfos method, of class DAO.
     * @throws Modele.DAOException
     */
    @Test
    public void testCustomersInfos() throws DAOException {
        HashMap<Integer,CustomerEntity> clients = myDAO.CustomersInfos();
        assertEquals(13,clients.keySet().size());
    }
    
    /**
     * Test of CustomersInfos method, of class DAO.
     * @throws Modele.DAOException
     */
    @Test
    public void testCustomersInfosValue() throws DAOException {
        HashMap<Integer,CustomerEntity> clients = myDAO.CustomersInfos();
        assertEquals("Zed Motor Co",clients.get(753).getName());
    }	
    
    /**
     * Test of addPurchaseOrder method, of class DAO.
     * @throws Modele.DAOException
     */
    @Test
    public void testaddPurchaseOrder() throws DAOException {
        int maj = myDAO.addPurchaseOrder(1, 3, 980001, 10, 100.f, "2018-12-04", "2018-12-04", "La Poste");
        assertEquals(1,maj);
    }
    
    /**
     * Test of supprPurchaseOrder method, of class DAO.
     * @throws Modele.DAOException
     */
    @Test
    public void testsupprPurchaseOrder() throws DAOException {
        myDAO.addPurchaseOrder(1, 3, 980001, 10, 100.f, "2018-12-04", "2018-12-04", "La Poste");
        int maj = myDAO.supprPurchaseOrder(1);
        assertEquals(1,maj);
    }
    
    /**
     * Test of modifPurchaseOrder method, of class DAO.
     * @throws Modele.DAOException
     */
    @Test
    public void testmodifPurchaseOrder() throws DAOException {
        myDAO.addPurchaseOrder(1, 3, 980001, 10, 100.f, "2018-12-04", "2018-12-04", "La Poste");
        int maj = myDAO.modifPurchaseOrder(1,100,200.f,"2018-12-04", "2018-12-04","Chronopst");
        assertEquals(1,maj);
    }
}
