/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.CommonInfa;

import Entity.AFOS.FlightCrewTeam;
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
import javax.persistence.OneToOne;

/**
 *
 * @author LI HAO
 */
@Entity
public class CockpitCrew implements Serializable {

    @Id
    private String cpName;
    private String cpPassword;
    private String stfType;
    @Column(unique = true)
    private String email;
    private String licence;
    private Integer attempt;
    private Integer locked;
    private String stfLevel;
    private String name;
    private Double salary;
    private Double hourPay;
    private long yearAccumMin = 0;
    private long monthAccumMin = 0;
    private long weekAccumMin = 0;
    private Integer firstSB = 0;    // monthly stand-by counter
    private Integer secondSB = 0;

    @ManyToMany(cascade = {CascadeType.PERSIST}, mappedBy = "cockpitList")
    private List<FlightInstance> fiList = new ArrayList<>();
    @ManyToMany(cascade = {CascadeType.PERSIST}, mappedBy = "cockpitStandByList")
    private List<FlightInstance> fiStandByList = new ArrayList<>();

    @OneToOne(cascade = {CascadeType.ALL})
    private UserEntity user;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    private FlightCrewTeam flightTeam;

    public void create(String strCpName, String strCpPassword, String email, String strStfType, String name, String stfLevel, Double salary, String licence) {
        this.setCpName(strCpName);
        this.setCpPassword(strCpPassword);
        this.setEmail(email);
        this.setStfType(strStfType);
        this.setLicence(licence);
        this.setName(name);
        this.setStfLevel(stfLevel);
        this.setAttempt(0);
        this.setLocked(0);
        this.setSalary(salary);
        this.setHourPay(0.0);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cpName != null ? cpName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the stfId fields are not set
        if (!(object instanceof CockpitCrew)) {
            return false;
        }
        CockpitCrew other = (CockpitCrew) object;
        if ((this.cpName == null && other.cpName != null) || (this.cpName != null && !this.cpName.equals(other.cpName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.CommonInfaEntity.CockpitCrew[ id=" + cpName + " ]";
    }

    /**
     * @return the cpName
     */
    public String getCpName() {
        return cpName;
    }

    /**
     * @param cpName the cpName to set
     */
    public void setCpName(String cpName) {
        this.cpName = cpName;
    }

    /**
     * @return the cpPassword
     */
    public String getCpPassword() {
        return cpPassword;
    }

    /**
     * @param cpPassword the cpPassword to set
     */
    public void setCpPassword(String cpPassword) {
        this.cpPassword = cpPassword;
    }

    /**
     * @return the stfType
     */
    public String getStfType() {
        return stfType;
    }

    /**
     * @param stfType the stfType to set
     */
    public void setStfType(String stfType) {
        this.stfType = stfType;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the licence
     */
    public String getLicence() {
        return licence;
    }

    /**
     * @param licence the licence to set
     */
    public void setLicence(String licence) {
        this.licence = licence;
    }

    /**
     * @return the attempt
     */
    public Integer getAttempt() {
        return attempt;
    }

    /**
     * @param attempt the attempt to set
     */
    public void setAttempt(Integer attempt) {
        this.attempt = attempt;
    }

    /**
     * @return the locked
     */
    public Integer getLocked() {
        return locked;
    }

    /**
     * @param locked the locked to set
     */
    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    /**
     * @return the user
     */
    public UserEntity getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(UserEntity user) {
        this.user = user;
    }

    public FlightCrewTeam getFlightTeam() {
        return flightTeam;
    }

    public void setFlightTeam(FlightCrewTeam flightTeam) {
        this.flightTeam = flightTeam;
    }

    /**
     * @return the stfLevel
     */
    public String getStfLevel() {
        return stfLevel;
    }

    /**
     * @param stfLevel the stfLevel to set
     */
    public void setStfLevel(String stfLevel) {
        this.stfLevel = stfLevel;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the salary
     */
    public Double getSalary() {
        return salary;
    }

    /**
     * @param salary the salary to set
     */
    public void setSalary(Double salary) {
        this.salary = salary;
    }

    /**
     * @return the hourPay
     */
    public Double getHourPay() {
        return hourPay;
    }

    /**
     * @param hourPay the hourPay to set
     */
    public void setHourPay(Double hourPay) {
        this.hourPay = hourPay;
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
