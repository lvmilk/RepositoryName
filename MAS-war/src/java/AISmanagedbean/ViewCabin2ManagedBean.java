/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AISmanagedbean;

import Entity.APS.AircraftType;
import Entity.AIS.CabinClass;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

/**
 *
 * @author LIU YUQI'
 */
@Named(value = "viewCabin2Bean")
@ViewScoped
public class ViewCabin2ManagedBean implements Serializable {

    AircraftType acType;
    List<CabinClass> cabinList = new ArrayList<>();
    CabinClass selectedCabin;
    ArrayList<CabinClass> cabinTable = new ArrayList<>();
    ArrayList<String> rowData;
    ArrayList<ArrayList<String>> seatMap = new ArrayList();
    ArrayList<String> rowSeat = new ArrayList<String>();
    String[][] seatChart;
    CabinClass thisCabin;
    Integer rowSeatCount;
    Integer aisle;
    Map<CabinClass, Integer> aisleMap = new HashMap<CabinClass, Integer>();
    Map<CabinClass, String[][]> chartMap = new HashMap<CabinClass, String[][]>();
    ArrayList<String[][]> chartList=new ArrayList<String[][]>();

    public ViewCabin2ManagedBean() {
    }

    @PostConstruct
    public void init() {
        selectedCabin = new CabinClass();
        acType = (AircraftType) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("aircraftType");
        cabinList = acType.getCabinList();
        
        Integer rowCount = 0;
        

        for (int i = 0; i < cabinList.size(); i++) {
            thisCabin = acType.getCabinList().get(i);
            if (thisCabin.getRowCount() != null && thisCabin.getRowSeatCount() != null && thisCabin.getRowConfig() != null && thisCabin.getRowConfig() != null) {
                rowCount=generateChart(thisCabin, rowCount);
            }
        }
    }

    public Integer generateChart(CabinClass cabin, Integer rowCount) {
        rowSeatCount = cabin.getRowSeatCount();

        String config = cabin.getRowConfig();
        String[] parts = config.split("-");

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

        aisleMap.put(cabin, aisle);
        System.out.println("Marker 0 is " + marker[0]);
        System.out.println("Marker 1 is " + marker[1]);

        seatChart = new String[cabin.getRowCount()][cabin.getRowSeatCount() + aisle];
        Integer row = 1;
        char rowSeatNum;

        for (int i = 0; i < cabin.getRowCount(); i++) {
            rowSeatNum = 'A';
            for (int j = 0; j < cabin.getRowSeatCount() + aisle; j++) {

                if (j != marker[0] && j != marker[1]) {
                    String seatNo = (rowCount+ i + 1) + "" + rowSeatNum;

                    System.out.println("Seat no is " + seatNo);
                    seatChart[i][j] = seatNo;

                    rowSeatNum++;
                } else {
                    seatChart[i][j] = "";

                }
            }

        }
        chartList.add(seatChart);
        chartMap.put(cabin, seatChart);
        rowCount=rowCount+cabin.getRowCount();
        
        System.out.println("row count = "+rowCount);
        return rowCount;

    }

 
    public void viewDetail(CabinClass cabinClass) throws IOException {

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cabinClass", cabinClass);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("aircraftType", acType);

        System.out.println("reach viewCabin2bean");

        FacesContext.getCurrentInstance().getExternalContext().redirect("./ViewCabin3.xhtml");

    }

    public AircraftType getAcType() {
        return acType;
    }

    public void setAcType(AircraftType acType) {
        this.acType = acType;
    }

    public List<CabinClass> getCabinList() {
        return cabinList;
    }

    public void setCabinList(List<CabinClass> cabinList) {
        this.cabinList = cabinList;
    }

    public CabinClass getSelectedCabin() {
        return selectedCabin;
    }

    public void setSelectedCabin(CabinClass selectedCabin) {
        this.selectedCabin = selectedCabin;
    }

    public CabinClass getThisCabin() {
        return thisCabin;
    }

    public void setThisCabin(CabinClass thisCabin) {
        this.thisCabin = thisCabin;
    }

    public ArrayList<ArrayList<String>> getSeatMap() {
        return seatMap;
    }

    public void setSeatMap(ArrayList<ArrayList<String>> seatMap) {
        this.seatMap = seatMap;
    }

    public ArrayList<String> getRowSeat() {
        return rowSeat;
    }

    public void setRowSeat(ArrayList<String> rowSeat) {
        this.rowSeat = rowSeat;
    }

    public String[][] getSeatChart() {
        return seatChart;
    }

    public void setSeatChart(String[][] seatChart) {
        this.seatChart = seatChart;
    }

    public Integer getRowSeatCount() {
        return rowSeatCount;
    }

    public void setRowSeatCount(Integer rowSeatCount) {
        this.rowSeatCount = rowSeatCount;
    }

    public Integer getAisle() {
        return aisle;
    }

    public void setAisle(Integer aisle) {
        this.aisle = aisle;
    }

    public Map<CabinClass, Integer> getAisleMap() {
        return aisleMap;
    }

    public void setAisleMap(Map<CabinClass, Integer> aisleMap) {
        this.aisleMap = aisleMap;
    }

    public ArrayList<String[][]> getChartList() {
        return chartList;
    }

    public void setChartList(Map<CabinClass, String[][]> chartList) {
        this.chartList = (ArrayList<String[][]>) chartList;
    }

    public Map<CabinClass, String[][]> getChartMap() {
        return chartMap;
    }

    public void setChartMap(Map<CabinClass, String[][]> chartMap) {
        this.chartMap = chartMap;
    }

    
    
}
