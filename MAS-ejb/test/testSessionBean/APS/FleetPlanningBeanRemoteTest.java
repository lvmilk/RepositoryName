/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testSessionBean.APS;

import Entity.APS.Aircraft;
import Entity.APS.AircraftType;
import Entity.APS.Airport;
import Entity.APS.FlightInstance;
import SessionBean.APS.FleetPlanningBeanRemote;
import SessionBean.APS.RoutePlanningBeanRemote;
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
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 *
 * @author LI HAO
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FleetPlanningBeanRemoteTest {

    FleetPlanningBeanRemote fpbr = lookupFleetPlanningBeanRemote();

    public FleetPlanningBeanRemoteTest() {
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
    public void test01getAllAircraftType() throws Exception {
        System.out.println("test01getAllAircraftType");
        List<AircraftType> actList = new ArrayList<AircraftType>();
        actList = fpbr.getAllAircraftType();
        assertFalse(actList.isEmpty());
    }

    @Test
    public void test02getAllAircraft() throws Exception {
        System.out.println("test02getAllAircraft");
        List<Aircraft> ac = new ArrayList<Aircraft>();
        ac = fpbr.getAllAircraft();
        assertNotNull(ac);
    }

    @Test
    public void test03getAircraft() throws Exception {
        System.out.println("test03getAircraft");
        Aircraft ac = new Aircraft();
        ac = fpbr.getAircraft("9V-000");
        assertNotNull(ac);

    }

    @Test
    public void test04getAircraft_NoSuchAircraft() throws Exception {
        System.out.println("test04getAircraft_NoSuchAircraft");
        Aircraft ac = new Aircraft();
        ac = fpbr.getAircraft("8V-000");
        assertNull(ac);

    }

    @Test
    public void test05getAircraftType() throws Exception {
        System.out.println("test05getAircraftType");
        AircraftType acType = new AircraftType();
        acType = fpbr.getAircraftType("A380");
        assertNotNull(acType);
    }

    @Test(expected = Exception.class)
    public void test06getAircraftType_NoSuchAircraftType() throws Exception {
        System.out.println("test06getAircraftType_NoSuchAircraftType");
        AircraftType acType = new AircraftType();
        acType = fpbr.getAircraftType("A390");
        assertNull(acType);
    }

    @Test
    public void test07checkDuplicate() throws Exception {
        System.out.println("test07checkDuplicate");
        boolean bl;
        bl = fpbr.checkDuplicate("A380");
        assertTrue(bl);

    }

    @Test
    public void test08checkDuplicate_NotDuplicate() throws Exception {
        System.out.println("test08checkDuplicate_NotDuplicate");
        boolean bl;
        bl = fpbr.checkDuplicate("A320");
        assertFalse(bl);

    }

    @Test
    public void test09canDeleteTypeList() throws Exception {
        System.out.println("test09canDeleteTypeList");
        List<AircraftType> actList = new ArrayList<AircraftType>();
        actList = fpbr.canDeleteTypeList();
        assertTrue(actList.isEmpty());

    }

    @Test
    public void test10cannotDeleteTypeList() throws Exception {
        System.out.println("test10cannotDeleteTypeList");
        List<AircraftType> actList = new ArrayList<AircraftType>();
        actList = fpbr.cannotDeleteTypeList();
        assertFalse(actList.isEmpty());
    }

    @Test
    public void test11getAllSize() throws Exception {
        System.out.println("test11getAllSize");
        Map<String, Integer> map = new HashMap<String, Integer>();
        map = fpbr.getAllSize("A380");
        assertFalse(map.isEmpty());
    }

    @Test
    public void test12getAllManufacturer() throws Exception {
        System.out.println("test12getAllManufacturer");
        Map<String, String> map = new HashMap<String, String>();
        map = fpbr.getAllManufacturer("A380");
        assertFalse(map.isEmpty());
    }

    @Test
    public void test13getAllNum() throws Exception {
        System.out.println("test13getAllNum");
        Map<String, List<String>> map= new HashMap<String, List<String>>();
        map=fpbr.getAllNum("A380");
        assertFalse(map.isEmpty());

    }

    @Test
    public void test14getThisTypeAircraft() throws Exception {
        System.out.println("test14getThisTypeAircrafts");
        List<Aircraft> acList;
        acList=fpbr.getThisTypeAircraft("777-300");
        assertFalse(acList.isEmpty());

    }

    @Test
    public void test15getThisFlightInstance() throws Exception {
        System.out.println("test15getThisFlightInstance");
        List<FlightInstance> fiList=new ArrayList<FlightInstance> ();
        fiList=fpbr.getThisFlightInstance("9V-000");
        assertFalse(fiList.isEmpty());
    }

    private FleetPlanningBeanRemote lookupFleetPlanningBeanRemote() {
        try {
            Context c = new InitialContext();
            return (FleetPlanningBeanRemote) c.lookup("java:global/MAS/MAS-ejb/FleetPlanningBean!SessionBean.APS.FleetPlanningBeanRemote");

        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
