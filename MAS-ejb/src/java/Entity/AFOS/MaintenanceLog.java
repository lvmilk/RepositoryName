/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.AFOS;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Xu
 */
@Entity
public class MaintenanceLog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String objective;
    private String aircraft;
    private String acType;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date startTime;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date endTime;
    private Integer manhour;
    private String activity;
    private String remark;
    private String mtCrew;
    @OneToOne(mappedBy="log")
    Maintenance maintenance;

    public MaintenanceLog create(Maintenance mt, String aircraft, String acType, String objective, Date startTime, Date endTime, Integer manhour, String activity, String remark, String mtCrew) {
        this.maintenance = mt;
        this.aircraft = aircraft;
        this.acType = acType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.manhour = manhour;
        this.objective = objective;
        this.activity = activity;
        this.remark = remark;
        this.mtCrew = mtCrew;
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getAircraft() {
        return aircraft;
    }

    public void setAircraft(String aircraft) {
        this.aircraft = aircraft;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
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

    public Integer getManhour() {
        return manhour;
    }

    public void setManhour(Integer manhour) {
        this.manhour = manhour;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMtCrew() {
        return mtCrew;
    }

    public void setMtCrew(String mtCrew) {
        this.mtCrew = mtCrew;
    }

    public Maintenance getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(Maintenance maintenance) {
        this.maintenance = maintenance;
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
        if (!(object instanceof MaintenanceLog)) {
            return false;
        }
        MaintenanceLog other = (MaintenanceLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.AFOS.MaintenanceLog[ id=" + id + " ]";
    }

}
