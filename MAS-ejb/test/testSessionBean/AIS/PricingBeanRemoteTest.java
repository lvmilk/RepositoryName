/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testSessionBean.AIS;

import Entity.APS.FlightFrequency;
import Entity.APS.Route;
import SessionBean.AIS.PricingBeanRemote;
import SessionBean.AIS.SeatAllocationBeanRemote;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

/**
 *
 * @author LI HAO
 */
public class PricingBeanRemoteTest {

    PricingBeanRemote pbr=lookupPricingBeanRemote();
    
    public PricingBeanRemoteTest() {
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
    public void test01getRouteList() throws Exception {
        System.out.println("test01getRouteList");
        List<Route> rtList=new ArrayList<Route>();
        rtList=pbr.getRouteList();
        assertFalse(rtList.isEmpty());
    }

    @Test
    public void test02getRouteInfo() throws Exception {
        System.out.println("test02getRouteInfo");
        Route rt=new Route();
        rt=pbr.getRouteInfo(Long.valueOf(30000));
        assertNotNull(rt);
    }

    @Test
    public void test03getCrewCost() throws Exception {
        System.out.println("test03getCrewCost");
        Double result;
        result=pbr.getCrewCost(11, 50.0, 4.0, 156);
        assertEquals((Double)343200.0, result);
    }


    @Test
    public void test04getAllFare() throws Exception {
        System.out.println("test01getFlightList");
                Route rt=new Route();
        rt=pbr.getRouteInfo(Long.valueOf(30000));
        
        Map<String, Double> routeMap=new HashMap<String, Double>();
        routeMap=pbr.getAllFare(rt);
        assertNotNull(routeMap);
        
    }

    private PricingBeanRemote lookupPricingBeanRemote() {
        try {
            Context c = new InitialContext();
            return (PricingBeanRemote) c.lookup("java:global/MAS/MAS-ejb/PricingBean!SessionBean.AIS.PricingBeanRemote");

        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
