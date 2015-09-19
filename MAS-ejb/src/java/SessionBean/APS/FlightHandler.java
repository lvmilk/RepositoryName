/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.APS;

import Entity.APS.Flight;
import Entity.APS.FlightPackage;
import Entity.APS.GenericFlight;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author victor
 */
public class FlightHandler {
    EntityManager em;
    
    public void setEm(EntityManager em){
        this.em = em;
    }
    public void addGenericFlight(GenericFlight gfl){
        em.persist(gfl);
        em.flush();
    }
    public boolean deleteGenericFlight(GenericFlight gfl){
        em.remove(gfl);
        em.flush();
        return true;
    }
    public void updateGenericFight(){
        em.flush();
    }
    public GenericFlight findGenericFlight(long id){
        GenericFlight result = null;
        result = em.find(GenericFlight.class, id);
        return result;
    }
    public List<GenericFlight> findGenericFlight(String flightNumber){
        Query query = em.createQuery
            ("SELECT GF FROM GenericFlight GF WHERE "
                    + "GF.flightNo = :flightNo");
        query.setParameter("flightNo", flightNumber);
        List<GenericFlight> result = query.getResultList();
        return result;
    }
    public List<GenericFlight> findGenericFlight(String orgIATA, String dstIATA){
        Query query = em.createQuery
            ("SELECT GF FROM GenericFlight GF WHERE "
                    + "GF.route.origin.IATA = :orgIATA"
                    + "GF.route.destination.IATA = :dstIATA");
        query.setParameter("orgIATA", orgIATA);
        query.setParameter("dstIATA", dstIATA);
        List<GenericFlight> result = query.getResultList();
        return result;
    }
    public boolean hasGenericFlight(String flightNumber){
        List find = this.findGenericFlight(flightNumber);
        return !find.isEmpty();
    }
    public boolean hasGenericFlight(String orgIATA, String dstIATA){
        List find = this.findGenericFlight(orgIATA, dstIATA);
        return !find.isEmpty();
    }
    
    public List<GenericFlight> listGenericFlight(){
        Query query = em.createQuery
            ("SELECT GFL FROM GenericFlight GFL");
        List<GenericFlight> result = query.getResultList();
        return result;
    }
    
    public void addFlight(Flight fl){
        em.persist(fl);
        em.flush();
    }
    public boolean deleteFlight(Flight fl){
        em.remove(fl);
        em.flush();
        return true;
    }
    public void updateFlight(){
        em.flush();
    }
    public void updateFlight(Flight fl){
        em.refresh(fl);
    }
    public Flight findFlight(long id){
        Flight result = null;
        result = em.find(Flight.class, id);
        return result;
    }
    public List<Flight> findFlight(String date, String flightNumber){
        Query query = em.createQuery
            ("SELECT FL FROM Flight FL WHERE "
                    + "FL.date = :date"
                    + "FL.genericFlight.flightNo = :flightNo");
        query.setParameter("date", date);
        query.setParameter("flightNo", flightNumber);
        List<Flight> result = query.getResultList();
        return result;
    }
    public List<Flight> findFlight(String date, String orgIATA, String dstIATA){
        Query query = em.createQuery
            ("SELECT FL FROM Flight FL WHERE "
                    + "FL.date = :date"
                    + "FL.genericFlight.route.origin.IATA = :orgIATA"
                    + "FL.genericFlight.route.destination.IATA = :dstIATA");
        query.setParameter("date", date);
        query.setParameter("orgIATA", orgIATA);
        query.setParameter("dstIATA", dstIATA);
        List<Flight> result = query.getResultList();
        return result;
    }
    
    public List<Flight> listFlight(){
        Query query = em.createQuery
            ("SELECT FL FROM Flight FL");
        List<Flight> result = query.getResultList();
        return result;
    }
    
    public void addFlightPackage(FlightPackage fk){
        em.persist(fk);
        em.flush();
    }
    
}
