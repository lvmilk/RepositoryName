/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.GDS;

import Entity.AIS.CabinClass;
import Entity.CommonInfa.AirAlliances;
import Entity.GDS.Airline;
import Entity.GDS.GDSFlight;
import Entity.GDS.GDSSeat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
@Stateless
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
    @WebMethod(operationName = "searchFlight")
    public boolean searchFlight(@WebParam(name = "origin") String origin, @WebParam(name = "dest") String dest,@WebParam(name = "departDate") Date departDate) {
        //TODO write your implementation code here:
        return false;
    }

    /**
     * Web service operation
     */
//    @WebMethod(operationName = "findResultInstanceList")
//    public ArrayList<ArrayList<GDSFlight>> findResultInstanceList(@WebParam(name = "origin") String origin, @WebParam(name = "dest") String dest, @WebParam(name = "departDate") Date departDate, @WebParam(name = "selectedCabin") CabinClass selectedCabin, @WebParam(name = "countPerson") int countPerson, @WebParam(name = "manageStatus") String manageStatus, @WebParam(name = "bookedFlights") List<GDSFlight> bookedFlights) {
//         System.out.println("#########################3getting into session bean findResultInstanceList() ");
//        
//        ArrayList<ArrayList<GDSFlight>> resultByDay = new ArrayList<>();
//        ArrayList<GDSFlight> resultOptionTrue = new ArrayList<>();
//        ArrayList<GDSFlight> resultOptionFalse = new ArrayList<>();
//        ArrayList<ArrayList<GDSFlight>> tempUncomplete = new ArrayList<>();
//        ArrayList<ArrayList<GDSFlight>> tempComplete = new ArrayList<>();
//        List<GDSFlight> gdsFlightList = getAllGDSFlights();
//
//        Calendar c = Calendar.getInstance();
//        c.setTime(departDate);
//
//        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
//        String departString = s.format(departDate);
//
//        System.out.println("origin is " + origin);
//        System.out.println("dest is " + dest);
//        System.out.println("departDate is " + departDate);
//
//        for (int i = 0; i < 3; i++) {
//            if (i == 0) {
//                System.out.println("tempUncomplete is Empty");
//                for (int k = 0; k < gdsFlightList.size(); k++) {
//                    SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
//                   String depDate=df.format(gdsFlightList.get(k).getDepTime());
//                   String ArrDate=df.format(gdsFlightList.get(k).getArrTime());
//                 
//
//                    if (gdsFlightList.get(k).getDepAirport().equals(origin) && gdsFlightList.get(k).getArrAirport().equals(dest) && depDate.equals(departString)) {
//                        System.out.println("%%%%%%%%%%%%%%%getting into 1st step");
//                        if (this.whetherAvailable(gdsFlightList.get(k), selectedCabin, countPerson, manageStatus, bookedFlights)) {
//                            resultOptionTrue.add(gdsFlightList.get(k));
//                            System.out.println("flight " + gdsFlightList.get(k).getFlightNo() + " on date " + depDate+ " fulfills criteria");
//                            tempComplete.add(resultOptionTrue);
//                            resultOptionTrue = new ArrayList<>();
//                        }
//                    } else if (gdsFlightList.get(k).getDepAirport().equals(origin) && !(gdsFlightList.get(k).getArrAirport().equals(dest)) && depDate.equals(departString)) {
//                        System.out.println("%%%%%%%%%%%%%%%getting into 2nd step");
//                        if (this.whetherAvailable(gdsFlightList.get(k), selectedCabin, countPerson, manageStatus, bookedFlights)) {
//                         System.out.println("flight " + gdsFlightList.get(k).getFlightNo() + " on date " + depDate+ " fulfills intermediate criteria");
//                            resultOptionFalse.add(gdsFlightList.get(k));
//                            tempUncomplete.add(resultOptionFalse);
//                            resultOptionFalse = new ArrayList<>();
//                        }
//                    }
//                }
//
//            } else if (i > 0 && !(tempUncomplete.isEmpty())) {
//
//                System.out.println("size of tempUncomplete is " + tempUncomplete.size());
//
//                for (int f = 0; f < tempUncomplete.size(); f++) {
//                    c = Calendar.getInstance();
//                    c.setTime(tempUncomplete.get(f).get(tempUncomplete.get(f).size() - 1).getStandardArrTimeDateType());
//                    c.add(Calendar.HOUR, 24);
//                    Date maxLimit = c.getTime();
//
//                    for (int k = 0; k < gdsFlightList.size(); k++) {
//
//                        Boolean checkDuplicate = findDuplicateInstance(tempUncomplete.get(f), gdsFlightList.get(k));
//
//                        if (!checkDuplicate && !(gdsFlightList.get(k).getFlightFrequency().getRoute().getDest().getAirportName().equals(origin)) && !(tempUncomplete.get(f).get(tempUncomplete.get(f).size() - 1).getFlightFrequency().getRoute().getDest().getAirportName().equals(dest)) && gdsFlightList.get(k).getFlightFrequency().getRoute().getDest().getAirportName().equals(dest) && gdsFlightList.get(k).getFlightFrequency().getRoute().getOrigin().getAirportName().equals(tempUncomplete.get(f).get(tempUncomplete.get(f).size() - 1).getFlightFrequency().getRoute().getDest().getAirportName()) && gdsFlightList.get(k).getStandardDepTimeDateType().after(tempUncomplete.get(f).get(tempUncomplete.get(f).size() - 1).getStandardArrTimeDateType()) && gdsFlightList.get(k).getStandardDepTimeDateType().before(maxLimit)) {
//                            System.out.println("%%%%%%%%%%%%%%%getting into 3rd step");
//                            if (!tempUncomplete.get(f).contains(gdsFlightList.get(k)) && this.whetherAvailable(gdsFlightList.get(k), selectedCabin, countPerson, manageStatus, bookedFlights)) {
//                                System.out.println("Final leg is " + gdsFlightList.get(k));
//                                System.out.println("Last leg's destination is " + tempUncomplete.get(f).get(tempUncomplete.get(f).size() - 1).getFlightFrequency().getRoute().getDest().getAirportName());
//                                System.out.println("New leg's dest is " + dest);
//
//                                ArrayList<FlightInstance> temp = tempUncomplete.get(f);
//                                temp.add(gdsFlightList.get(k));
//                                tempComplete.add(temp);
//                                tempUncomplete.set(f, temp);
//                            }
////                            tempUncomplete.remove(temp2);
//                        } else if (i < 2 && this.whetherAvailable(gdsFlightList.get(k), selectedCabin, countPerson, manageStatus, bookedFlights) && !(tempUncomplete.get(f).get(tempUncomplete.get(f).size() - 1).getFlightFrequency().getRoute().getDest().getAirportName().equals(dest)) && !(gdsFlightList.get(k).getFlightFrequency().getRoute().getDest().getAirportName().equals(origin)) && !(tempUncomplete.get(f).contains(gdsFlightList.get(k))) && !(gdsFlightList.get(k).getFlightFrequency().getRoute().getDest().getAirportName().equals(dest)) && gdsFlightList.get(k).getFlightFrequency().getRoute().getOrigin().getAirportName().equals(tempUncomplete.get(f).get(tempUncomplete.get(f).size() - 1).getFlightFrequency().getRoute().getDest().getAirportName()) && gdsFlightList.get(k).getStandardDepTimeDateType().after(tempUncomplete.get(f).get(tempUncomplete.get(f).size() - 1).getStandardArrTimeDateType()) && gdsFlightList.get(k).getStandardDepTimeDateType().before(maxLimit)) {
//
//                            System.out.println("%%%%%%%%%%%%%%%getting into 4th step");
//                            ArrayList<FlightInstance> temp = tempUncomplete.get(f);
//                            temp.add(gdsFlightList.get(k));
//                            tempUncomplete.set(f, temp);
//                        }
//                    }
//                }
//            }
//        }
//
//        if (!tempComplete.isEmpty()) {
//            System.out.println("size of tempComplete is " + tempComplete.size());
//            for (int i = 0; i < tempComplete.size(); i++) {
//                System.out.println("size of flightlegs for option " + i + " is " + tempComplete.get(i).size());
//            }
//        }
//
//        return tempComplete;
//    }
//
//    /**
//     * Web service operation
//     */
//    @WebMethod(operationName = "getAllGDSFlights")
//    public List<GDSFlight> getAllGDSFlights() {
//         Query q1 = em.createQuery("SELECT fi FROM GDSFlight fi");
//        List<GDSFlight> flightInstList = q1.getResultList();
//        if (flightInstList.isEmpty()) {
//            System.out.println("flightInstList: No flight instance.");
//        } else {
//            //    System.out.println("flightInstList got");
//        }
//        return flightInstList;
//    }
//
//    /**
//     * Web service operation
//     */
//    @WebMethod(operationName = "whetherAvailable")
//    public String whetherAvailable(@WebParam(name = "thisFlight") GDSFlight thisFlight, @WebParam(name = "selectedCabin") CabinClass selectedCabin, @WebParam(name = "countPerson") int countPerson, @WebParam(name = "manageStatus") String manageStatus, @WebParam(name = "bookedFlights") List<GDSFlight> bookedFlights) {
//      boolean available = false;
//        int countAvailable = 0;
//        if(thisFlight.getCabinName().equals(selectedCabin.getCabinName()) && thisFlight.getAvailableSeat()>=countPerson){
//                 
//             
//            
//            
//            
//        }
//    }

    

}
