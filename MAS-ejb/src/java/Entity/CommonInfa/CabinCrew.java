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
    private String name;
    private Double salary;
    private String secondLang;
    private long yearAccumMin = 0;
    private long monthAccumMin = 0;
    private long weekAccumMin = 0;
    private Integer firstSB = 0;    // monthly stand-by counter
    private Integer secondSB = 0;

    @ManyToMany(cascade = {CascadeType.PERSIST}, mappedBy = "cabinList")
    private List<FlightInstance> fiList = new ArrayList<>();
    @ManyToMany(cascade = {CascadeType.PERSIST}, mappedBy = "cabinStandByList")
    private List<FlightInstance> fiStandByList = new ArrayList<>();

    @OneToOne(cascade = {CascadeType.ALL})
    private UserEntity user;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    private FlightCrewTeam flightTeam;

    public void create(String strCbName, String strCbPassword, String email, String strStfType, String name, String stfLevel, Double salary) {
        this.setCbName(strCbName);
        this.setCbPassword(strCbPassword);
        this.setEmail(email);
        this.setStfType(strStfType);
        this.name = name;
        this.stfLevel = stfLevel;
        this.setAttempt(0);
        this.setLocked(0);
        this.setSalary(salary);
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

    /**
     * @return the cbName
     */
    public String getCbName() {
        return cbName;
    }

    /**
     * @param cbName the cbName to set
     */
    public void setCbName(String cbName) {
        this.cbName = cbName;
    }

    /**
     * @return the cbPassword
     */
    public String getCbPassword() {
        return cbPassword;
    }

    /**
     * @param cbPassword the cbPassword to set
     */
    public void setCbPassword(String cbPassword) {
        this.cbPassword = cbPassword;
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
