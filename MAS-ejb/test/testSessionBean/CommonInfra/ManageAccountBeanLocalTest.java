/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testSessionBean.CommonInfra;

import SessionBean.CommonInfra.ManageAccountBeanLocal;
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
public class ManageAccountBeanLocalTest {

    ManageAccountBeanLocal mabl= lookupManageAccountBeanLocal();

    public ManageAccountBeanLocalTest() {
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
    public void test01ValidateLogin() {
        System.out.println("validateLogin");
        boolean flag=mabl.validateLogin("admin", "admin", "administrator");
        assertTrue(flag);
    }
    
    @Test
    public void test02AddAccountAndValidateLogin() {
        System.out.println("validateLogin");
        boolean flag=mabl.validateLogin("admin", "admin", "administrator");
        assertTrue(flag);
    }

    private ManageAccountBeanLocal lookupManageAccountBeanLocal() {
        try {
            Context c = new InitialContext();
            return (ManageAccountBeanLocal) c.lookup("java:global/MAS/MAS-ejb/ManageAccountBean!SessionBean.CommonInfra.ManageAccountBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
