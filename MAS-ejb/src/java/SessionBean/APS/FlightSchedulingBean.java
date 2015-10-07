/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.APS;

import Entity.APS.Airport;
import Entity.APS.FlightFrequency;
import Entity.APS.FlightInstance;
import Entity.APS.Route;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Xu
 */
@Stateless
public class FlightSchedulingBean implements FlightSchedulingBeanLocal {

    @PersistenceContext
    EntityManager em;

    FlightFrequency flightFreq;
    FlightInstance flightInst;

    public FlightSchedulingBean() {
    }

    @Override
    public void addFlightFrequency(Route route, String flightNo, String depTimeString, String arrTimeString, Integer dateAdjust,
            boolean onMon, boolean onTue, boolean onWed, boolean onThu, boolean onFri, boolean onSat, boolean onSun, String startDateString, String endDateString) throws Exception {

//        LocalDate startDate = startDateString.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        LocalDate endDate = endDateString.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalTime depTime = LocalTime.parse(depTimeString, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime arrTime = LocalTime.parse(arrTimeString, DateTimeFormatter.ofPattern("HH:mm"));

        if (depTime.isAfter(arrTime)) {
            if (dateAdjust == 0) {
                throw new Exception("Departure time should before arrival time.");
            }
        }
        LocalDate startDate = LocalDate.parse(startDateString, DateTimeFormatter.ofPattern("uuuu-MM-dd"));
        LocalDate endDate = LocalDate.parse(endDateString, DateTimeFormatter.ofPattern("uuuu-MM-dd"));
        if (startDate.isAfter(endDate)) {
            throw new Exception("Start operation date should before end operation date.");
        }

        flightFreq = new FlightFrequency();
        flightFreq.create(route, flightNo, depTimeString, arrTimeString, dateAdjust, onMon, onTue, onWed, onThu, onFri, onSat, onSun, startDateString, endDateString);
        em.persist(flightFreq);
        Route r = em.find(Route.class, route.getId());
        r.setStatus("Serving");
        em.merge(r);
        em.flush();
    }
    

    @Override
    public void validateFlightNo(String flightNo) throws Exception {
        Query q1 = em.createQuery("select f from FlightFrequency f where f.flightNo =:flightNo").setParameter("flightNo", flightNo);
        if (!q1.getResultList().isEmpty()) {
            throw new Exception("Flight No. has already been used.");
        }
    }

    @Override
    public List<FlightFrequency> getAllFlightFrequency() {
        Query q1 = em.createQuery("SELECT f FROM FlightFrequency f");
        List<FlightFrequency> flightFreqList = q1.getResultList();
        if (flightFreqList.isEmpty()) {
            System.out.println("fsb.viewAllRoute(): No flight frequency has been added.");
        } else {
            System.out.println("fsb.viewAllRoute(): Return flight frequency list.");
        }
        return flightFreqList;
    }

    @Override
    public void editFlightFrequency(String flightNo, String depTime, String arrTime, Integer dateAdjust, boolean onMon, boolean onTue,
            boolean onWed, boolean onThu, boolean onFri, boolean onSat, boolean onSun, String startDate, String endDate) {
        Query q1 = em.createQuery("SELECT f FROM FlightFrequency f WHERE f.flightNo =:flightNo").setParameter("flightNo", flightNo);
        flightFreq = (FlightFrequency) q1.getSingleResult();
        flightFreq.setScheduleDepTime(depTime);
        flightFreq.setScheduleArrTime(arrTime);
        flightFreq.setDateAdjust(dateAdjust);
        flightFreq.setOnMon(onMon);
        flightFreq.setOnTue(onTue);
        flightFreq.setOnWed(onWed);
        flightFreq.setOnThu(onThu);
        flightFreq.setOnFri(onFri);
        flightFreq.setOnSat(onSat);
        flightFreq.setOnSat(onSat);
        flightFreq.setOnSun(onSun);
        flightFreq.setStartDate(startDate);
        flightFreq.setEndDate(endDate);

        em.merge(flightFreq);
        em.flush();
        System.out.println("fsb.editFlightFrequency(): Flight frequency updated!");
    }

    @Override
    public List<FlightFrequency> canDeleteFlightFreqList() {
        Query q1 = em.createQuery("SELECT f FROM FlightFrequency f");
        List<FlightFrequency> fList = q1.getResultList();
        List<FlightFrequency> fListCopy = q1.getResultList();
        FlightFrequency f = new FlightFrequency();
        for (int i = 0; i < fList.size(); i++) {
            f = fList.get(i);
            Query q2 = em.createQuery("SELECT fi FROM FlightInstance fi where fi.flightFrequency=:flightFrequency").setParameter("flightFrequency", f);
            if (!q2.getResultList().isEmpty()) {
//                System.out.println(ap.toString());
                fListCopy.remove(f);
            }
        }
        return fListCopy;
    }

    @Override
    public void deleteFlightFreqList(List<FlightFrequency> flightFreqList) {
        for (FlightFrequency f : flightFreqList) {
            Query q1 = em.createQuery("SELECT f FROM FlightFrequency f WHERE f.id=:id").setParameter("id", f.getId());
            em.remove(q1.getSingleResult());
        }
        em.flush();
    }

    @Override
    public List<FlightFrequency> getFlightOfRoute(Route route) {
        Query q2 = em.createQuery("SELECT f FROM FlightFrequency f WHERE f.route=:route").setParameter("route", route);
        return (List<FlightFrequency>) q2.getResultList();
    }

}
