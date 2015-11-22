/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AIS;

import Entity.AIS.CabinClass;
import Entity.APS.AircraftType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.*;

/**
 *
 * @author LIU YUQI'
 */
@Stateless
public class SeatPlanBean implements SeatPlanBeanLocal,SeatPlanBeanRemote {

    AircraftType airType;
    @PersistenceContext
    EntityManager em;

     public void addCabin(AircraftType aircraftType, Integer suiteNo, Integer fcSeatNo, Integer bcSeatNo, Integer pecSeatNo, Integer ecSeatNo) {
        AircraftType AirType = em.find(AircraftType.class, aircraftType.getType());
        System.out.println("Aircraft Type found is " + aircraftType.getType());
        List<CabinClass> cabinSet = new ArrayList<CabinClass>();
        AirType.setCabinList(cabinSet);

        if (suiteNo > 0) {
            CabinClass suite = new CabinClass();
            suite.setCabinName("Suite");
            suite.setSeatCount(suiteNo);
            suite.setAircraftType(AirType);
//            em.persist(suite);
            AirType.getCabinList().add(suite);
        }

        if (fcSeatNo > 0) {
            CabinClass First = new CabinClass();
            First.setCabinName("First Class");
            First.setSeatCount(fcSeatNo);
            First.setAircraftType(AirType);
//            em.persist(First);

            AirType.getCabinList().add(First);
        }

        if (bcSeatNo > 0) {
            CabinClass biz = new CabinClass();
            biz.setCabinName("Business Class");
            biz.setSeatCount(bcSeatNo);
            biz.setAircraftType(AirType);
//            em.persist(biz);
            AirType.getCabinList().add(biz);
        }

        if (pecSeatNo > 0) {
            CabinClass Pecon = new CabinClass();
            Pecon.setCabinName("Premium Economy Class");
            Pecon.setSeatCount(pecSeatNo);
            Pecon.setAircraftType(AirType);
//            em.persist(Pecon);
            cabinSet.add(Pecon);
        }

        if (ecSeatNo > 0) {
            CabinClass econ = new CabinClass();
            econ.setCabinName("Economy Class");
            econ.setSeatCount(ecSeatNo);
            econ.setAircraftType(AirType);
//            em.persist(econ);
            AirType.getCabinList().add(econ);
        }
//        AirType.setCabinList(cabinSet);
        System.out.println("in addCabin: aircraftType is " + AirType.getType());
        System.out.println("No. of cabin class in this type is " + AirType.getCabinList().size());

        em.merge(AirType);

    }
    
    public AircraftType findType(String type) {
        Query query = em.createQuery("SELECT T FROM AircraftType T WHERE T.type = :type ");
        query.setParameter("type", type);

        //    SystemUser user = null;
        List resultList = new ArrayList<AircraftType>();
        resultList = (List) query.getResultList();

        return (AircraftType) resultList.get(0);

    }

    @Override
    public boolean checkAirTypeEmpty() {
        System.out.println("hahahaha");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(SeatPlanBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        Query query = em.createQuery("SELECT at FROM AircraftType at ");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(SeatPlanBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("hehehehe");
        List<AircraftType> resultList = (List) query.getResultList();
        if (resultList.isEmpty()) {
            System.out.println("List is empty");
            return true;
        } else {
            System.out.println("List data exists");
            return false;
        }

    }
    
    

}
