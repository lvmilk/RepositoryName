/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.APS;

import Entity.APS.AircraftType;
import Entity.AIS.CabinClass;
import java.util.ArrayList;
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
public class ManageCabinBean implements ManageCabinBeanLocal {

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
    public void updateCabin(CabinClass cabinSelected, Double seatWidth, Integer rowCount, Integer rowSeatCount, String rowConfig) {
        AircraftType airType = cabinSelected.getAircraftType();
        cabinSelected.setRowCount(rowCount);
        cabinSelected.setRowSeatCount(rowSeatCount);
        cabinSelected.setSeatWidth(seatWidth);
        cabinSelected.setRowConfig(rowConfig);
        em.merge(cabinSelected);
        if (this.checkAllConfigComplete(airType)) {
         generateAllCharts(airType.getCabinList());
        }
        cabinSelected=em.find(CabinClass.class, cabinSelected.getCabinID());
        
        System.out.println(cabinSelected.getSeatChart());

    }

    public boolean checkAllConfigComplete(AircraftType airType) {
        boolean check = true;
        System.out.println("%%%%%%%%%%getting into checkAllConfigComplete()");
        List<CabinClass> cabinList = airType.getCabinList();
        for (int i = 0; i < cabinList.size(); i++) {
            if (cabinList.get(i).getRowConfig() != null && cabinList.get(i).getRowCount() != null && cabinList.get(i).getRowSeatCount() != null && cabinList.get(i).getSeatWidth() != null) {
                System.out.println("cabin config for " + cabinList.get(i).getCabinName() + "is complete");

            } else {
                System.out.println("cabin config for " + cabinList.get(i).getCabinName() + " is not complete");
                check = false;
                break;
            }
        }
        return check;
    }

    
    public void generateAllCharts(List<CabinClass> cabinList) {
        Integer rowCount = 0;
        for (int i = 0; i < cabinList.size(); i++) {
         rowCount=generateChart(cabinList.get(i), rowCount);
        }
    }

    
    public Integer generateChart(CabinClass cabin, Integer rowCount) {
        String[][] seatChart;
        Integer aisle;
        String config = cabin.getRowConfig();
        String[] parts = config.split("-");
        
        System.out.println("Before generate seatchart for cabin "+cabin.getCabinName()+", row count is now "+rowCount);

        Integer[] marker = new Integer[parts.length];
        for (int i = 0; i < marker.length; i++) {
            marker[i] = (Integer.parseInt(parts[i]));
        }

        if (marker[1] == 0) {
            aisle = 1;
            marker[1] = marker[0];

        } else {
            aisle = 2;
            marker[1] = marker[0] + marker[1] + 1;

        }

        System.out.println("Marker 0 is " + marker[0]);
        System.out.println("Marker 1 is " + marker[1]);

        seatChart = new String[cabin.getRowCount()][cabin.getRowSeatCount() + aisle];
        Integer row = 1;
        char rowSeatNum;

        for (int i = 0; i < cabin.getRowCount(); i++) {
            rowSeatNum = 'A';
            for (int j = 0; j < cabin.getRowSeatCount() + aisle; j++) {

                if (j != marker[0] && j != marker[1]) {
                    String seatNo = (rowCount + i + 1) + "" + rowSeatNum;

                    System.out.println("Seat no is " + seatNo);
                    seatChart[i][j] = seatNo;

                    rowSeatNum++;
                } else {
                    seatChart[i][j] = "";

                }
            }

        }
        CabinClass thisCabin=em.find(CabinClass.class, cabin.getCabinID());
        thisCabin.setSeatChart(seatChart);
        em.merge(thisCabin);

        rowCount = rowCount + cabin.getRowCount();

        System.out.println("row count = " + rowCount);
        return rowCount;

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
