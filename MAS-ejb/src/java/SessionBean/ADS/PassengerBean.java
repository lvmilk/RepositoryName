/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.ADS;

import java.util.ArrayList;
import javax.ejb.Stateless;
import Entity.ADS.*;
import Entity.AIS.BookingClassInstance;
import Entity.APS.FlightInstance;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author LI HAO
 */
@Stateless
public class PassengerBean implements PassengerBeanLocal {

    @PersistenceContext
    private EntityManager em;

    private Passenger passenger = new Passenger();
    private Booker booker = new Booker();

    private Ticket ticket = new Ticket();
    private Passenger psg = new Passenger();

    private String depCity = null;
    private String arrCity = null;
    private String depTime = null;
    private String arrTime = null;
    private String flightNo = null;

    public Booker createTempBooker(String title, String firstName, String lastName, String address, String email, String contactNo) {
        booker = new Booker();

        booker.createMember(title, firstName, lastName, email, address, contactNo, false);

        return booker;

    }

    public List<Passenger> createPsgList(List<Passenger> passengerList) {
        for (int i = 0; i < passengerList.size(); i++) {
            Passenger psg = passengerList.get(i);
            em.persist(psg);
            passengerList.set(i, em.find(Passenger.class, psg.getId()));

        }
        em.flush();

        System.out.println("LEAVING createPsgList");

        return passengerList;
    }

    @Override
    public void makeReservation(Booker booker, ArrayList<Passenger> passengerList, ArrayList<FlightInstance> departSelected, ArrayList<FlightInstance> returnSelected, ArrayList<BookingClassInstance> BookClassInstanceList, Integer psgCount, String origin, String dest, Boolean returnTrip, String bkSystem, Double totalPrice, String action, String companyName) {
        Booker tempBk;
        String bookerEmail = booker.getEmail();
        Query query = em.createQuery("SELECT b FROM Booker b WHERE b.email=:bookerEmail").setParameter("bookerEmail", bookerEmail);
        if (query.getResultList().isEmpty()) {
            em.persist(booker);
        } else {
            booker = (Booker) query.getResultList().get(0);
        }

        Reservation rsv = new Reservation();
        rsv.createReservation(booker.getFirstName(), booker.getLastName(), booker.getEmail(), origin, dest, returnTrip,bkSystem,companyName);
        rsv = makeRsvBooker(rsv, booker);

        rsv = makeRsvBookInstance(rsv, BookClassInstanceList, psgCount);

        ArrayList<Ticket> tickets = new ArrayList<>();
        createPsgList(passengerList);

        tickets = setupPsg_Ticket(departSelected, returnSelected, passengerList, booker, BookClassInstanceList, psgCount, origin, dest, returnTrip, bkSystem);

        setupTicket_Reservation(rsv, tickets);

        setupTicket_BookInstance(BookClassInstanceList, tickets);

        Payment payment = makeRsvPayment(rsv, psgCount, totalPrice, action);

    }

    public void setupTicket_BookInstance(ArrayList<BookingClassInstance> BookClassInstanceList, ArrayList<Ticket> tickets) {
        for (int i = 0; i < BookClassInstanceList.size(); i++) {
            BookingClassInstance bcInstance = em.find(BookingClassInstance.class, BookClassInstanceList.get(i).getId());
            em.refresh(bcInstance);
            for (int j = 0; j < tickets.size(); j++) {
                if (tickets.get(j).getArrCity().equals(bcInstance.getFlightCabin().getFlightInstance().getFlightFrequency().getRoute().getDest().getCityName())
                        && tickets.get(j).getDepCity().equals(bcInstance.getFlightCabin().getFlightInstance().getFlightFrequency().getRoute().getOrigin().getCityName())) {
                    Ticket ticket = em.find(Ticket.class, tickets.get(j).getTicketID());
                    ticket.setBkInstance(bcInstance);
                    bcInstance.getTickets().add(ticket);
                }
                em.merge(bcInstance);
                em.flush();
            }

        }
        em.flush();
    }

    public Payment makeRsvPayment(Reservation rsv, Integer psgCount, Double totalPrice, String action) {

        if (action.equals("rebook")) {
            Payment payment = new Payment();
            payment.createPayment(totalPrice);
            rsv = em.find(Reservation.class, rsv.getId());
            payment.setReservation(rsv);
            rsv.setPayment(payment);
            em.persist(payment);
            em.merge(rsv);
            return payment;
        } else {
            for (int i = 0; i < rsv.getBkcInstance().size(); i++) {
                totalPrice += rsv.getBkcInstance().get(i).getPrice();
            }
            totalPrice *= psgCount;
            Payment payment = new Payment();
            payment.createPayment(totalPrice);
            rsv = em.find(Reservation.class, rsv.getId());
            payment.setReservation(rsv);
            rsv.setPayment(payment);
            em.persist(payment);
            em.merge(rsv);
            return payment;
        }
    }

