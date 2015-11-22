/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testSessionBean.AIS;

import Entity.AIS.BookingClassInstance;
import Entity.AIS.FlightCabin;
import Entity.APS.FlightFrequency;
import Entity.APS.FlightInstance;
import SessionBean.AIS.BookingClassBeanRemote;
import SessionBean.AIS.SeatAssignBeanRemote;
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
public class SeatAssignBeanRemoteTest {

    SeatAssignBeanRemote sabr = lookupSeatAssignBeanRemote();

    public SeatAssignBeanRemoteTest() {
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
    public void test01getFlightList() throws Exception {
        System.out.println("test01CheckAirTypeEmpty");
        List<FlightFrequency> ffList = new ArrayList<FlightFrequency>();
        ffList = sabr.getFlightList("2015-12-08");
        assertFalse(ffList.isEmpty());
    }

    @Test
    public void test02findFlightInstance() throws Exception {
        System.out.println("test02findFlightInstance");
        FlightInstance fi = new FlightInstance();
        fi = sabr.findFlightInstance("MR003", "2015-12-08");
        assertNotNull(fi);
    }

    @Test
    public void test03getBkiList() throws Exception {
        System.out.println("test03getBkiList");
        List<BookingClassInstance> bkiList = new ArrayList<BookingClassInstance>();
        FlightInstance fi = new FlightInstance();
        fi = sabr.findFlightInstance("MR003", "2015-12-08");

        bkiList = sabr.getBkiList("Economy Class", fi);
        assertFalse(bkiList.isEmpty());
    }

    @Test
    public void test04listAssign() throws Exception {
        System.out.println("test04listAssign");
        ArrayList<BookingClassInstance> result = new ArrayList<BookingClassInstance>();
        List<BookingClassInstance> bkiList = new ArrayList<BookingClassInstance>();
        FlightInstance fi = new FlightInstance();
        fi = sabr.findFlightInstance("MR003", "2015-12-08");

        bkiList = sabr.getBkiList("Economy Class", fi);
        result=sabr.listAssign(bkiList, "Economy Class");
        assertNotNull(result);

    }

    @Test
    public void test05getCabinList() throws Exception {
        System.out.println("test05getCabinList");
        List<FlightCabin> fcList=new ArrayList<FlightCabin>();
        fcList=sabr.getCabinList("MR005","2015-12-08");
        assertFalse(fcList.isEmpty());
    }


    private SeatAssignBeanRemote lookupSeatAssignBeanRemote() {
        try {
            Context c = new InitialContext();
            return (SeatAssignBeanRemote) c.lookup("java:global/MAS/MAS-ejb/SeatAssignBean!SessionBean.AIS.SeatAssignBeanRemote");

        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
