/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AIS;

import Entity.AIS.BookingClass;
import Entity.AIS.BookingClassInstance;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ejb.Stateless;
import java.util.*;
import javax.persistence.Query;

/**
 *
 * @author LIU YUQI'
 */
@Stateless
public class BookingClassBean implements BookingClassBeanLocal,BookingClassBeanRemote {

    @PersistenceContext
    EntityManager entityManager;

    BookingClass bookingClass = new BookingClass();

    public BookingClassBean() {

    }

    public void updateBookClassInstance(BookingClass selectedBookClass) {
        Query query = entityManager.createQuery("SELECT b FROM BookingClassInstance b where b.bookingClass.annotation=:annotation ");
        query.setParameter("annotation", selectedBookClass.getAnnotation());

        List<BookingClassInstance> resultList = (List) query.getResultList();
        if (!resultList.isEmpty()) {

            System.out.println("No. of booking class instance affected is " + resultList.size());
            for (int i = 0; i < resultList.size(); i++) {
                if (selectedBookClass.getCabinName().equals("Suite")) {
                    System.out.println("New price for this booking class is "+selectedBookClass.getPrice_percentage() * resultList.get(i).getFlightCabin().getFlightInstance().getFlightFrequency().getRoute().getBasicScFare());
                    resultList.get(i).setPrice(selectedBookClass.getPrice_percentage() * resultList.get(i).getFlightCabin().getFlightInstance().getFlightFrequency().getRoute().getBasicScFare());
                } else if (selectedBookClass.getCabinName().equals("First Class")) {
                    resultList.get(i).setPrice(selectedBookClass.getPrice_percentage() * resultList.get(i).getFlightCabin().getFlightInstance().getFlightFrequency().getRoute().getBasicFcFare());
                } else if (selectedBookClass.getCabinName().equals("Business Class")) {
                    resultList.get(i).setPrice(selectedBookClass.getPrice_percentage() * resultList.get(i).getFlightCabin().getFlightInstance().getFlightFrequency().getRoute().getBasicBcFare());
                }
                if (selectedBookClass.getCabinName().equals("Premium Economy Class")) {
                    resultList.get(i).setPrice(selectedBookClass.getPrice_percentage() * resultList.get(i).getFlightCabin().getFlightInstance().getFlightFrequency().getRoute().getBasicPecFare());
                }
                if (selectedBookClass.getCabinName().equals("Economy Class")) {
                    resultList.get(i).setPrice(selectedBookClass.getPrice_percentage() * resultList.get(i).getFlightCabin().getFlightInstance().getFlightFrequency().getRoute().getBasicEcFare());
                }

                BookingClassInstance temp = entityManager.find(BookingClassInstance.class, resultList.get(i).getId());
                
                System.out.println("new price for resultlist.get(i) is "+resultList.get(i).getPrice());
                temp.setPrice(resultList.get(i).getPrice());
                entityManager.merge(temp);
            }
        } else {
            System.out.println("No Booking Class instance affected");
        }

    }

    @Override
    public boolean checkGotInstance(ArrayList<BookingClass> selectedClass) {

        boolean check = false;
        for (int i = 0; i < selectedClass.size(); i++) {

            Query query = entityManager.createQuery("SELECT b FROM BookingClassInstance b where b.bookingClass.annotation=:annotation ");
            query.setParameter("annotation", selectedClass.get(i).getAnnotation());

            List<BookingClass> resultList = (List) query.getResultList();
            if (!resultList.isEmpty()) {
                check = true;
                break;
            }
        }
        return check;

    }

