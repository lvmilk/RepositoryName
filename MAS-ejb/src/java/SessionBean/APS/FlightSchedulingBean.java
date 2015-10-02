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
            boolean onMon, boolean onTue, boolean onWed, boolean onThu, boolean onFri, boolean onSat, boolean onSun, String startDateString, String endDateString) {
        
        LocalDate startDate = LocalDate.parse(startDateString, DateTimeFormatter.ofPattern("dd-MM-uuuu"));
        LocalDate endDate = LocalDate.parse(endDateString, DateTimeFormatter.ofPattern("dd-MM-uuuu"));
        LocalTime depTime = LocalTime.parse(depTimeString, DateTimeFormatter.ofPattern("HH:mm:ss"));
        LocalTime arrTime = LocalTime.parse(arrTimeString, DateTimeFormatter.ofPattern("HH:mm:ss"));
        flightFreq.create(route, flightNo, depTime, arrTime, dateAdjust, onMon, onTue, onWed, onThu, onFri, onSat, onSun, startDate, endDate);
        em.persist(flightFreq);
        em.flush();
    }
}
