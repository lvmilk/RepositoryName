/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testSessionBean.CommonInfra;

import Entity.CommonInfa.MsgReceiver;
import Entity.CommonInfa.MsgSender;
import Entity.CommonInfa.UserEntity;
import SessionBean.CommonInfra.ManageAccountBeanRemote;
import SessionBean.CommonInfra.MsgBeanRemote;
import java.util.ArrayList;
import java.util.Collection;
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

    MsgBeanRemote mbr = lookupMsgBeanRemote();

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
        List<UserEntity> rList = new ArrayList<UserEntity>();
        rList = mbr.getAllUsers();
        assertFalse(rList.isEmpty());
    }

    @Test
    public void test02ViewSendMessage() throws Exception {
        System.out.println("test02ViewSendMessage");
        Collection<MsgSender> senderList = new ArrayList<MsgSender>();
        senderList = mbr.viewSendMessage("O777777");
        assertTrue(senderList.isEmpty());
    }

    @Test(expected = Exception.class)
    public void test03ViewSendMessage_NoSuchUser() throws Exception {
        System.out.println("test03ViewSendMessage_NoSuchUser");
        Collection<MsgSender> senderList = new ArrayList<MsgSender>();
        senderList = mbr.viewSendMessage("O999999");

    }

    @Test
    public void test04tViewReceiveMessage() {
        System.out.println("test04ViewSendMessage");
        Collection<MsgReceiver> rcvList = new ArrayList<MsgReceiver>();
        rcvList = mbr.viewReceiveMessage("O777777");
        assertTrue(rcvList.isEmpty());

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