    public void setupTicket_Reservation(Reservation rsv, ArrayList<Ticket> tickets) {

        rsv = em.find(Reservation.class, rsv.getId());
//        rsv.setTickets(tickets);
//        em.merge(rsv);
        System.out.println("in ricketRsv(): rsv found is " + rsv);

//        List<Ticket> ticketList = new ArrayList<>();
//        List<Ticket> t1 = rsv.getTickets();
//        rsv.setTickets(ticketList);
        for (int i = 0; i < tickets.size(); i++) {
            System.out.println("@@@@@@This is in setupTicket_Reservation:" + tickets.get(i));
            Ticket ticket = em.find(Ticket.class, tickets.get(i).getTicketID());
            ticket.setRsv(rsv);
//            em.merge(ticket);

            rsv.getTickets().add(ticket);

        }

//        em.merge(rsv);
        em.flush();
        System.out.println("After setting ticket list");

    }

    public ArrayList<Ticket> setupPsg_Ticket(ArrayList<FlightInstance> departSelected, ArrayList<FlightInstance> returnSelected, ArrayList<Passenger> passengerList, Booker booker, ArrayList<BookingClassInstance> BookClassInstanceList, int psgCount, String origin, String dest, Boolean returnTrip, String bkSystem) {
        Ticket depTicket;
        Ticket arrTicket;
        ArrayList<Ticket> tkList = new ArrayList<Ticket>();
        Date temp;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println("^^^^^^^^^^^^^^^Size of passengerList is: " + passengerList.size());

        for (int i = 0; i < departSelected.size(); i++) {
//            depTicket=new Ticket();
            depCity = departSelected.get(i).getFlightFrequency().getRoute().getOrigin().getCityName();
            arrCity = departSelected.get(i).getFlightFrequency().getRoute().getDest().getCityName();
            temp = departSelected.get(i).getStandardDepTimeDateType();
            depTime = df.format(temp);
            temp = departSelected.get(i).getStandardArrTimeDateType();
            arrTime = df.format(temp);
            flightNo = departSelected.get(i).getFlightFrequency().getFlightNo();

            for (int j = 0; j < passengerList.size(); j++) {
                depTicket = new Ticket();
                depTicket.createTicket(depCity, arrCity, depTime, arrTime, flightNo, bkSystem);
                psg = passengerList.get(j);
                System.out.println("*************Passenger Id is :" + psg.getId());
                Passenger psgl = em.find(Passenger.class, psg.getId());
                em.refresh(psgl);
                if (psgl != null) {
                    depTicket.setPassenger(psgl);
                    em.persist(depTicket);

                    psgl.getTickets().add(depTicket);
                    em.merge(psgl);

                    tkList.add(depTicket);
                    passengerList.set(j, em.find(Passenger.class, psgl.getId()));
                }
            }

        }

        for (int i = 0; i < returnSelected.size(); i++) {
//            arrTicket=new Ticket();
            depCity = returnSelected.get(i).getFlightFrequency().getRoute().getOrigin().getCityName();
            arrCity = returnSelected.get(i).getFlightFrequency().getRoute().getDest().getCityName();
            temp = returnSelected.get(i).getStandardDepTimeDateType();
            depTime = df.format(temp);
            temp = returnSelected.get(i).getStandardArrTimeDateType();
            arrTime = df.format(temp);
            flightNo = returnSelected.get(i).getFlightFrequency().getFlightNo();

            for (int j = 0; j < passengerList.size(); j++) {
                arrTicket = new Ticket();
                arrTicket.createTicket(depCity, arrCity, depTime, arrTime, flightNo, bkSystem);
                psg = passengerList.get(j);
                Passenger psgl = em.find(Passenger.class, psg.getId());
                if (psgl != null) {
                    arrTicket.setPassenger(psgl);
                    em.persist(arrTicket);

                    psgl.getTickets().add(arrTicket);
                    em.merge(psgl);

                    tkList.add(arrTicket);
                    passengerList.set(j, em.find(Passenger.class, psgl.getId()));
                }
            }

        }

        em.flush();
        System.out.println("LEAVING setupPsg_Ticket");

        return tkList;
    }

    public Reservation makeRsvBookInstance(Reservation rsv, ArrayList<BookingClassInstance> BookClassInstanceList, Integer psgCount) {
        rsv = em.find(Reservation.class, rsv.getId());
        em.refresh(rsv);
        BookingClassInstance instance = new BookingClassInstance();
        if (rsv != null) {
            for (int i = 0; i < BookClassInstanceList.size(); i++) {
                instance = em.find(BookingClassInstance.class, BookClassInstanceList.get(i).getId());
                em.refresh(instance);
                instance.setBookedSeatNo(instance.getBookedSeatNo() + psgCount);
                instance.getReservation().add(rsv);
                em.merge(instance);
                rsv.getBkcInstance().add(instance);

            }
            em.merge(rsv);
            em.flush();

            for (int i = 0; i < rsv.getBkcInstance().size(); i++) {
                System.out.println("i=" + i + " flightInstance is " + rsv.getBkcInstance().get(i).getFlightCabin().getFlightInstance().getFlightFrequency().getFlightNo());

            }

        }

        em.flush();

        System.out.println("LEAVING makeRsvBookInstance");

        return rsv;
    }

