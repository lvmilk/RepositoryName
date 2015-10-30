/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.AFOS;

import Entity.APS.Aircraft;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Xu
 */
@Entity
public class Maintenance implements Serializable, Comparable<Maintenance> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date startTime;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date endTime;
//    private Integer expectedHour;
    private String objective;       // A Check/ B Check/ C Check/ D Check/ Special
    private String status;
    
    @OneToOne(cascade = {CascadeType.PERSIST})
    private MaintenanceLog log;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Aircraft aircraft;

    public Maintenance create(Aircraft ac, Date startTime, Date endTime, String objective) {
        this.aircraft = ac;
        this.startTime = startTime;
        this.endTime = endTime;
        this.objective = objective;
        this.status = "Scheduled";
        return this;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

//    public Integer getExpectedHour() {
//        return expectedHour;
//    }
//
//    public void setExpectedHour(Integer expectedHour) {
//        this.expectedHour = expectedHour;
//    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public MaintenanceLog getLog() {
        return log;
    }

    public void setLog(MaintenanceLog log) {
        this.log = log;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Maintenance)) {
            return false;
        }
        Maintenance other = (Maintenance) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return objective + " : for " + aircraft.getRegistrationNo() + " from " + startTime.toString() + " to " + endTime.toString() ;
    }

    @Override
    public int compareTo(Maintenance mt) {
        Date thisTime = this.getStartTime();
        Date mtTime = mt.getStartTime();
        return thisTime.compareTo(mtTime);
    }

}
