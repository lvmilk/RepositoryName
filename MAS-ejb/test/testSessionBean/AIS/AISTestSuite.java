/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testSessionBean.AIS;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author LI HAO
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({testSessionBean.AIS.BookingClassBeanRemoteTest.class, testSessionBean.AIS.SeatPlanBeanRemoteTest.class,
    testSessionBean.AIS.SeatAssignBeanRemoteTest.class, testSessionBean.AIS.ViewBookingClassPriceBeanRemoteTest.class, testSessionBean.AIS.AssignPriceBeanRemoteTest.class, 
    testSessionBean.AIS.ModifyPriceBeanRemoteTest.class})
public class AISTestSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

}
