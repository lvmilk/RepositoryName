/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testSessionBean.AIS;

import Entity.AIS.BookingClassInstance;
import Entity.AIS.CabinClass;
import Entity.APS.FlightFrequency;
import SessionBean.AIS.ModifyPriceBeanRemote;
import SessionBean.AIS.SeatAllocationBeanRemote;
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
public class SeatAllocationBeanRemoteTest {

    SeatAllocationBeanRemote sabr = lookupSeatAllocationBeanRemote();

    public SeatAllocationBeanRemoteTest() {
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
        System.out.println("test01getFlightList");
        List<FlightFrequency> ffList = new ArrayList<FlightFrequency>();
        ffList = sabr.getFlightList("2015-12-23");
        assertFalse(ffList.isEmpty());
    }

    @Test
    public void test02getBkiList() throws Exception {
        System.out.println("test02getBkiList");
        List<BookingClassInstance> bkiList = new ArrayList<BookingClassInstance>();
        bkiList = sabr.getBkiList("MR006", "2015-12-23", "Economy Class");
        assertFalse(bkiList.isEmpty());
    }

    @Test
    public void test03getCabinList() throws Exception {
        System.out.println("test03getCabinList");
        List<CabinClass> cbList = new ArrayList<CabinClass>();
        cbList = sabr.getCabinList("MR006");
        assertFalse(cbList.isEmpty());
    }

    private SeatAllocationBeanRemote lookupSeatAllocationBeanRemote() {
        try {
            Context c = new InitialContext();
            return (SeatAllocationBeanRemote) c.lookup("java:global/MAS/MAS-ejb/SeatAllocationBean!SessionBean.AIS.SeatAllocationBeanRemote");

        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
