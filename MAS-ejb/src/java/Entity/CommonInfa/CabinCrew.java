/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.CommonInfa;

import Entity.AFOS.FlightCrewTeam;
import Entity.AFOS.StaffLeave;
import Entity.APS.FlightInstance;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author LI HAO
 */
@Entity
public class CabinCrew implements Serializable {

    @Id
    private String cbName;
    private String cbPassword;
    private String stfType;
    @Column(unique = true)
    private String email;
    private Integer attempt;
    private Integer locked;
    private String stfLevel;
    private String firstName;
    private String lastName;
    private Double salary;
    private Double hourPay;
    private String secondLang;
    private long yearAccumMin;
    private long monthAccumMin;
    private long weekAccumMin;
    private Integer firstSB;    // monthly stand-by counter
    private Integer secondSB;

    @ManyToMany(cascade = {CascadeType.PERSIST}, mappedBy = "cabinList")
    private List<FlightInstance> fiList = new ArrayList<>();
    @ManyToMany(cascade = {CascadeType.PERSIST}, mappedBy = "cabinStandByList")
    private List<FlightInstance> fiStandByList = new ArrayList<>();

    @OneToOne(cascade = {CascadeType.ALL})
    private UserEntity user;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    private FlightCrewTeam flightTeam;
  @OneToMany(cascade = {CascadeType.PERSIST},mappedBy="cabinCrew")
    private List<StaffLeave> leaves = new ArrayList<StaffLeave>();
    
    public void create(String strCbName, String strCbPassword, String email, String strStfType, String firstName, String lastName, String stfLevel, Double salary, String secondLang) {
        this.setCbName(strCbName);
        this.setCbPassword(strCbPassword);
        this.setEmail(email);
        this.setStfType(strStfType);
        this.firstName = firstName;
        this.lastName = lastName;
        this.stfLevel = stfLevel;
        this.setAttempt(0);
        this.setLocked(0);
        this.setSalary(salary);
        this.setHourPay(0.0);
        this.setSecondLang(secondLang);
        
        this.setWeekAccumMin(0);
        this.setMonthAccumMin(0);
        this.setYearAccumMin(0);
        this.setFirstSB(0);
        this.setSecondSB(0);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cbName != null ? cbName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CabinCrew)) {
            return false;
        }
        CabinCrew other = (CabinCrew) object;
        if ((this.cbName == null && other.cbName != null) || (this.cbName != null && !this.cbName.equals(other.cbName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.CommonInfaEntity.CabinCrew[ id=" + cbName + " ]";
    }

    public List<StaffLeave> getLeaves() {
        return leaves;
    }

    public void setLeaves(List<StaffLeave> leaves) {
        this.leaves = leaves;
    }

    public String getCbName() {
        return cbName;
    }

    public void setCbName(String cbName) {
        this.cbName = cbName;
    }

    public String getCbPassword() {
        return cbPassword;
    }

    public void setCbPassword(String cbPassword) {
        this.cbPassword = cbPassword;
    }

    public String getStfType() {
        return stfType;
    }

    public void setStfType(String stfType) {
        this.stfType = stfType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAttempt() {
        return attempt;
    }

    public void setAttempt(Integer attempt) {
        this.attempt = attempt;
    }

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public FlightCrewTeam getFlightTeam() {
        return flightTeam;
    }

    public void setFlightTeam(FlightCrewTeam flightTeam) {
        this.flightTeam = flightTeam;
    }

    public String getStfLevel() {
        return stfLevel;
    }

    public void setStfLevel(String stfLevel) {
        this.stfLevel = stfLevel;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Double getHourPay() {
        return hourPay;
    }

    public void setHourPay(Double hourPay) {
        this.hourPay = hourPay;
    }

    public String getSecondLang() {
        return secondLang;
    }

    public void setSecondLang(String secondLang) {
        this.secondLang = secondLang;
    }

    public long getYearAccumMin() {
        return yearAccumMin;
    }

    public void setYearAccumMin(long yearAccumMin) {
        this.yearAccumMin = yearAccumMin;
    }

    public long getMonthAccumMin() {
        return monthAccumMin;
    }

    public void setMonthAccumMin(long monthAccumMin) {
        this.monthAccumMin = monthAccumMin;
    }

    public long getWeekAccumMin() {
        return weekAccumMin;
    }

    public void setWeekAccumMin(long weekAccumMin) {
        this.weekAccumMin = weekAccumMin;
    }

    public List<FlightInstance> getFiList() {
        return fiList;
    }

    public void setFiList(List<FlightInstance> fiList) {
        this.fiList = fiList;
    }

    public List<FlightInstance> getFiStandByList() {
        return fiStandByList;
    }

    public void setFiStandByList(List<FlightInstance> fiStandByList) {
        this.fiStandByList = fiStandByList;
    }

    public Integer getFirstSB() {
        return firstSB;
    }

    public void setFirstSB(Integer firstSB) {
        this.firstSB = firstSB;
    }

    public Integer getSecondSB() {
        return secondSB;
    }

    public void setSecondSB(Integer secondSB) {
        this.secondSB = secondSB;
    }


}