    public Reservation makeRsvBooker(Reservation rsv, Booker booker) {

        em.persist(rsv);
        booker = em.find(Booker.class, booker.getId());
        rsv.setBooker(booker);

//        List<Reservation> rsvList = new ArrayList<>();
//
//        booker.setRsvList(rsvList);
        booker.getRsvList().add(rsv);
//        em.merge(booker);
        rsv = em.find(Reservation.class, rsv.getId());

        em.flush();

        System.out.println("rsv found in makeRsvBooker is " + rsv);
        return rsv;
    }

//    public ArrayList<Passenger> makeReservation(ArrayList<Passenger> passengerList, String email, Long memberId) {
//        System.out.println("******PassengerList size:" + passengerList.size());
//        Passenger tempPsg;
//        boolean status;
//
//        status = checkMemberExist(memberId, email);
//        ArrayList<Passenger> psgList = new ArrayList<Passenger>();
//        if (status == true) {
//            booker = new Booker();
//            booker = em.find(Booker.class, memberId);
//
//            for (int i = 0; i < passengerList.size(); i++) {
//
//                tempPsg = passengerList.get(i);
//
//                System.out.println("hehe");
//                passenger = new Passenger();
//                passenger.createPsg(tempPsg.getPassport(), tempPsg.getTitle(), tempPsg.getFirstName(), tempPsg.getLastName(), tempPsg.getFfpName(), tempPsg.getFfpNo());
//
//                passenger.setMember(member);
//                em.persist(passenger);
//                em.flush();
//
//                psgList.add(passenger);
//
//            }
//            member.setPsgs(psgList);
//            em.merge(member);
//            em.flush();
//
//        } else {
//            System.out.println("##########Make reservation: member does not exist");
//
//        }
//        return psgList;
//    }
    @Override
    public Booker checkMemberExist(Long memberId, String email) {
        Query query = null;
        List<Booker> resultList = new ArrayList<Booker>();
        query = em.createQuery("SELECT u FROM Booker u WHERE u.id = :inMemberId and u.memberStatus=true and u.email = :inEmail");
        query.setParameter("inMemberId", memberId);
        query.setParameter("inEmail", email);

        System.out.println("##########Check Member Exist");
        System.out.println("##########MemberId" + memberId);
        System.out.println("##########Email" + email);
        resultList = (List) query.getResultList();
        if (resultList.size() == 1) {
            System.out.println("##########Check Member Exist, size:" + resultList.size());
            return resultList.get(0);

        } else {
            return null;
        }

    }

    public boolean checkPassengerExist(Passenger passenger) {
        Query query = null;
        String tempPassport;
        List resultList = new ArrayList<Passenger>();

        tempPassport = passenger.getPassport();
        System.out.println("************Inside checkPassengerExist: " + tempPassport);
        query = em.createQuery("SELECT u FROM Passenger u WHERE u.passport = :inPsgPassport");
        query.setParameter("inPsgPassport", tempPassport);

        resultList = (List) query.getResultList();
        if (resultList.isEmpty()) {
            return false;

        } else {
            return true;
        }

    }

//    @Override
//    public ArrayList<Passenger> makeRsvGuest(ArrayList<Passenger> passengerList, String title, String firstName, String lastName, String address, String email, String contactNo) {
//
//        System.out.println("******PassengerList size:" + passengerList.size());
//        Passenger tempPsg;
//        booker = new Booker();
//
//        booker.createMember(title, firstName, lastName, email, address, contactNo, false);
//        em.persist(booker);
//        em.flush();
//
//        ArrayList<Passenger> psgList = new ArrayList<Passenger>();
//
//        for (int i = 0; i < passengerList.size(); i++) {
////            System.out.println("******PassengerList size i:"+passengerList.get(i).toString());
////            status = checkPassengerExist(passengerList.get(i));
//            tempPsg = passengerList.get(i);
//
//            System.out.println("hehe");
//            passenger = new Passenger();
//            passenger.createPsg(tempPsg.getPassport(), tempPsg.getTitle(), tempPsg.getFirstName(), tempPsg.getLastName(), tempPsg.getFfpName(), tempPsg.getFfpNo());
////            em.persist(passengerList.get(i));
//            passenger.setMember(booker);
//            em.persist(passenger);
//            em.flush();
//
//            psgList.add(passenger);
//
//        }
//
//        booker.setPsgs(psgList);
//        em.merge(booker);
//        em.flush();
//
//        System.out.println("~~~~~~~~The size that member/guest booked" + member.getPsgs().size());
//        return psgList;
//    }
    public boolean checkPassportExist(String passport) {
        Query query = null;

        query = em.createQuery("SELECT u FROM Passenger u WHERE u.passport = :inUserPassport");
        query.setParameter("inUserPassport", passport);
        return checkList(query);
    }

    private boolean checkList(Query query) {
        List resultList = new ArrayList();
        resultList = (List) query.getResultList();
        if (resultList.isEmpty()) {
            return false;

        } else {
            return true;
        }
    }

}
