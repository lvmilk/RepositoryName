/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testSessionBean.APS;

import Entity.APS.AircraftType;
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

    @Test(expected = Exception.class)
    public void test06ViewRoute_NoSuchRoute() throws Exception {
        System.out.println("test06ViewRoute_NoSuchRoute");
        Route rt = new Route();
        rt = rpbr.viewRoute("SIN", "NRT");
        assertEquals("SIN", rt.getOrigin().getIATA());

    }

    @Test
    public void test07ViewAsOriginRoute() throws Exception {
        System.out.println("test07ViewAsOriginRoute");
        Airport ap = new Airport();
        ap = rpbr.findAirport("SIN");

        List<Route> rt = new ArrayList<Route>();
        rt = rpbr.viewApAsOriginRoute(ap);
        assertFalse(rt.isEmpty());
    }

    @Test
    public void test08ViewAsDestRoute() throws Exception {
        System.out.println("test08ViewAsDestRoutes");
        Airport ap = new Airport();
        ap = rpbr.findAirport("SIN");

        List<Route> rt = new ArrayList<Route>();
        rt = rpbr.viewApAsOriginRoute(ap);
        assertFalse(rt.isEmpty());
    }

    @Test
    public void test09ViewAllRouteString() throws Exception {
        System.out.println("test09ViewAllRouteString");
        List<String> sList = new ArrayList<String>();
        sList = rpbr.viewAllRouteString();

        assertFalse(sList.isEmpty());
    }

    @Test
    public void test10CanDeleteAirportList() throws Exception {
        System.out.println("test10CanDeleteAirportList");
        List<Airport> ap = new ArrayList<Airport>();
        ap = rpbr.canDeleteAirportList();

        assertFalse(ap.isEmpty());
    }

    @Test
    public void test11CannotDeleteAirportList() throws Exception {
        System.out.println("test11CannotDeleteAirportList");
        List<Airport> ap = new ArrayList<Airport>();
        ap = rpbr.cannotDeleteAirportList();

        assertFalse(ap.isEmpty());
    }

    @Test
    public void test12FindRoute() throws Exception {
        System.out.println("test12FindRoute");
        Route rt = new Route();
        rt = rpbr.findRoute(Long.valueOf(10000));

        assertNotNull(rt);
    }

    @Test
    public void test13CanDeleteRouteList() throws Exception {
        System.out.println("test13CanDeleteRouteList");
        List<Route> rt = new ArrayList<Route>();
        rt = rpbr.canDeleteRouteList();

        assertFalse(rt.isEmpty());
    }

    @Test
    public void test14CheckFeasibleAcByDis() throws Exception {
        System.out.println("test14CheckFeasibleAcByDis");
        Route rt = new Route();
        rt = rpbr.findRoute(Long.valueOf(20000));

        List<AircraftType> atList = new ArrayList<AircraftType>();
        atList = rpbr.checkFeasibleAcByDis(rt);
        assertFalse(atList.isEmpty());
    }

    @Test
    public void test16CheckFeasibleAcByAsp() throws Exception {
        System.out.println("test16CheckFeasibleAcByAsp");
        Route rt = new Route();
        rt = rpbr.findRoute(Long.valueOf(20000));

        List<AircraftType> atList = new ArrayList<AircraftType>();
        atList = rpbr.checkFeasibleAcByAsp(rt);
        assertFalse(atList.isEmpty());
    }

    @Test
    public void test17FeasibleAc() throws Exception {
        System.out.println("test17FeasibleAc");
        Route rt = new Route();
        rt = rpbr.findRoute(Long.valueOf(20000));

        List<AircraftType> atList = new ArrayList<AircraftType>();
        atList = rpbr.feasibleAc(rt);
        assertFalse(atList.isEmpty());
    }

    @Test
    public void test18FindAirport() throws Exception {
        System.out.println("test18FindAirport");
        Airport ap = new Airport();
        ap = rpbr.findAirport("SIN");
        assertNotNull(ap);

    }

    @Test
    public void test19ViewHubAirport() throws Exception {
        System.out.println("test19ViewHubAirport");
        List<Airport> apList = new ArrayList<Airport>();
        apList = rpbr.viewHubAirport();
        assertFalse(apList.isEmpty());
    }

    @Test
    public void test20IsHubAirport() throws Exception {
        System.out.println("test20IsHubAirport");
        boolean bl;
        bl = rpbr.isHubAirport("SIN");
        assertTrue(bl);

    }

    @Test
    public void test21CalRouteDistance() throws Exception {
        System.out.println("test21CalRouteDistance");
        Double result;
        result = rpbr.calRouteDistance("SIN", "XIA");
        assertTrue(3700 < result);
    }

    @Test
    public void test22CalRouteDistance_BoundryCase() throws Exception {
        System.out.println("test22CalRouteDistance_ErrorCase");
        Double result;
        result = rpbr.calRouteDistance("SIN", "XIA");
//        assertEquals((Double)3711.2,result);
        assertTrue(3711.2 < result && result < 3711.22);

    }

    @Test
    public void test23MaxBlockHour() throws Exception {
        System.out.println("test23MaxBlockHour");
        Double result;
        result = rpbr.maxBlockHour(3800.0);
        assertTrue(3.5 < result);
    }

    @Test
    public void test24MaxBlockHour_ErrorCase() throws Exception {
        System.out.println("test24MaxBlockHour_ErrorCase");
        Double result;
        result = rpbr.maxBlockHour(3800.0);
        assertTrue(5.42<result && result<5.43);

    }

    @Test
    public void test25MinBlockHour() throws Exception {
        System.out.println("test25MinBlockHour");
        Double result;
        result = rpbr.minBlockHour(3800.0);
        assertFalse(5.5 < result);
    }

    @Test
    public void test26MinBlockHour_ErrorCase() throws Exception {
        System.out.println("test26MinBlockHour_ErrorCase");
        Double result;
        result = rpbr.minBlockHour(3800.0);
        assertEquals((Double) 3.8, result);
    }

    @Test
    public void test27CanDeleteRoutePair() throws Exception {
        System.out.println("test27CanDeleteRoutePair");
        List<Route> rt=new ArrayList<Route>();
        rt=rpbr.canDeleteRoutePair();
        assertFalse(rt.isEmpty());
    }

    @Test
    public void test28AirportHasFlight() throws Exception {
        System.out.println("test28AirportHasFlight");
        Airport ap = new Airport();
        ap = rpbr.findAirport("SIN");
        
        boolean bl;
        bl=rpbr.airportHasFlight(ap);
        assertTrue(bl);
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
