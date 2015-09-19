/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SessionBeanMAS.APS;

import EntityMAS.APS.Airport;
import EntityMAS.APS.Route;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author victor
 */
public class RouteHandler {
    EntityManager em;
    
    public void setEm(EntityManager em){
        this.em = em;
    }
    
    public void addAirport(Airport ap){
        em.persist(ap);
        em.flush();
    }
    public boolean deleteAirport(Airport ap){
        em.remove(ap);
        em.flush();
        return true;
    }
    public void updateAirport(Airport ap){
        em.flush();
    }
    public Airport findAirport(long id){
        Airport result = null;
        result = em.find(Airport.class, id);
        return result;
    }
    public List<Airport> findAirport(String IATA){
        Query query = em.createQuery
            ("SELECT AP FROM Airport AP WHERE "
                    + "AP.IATA = :IATA");
        query.setParameter("IATA", IATA);
        List<Airport> result = query.getResultList();
        return result;
    }
    public boolean hasAirport(String IATA){
        List find = this.findAirport(IATA);
        return !find.isEmpty();
    }
    public List<Airport> listAirport(){
        Query query = em.createQuery
            ("SELECT AP FROM Airport Ap");
        List<Airport> result = query.getResultList();
        return result;
    }
    
    public void addRoute(Route rt){
        em.persist(rt);
        em.flush();
    }
    public boolean deleteRoute(Route rt){
        em.remove(rt);
        em.flush();
        return true;
    }
    public void updateRoute(Route rt){
        em.flush();
    }
    public Route findRoute(long id){
        Route result = null;
        result = em.find(Route.class, id);
        return result;
    }
    public List<Route> findRoute(long orgId, long dstId){
        Airport org = findAirport (orgId);
        Airport dst = findAirport (dstId);
        
        //for test
        System.out.println("Find Route: org = " + org);
        System.out.println("Find Route: dst = " + dst);
        
        Query query = em.createQuery
            ("SELECT OD FROM Route OD WHERE "
                    + "OD.origin = :origin AND OD.destination = :destination");
        query.setParameter("origin", org);
        query.setParameter("destination", dst);
        List<Route> result = query.getResultList();
        return result;
    }
    public List<Route> findRoute(String orgIATA, String dstIATA){
        Airport org = findAirport (orgIATA).get(0);
        Airport dst = findAirport (dstIATA).get(0);
        
        //for test
        System.out.println("Find Route: org = " + org);
        System.out.println("Find Route: dst = " + dst);
        
        Query query = em.createQuery
            ("SELECT OD FROM Route OD WHERE "
                    + "OD.origin = :origin AND OD.destination = :destination");
        query.setParameter("origin", org);
        query.setParameter("destination", dst);
        List<Route> result = query.getResultList();
        return result;
    }
    public boolean hasRoute(String orgIATA, String dstIATA){
        List find = this.findRoute(orgIATA, dstIATA);
        return !find.isEmpty();
    }
    public List<Route> listRoute(){
        Query query = em.createQuery
            ("SELECT OD FROM Route OD");
        List<Route> result = query.getResultList();
        return result;
    }
    
}
