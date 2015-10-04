/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.APS;

import Entity.APS.FlightFrequency;
import Entity.APS.FlightInstance;
import Entity.APS.Route;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

        if(depTime.isAfter(arrTime)) {
            if(dateAdjust == 0)
            throw new Exception("Departure time should before arrival time.");
        }
        LocalDate startDate = LocalDate.parse(startDateString, DateTimeFormatter.ofPattern("dd-MM-uuuu"));
        LocalDate endDate = LocalDate.parse(endDateString, DateTimeFormatter.ofPattern("dd-MM-uuuu"));
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
            throw new Exception("Flight No already used.");
        }
    }
}
