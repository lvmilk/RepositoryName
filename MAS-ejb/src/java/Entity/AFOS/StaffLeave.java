/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.AFOS;

import Entity.CommonInfa.CabinCrew;
import Entity.CommonInfa.CockpitCrew;
import Entity.CommonInfa.GroundStaff;
import Entity.CommonInfa.OfficeStaff;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author wang
 */
@Entity
public class StaffLeave implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date startDate;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date endDate;
    private Integer length; // number of days
    private String status=new String();
    private String remark="Normal";
    private String staffType;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date applyDate = new Date();
    private  String userName;
    

    @ManyToOne
    private CockpitCrew cockpitCrew ;
    @ManyToOne
    private OfficeStaff officeStaff;
    @ManyToOne
    private GroundStaff groundStaff;
    @ManyToOne
    private CabinCrew cabinCrew;

    
  
    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStaffType() {
        return staffType;
    }

    public void setStaffType(String staffType) {
        this.staffType = staffType;
    }

    public CockpitCrew getCockpitCrew() {
        return cockpitCrew;
    }

    public void setCockpitCrew(CockpitCrew cockpitCrew) {
        this.cockpitCrew = cockpitCrew;
    }

    public OfficeStaff getOfficeStaff() {
        return officeStaff;
    }

    public void setOfficeStaff(OfficeStaff officeStaff) {
        this.officeStaff = officeStaff;
    }

    public GroundStaff getGroundStaff() {
        return groundStaff;
    }

    public void setGroundStaff(GroundStaff groundStaff) {
        this.groundStaff = groundStaff;
    }

    public CabinCrew getCabinCrew() {
        return cabinCrew;
    }

    public void setCabinCrew(CabinCrew cabinCrew) {
        this.cabinCrew = cabinCrew;
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
        if (!(object instanceof StaffLeave)) {
            return false;
        }
        StaffLeave other = (StaffLeave) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.AFOS.Leave[ id=" + id + " ]";
    }

}
