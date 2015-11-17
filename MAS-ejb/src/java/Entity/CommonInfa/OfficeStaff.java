/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.CommonInfa;

import Entity.AFOS.StaffLeave;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author LI HAO
 */
@Entity
public class OfficeStaff implements Serializable {

    @Id
    private String offName;
    private String offPassword;
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

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "officeStaff")
    private List<StaffLeave> leaves = new ArrayList<>();

    public void create(String strOffName, String strOffPassword, String strOffEmail, String strStfType, String firstName, String lastName, String stfLevel, Double salary) {
        this.setOffName(strOffName);
        this.setOffPassword(strOffPassword);
        this.setEmail(strOffEmail);
        this.setStfType(strStfType);
        this.firstName = firstName;
        this.lastName = lastName;
        this.setStfLevel(stfLevel);
        this.setAttempt(0);
        this.setLocked(0);
        this.setSalary(salary);
        this.setHourPay(0.0);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (offName != null ? offName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OfficeStaff)) {
            return false;
        }
        OfficeStaff other = (OfficeStaff) object;
        if ((this.offName == null && other.offName != null) || (this.offName != null && !this.offName.equals(other.offName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.CommonInfaEntity.OfficeStaff[ id=" + offName + " ]";
    }

    public List<StaffLeave> getLeaves() {
        return leaves;
    }

    public void setLeaves(List<StaffLeave> leaves) {
        this.leaves = leaves;
    }

    /**
     * @return the offName
     */
    public String getOffName() {
        return offName;
    }

    /**
     * @param offName the offName to set
     */
    public void setOffName(String offName) {
        this.offName = offName;
    }

    /**
     * @return the offPassword
     */
    public String getOffPassword() {
        return offPassword;
    }

    /**
     * @param offPassword the offPassword to set
     */
    public void setOffPassword(String offPassword) {
        this.offPassword = offPassword;
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