    @Override
    public void addBookingClass(String annotation, String cabinName, Double price_percentage, Double refund_percentage, Double change_route_percentage,
            Double change_date_percentage, Double change_passenger_percentage, Double open_jaw_percentage, Double earn_mile_percentage, Integer min_stay, Integer max_stay,
            Integer ticket_advance, Integer reserve_advance, Boolean can_standby, Boolean dds_available, Boolean gds_available) {

        bookingClass = new BookingClass();

        bookingClass.setCabinName(cabinName);
        bookingClass.setAnnotation(annotation);
        bookingClass.setPrice_percentage(price_percentage);
        bookingClass.setRefund_percentage(refund_percentage);
        bookingClass.setChange_route_percentage(change_route_percentage);
        bookingClass.setChange_date_percentage(change_date_percentage);
        bookingClass.setChange_passenger_percentage(change_passenger_percentage);
        bookingClass.setOpen_jaw_percentage(open_jaw_percentage);
        bookingClass.setEarn_mile_percentage(earn_mile_percentage);
        bookingClass.setMin_stay(min_stay);
        bookingClass.setMax_stay(max_stay);
        bookingClass.setTicket_advance(ticket_advance);
        bookingClass.setReserve_advance(reserve_advance);
        bookingClass.setCan_standby(can_standby);
        bookingClass.setDds_available(dds_available);
        bookingClass.setGds_available(gds_available);

        entityManager.persist(bookingClass);
 
    }

    @Override
    public BookingClass editBookingClass(String annotation, String annotation2, String cabinName, Double price_percentage, Double refund_percentage, Double change_route_percentage,
            Double change_date_percentage, Double change_passenger_percentage, Double open_jaw_percentage, Double earn_mile_percentage, Integer min_stay, Integer max_stay,
            Integer ticket_advance, Integer reserve_advance, Boolean can_standby, Boolean dds_available, Boolean gds_available) {

        Query query = entityManager.createQuery("SELECT b FROM BookingClass b where b.annotation=:annotation ");
        query.setParameter("annotation", annotation2);

        List<BookingClass> resultList = (List) query.getResultList();

        if (!resultList.isEmpty()) {
            bookingClass = resultList.get(0);

            bookingClass.setCabinName(cabinName);
            bookingClass.setAnnotation(annotation);
            bookingClass.setPrice_percentage(price_percentage);
            bookingClass.setRefund_percentage(refund_percentage);
            bookingClass.setChange_route_percentage(change_route_percentage);
            bookingClass.setChange_date_percentage(change_date_percentage);
            bookingClass.setChange_passenger_percentage(change_passenger_percentage);
            bookingClass.setOpen_jaw_percentage(open_jaw_percentage);
            bookingClass.setEarn_mile_percentage(earn_mile_percentage);
            bookingClass.setMin_stay(min_stay);
            bookingClass.setMax_stay(max_stay);
            bookingClass.setTicket_advance(ticket_advance);
            bookingClass.setReserve_advance(reserve_advance);
            bookingClass.setCan_standby(can_standby);
            bookingClass.setDds_available(dds_available);
            bookingClass.setGds_available(gds_available);

            entityManager.merge(bookingClass);
            entityManager.flush();

        }
        return bookingClass;
    }

    @Override
    public boolean checkDuplicate(String annotation) {

        Query query = entityManager.createQuery("SELECT b FROM BookingClass b WHERE b.annotation = :annotation ");
        query.setParameter("annotation", annotation);

        //    SystemUser user = null;
        List resultList = new ArrayList<BookingClass>();
        resultList = (List) query.getResultList();
        if (resultList.isEmpty()) {
            return false;

        } else {
            return true;
        }

    }

    @Override
    public boolean deleteBookingClass(ArrayList<BookingClass> selectedClass) {
        if (selectedClass.size() > 0) {
            for (int i = 0; i < selectedClass.size(); i++) {
                Long pKey = selectedClass.get(i).getId();
                BookingClass bookClass = entityManager.find(BookingClass.class, pKey);

                entityManager.remove(bookClass);

            }

            return true;

        }
        return false;

    }

    @Override
    public List<BookingClass> getAllBookingClasses() {
        Query query = entityManager.createQuery("SELECT b FROM BookingClass b ");
        List<BookingClass> resultList = (List) query.getResultList();
        if (resultList.isEmpty()) {
            System.out.println("List is empty");
        } else {
            System.out.println("List data exists");
        }

        return resultList;

    }

}
