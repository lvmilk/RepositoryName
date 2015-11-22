/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testSessionBean.APS;

import Entity.APS.Aircraft;
import Entity.APS.AircraftType;
import Entity.APS.FlightFrequency;
import Entity.APS.FlightInstance;
import Entity.APS.Route;
import SessionBean.APS.FleetPlanningBeanRemote;
import SessionBean.APS.FlightSchedulingBeanRemote;
import SessionBean.APS.RoutePlanningBeanRemote;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
public class FlightSchedulingBeanRemoteTest {

    FlightSchedulingBeanRemote fsbr = lookupFlightSchedulingBeanRemote();
    RoutePlanningBeanRemote rpbr = lookupRoutePlanningBeanRemote();

    public FlightSchedulingBeanRemoteTest() {
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
    public void test01addFlightFrequency() throws Exception {
        System.out.println("test01addFlightFrequency");
        Route rt = new Route();

        rt = rpbr.findRoute(Long.valueOf(99999));

        FlightFrequency ff;
        ff = fsbr.addFlightFrequency(rt, "MR999", "08:00", "12:00", 0, false, true, false, true, false, true, false, "2016-10-08", "2022-10-08", "", "", "Terminal 1", "Terminal 2");
        assertNotNull(ff);
    }

    @Test
    public void test02getAllFlightFrequency() throws Exception {
        System.out.println("test02getAllFlightFrequency");
        List<FlightFrequency> ffList = new ArrayList<FlightFrequency>();
        ffList = fsbr.getAllFlightFrequency();
        assertTrue(ffList.size() > 0);

    }

    @Test
    public void test03canDeleteFlightFreqList() throws Exception {
        System.out.println("test03canDeleteFlightFreqList");
        List<FlightFrequency> ffList = new ArrayList<FlightFrequency>();
        ffList = fsbr.canDeleteFlightFreqList();
        assertNotNull(ffList);

    }

    @Test
    public void test04getAllFlightInstance() throws Exception {
        System.out.println("test04getAllFlightInstance");
        List<FlightInstance> ffInstance = new ArrayList<FlightInstance>();
        ffInstance = fsbr.getAllFlightInstance();
        assertTrue(ffInstance.size() > 0);

    }

    @Test
    public void test05getFlightOfRoute() throws Exception {
        System.out.println("test05getFlightOfRoute");
        Route rt = new Route();

        rt = rpbr.findRoute(Long.valueOf(30000));

        List<FlightFrequency> ffList = new ArrayList<FlightFrequency>();
        ffList = fsbr.getFlightOfRoute(rt);
        assertNotNull(ffList.size() > 0);

    }

    @Test
    public void test06getAircraft() throws Exception {
        System.out.println("test06getAircraft");
        Aircraft ac = new Aircraft();
        ac = fsbr.getAircraft("9V-ABC");
        assertNotNull(ac);
    }

    @Test
    public void test07getUnplannedFlightInstance() throws Exception {
        System.out.println("test07getUnplannedFlightInstance");
        Aircraft ac = new Aircraft();
        ac = fsbr.getAircraft("9V-ABC");
        List<FlightInstance> ffInstance = new ArrayList<FlightInstance>();
        ffInstance = fsbr.getUnplannedFlightInstance(ac);
        assertTrue(ffInstance.size() > 0);
    }

    @Test
    public void test08getAllAircraft() throws Exception {
        System.out.println("test08getAllAircraft");
        List<Aircraft> acList = new ArrayList<Aircraft>();
        acList = fsbr.getAllAircraft();
        assertTrue(acList.size() > 0);

    }

    @Test
    public void test09getThisFlightInstance() throws Exception {
        System.out.println("test09getThisFlightInstance");
        List<FlightInstance> ffInstance = new ArrayList<FlightInstance>();
        ffInstance = fsbr.getThisFlightInstance("MR003");
        assertTrue(ffInstance.size() > 0);

    }

    @Test
    public void test10getFirstInstDate() throws Exception {
        System.out.println("test10getFirstInstDate");
        String result;
        result = fsbr.getFirstInstDate();
        assertNotNull(result);
    }

    @Test
    public void test11getUnplannedFiWithinPeriod() throws Exception {
        System.out.println("test11getUnplannedFiWithinPeriod");
        List<FlightInstance> fiList = new ArrayList<FlightInstance>();

        String startDate = "2015-12-15 00:00";
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date sDate = format.parse(startDate);
        String endDate = "2016-01-15 00:00";
        Date eDate = format.parse(endDate);
        fiList = fsbr.getUnplannedFiWithinPeriod(sDate, eDate);
        assertTrue(fiList.size() > 0);
    }

    @Test
    public void test12findFlight() throws Exception {
        System.out.println("test12findFlight");
        FlightInstance fi = new FlightInstance();
        fi = fsbr.findFlight(Long.valueOf(30000));
        assertNotNull(fi);
    }

    @Test
    public void test13findAircraft() throws Exception {
        System.out.println("test13findAircraft");
        Aircraft ac = new Aircraft();
        ac = fsbr.findAircraft("9V-ABC");
        assertNotNull(ac);
    }

    @Test
    public void test14getUnassignedFlight() throws Exception {
        System.out.println("test14getUnassignedFlight");
        List<FlightInstance> fi = new ArrayList<FlightInstance>();
        fi = fsbr.getUnassignedFlight();
        assertNotNull(fi);

    }

    @Test
    public void test15getFlightAccumMinute() throws Exception {
        System.out.println("test15getFlightAccumMinute");
        List<FlightFrequency> ffList = new ArrayList<FlightFrequency>();
        ffList = fsbr.getAllFlightFrequency();
        Long result;
        result=fsbr.getFlightAccumMinute(ffList.get(0));
        assertNotNull(result);

    }

    @Test
    public void test16getAllUnplannedFi() throws Exception {
        System.out.println("test16getAllUnplannedFi");
        List<FlightInstance> fi = new ArrayList<FlightInstance>();
        fi = fsbr.getAllUnplannedFi();
        assertNotNull(fi);
    }

    @Test
    public void test17getDummyFi() throws Exception {
        System.out.println("test17getDummyFi");
        FlightInstance fi = new FlightInstance();
        fi = fsbr.getDummyFi("outBound");
        assertNotNull(fi);

    }

    @Test
    public void test18getSortedFiWithinPeriod() throws Exception {
        System.out.println("test18getSortedFiWithinPeriod");
        List<FlightInstance> fiList = new ArrayList<FlightInstance>();

        String startDate = "2015-12-15 00:00";
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date sDate = format.parse(startDate);
        String endDate = "2016-01-15 00:00";
        Date eDate = format.parse(endDate);
        fiList = fsbr.getSortedFiWithinPeriod(sDate, eDate);
        assertTrue(fiList.size() > 0);

    }

    private FlightSchedulingBeanRemote lookupFlightSchedulingBeanRemote() {
        try {
            Context c = new InitialContext();
            return (FlightSchedulingBeanRemote) c.lookup("java:global/MAS/MAS-ejb/FlightSchedulingBean!SessionBean.APS.FlightSchedulingBeanRemote");

        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
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
