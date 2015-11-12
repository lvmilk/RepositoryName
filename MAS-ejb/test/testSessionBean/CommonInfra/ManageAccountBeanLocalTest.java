/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testSessionBean.CommonInfra;

import SessionBean.CommonInfra.ManageAccountBeanLocal;
import SessionBean.CommonInfra.ManageAccountBeanRemote;
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

    ManageAccountBeanRemote mabl = lookupManageAccountBeanRemote();

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
        boolean flag = mabl.validateLogin("admin", "admin", "administrator");
        assertTrue(flag);
    }

    @Test
    public void test02ValidateLogin_invalid() {
        System.out.println("validateLogin_invalid scenario");
        boolean flag = mabl.validateLogin("admins", "admins", "administrator");
        assertFalse(flag);
    }

    @Test
    public void test03AddAdminAndValidateLogin() {
        System.out.println("test03AddAdminAndValidateLogin");
        mabl.addAdmin("admin1", "admin1", "administrator");
        boolean flag = mabl.validateLogin("admin1", "admin1", "administrator");
        assertTrue(flag);
    }

    @Test
    public void test04AddAccountAndValidateLogin() {
        System.out.println("test04AddAccountAndValidateLogin");
        mabl.addAccount("O999999","O999999","O999@999.com", "officeStaff","HAO","LI","normal",5000.0);
        boolean flag = mabl.validateLogin("O999999", "O999999", "officeStaff");
        assertTrue(flag);
    }
    
    @Test
    public void test05AddCocpitAccAndValidateLogin(){
        System.out.println("test05AddCocpitAccAndValidateLogin");
        mabl.addCocpitAcc("CP999999","CP999999","CP999@999.com","cockpit","HAO","LI","Captain",10000.0,"A380");
        boolean flag = mabl.validateLogin("CP999999", "CP999999", "cockpit");
        assertTrue(flag);
    }

    private ManageAccountBeanRemote lookupManageAccountBeanRemote() {
        try {
            Context c = new InitialContext();
            return (ManageAccountBeanRemote) c.lookup("java:global/MAS/MAS-ejb/ManageAccountBean!SessionBean.CommonInfra.ManageAccountBeanRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
