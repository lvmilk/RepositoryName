/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testSessionBean.AIS;

import Entity.AIS.BookingClassInstance;
import Entity.APS.FlightFrequency;
import Entity.APS.Route;
import SessionBean.AIS.AssignPriceBeanRemote;
import SessionBean.AIS.ModifyPriceBeanRemote;
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
public class ModifyPriceBeanRemoteTest {

    ModifyPriceBeanRemote mpbr = lookupModifyPriceBeanRemote();

    public ModifyPriceBeanRemoteTest() {
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
        List<FlightFrequency>  ffList=new ArrayList<FlightFrequency> ();
        ffList=mpbr.getFlightList("2015-12-23");
        assertFalse(ffList.isEmpty());
    }

    @Test
    public void test02getBkiList() throws Exception {
        System.out.println("test02getBkiList");
        List<BookingClassInstance> bkiList=new ArrayList<BookingClassInstance>();
        bkiList=mpbr.getBkiList("MR006","2015-12-23");
        assertFalse(bkiList.isEmpty());

    }

    private ModifyPriceBeanRemote lookupModifyPriceBeanRemote() {
        try {
            Context c = new InitialContext();
            return (ModifyPriceBeanRemote) c.lookup("java:global/MAS/MAS-ejb/ModifyPriceBean!SessionBean.AIS.ModifyPriceBeanRemote");

        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
