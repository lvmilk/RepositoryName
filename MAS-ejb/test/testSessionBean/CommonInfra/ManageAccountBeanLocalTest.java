/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testSessionBean.CommonInfra;

import Entity.CommonInfa.CabinCrew;
import Entity.CommonInfa.CockpitCrew;
import Entity.CommonInfa.GroundStaff;
import Entity.CommonInfa.OfficeStaff;
import SessionBean.CommonInfra.ManageAccountBeanRemote;
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
        mabl.addAccount("O999999", "O999999", "O999@999.com", "officeStaff", "HAO", "LI", "normal", 5000.0);
        boolean flag = mabl.validateLogin("O999999", "O999999", "officeStaff");
        assertTrue(flag);
    }

    @Test
    public void test05AddCocpitAccAndValidateLogin() {
        System.out.println("test05AddCocpitAccAndValidateLogin");
        mabl.addCocpitAcc("CP999999", "CP999999", "CP999@999.com", "cockpit", "HAO", "LI", "Captain", 10000.0, "A380");
        boolean flag = mabl.validateLogin("CP999999", "CP999999", "cockpit");
        assertTrue(flag);
    }

    @Test
    public void test06AddCheckEmailExist() {
        System.out.println("test06AddCheckEmailExist");
        boolean flag = mabl.checkEmailExists("o777777@mas.com");
        assertTrue(flag);
    }

    @Test
    public void test07AddCheckEmailExist_NotExist() {
        System.out.println("test07AddCheckEmailExist_NotExist");
        boolean flag = mabl.checkEmailExists("o777778@mas.com");
        assertFalse(flag);
    }

    @Test
    public void test08CheckEmailDuplicate() {
        System.out.println("test08CheckEmailDuplicate");
        boolean flag = mabl.checkEmailDuplicate("o777777@mas.com", "o777777@mas.com");
        assertFalse(flag);
    }

    @Test
    public void test09CheckEmailDuplicate_NotDuplicate() {
        System.out.println("test08CheckEmailDuplicate");
        boolean flag = mabl.checkEmailDuplicate("o777778@mas.com", "o777777@mas.com");
        assertTrue(flag);
    }

    @Test
    public void test10DeleteAcc() {
        System.out.println("test10DeleteDelAcc");
        List<OfficeStaff> selectedOffStf = new ArrayList<OfficeStaff>();
        OfficeStaff offStaff = new OfficeStaff();
        offStaff.setOffName("O999999");
        selectedOffStf.add(offStaff);
        boolean flag = mabl.delAcc(selectedOffStf);
        assertTrue(flag);
    }

    @Test
    public void test11DeleteGrdAcc() {
        System.out.println("test11DeleteGrdAcc");
        List<GroundStaff> selectedGrdStf = new ArrayList<GroundStaff>();
        GroundStaff grdStaff = new GroundStaff();
        grdStaff.setGrdName("G666631");
        selectedGrdStf.add(grdStaff);
        boolean flag = mabl.delGrdAcc(selectedGrdStf);
        assertTrue(flag);
    }

    @Test
    public void test12DeleteCabinAcc() {
        System.out.println("test12DeleteCabinAcc");
        List<CabinCrew> selectedCbCrew = new ArrayList<CabinCrew>();
        CabinCrew cbCrew = new CabinCrew();
        cbCrew.setCbName("CB888851");
        selectedCbCrew.add(cbCrew);
        boolean flag = mabl.delCabinAcc(selectedCbCrew);
        assertTrue(flag);
    }

    @Test
    public void test13DeleteCockpitAcc() {
        System.out.println("test13DeleteCockpitAcc");
        List<CockpitCrew> selectedCpCrew = new ArrayList<CockpitCrew>();
        CockpitCrew cpCrew = new CockpitCrew();
        cpCrew.setCpName("CP777751");
        selectedCpCrew.add(cpCrew);
        boolean flag = mabl.delCockpitAcc(selectedCpCrew);
        assertTrue(flag);
    }

    @Test
    public void test14GetOfficeStaff() {
        System.out.println("test14GetOfficeStaff");
        OfficeStaff offStaff = new OfficeStaff();
        offStaff = mabl.getOfficeStaff("O777777");
        assertEquals("O777777", offStaff.getOffName());

    }

    @Test
    public void test15GetOfficeStaff_NotFound() {
        System.out.println("test15GetOfficeStaff_NotFound");
        OfficeStaff offStaff = new OfficeStaff();
        offStaff = mabl.getOfficeStaff("O999999");
        assertNull(offStaff);

    }

    @Test
    public void test16GetGroundStaff() {
        System.out.println("test16GetGroundStaff");
        GroundStaff grdStaff = new GroundStaff();
        grdStaff = mabl.getGroundStaff("G666632");
        assertEquals("G666632", grdStaff.getGrdName());
    }

    @Test
    public void test17GetGroundStaff_NotFound() {
        System.out.println("test17GetGroundStaff_NotFound");
        GroundStaff grdStaff = new GroundStaff();
        grdStaff = mabl.getGroundStaff("G666631");
        assertNull(grdStaff);
    }

    @Test
    public void test18GetCabinCrew() {
        System.out.println("test18GetCabinCrew");
        CabinCrew cb = new CabinCrew();
        cb = mabl.getCabinCrew("CB888852");
        assertEquals("CB888852", cb.getCbName());

    }

    @Test
    public void test19GetCabinCrew_NotFound() {
        System.out.println("test19GetCabinCrew_NotFound");
        CabinCrew cb = new CabinCrew();
        cb = mabl.getCabinCrew("CB888851");
        assertNull(cb);

    }

    @Test
    public void test20GetCockpitCrew() {
        System.out.println("test20GetCockpitCrew");
        CockpitCrew cp = new CockpitCrew();
        cp = mabl.getCockpitCrew("CP777752");
        assertEquals("CP777752", cp.getCpName());
    }

    @Test
    public void test21GetCockpitCrew_NotFound() {
        System.out.println("test21GetCockpitCrew_NotFound");
        CockpitCrew cp = new CockpitCrew();
        cp = mabl.getCockpitCrew("CP777751");
        assertNull(cp);
    }

    @Test
    public void test22GetLockedOutStatus() {
        System.out.println("test22GetLockedOutStatus");
        int result;
        result=mabl.getLockedOutStatus("O777777","officeStaff");
        assertEquals(0,result);

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
