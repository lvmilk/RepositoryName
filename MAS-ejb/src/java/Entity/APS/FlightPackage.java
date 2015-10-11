///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package Entity.APS;
//
//import java.io.Serializable;
//import java.text.Format;
//import java.text.SimpleDateFormat;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//import javax.persistence.CascadeType;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.OneToMany;
//
///**
// *
// * @author Xu
// */
//@Entity
//public class FlightPackage implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    private String startTime;
//    private String endTime;
//    private String startDay;
//    private String endDay;
//
//    @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "flightPackage")
//    private List<FlightFrequency> flightList = new ArrayList<>();
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public List<FlightFrequency> getFlightList() {
//        return flightList;
//    }
//
//    public void setFlightList(List<FlightFrequency> flightList) {
//        this.flightList = flightList;
//    }
//
//    public void addFlight(FlightFrequency fl) {
//        fl.setFlightPackage(this);
//        this.flightList.add(fl);
//    }
//
//    public void setStartTime(String startTime) {
//        this.startTime = startTime;
//    }
//
//    public String getStartTime() {
//        LocalTime start = null;
//        Format formatter = new SimpleDateFormat("HH:mm");
//        String depTimeString;
//        LocalTime depTime;
//        for (FlightFrequency f1 : flightList) {
//            depTimeString = f1.getScheduleDepTime();
//            depTime = LocalTime.parse(depTimeString, DateTimeFormatter.ofPattern("HH:mm"));
//            if (start == null) {
//                start = depTime;
//            } else {
//                if (depTime.isBefore(start)) {
//                    start = depTime;
//                }
//            }
//        }
//        return formatter.format(startTime);
//    }
//
//    public void setEndTime(String endTime) {
//        this.endTime = endTime;
//    }
//
//    public String getEndTime() {
//        LocalTime end = null;
//        Format formatter = new SimpleDateFormat("HH:mm");
//        String arrTimeString;
//        LocalTime arrTime;
//        for (FlightFrequency f1 : flightList) {
//            arrTimeString = f1.getScheduleArrTime();
//            arrTime = LocalTime.parse(arrTimeString, DateTimeFormatter.ofPattern("HH:mm"));
//            if (end == null) {
//                end = arrTime;
//            } else {
//                if (arrTime.isAfter(end)) {
//                    end = arrTime;
//                }
//            }
//        }
//        return formatter.format(end);
//    }
//// not finished!!!
//    public String getStartDay() {
//        return startDay;
//    }
//
//    public void setStartDay(String startDay) {
//        this.startDay = startDay;
//    }
//
//    public String getEndDay() {
//        return endDay;
//    }
//
//    public void setEndDay(String endDay) {
//        this.endDay = endDay;
//    }
//    
//    @Override
//    public int hashCode() {
//        int hash = 0;
//        hash += (id != null ? id.hashCode() : 0);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object object) {
//        // TODO: Warning - this method won't work in the case the id fields are not set
//        if (!(object instanceof FlightPackage)) {
//            return false;
//        }
//        FlightPackage other = (FlightPackage) object;
//        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        String result = "";
//        for (FlightFrequency fl : flightList) {
//            result += fl.getFlightNo()
//                    + "\t" + fl.getRoute().getOrigin().getIATA()
//                    + "\t" + fl.getRoute().getDest().getIATA() + "\n";
//        }
//        return result;
//    }
//
//}
