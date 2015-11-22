/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testSessionBean.AIS;

import Entity.AIS.BookingClassInstance;
import Entity.APS.FlightFrequency;
import Entity.APS.FlightInstance;
import Entity.APS.Route;
import SessionBean.AIS.AssignPriceBeanRemote;
import SessionBean.AIS.ViewBookingClassPriceBeanRemote;
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

/**
 *
 * @author LI HAO
 */
public class AssignPriceBeanRemoteTest {

    AssignPriceBeanRemote apbr = lookupAssignPriceBeanRemote();

    public AssignPriceBeanRemoteTest() {
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
        List<Route> rt = new ArrayList<Route>();
        rt = apbr.getRouteList();
        assertFalse(rt.isEmpty());
    }

    @Test
    public void test02getFlightFrequencyList() throws Exception {
        System.out.println("test02getFlightFrequencyList");
        List<FlightFrequency> ffList = new ArrayList<FlightFrequency>();
        ffList = apbr.getFlightFrequencyList(Long.valueOf(30000));
        assertFalse(ffList.isEmpty());
    }

    @Test
    public void test03getFlightInstanceList() throws Exception {
        System.out.println("test03getFlightInstanceList");
        List<FlightInstance> fiList = new ArrayList<FlightInstance>();
        fiList = apbr.getFlightInstanceList("MR003");
        assertFalse(fiList.isEmpty());
    }

    @Test
    public void test04getBkiList() throws Exception {
        System.out.println("test04getBkiList");
        List<FlightInstance> fiList = new ArrayList<FlightInstance>();
        fiList = apbr.getFlightInstanceList("MR003");
        
        List<BookingClassInstance> bkiList=new ArrayList<BookingClassInstance>();
        bkiList=apbr.getBkiList(fiList.get(0));
        assertNotNull(bkiList);

    }

    private AssignPriceBeanRemote lookupAssignPriceBeanRemote() {
        try {
            Context c = new InitialContext();
            return (AssignPriceBeanRemote) c.lookup("java:global/MAS/MAS-ejb/AssignPriceBean!SessionBean.AIS.AssignPriceBeanRemote");

        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
