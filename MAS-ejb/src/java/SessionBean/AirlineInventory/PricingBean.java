/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AirlineInventory;

import Entity.APS.AircraftType;
import Entity.APS.Route;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author wang
 */
@Stateless
public class PricingBean implements PricingBeanLocal {
    @PersistenceContext
    EntityManager em;
    
    AircraftType aircraftType= new AircraftType();
    Route route = new Route();
    
    @Override
    public AircraftType getAircraftType(String type){
        Query query= em.createQuery("SELECT a FROM AircraftType a where a.type=:atype ");
        query.setParameter("atype", type);
        List<AircraftType> resultList= query.getResultList();
        
        return resultList.get(0);  //Since the aircraft is selected, but be non-empty
        
    }
    
    @Override
    public List<Route> getRouteList(){
        Query query= em.createQuery("SELECT r FROM Route r");
        List<Route> resultList= query.getResultList();
        return resultList;
    }
    
    @Override
    public Route getRouteInfo(Long ID){
    Query query=em.createQuery("SELECT r FROM Route r where r.id=:rID ");
    query.setParameter("rID",ID);
    List<Route> resultList =query.getResultList();
    return resultList.get(0);
    }
    
    @Override
    public Integer calculateCrewNo(Integer seatNo){
        Integer crewNo;
        if(seatNo>=19&&seatNo<=50)
             crewNo= 3;     //one cabin crew and two cockpit crew
        else if(seatNo>50&&seatNo<=100)
            crewNo=4;
        else if (seatNo>100)
            crewNo=Double.valueOf(Math.ceil(seatNo/50)).intValue()+4;
        else
            crewNo=2;  //if seatNo<19:No need cabin crew
        
      return crewNo;
    }
    
    @Override
    public Double calculateCrewCost(Integer crewNo,Double crewCost,Double blockHour,Integer annualDepartures){
       return crewNo*crewCost*blockHour*annualDepartures;
       
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
