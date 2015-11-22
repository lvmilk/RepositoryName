/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testSessionBean.AIS;

import Entity.AIS.BookingClass;
import SessionBean.AIS.BookingClassBeanRemote;
import SessionBean.APS.FleetPlanningBeanRemote;
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
public class BookingClassBeanRemoteTest {

    BookingClassBeanRemote bcbr = lookupBookingClassBeanRemote();

    public BookingClassBeanRemoteTest() {
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
    public void test01TestCheckDuplicate() throws Exception {
        System.out.println("test01TestCheckDuplicate");
        boolean bl;
        bl = bcbr.checkDuplicate("S");
        assertTrue(bl);
    }

    @Test
    public void test02TestAddAccountAndEditBookingClass() throws Exception {
        System.out.println("test02TestAddAccountAndEditBookingClass");
        BookingClass bc = new BookingClass();
        bc = bcbr.editBookingClass("S", "S", "Suite", 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 2.0, 0, 180, 0, 0, true, true, true);
        assertNotNull(bc);

    }

    @Test
    public void test03TestCheckGotInstance() throws Exception {
        System.out.println("test03TestCheckGotInstance");
        ArrayList<BookingClass> selectedClass = new ArrayList<BookingClass>();
        List<BookingClass> bcList = new ArrayList<BookingClass>();
        bcList = bcbr.getAllBookingClasses();
        selectedClass.add(bcList.get(0));

        boolean bl;
        bl = bcbr.checkGotInstance(selectedClass);
        assertTrue(bl);
    }


    private BookingClassBeanRemote lookupBookingClassBeanRemote() {
        try {
            Context c = new InitialContext();
            return (BookingClassBeanRemote) c.lookup("java:global/MAS/MAS-ejb/BookingClassBean!SessionBean.AIS.BookingClassBeanRemote");

        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
