/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.GDS;

import Entity.CommonInfa.AirAlliances;
import Entity.GDS.Airline;
import Entity.GDS.GDSFlight;
import Entity.GDS.GDSSeat;
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
    public boolean publishFlight(@WebParam(name = "flightNo") String flightNo, @WebParam(name = "depTime") Date depTime, @WebParam(name = "arrTime") Date arrTime, @WebParam(name = "depAirport") String depAirport, @WebParam(name = "arrAirport") String arrAirport, @WebParam(name = "depIATA") String depIATA, @WebParam(name = "arrIATA") String arrIATA, @WebParam(name = "seatQuota") Integer seatQuota, @WebParam(name = "companyName") String companyName, @WebParam(name = "cabinName") String cabinName, @WebParam(name = "price") Double price, @WebParam(name = "rowStart") Integer rowStart, @WebParam(name = "rowEnd") Integer rowEnd, @WebParam(name = "columnStart") char columnStart, @WebParam(name = "columnEnd") char columnEnd) {
        //TODO write your implementation code here:
        GDSFlight gdsFlight = new GDSFlight();
        gdsFlight.createGDSFlight(flightNo, depTime, arrTime, depAirport, arrAirport, depIATA, arrIATA, seatQuota, companyName, cabinName, price);
//            al.getFlightInstances().add(gdsFlight);

        Integer i;
        char j;
        String seatNo;
        String status = "available";

        for (i = rowStart; i <= rowEnd; i++) {
            for (j = columnStart; j <= columnEnd; j++) {
                GDSSeat gdsSeat = new GDSSeat();
                seatNo = i.toString() + j;
                System.out.println("********GDSSessionBean: seatNo" + seatNo);
                gdsSeat.createSeat(seatNo, i, j, status, cabinName);
                gdsSeat.setFlight(gdsFlight);
                gdsFlight.getSeats().add(gdsSeat);

            }
        }

        em.persist(gdsFlight);
        em.flush();

        return true;

    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "viewReleasedFlight")
    public List<GDSFlight> viewReleasedFlight(@WebParam(name = "companyName") String companyName) {
        //TODO write your implementation code here:
        Query query = em.createQuery("SELECT r FROM GDSFlight r WHERE r.companyName=:inCompanyName");
        query.setParameter("inCompanyName", companyName);
;
        List<GDSFlight> resultList = query.getResultList();
        if (!resultList.isEmpty()) {
            return resultList;
        } else {
            resultList = new ArrayList<>();
            return resultList;
        }
    }

}
