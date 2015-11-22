/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testSessionBean.APS;

import Entity.APS.AircraftType;
import Entity.APS.Airport;
import SessionBean.APS.FleetPlanningBeanRemote;
import SessionBean.APS.ManageCabinBeanRemote;
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
public class ManageCabinBeanRemoteTest {

    ManageCabinBeanRemote mcbr = lookupManageCabinBeanRemote();
    FleetPlanningBeanRemote fpbr=lookupFleetPlanningBeanRemote();

    public ManageCabinBeanRemoteTest() {
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
    public void test01widthExist() throws Exception {
        System.out.println("test01ViewAirport");
        boolean bl;
        AircraftType acType=fpbr.getAircraftType("A380");
        bl = mcbr.widthExist(acType, "Economy Class");
        assertFalse(bl);

    }

    @Test
    public void test02rowCountExist() throws Exception {
        System.out.println("test02rowCountExist");
        boolean bl;
        AircraftType acType=fpbr.getAircraftType("A380");
        bl = mcbr.rowCountExist(acType, "Economy Class");
        assertFalse(bl);

    }

    @Test
    public void test03rowSeatCount() throws Exception {
        System.out.println("test03rowSeatCount");
        boolean bl;
        AircraftType acType=fpbr.getAircraftType("A380");
        bl = mcbr.rowSeatCount(acType, "Economy Class");
        assertFalse(bl);
    }

    @Test
    public void test04rowConfig() throws Exception {
        System.out.println("test04rowConfig");
        boolean bl;
        AircraftType acType=fpbr.getAircraftType("A380");
        bl = mcbr.rowConfig(acType, "Economy Class");
        assertFalse(bl);

    }

    private ManageCabinBeanRemote lookupManageCabinBeanRemote() {
        try {
            Context c = new InitialContext();
            return (ManageCabinBeanRemote) c.lookup("java:global/MAS/MAS-ejb/ManageCabinBean!SessionBean.APS.ManageCabinBeanRemote");

        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
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
