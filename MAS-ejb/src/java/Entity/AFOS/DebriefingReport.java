/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.AFOS;

import Entity.APS.FlightInstance;
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
public class DebriefingReport implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    String captainId;
    String flightNo;
    String acReg;
    String acType;
    String depTimeString;
    String arrTimeString;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    Date fiDate;
    String origin;
    String dest;
    String issueCategory;
    String issue;
    String remark;

    @OneToOne(mappedBy = "debrief")
    FlightInstance fi = new FlightInstance();

    public void create(String captainId, Date fiDate, String flightNo, String acReg, String acType, String origin, String dest, String depTimeString, String arrTimeString, String issueCategory, String issue, String remark) {
        this.captainId = captainId;
        this.flightNo = flightNo;
        this.fiDate = fiDate;
        this.acReg = acReg;
        this.acType = acType;
        this.origin = origin;
        this.dest = dest;
        this.depTimeString = depTimeString;
        this.arrTimeString = arrTimeString;
        this.issueCategory = issueCategory;
        this.issue = issue;
        this.remark = remark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof DebriefingReport)) {
            return false;
        }
        DebriefingReport other = (DebriefingReport) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.AFOS.DebriefingReport[ id=" + id + " ]";
    }

    public String getCaptainId() {
        return captainId;
    }

    public void setCaptainId(String captainId) {
        this.captainId = captainId;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getAcReg() {
        return acReg;
    }

    public void setAcReg(String acReg) {
        this.acReg = acReg;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public String getDepTimeString() {
        return depTimeString;
    }

    public void setDepTimeString(String depTimeString) {
        this.depTimeString = depTimeString;
    }

    public String getArrTimeString() {
        return arrTimeString;
    }

    public void setArrTimeString(String arrTimeString) {
        this.arrTimeString = arrTimeString;
    }

    public String getIssueCategory() {
        return issueCategory;
    }

    public void setIssueCategory(String issueCategory) {
        this.issueCategory = issueCategory;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public FlightInstance getFi() {
        return fi;
    }

    public void setFi(FlightInstance fi) {
        this.fi = fi;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public Date getFiDate() {
        return fiDate;
    }

    public void setFiDate(Date fiDate) {
        this.fiDate = fiDate;
    }

}
