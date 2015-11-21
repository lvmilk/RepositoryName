/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testSessionBean.CommonInfra;

import SessionBean.CommonInfra.MsgBeanRemote;
import SessionBean.CommonInfra.VerifyAccountBeanRemote;
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
public class VerifyAccountBeanRemoteTest {

    VerifyAccountBeanRemote vabr=lookupVerifyAccountBeanRemote();
    
    public VerifyAccountBeanRemoteTest() {
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
    @Test
    public void test01VerifyAccInfo() {
        System.out.println("test01VerifyAccInfo");
        boolean result;
        result=vabr.validateAccInfo("O777777", "o777777@mas.com","officeStaff");
        assertTrue(result);
        
    }

    private VerifyAccountBeanRemote lookupVerifyAccountBeanRemote() {
        try {
            Context c = new InitialContext();
            return (VerifyAccountBeanRemote) c.lookup("java:global/MAS/MAS-ejb/VerifyAccountBean!SessionBean.CommonInfra.VerifyAccountBeanRemote");

        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
