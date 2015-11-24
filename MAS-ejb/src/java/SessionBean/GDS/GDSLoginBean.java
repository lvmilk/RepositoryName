/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.GDS;

import Entity.AIS.CabinClass;
import Entity.AIS.FlightCabin;
import Entity.APS.FlightInstance;
import Entity.CommonInfa.AirAlliances;
import Entity.AAS.Revenue;
import Entity.AAS.Expense;
import Entity.GDS.GDSFlight;
import Entity.GDS.GDSSeat;
import Entity.GDS.GDSBooker;
import Entity.GDS.GDSPassenger;
import Entity.GDS.GDSPayment;
import Entity.GDS.GDSReservation;
import Entity.GDS.GDSTicket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import javax.jws.Oneway;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
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
    private Revenue revenue;
    private Expense expense;

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
    public boolean searchFlight(@WebParam(name = "origin") String origin, @WebParam(name = "dest") String dest, @WebParam(name = "departDate") Date departDate) {
        //TODO write your implementation code here:
        return false;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "findResultInstanceList")
    public List<GDSFlight> findResultInstanceList(@WebParam(name = "origin") String origin, @WebParam(name = "dest") String dest, @WebParam(name = "departDate") Date departDate, @WebParam(name = "selectedCabin") String cabinName, @WebParam(name = "countPerson") int countPerson) {
        System.out.println("#########################3getting into session bean findResultInstanceList() ");

        ArrayList<ArrayList<GDSFlight>> resultByDay = new ArrayList<>();
        List<GDSFlight> resultOptionTrue = new ArrayList<>();
        List<GDSFlight> resultOptionFalse = new ArrayList<>();
        List<GDSFlight> tempUncomplete = new ArrayList<>();
        List<GDSFlight> tempComplete = new ArrayList<>();
        List<GDSFlight> gdsFlightList = getAllGDSFlights();

        Calendar c = Calendar.getInstance();
        c.setTime(departDate);

        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        String departString = s.format(departDate);

        System.out.println("origin is " + origin);
        System.out.println("dest is " + dest);
        System.out.println("departDate is " + departDate);

        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                System.out.println("tempUncomplete is Empty");
                for (int k = 0; k < gdsFlightList.size(); k++) {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    String depDate = df.format(gdsFlightList.get(k).getDepTime());
                    String ArrDate = df.format(gdsFlightList.get(k).getArrTime());

                    if (gdsFlightList.get(k).getDepAirport().equals(origin) && gdsFlightList.get(k).getArrAirport().equals(dest) && depDate.equals(departString)) {
                        System.out.println("%%%%%%%%%%%%%%%getting into 1st step");
                        if (this.whetherAvailable(gdsFlightList.get(k), cabinName, countPerson)) {
                            resultOptionTrue.add(gdsFlightList.get(k));
                            System.out.println("flight " + gdsFlightList.get(k).getFlightNo() + " on date " + depDate + " fulfills criteria");
                            tempComplete = resultOptionTrue;
                            break;
                        }
                    } else if (gdsFlightList.get(k).getDepAirport().equals(origin) && !(gdsFlightList.get(k).getArrAirport().equals(dest)) && depDate.equals(departString)) {
                        System.out.println("%%%%%%%%%%%%%%%getting into 2nd step");
                        if (this.whetherAvailable(gdsFlightList.get(k), cabinName, countPerson)) {
                            System.out.println("flight " + gdsFlightList.get(k).getFlightNo() + " on date " + depDate + " fulfills intermediate criteria");
                            resultOptionFalse.add(gdsFlightList.get(k));
                            tempUncomplete = resultOptionFalse;

                        }
                    }
                }

            } else if (i > 0 && !(tempUncomplete.isEmpty())) {

                System.out.println("size of tempUncomplete is " + tempUncomplete.size());

                c = Calendar.getInstance();
                c.setTime(tempUncomplete.get(0).getArrTime());
                c.add(Calendar.HOUR, 24);
                Date maxLimit = c.getTime();

                System.out.println(" maxLimit is " + maxLimit);

                for (int k = 0; k < gdsFlightList.size(); k++) {

                    Boolean checkDuplicate = findDuplicateInstance(tempUncomplete, gdsFlightList.get(k));

                    if (gdsFlightList.get(k).getDepTime().after(tempUncomplete.get(tempUncomplete.size() - 1).getArrTime()) && gdsFlightList.get(k).getDepTime().before(maxLimit)) {
                        System.out.println(" for flightNo " + gdsFlightList.get(k).getFlightNo() + " time constraint is fullfilled for second leg and checkduplicate is " + checkDuplicate);

                    }

                    if (!checkDuplicate && !(gdsFlightList.get(k).getArrAirport().equals(origin)) && !(tempUncomplete.get(tempUncomplete.size() - 1).getArrAirport().equals(dest)) && gdsFlightList.get(k).getArrAirport().equals(dest) && gdsFlightList.get(k).getDepAirport().equals(tempUncomplete.get(tempUncomplete.size() - 1).getArrAirport()) && gdsFlightList.get(k).getDepTime().after(tempUncomplete.get(tempUncomplete.size() - 1).getArrTime()) && gdsFlightList.get(k).getDepTime().before(maxLimit)) {
                        System.out.println("%%%%%%%%%%%%%%%getting into 3rd step");
                        if (!tempUncomplete.contains(gdsFlightList.get(k)) && this.whetherAvailable(gdsFlightList.get(k), cabinName, countPerson)) {
                            System.out.println("Final leg is " + gdsFlightList.get(k));
                            System.out.println("Last leg's destination is " + tempUncomplete.get(0).getArrAirport());
                            System.out.println("New leg's dest is " + dest);

                            List<GDSFlight> temp = tempUncomplete;
                            temp.add(gdsFlightList.get(k));
                            tempComplete = temp;
                            tempUncomplete = temp;
                        }

                    }

                }

            }
        }

        if (!tempComplete.isEmpty()) {
            System.out.println("size of tempComplete is " + tempComplete.size());
            for (int i = 0; i < tempComplete.size(); i++) {
                System.out.println("size of flightlegs for option " + i + " is " + tempComplete.size());
            }
        }

        return tempComplete;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getAllGDSFlights")
    public List<GDSFlight> getAllGDSFlights() {
        Query q1 = em.createQuery("SELECT fi FROM GDSFlight fi");
        List<GDSFlight> flightInstList = q1.getResultList();
        if (flightInstList.isEmpty()) {
            System.out.println("flightInstList: No flight instance.");
        } else {
            //    System.out.println("flightInstList got");
        }
        return flightInstList;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "whetherAvailable")
    @RequestWrapper(className = "SessionBean.whetherAvailable")
    @ResponseWrapper(className = "SessionBean.whetherAvailableResponse")
    public boolean whetherAvailable(@WebParam(name = "flight") GDSFlight flight, @WebParam(name = "cabin") String cabin, @WebParam(name = "countPerson") int countPerson) {
        boolean available = false;
        int countAvailable = 0;
        FlightCabin fCabin;
        System.out.println("flightinstance is " + flight);
        if (flight.getCabinName() != null) {
            System.out.println("~~~~~~~~~getflightcabin size is " + flight.getSeatQuota());

            if (flight.getCabinName().equals(cabin)) {

                if (flight.getAvailableSeat() != null) {
                    countAvailable = flight.getAvailableSeat();
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~for GDSFlight " + flight);

                }
                if (countAvailable >= countPerson) {
                    available = true;

                }
            }
        }

        return available;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "findDuplicateInstance")
    public boolean findDuplicateInstance(@WebParam(name = "tempList") List<GDSFlight> tempList, @WebParam(name = "flight") GDSFlight flight) {
        if (tempList.contains(flight)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getAllOrigins")
    public List<String> getAllOrigins() {
        List<String> allOrigin = new ArrayList<>();

        Query query = em.createQuery("SELECT f FROM GDSFlight f");
        List<GDSFlight> flightList = query.getResultList();
        for (int i = 0; i < flightList.size(); i++) {
            if (!allOrigin.contains(flightList.get(i).getDepAirport())) {
                allOrigin.add(flightList.get(i).getDepAirport());
            }
        }

        return allOrigin;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getRestCitys")
    public List<String> getRestCitys(@WebParam(name = "selectedOrigin") String selectedOrigin) {
        List<String> allDest = new ArrayList<>();

        Query query = em.createQuery("SELECT f FROM GDSFlight f");
        List<GDSFlight> flightList = query.getResultList();
        for (int i = 0; i < flightList.size(); i++) {
            if (!allDest.contains(flightList.get(i).getArrAirport()) && !flightList.get(i).getArrAirport().equals(selectedOrigin)) {
                allDest.add(flightList.get(i).getArrAirport());
            }
            if (!allDest.contains(flightList.get(i).getDepAirport()) && !flightList.get(i).getDepAirport().equals(selectedOrigin)) {
                allDest.add(flightList.get(i).getDepAirport());
            }
        }

        return allDest;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "createTempGdsBooker")
    public GDSBooker createTempGdsBooker(@WebParam(name = "title") String title, @WebParam(name = "firstName") String firstName, @WebParam(name = "lastName") String lastName, @WebParam(name = "address") String address, @WebParam(name = "email") String email, @WebParam(name = "contactNo") String contactNo) {
        GDSBooker booker = new GDSBooker();

        booker.createMember(title, firstName, lastName, email, address, contactNo);

        return booker;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "makeReservation")
    public Long makeReservation(@WebParam(name = "booker") GDSBooker booker, @WebParam(name = "passengerList") List<GDSPassenger> passengerList, @WebParam(name = "departSelected") List<GDSFlight> departSelected, @WebParam(name = "returnSelected") List<GDSFlight> returnSelected, @WebParam(name = "psgCount") Integer psgCount, @WebParam(name = "origin") String origin, @WebParam(name = "dest") String dest, @WebParam(name = "returnTrip") Boolean returnTrip, @WebParam(name = "bkSystem") String bkSystem, @WebParam(name = "totalPrice") Double totalPrice, @WebParam(name = "companyName") String companyName) {
        GDSBooker tempBk;
        String bookerEmail = booker.getEmail();
        Query query = em.createQuery("SELECT b FROM GDSBooker b WHERE b.email=:bookerEmail").setParameter("bookerEmail", bookerEmail);
        if (query.getResultList().isEmpty()) {
            em.persist(booker);
            System.out.println("booker email is " + booker.getEmail());
        } else {
            booker = (GDSBooker) query.getResultList().get(0);
        }

        GDSReservation rsv = new GDSReservation();
        rsv.createReservation(booker.getFirstName(), booker.getLastName(), booker.getEmail(), origin, dest, returnTrip, bkSystem);
        rsv = makeRsvBooker(rsv, booker);

        rsv = makeRsv_GDSFlight(rsv, departSelected, returnSelected, psgCount);

        ArrayList<GDSTicket> tickets = new ArrayList<>();

        createPsgList(passengerList);

        tickets = setupPsg_Ticket(departSelected, returnSelected, passengerList, booker, psgCount, origin, dest, returnTrip, bkSystem);

        setupTicket_Reservation(rsv, tickets);

        GDSPayment payment = makeRsvPayment(rsv, psgCount, totalPrice);

        return rsv.getAirlineRsvCode();

    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "makeRsvBooker")
    public GDSReservation makeRsvBooker(@WebParam(name = "rsv") GDSReservation gdsRsv, @WebParam(name = "booker") GDSBooker booker) {
        em.persist(gdsRsv);
        GDSReservation rsv = em.find(GDSReservation.class, gdsRsv.getAirlineRsvCode());
//        em.refresh(rsv);
//        rsv=em.find(GDSReservation.class, rsv.getAirlineRsvCode());
//        em.refresh(rsv);

        booker = em.find(GDSBooker.class, booker.getId());
        rsv.setGdsBooker(booker);

//        List<Reservation> rsvList = new ArrayList<>();
//
//        booker.setRsvList(rsvList);
        booker.getRsvList().add(rsv);
//        em.merge(booker);
        em.flush();
//        rsv = em.find(GDSReservation.class, rsv.getAirlineRsvCode());
//
//        em.flush();

        System.out.println("rsv found in makeRsvBooker is " + rsv);
        return rsv;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "createPsgList")
    public List<GDSPassenger> createPsgList(@WebParam(name = "passengerList") List<GDSPassenger> passengerList) {
        for (int i = 0; i < passengerList.size(); i++) {
            GDSPassenger psg = passengerList.get(i);
            em.persist(psg);
            passengerList.set(i, em.find(GDSPassenger.class, psg.getId()));

        }
        em.flush();

        System.out.println("LEAVING createPsgList");

        return passengerList;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "makeRsv_GDSFlight")
    public GDSReservation makeRsv_GDSFlight(@WebParam(name = "rsv") GDSReservation rsv, @WebParam(name = "departFlight") List<GDSFlight> departFlight, @WebParam(name = "returnFlight") List<GDSFlight> returnFlight, @WebParam(name = "psgCount") Integer psgCount) {
        rsv = em.find(GDSReservation.class, rsv.getAirlineRsvCode());
        em.refresh(rsv);
        GDSFlight instance = new GDSFlight();
        if (rsv != null) {
            for (int i = 0; i < departFlight.size(); i++) {
                instance = em.find(GDSFlight.class, departFlight.get(i).getId());
                em.refresh(instance);
                instance.setBookedSeat(instance.getBookedSeat() + psgCount);
                instance.setAvailableSeat(instance.getAvailableSeat() - psgCount);

                instance.getRsv().add(rsv);
                em.merge(instance);
                rsv.getGdsFlightList().add(instance);
                System.out.println("in depart loop, i is " + i + ", rsv.gdsList is " + rsv.getGdsFlightList());

            }

            for (int i = 0; i < returnFlight.size(); i++) {
                instance = em.find(GDSFlight.class, returnFlight.get(i).getId());
                em.refresh(instance);
                instance.setBookedSeat(instance.getBookedSeat() + psgCount);
                instance.setAvailableSeat(instance.getAvailableSeat() - psgCount);

                instance.getRsv().add(rsv);
                em.merge(instance);
                rsv.getGdsFlightList().add(instance);
                System.out.println("in return loop, i is " + i + ", rsv.gdsList is " + rsv.getGdsFlightList());

            }

            em.merge(rsv);
            em.flush();

            for (int i = 0; i < rsv.getGdsFlightList().size(); i++) {
                System.out.println("i=" + i + " flightInstance is " + rsv.getGdsFlightList().get(i).getFlightNo());

            }

        }

        em.flush();

        System.out.println("LEAVING makeRsvBookInstance");

        return rsv;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "setupPsg_Ticket")
    public ArrayList<GDSTicket> setupPsg_Ticket(@WebParam(name = "departFlights") List<GDSFlight> departFlights, @WebParam(name = "returnFlights") List<GDSFlight> returnFlights, @WebParam(name = "passengerList") List<GDSPassenger> passengerList, @WebParam(name = "booker") GDSBooker booker, @WebParam(name = "psgCount") Integer psgCount, @WebParam(name = "origin") String origin, @WebParam(name = "dest") String dest, @WebParam(name = "returnTrip") Boolean returnTrip, @WebParam(name = "bkSystem") String bkSystem) {
        GDSTicket depTicket;
        GDSTicket arrTicket;
        ArrayList<GDSTicket> tkList = new ArrayList<GDSTicket>();
        Date temp;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String depAirport;
        String arrAirport;
        String depTime;
        String arrTime;
        String flightNo;
        GDSPassenger psg;

        System.out.println("^^^^^^^^^^^^^^^Size of passengerList is: " + passengerList.size());

        for (int i = 0; i < departFlights.size(); i++) {
//            depTicket=new Ticket();
            depAirport = departFlights.get(i).getDepAirport();
            arrAirport = departFlights.get(i).getArrAirport();
            temp = departFlights.get(i).getDepTime();
            depTime = df.format(temp);

            temp = departFlights.get(i).getArrTime();
            arrTime = df.format(temp);
            flightNo = departFlights.get(i).getFlightNo();

            for (int j = 0; j < passengerList.size(); j++) {
                depTicket = new GDSTicket();
                depTicket.createTicket(depAirport, arrAirport, depTime, arrTime, flightNo, bkSystem);
                psg = passengerList.get(j);
                System.out.println("*************Passenger Id is :" + psg.getId());
                GDSPassenger thisPsg = em.find(GDSPassenger.class, psg.getId());
                System.out.println("in depart loop: before add ticket, all tickets in psg are " + thisPsg.getTickets());

                if (thisPsg != null) {
                    em.refresh(thisPsg);
                    depTicket.setPassenger(thisPsg);
                    em.persist(depTicket);

                    List<GDSTicket> tkt = thisPsg.getTickets();
                    System.out.println("tkt in thisPsg before adding " + thisPsg.getTickets());
                    tkt.add(depTicket);
                    thisPsg.setTickets(tkt);
                    System.out.println("tkt in thisPsg after adding " + tkt);
//                    em.merge(thisPsg);
                    System.out.println("tkt in thisPsg after merge " + tkt);

                    tkList.add(depTicket);
//                    passengerList.set(j, em.find(Passenger.class, thisPsg.getId()));

                    System.out.println("depTicket is " + depTicket.getTicketID());
                    System.out.println("in depart loop: after add ticket, all tickets in psg are " + thisPsg.getTickets());

                    em.flush();

                }
            }

        }
        em.flush();

        for (int i = 0; i < returnFlights.size(); i++) {
//            arrTicket=new Ticket();
            depAirport = returnFlights.get(i).getDepAirport();
            arrAirport = returnFlights.get(i).getArrAirport();
            temp = returnFlights.get(i).getDepTime();
            depTime = df.format(temp);
            temp = returnFlights.get(i).getArrTime();
            arrTime = df.format(temp);
            flightNo = returnFlights.get(i).getFlightNo();

            for (int j = 0; j < passengerList.size(); j++) {
                arrTicket = new GDSTicket();
                arrTicket.createTicket(depAirport, arrAirport, depTime, arrTime, flightNo, bkSystem);
                psg = passengerList.get(j);
                GDSPassenger thisPsg = em.find(GDSPassenger.class, psg.getId());
                if (thisPsg != null) {
                    arrTicket.setPassenger(thisPsg);
                    em.persist(arrTicket);

//                    thisPsg.getTickets().add(arrTicket);
//                    em.merge(thisPsg);
                    List<GDSTicket> tkt = thisPsg.getTickets();
                    tkt.add(arrTicket);
                    thisPsg.setTickets(tkt);
                    em.merge(thisPsg);

                    tkList.add(arrTicket);
//                    passengerList.set(j, em.find(Passenger.class, thisPsg.getId()));

                    System.out.println("arrTicket is " + arrTicket.getTicketID());
                    System.out.println("all tickets in psg are " + thisPsg.getTickets());
                }
            }

        }

        em.flush();
        System.out.println("in setUpPsg_tickets: passenger 1 has tickets:　" + passengerList.get(0).getTickets());
        System.out.println("LEAVING setupPsg_Ticket");

        return tkList;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "setupTicket_Reservation")
    @Oneway
    public void setupTicket_Reservation(@WebParam(name = "rsv") GDSReservation rsv, @WebParam(name = "tickets") ArrayList<GDSTicket> tickets) {
        rsv = em.find(GDSReservation.class, rsv.getAirlineRsvCode());
//        rsv.setTickets(tickets);
//        em.merge(rsv);
        System.out.println("in ricketRsv(): rsv found is " + rsv);

//        List<Ticket> ticketList = new ArrayList<>();
//        List<Ticket> t1 = rsv.getTickets();
//        rsv.setTickets(ticketList);
        for (int i = 0; i < tickets.size(); i++) {
            System.out.println("@@@@@@This is in setupTicket_Reservation:" + tickets.get(i));
            GDSTicket ticket = em.find(GDSTicket.class, tickets.get(i).getTicketID());
            ticket.setRsv(rsv);
//            em.merge(ticket);

            rsv.getTickets().add(ticket);

        }

//        em.merge(rsv);
        em.flush();
        System.out.println("After setting ticket list");

    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "makeRsvPayment")
    public GDSPayment makeRsvPayment(@WebParam(name = "rsv") GDSReservation rsv, @WebParam(name = "psgCount") Integer psgCount, @WebParam(name = "totalPrice") Double totalPrice) {

        for (int i = 0; i < rsv.getGdsFlightList().size(); i++) {
            totalPrice += rsv.getGdsFlightList().get(i).getPrice();
        }
        totalPrice *= psgCount;
        GDSPayment payment = new GDSPayment();
        payment.createPayment(totalPrice);
        rsv = em.find(GDSReservation.class, rsv.getAirlineRsvCode());
        payment.setReservation(rsv);
        rsv.setPayment(payment);
        em.persist(payment);
        em.merge(rsv);

        /////////////////////
        String channel = "GDS";
        for (int j = 0; j < rsv.getGdsFlightList().size(); j++) {
            revenue = new Revenue();
            String name = rsv.getGdsFlightList().get(j).getCompanyName();
            revenue.setPayer(name);
            revenue.setChannel(channel);
            if (name.equals("MAS")) {
                revenue.setType("Ticket Sale");
                revenue.setReceivable(totalPrice);
            } else {
                revenue.setType("Commission");
                revenue.setReceivable(0.1 * totalPrice);
            }
            revenue.setPaymentDate(new Date());
            revenue.setRefund(0.0);
            em.persist(revenue);
            em.flush();
        }
        return payment;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getFlightList")
    public List<GDSFlight> getFlightList(@WebParam(name = "rsv") GDSReservation rsv, @WebParam(name = "origin") String origin, @WebParam(name = "dest") String dest, @WebParam(name = "index") Integer number) {
        List<GDSFlight> newList = new ArrayList<>();
        rsv=em.find(GDSReservation.class, rsv.getAirlineRsvCode());
        List<GDSFlight> resultList = rsv.getGdsFlightList();
        System.out.println(" resultList of gdsFlight is " + resultList);
        Collections.sort(resultList);
        System.out.println(" resultList of gdsFlight is " + resultList);
        for (int i = number; i < resultList.size(); i++) {
            if (resultList.get(i).getArrAirport().equals(dest)) {
                System.out.println("flight fulfills requirement is " + resultList.get(i));
                newList.add(resultList.get(i));
                break;
            } else {
                newList.add(resultList.get(i));
            }
        }
        return newList;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getPassengerList")
    public List<GDSPassenger> getPassengerList(@WebParam(name = "rsv") GDSReservation rsv) {
        rsv=em.find(GDSReservation.class, rsv.getAirlineRsvCode());
        List<GDSTicket> tickets = rsv.getTickets();
        List<GDSPassenger> psgList = new ArrayList<>();
        for (int i = 0; i < tickets.size(); i++) {
            if (!psgList.contains(tickets.get(i).getPassenger())) {
                psgList.add(tickets.get(i).getPassenger());
            }
        }

        List<GDSPassenger> psgs = new ArrayList<>();
        for (int i = 0; i < psgList.size(); i++) {
            psgs.add(psgList.get(i));
        }
        return psgs;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getGdsReservationList")
    public List<GDSReservation> getGdsReservationList(@WebParam(name = "companyName") String companyName) {
        List<GDSReservation> rsvList = new ArrayList<>();
        Query query = em.createQuery("SELECT g FROM GDSFlight g WHERE g.companyName=:companyName ").setParameter("companyName", companyName);
        List<GDSFlight> resultList = query.getResultList();

        if (!resultList.isEmpty()) {

            System.out.println("size of flight list is System " + resultList);
            for (GDSFlight fl : resultList) {
                if (fl.getRsv() != null && !fl.getRsv().isEmpty()) {
                    List<GDSReservation> temp = fl.getRsv();
                    System.out.println("found reservation list is " + temp);
                    for (GDSReservation r : temp) {
                        System.out.println("individual rsv is " + r);
                        rsvList.add(r);
                    }
                }
            }
        }
        System.out.println("result rsvList is " + rsvList);
        return rsvList;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getRsvFromDB")
    public GDSReservation getRsvFromDB(@WebParam(name = "rsv") GDSReservation rsv) {
        GDSReservation res=em.find(GDSReservation.class, rsv.getAirlineRsvCode());
        return res;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getBookerFromRsv")
    public GDSBooker getBookerFromRsv(@WebParam(name = "rsv") GDSReservation rsv) {
       GDSReservation res=em.find(GDSReservation.class, rsv.getAirlineRsvCode());
        GDSBooker booker =res.getGdsBooker();
        return booker;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getPaymentFromRsv")
    public GDSPayment getPaymentFromRsv(@WebParam(name = "rsv") GDSReservation rsv) {
        GDSReservation res=em.find(GDSReservation.class, rsv.getAirlineRsvCode());
        GDSPayment payment =res.getPayment();
        return payment;
    }

    /**
     * Web service operation
     */
}
