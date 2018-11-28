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
        List<CustomerEntity> clients = myDAO.listeClients();
        assertEquals(13, clients.size());
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
    
}
