/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testSessionBean.AIS;

import Entity.APS.AircraftType;
import SessionBean.AIS.BookingClassBeanRemote;
import SessionBean.AIS.SeatPlanBeanRemote;
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
public class SeatPlanBeanRemoteTest {

    SeatPlanBeanRemote spbr = lookupSeatPlanBeanRemote();

    public SeatPlanBeanRemoteTest() {
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
    public void test01CheckAirTypeEmpty() throws Exception {
        System.out.println("test01CheckAirTypeEmpty");
        boolean bl;
        bl = spbr.checkAirTypeEmpty();
        assertNotNull(bl);

    }

    @Test
    public void test02TestFindType() throws Exception {
        System.out.println("test02TestFindType");
        AircraftType acType = new AircraftType();
        acType = spbr.findType("A380");
        assertNotNull(acType);

    }

    @Test(expected=Exception.class)
    public void test03TestFindType_NoSuchType() throws Exception {
        System.out.println("test02TestFindType");
        AircraftType acType = new AircraftType();
        acType = spbr.findType("A390");
        assertNull(acType);

    }

    private SeatPlanBeanRemote lookupSeatPlanBeanRemote() {
        try {
            Context c = new InitialContext();
            return (SeatPlanBeanRemote) c.lookup("java:global/MAS/MAS-ejb/SeatPlanBean!SessionBean.AIS.SeatPlanBeanRemote");

        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
