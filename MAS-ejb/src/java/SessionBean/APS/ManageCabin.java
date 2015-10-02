/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.APS;

import Entity.APS.AircraftType;
import Entity.APS.CabinClass;
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
public class ManageCabin implements ManageCabinLocal {

    @PersistenceContext
    EntityManager em;

//    private String cabinName;
//    private Integer seatCount; //total seat count
////    private Double fullFare;
//    private Double seatWidth;
//    private Double rowCount;//no. of rows
//    private Integer rowSeatCount; //no. of seat counts per row
//    private String rowConfig; //e.g for 7 seats/row, 2-3-2
//
    
    public void updateCabin(CabinClass cabinSelected, Double seatWidth, Integer rowCount,Integer rowSeatCount,String rowConfig){
    
    cabinSelected.setRowCount(rowCount);
    cabinSelected.setRowSeatCount(rowSeatCount);
    cabinSelected.setSeatWidth(seatWidth);
    cabinSelected.setRowConfig(rowConfig);
    em.merge(cabinSelected);
    

    }


    public boolean widthExist(AircraftType type, String cabin) {
//        Query query = em.createQuery("SELECT c FROM CabinClass c where c.aircraftType=:type and c.cabinName=:name and c.seatWidth<>'' ");
         Query query = em.createQuery("SELECT c FROM CabinClass c where c.aircraftType=:type and c.cabinName=:cabinName and c.seatWidth IS NULL ");
        query.setParameter("type", type);
        query.setParameter("cabinName", cabin);

        List<CabinClass> resultList = (List) query.getResultList();

        if (resultList.isEmpty()) {
            System.out.println("List is empty");
            return true;
        } else {
            System.out.println("List data exists");
            return false;
        }

    }

    public boolean rowCountExist(AircraftType type, String cabin) {
//        Query query = em.createQuery("SELECT c FROM CabinClass c where c.aircraftType=:type and c.cabinName=:name and c.rowCount<>'' ");
        Query query = em.createQuery("SELECT c FROM CabinClass c where c.aircraftType=:type and c.cabinName=:cabinName and c.rowCount IS NULL ");
        query.setParameter("type", type);
        query.setParameter("cabinName", cabin);

        List<CabinClass> resultList = (List) query.getResultList();

        if (resultList.isEmpty()) {
            System.out.println("List is empty");
            return true;
        } else {
            System.out.println("List data exists");
            return false;
        }

    }

    public boolean rowSeatCount(AircraftType type, String cabin) {
//        Query query = em.createQuery("SELECT c FROM CabinClass c where c.aircraftType=:type and c.cabinName=:name and c.rowSeatCount<>'' ");
        Query query = em.createQuery("SELECT c FROM CabinClass c where c.aircraftType=:type and c.cabinName=:cabinName and c.rowSeatCount IS NULL ");
        query.setParameter("type", type);
        query.setParameter("cabinName", cabin);

        List<CabinClass> resultList = (List) query.getResultList();

        if (resultList.isEmpty()) {
            System.out.println("List is empty");
            return true;
        } else {
            System.out.println("List data exists");
            return false;
        }

    }

    public boolean rowConfig(AircraftType type, String cabin) {
//        Query query = em.createQuery("SELECT c FROM CabinClass c where c.aircraftType=:type and c.cabinName=:name and c.rowConfig<>'' ");
          Query query = em.createQuery("SELECT c FROM CabinClass c where c.aircraftType=:type and c.cabinName=:cabinName and c.rowConfig IS NULL ");
        query.setParameter("type", type);
        query.setParameter("cabinName", cabin);

        List<CabinClass> resultList = (List) query.getResultList();
    

        if (resultList.isEmpty()) {
            System.out.println("List is empty");
            return true;
        } else {
            System.out.println("List data exists");
            return false;
        }

    }

   

}
