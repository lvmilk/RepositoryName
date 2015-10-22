/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.ADS;

import Entity.APS.CabinClass;
import Entity.APS.FlightFrequency;
import Entity.APS.FlightInstance;
import Entity.APS.Route;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author LIU YUQI'
 */
@Stateless
public class ReserveFlightBean implements ReserveFlightBeanLocal {

    @PersistenceContext
    EntityManager em;

    public CabinClass findCabinClass(String cabinName) {
        CabinClass select;
        Query query = em.createQuery("SELECT c FROM CabinClass c WHERE c.cabinName=:cabinName");
        query.setParameter("cabinName", cabinName);
        List<CabinClass> resultList = query.getResultList();

        return resultList.get(0);
    }
    

    public List<FlightInstance> findResultInstanceList(String origin, String dest, Date departDate) {
        Query query = em.createQuery("SELECT f FROM FlightInstance f WHERE f.flightFrequency.route.origin.airportName=:origin AND f.flightFrequency.route.dest.airportName=:dest AND f.standardDepTimeDateType=:departDate");
        query.setParameter("origin", origin);
        query.setParameter("dest", dest);
        query.setParameter("departDate", departDate);

        List<FlightInstance> resultList = query.getResultList();

        System.out.println("flightInstance size returned is " + resultList.size());
        return resultList;

    }

    public List<FlightFrequency> findFrequencies(String origin, String dest) {

        Query query = em.createQuery("SELECT f FROM FlightFrequency f WHERE f.route.origin.airportName=:origin AND f.route.dest.airportName=:dest");
        query.setParameter("origin", origin);
        query.setParameter("dest", dest);

        List<FlightFrequency> resultList = query.getResultList();

        System.out.println("In ReserveFlightBean: findFrequencies():flightfrequency size returned is " + resultList.size());
        return resultList;

    }

    public List<FlightFrequency> getAllFlightFrequency() {

        Query query = em.createQuery("SELECT f FROM FlightFrequency f WHERE f.flightList IS NOT EMPTY");

        List<FlightFrequency> resultList = query.getResultList();

        System.out.println("flightFrequency size returned is " + resultList.size());
        return resultList;

    }

    public List<FlightFrequency> getSecondFrequency(String origin) {
        Query query = em.createQuery("SELECT f FROM FlightFrequency f where f.route.origin.airportName=:origin AND f.flightList IS NOT EMPTY ");
        query.setParameter("origin", origin);

        List<FlightFrequency> resultList = query.getResultList();

        System.out.println("flightFrequency size returned is " + resultList.size());
        return resultList;

    }

}
