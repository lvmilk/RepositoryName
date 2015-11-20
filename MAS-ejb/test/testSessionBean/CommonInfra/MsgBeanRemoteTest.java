/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testSessionBean.CommonInfra;

import Entity.CommonInfa.UserEntity;
import SessionBean.CommonInfra.ManageAccountBeanRemote;
import SessionBean.CommonInfra.MsgBeanRemote;
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
public class MsgBeanRemoteTest {

    MsgBeanRemote mbr=lookupMsgBeanRemote();
    public MsgBeanRemoteTest() {
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
    public void test01GetAllUsers() {
        System.out.println("test01GetAllUsers");
        List<UserEntity> rList=new ArrayList<UserEntity>();
        rList=mbr.getAllUsers();
        assertFalse(rList.isEmpty());
    }

    private MsgBeanRemote lookupMsgBeanRemote() {
        try {
            Context c = new InitialContext();
            return (MsgBeanRemote) c.lookup("java:global/MAS/MAS-ejb/MsgBean!SessionBean.CommonInfra.MsgBeanRemote");

        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
