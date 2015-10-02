/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AirlineInventory;

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
public class SeatPlanBean implements SeatPlanBeanLocal {

    AircraftType airType;
    @PersistenceContext
    EntityManager em;

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
