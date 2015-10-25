/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.ADS;

import java.util.ArrayList;
import javax.ejb.Stateless;
import Entity.ADS.*;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author LI HAO
 */
@Stateless
public class PassengerSessionBean implements PassengerSessionBeanLocal {

    @PersistenceContext
    private EntityManager em;

    private Passenger passenger;
    private Member member;

    @Override
    public void makeReservation(ArrayList<Passenger> passengerList, String email, Long memberId) {
        System.out.println("******PassengerList size:" + passengerList.size());
        Passenger tempPsg;

        for (int i = 0; i < passengerList.size(); i++) {
//            System.out.println("******PassengerList size i:"+passengerList.get(i).toString());
//            status = checkPassengerExist(passengerList.get(i));
            tempPsg = passengerList.get(i);

            System.out.println("hehe");
            passenger = new Passenger();
//            passenger.CreatePsg(tempPsg.getPassport(), tempPsg.getTitle(), tempPsg.getFirstName(), tempPsg.getLastName(), tempPsg.getFfpName(), tempPsg.getFfpNo());
            em.persist(passengerList.get(i));
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

    @Override
    public void makeRsvGuest(ArrayList<Passenger> passengerList, String title, String firstName, String lastName, String address, String email, String contactNo) {

        System.out.println("******PassengerList size:" + passengerList.size());
        Passenger tempPsg;
        member = new Member();

        member.CreateMember(title, firstName, lastName, email, address, contactNo, false);
        em.persist(member);
        em.flush();

        ArrayList<Passenger> psgList = new ArrayList<Passenger>();

        for (int i = 0; i < passengerList.size(); i++) {
//            System.out.println("******PassengerList size i:"+passengerList.get(i).toString());
//            status = checkPassengerExist(passengerList.get(i));
            tempPsg = passengerList.get(i);

            System.out.println("hehe");
            passenger = new Passenger();
            passenger.CreatePsg(tempPsg.getPassport(), tempPsg.getTitle(), tempPsg.getFirstName(), tempPsg.getLastName(), tempPsg.getFfpName(), tempPsg.getFfpNo());
//            em.persist(passengerList.get(i));
            passenger.setMember(member);
            em.persist(passenger);
            em.flush();
                     
            psgList.add(passenger);

        }
        
        member.setPsgs(psgList);
        em.merge(member);
        em.flush();
        
        System.out.println("~~~~~~~~The size that member/guest booked" + member.getPsgs().size());

    }

}
