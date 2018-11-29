/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.sql.DataSource;
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
    
    @Before
    public void setUp() {
        myDataSource = DataSourceFactory.getDataSource();
	myDAO = new DAO(myDataSource);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of listeClients method, of class DAO.
     * @throws Modele.DAOException
     */
    @Test
    public void testListeClients() throws DAOException {
        HashMap<Integer,String> clients = myDAO.listeClients();
        assertEquals(13, clients.keySet().size());
    }
    
    /**
     * Test of testmapProductCode method, of class DAO.
     * @throws Modele.DAOException
     */
    @Test
    public void testmapProductCode() throws DAOException {
        HashMap<String,Float> clients = myDAO.mapProductCode();
        assertEquals(9987.5f,clients.get("BK"),0.0f);
        assertEquals(3065.05f,clients.get("CB"),0.0f);
    }
    
    /**
     * Test of testmapStateWhenNoPurchase method, of class DAO.
     * @throws Modele.DAOException
     */
    @Test
    public void testmapStateWhenNoPurchase() throws DAOException {
        String d1="2011-01-01";
        String d2="2011-01-01";
        HashMap<String,Float> clients = myDAO.mapState(d1,d2);
        assertEquals(null,clients.get("NY"));
    }
    
    /**
     * Test of testmapState method, of class DAO.
     * @throws Modele.DAOException
     */
    @Test
    public void testmapState() throws DAOException {
        String d1="2011-01-01";
        String d2="2011-12-31";
        HashMap<String,Float> clients = myDAO.mapState(d1,d2);
        assertEquals(135888.8f,clients.get("CA"),0.0f);
        assertEquals(160895.66f,clients.get("FL"),0.0f);
        assertEquals(874.5f,clients.get("GA"),0.0f);
        assertEquals(212334.74f,clients.get("MI"),0.0f);
        assertEquals(117045.99f,clients.get("NY"),0.0f);
    }
    
    /**
     * Test of testmapCustomerWhenNoPurchase method, of class DAO.
     * @throws Modele.DAOException
     */
    @Test
    public void testmapCustomerWhenNoPurchase() throws DAOException {
        String d1="2011-01-01";
        String d2="2011-01-01";
        HashMap<String,Float> clients = myDAO.mapCustomer(d1,d2);
        assertEquals(null,clients.get("NY"));
    }
    
    /**
     * Test of testmapCustomer method, of class DAO.
     * @throws Modele.DAOException
     */
    @Test
    public void testmapCustomer() throws DAOException {
        String d1="2011-01-01";
        String d2="2011-12-31";
        HashMap<String,Float> clients = myDAO.mapCustomer(d1,d2);
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
        int maj = myDAO.supprProduct("10000");
        assertEquals(1,maj);
    }
    
    /**
     * Test of modifProduct method, of class DAO.
     * @throws Modele.DAOException
     */
    @Test
    public void testmodifProduct() throws DAOException {
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
     * Test of mapProductInfo method, of class DAO.
     * @throws Modele.DAOException
     */
    @Test
    public void testmapProductInfo() throws DAOException {
        HashMap<Integer,Product> produits = myDAO.mapProductInfo();
        System.out.println(produits.keySet());
        assertEquals(32,produits.keySet().size());
    }
    
    /**
     * Test of mapProductInfoValue method, of class DAO.
     * @throws Modele.DAOException
     */
    @Test
    public void testmapProductInfoValue() throws DAOException {
        HashMap<Integer,Product> produits = myDAO.mapProductInfo();
        assertEquals("Boite de sushis",produits.get(10000).getDescription());
    }
    
    /**
     * Test of mapCustomerInfo method, of class DAO.
     * @throws Modele.DAOException
     */
    @Test
    public void testmapCustomerInfo() throws DAOException {
        HashMap<Integer,CustomerEntity> clients = myDAO.mapCustomerInfo();
        assertEquals(13,clients.keySet().size());
    }
    
    /**
     * Test of mapCustomerInfoValue method, of class DAO.
     * @throws Modele.DAOException
     */
    @Test
    public void testmapCustomerInfoValue() throws DAOException {
        HashMap<Integer,CustomerEntity> clients = myDAO.mapCustomerInfo();
        assertEquals("Zed Motor Co",clients.get(753).getName());
    }
    
}
