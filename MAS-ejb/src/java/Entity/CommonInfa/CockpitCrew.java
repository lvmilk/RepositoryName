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
    private String surName;
    private String givenName;
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
        this.setSurName(name);
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

    public String getCpName() {
        return cpName;
    }

    public void setCpName(String cpName) {
        this.cpName = cpName;
    }

    public String getCpPassword() {
        return cpPassword;
    }

    public void setCpPassword(String cpPassword) {
        this.cpPassword = cpPassword;
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

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
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

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
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
