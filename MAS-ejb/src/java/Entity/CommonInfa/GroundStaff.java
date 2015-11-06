/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.CommonInfa;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author LI HAO
 */
@Entity
public class GroundStaff implements Serializable {

    @Id
    private String grdName;
    private String grdPassword;
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

    @OneToOne(cascade = {CascadeType.ALL})
    private UserEntity user;

    public void create(String strGrdName, String strGrdPassword, String email, String strType, String firstName, String lastName, String stfLevel, Double salary) {
        this.setGrdName(strGrdName);
        this.setGrdPassword(strGrdPassword);
        this.setEmail(email);
        this.setStfType(strType);
        this.firstName = firstName;
        this.lastName = lastName;
        this.setStfLevel(stfLevel);
        this.setAttempt(0);
        this.setLocked(0);
        this.setHourPay(0.0);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (grdName != null ? grdName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GroundStaff)) {
            return false;
        }
        GroundStaff other = (GroundStaff) object;
        if ((this.grdName == null && other.grdName != null) || (this.grdName != null && !this.grdName.equals(other.grdName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.CommonInfaEntity.groudCrew[ id=" + grdName + " ]";
    }

    /**
     * @return the grdName
     */
    public String getGrdName() {
        return grdName;
    }

    /**
     * @param grdName the grdName to set
     */
    public void setGrdName(String grdName) {
        this.grdName = grdName;
    }

    /**
     * @return the grdPassword
     */
    public String getGrdPassword() {
        return grdPassword;
    }

    /**
     * @param grdPassword the grdPassword to set
     */
    public void setGrdPassword(String grdPassword) {
        this.grdPassword = grdPassword;
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
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
