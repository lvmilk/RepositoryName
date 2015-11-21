/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testSessionBean.APS;

import Entity.APS.Airport;
import Entity.APS.Route;
import SessionBean.APS.RoutePlanningBeanRemote;
import SessionBean.CommonInfra.VerifyAccountBeanRemote;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 *
 * @author LI HAO
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoutePlannigBeanRemoteTest {

    RoutePlanningBeanRemote rpbr = lookupRoutePlanningBeanRemote();

    public RoutePlannigBeanRemoteTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void test01ViewAirport() throws Exception {
        System.out.println("test01ViewAirport");
        Airport ap = new Airport();
        ap = rpbr.viewAirport("SIN");
        assertEquals("SIN", ap.getIATA());

    }

    @Test(expected = Exception.class)
    public void test02ViewAirport_NoSuchAirport() throws Exception {
        System.out.println("test02ViewAirport_NoSuchAirport");
        Airport ap = new Airport();
        ap = rpbr.viewAirport("ICN");
        assertEquals("ICN", ap.getIATA());

    }

    @Test
    public void test03ViewAllAirport() throws Exception {
        System.out.println("test03ViewAllAirport");
        List<Airport> ap = new ArrayList<Airport>();
        ap = rpbr.viewAllAirport();
        assertFalse(ap.isEmpty());

    }

    @Test
    public void test04ViewAllRoute() throws Exception {
        System.out.println("test04ViewAllRoute");
        List<Route> rt = new ArrayList<Route>();
        rt = rpbr.viewAllRoute();
        assertFalse(rt.isEmpty());

    }

    @Test
    public void test05ViewRoute() throws Exception {
        System.out.println("test05ViewRoute");
        Route rt = new Route();
        rt = rpbr.viewRoute("SIN", "XIA");
        assertEquals("SIN", rt.getOrigin().getIATA());

    }

    @Test(expected=Exception.class)
    public void test06ViewRoute_NoSuchRoute() throws Exception {
        System.out.println("test06ViewRoute_NoSuchRoute");
        Route rt = new Route();
        rt = rpbr.viewRoute("SIN", "NRT");
        assertEquals("SIN", rt.getOrigin().getIATA());

    }

    private RoutePlanningBeanRemote lookupRoutePlanningBeanRemote() {
        try {
            Context c = new InitialContext();
            return (RoutePlanningBeanRemote) c.lookup("java:global/MAS/MAS-ejb/RoutePlanningBean!SessionBean.APS.RoutePlanningBeanRemote");

        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
