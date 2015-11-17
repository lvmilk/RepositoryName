/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.GDS;

import Entity.CommonInfa.AirAlliances;
import Entity.GDS.Airline;
import Entity.GDS.GDSFlight;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.CryptoHelper;

/**
 *
 * @author LI HAO
 */
@WebService(serviceName = "GDSLoginBean")
@Stateless()
public class GDSLoginBean {

    @PersistenceContext
    EntityManager em;

    private String hPwd = new String();
    private CryptoHelper cryptoHelper = CryptoHelper.getInstanceOf();

    /**
     * Web service operation
     */
    @WebMethod(operationName = "login")
    public boolean login(@WebParam(name = "gdsUserId") String gdsUserId, @WebParam(name = "gdsPwd") String gdsPwd) {
        //TODO write your implementation code here:
        Query query = null;

        hPwd = this.encrypt(gdsUserId, gdsPwd);
        System.out.println("validatelogin:" + hPwd);
        System.out.println("validatelogin:" + gdsPwd);

        query = em.createQuery("SELECT u FROM AirAlliances u WHERE u.allianceID = :inUserName and u.allPwd= :inPassWord ");
        query.setParameter("inPassWord", hPwd);
        query.setParameter("inUserName", gdsUserId);
        List resultList = new ArrayList<AirAlliances>();
        resultList = (List) query.getResultList();
        if (resultList.isEmpty()) {
            return false;

        } else {
            return true;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "retrieveAccInfo")
    public AirAlliances retrieveAccInfo(@WebParam(name = "gdsUserId") String gdsUserId) throws Exception {
        Query query = null;

        query = em.createQuery("SELECT u FROM AirAlliances u WHERE u.allianceID = :inUserName ");
        query.setParameter("inUserName", gdsUserId);

        AirAlliances al = new AirAlliances();
        al = (AirAlliances) query.getSingleResult();

        return al;

    }

    private String encrypt(String username, String password) {
        String temp;
        if (!username.isEmpty() && !password.isEmpty()) {
            System.out.println("*****The original password for " + username + " is " + password + "*****");
            temp = cryptoHelper.doMD5Hashing(username + password);
            return temp;
        }
        return password;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "publishFlight")
    public boolean publishFlight(@WebParam(name = "flightNo") String flightNo, @WebParam(name = "flightDate") Date flightDate, @WebParam(name = "depTime") Date depTime, @WebParam(name = "arrTime") Date arrTime, @WebParam(name = "depAirport") String depAirport, @WebParam(name = "arrAirport") String arrAirport, @WebParam(name = "depIATA") String depIATA, @WebParam(name = "arrIATA") String arrIATA, @WebParam(name = "seatQuota") Integer seatQuota) {
        //TODO write your implementation code here:
        GDSFlight gdsFlight=new GDSFlight();
        Airline al=new Airline();
        
        gdsFlight.createGDSFlight(flightNo, flightDate, depTime, arrTime, depAirport, arrAirport, depIATA, arrIATA, seatQuota);
        al.getFlightInstances().add(gdsFlight);
        
        em.persist(gdsFlight);
        
        return false;
    }


}
