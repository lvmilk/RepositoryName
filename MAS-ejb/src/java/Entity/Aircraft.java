/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author victor
 */
@Entity
public class Aircraft implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String registrationNo;
    private String MnufacturerName;
    private String SerialNo;
    private String Status;
    private String FirstFlyDate;
    private String DeliveryDate;
    private String RetairDate;
    private String AirCraftType;
    private float MaxDistance;
    private float CruiseSpeed;
    private float CruiseAltitude;
    private float Length;
    private float Wingspan;
    
    private int FirstClassSeatNo;
    private int BusinessClassSeatNo;
    private int PremiumEconomyClassSeatNo;
    private int EconomyClassSeatNo;
    
    private ArrayList<Seat> seatList;
    private long seatMapId;
    private long flightLogId;
    private long MaintanencLogId;
    private long TransactionLogId;
    

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
        if (!(object instanceof Aircraft)) {
            return false;
        }
        Aircraft other = (Aircraft) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Aircraft[ id=" + id + " ]";
    }

    /**
     * @return the registrationNo
     */
    public String getRegistrationNo() {
        return registrationNo;
    }

    /**
     * @param registrationNo the registrationNo to set
     */
    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    /**
     * @return the MnufacturerName
     */
    public String getMnufacturerName() {
        return MnufacturerName;
    }

    /**
     * @param MnufacturerName the MnufacturerName to set
     */
    public void setMnufacturerName(String MnufacturerName) {
        this.MnufacturerName = MnufacturerName;
    }

    /**
     * @return the SerialNo
     */
    public String getSerialNo() {
        return SerialNo;
    }

    /**
     * @param SerialNo the SerialNo to set
     */
    public void setSerialNo(String SerialNo) {
        this.SerialNo = SerialNo;
    }

    /**
     * @return the Status
     */
    public String getStatus() {
        return Status;
    }

    /**
     * @param Status the Status to set
     */
    public void setStatus(String Status) {
        this.Status = Status;
    }

    /**
     * @return the FirstFlyDate
     */
    public String getFirstFlyDate() {
        return FirstFlyDate;
    }

    /**
     * @param FirstFlyDate the FirstFlyDate to set
     */
    public void setFirstFlyDate(String FirstFlyDate) {
        this.FirstFlyDate = FirstFlyDate;
    }

    /**
     * @return the DeliveryDate
     */
    public String getDeliveryDate() {
        return DeliveryDate;
    }

    /**
     * @param DeliveryDate the DeliveryDate to set
     */
    public void setDeliveryDate(String DeliveryDate) {
        this.DeliveryDate = DeliveryDate;
    }

    /**
     * @return the RetairDate
     */
    public String getRetairDate() {
        return RetairDate;
    }

    /**
     * @param RetairDate the RetairDate to set
     */
    public void setRetairDate(String RetairDate) {
        this.RetairDate = RetairDate;
    }

    /**
     * @return the AirCraftType
     */
    public String getAirCraftType() {
        return AirCraftType;
    }

    /**
     * @param AirCraftType the AirCraftType to set
     */
    public void setAirCraftType(String AirCraftType) {
        this.AirCraftType = AirCraftType;
    }

    /**
     * @return the MaxDistance
     */
    public float getMaxDistance() {
        return MaxDistance;
    }

    /**
     * @param MaxDistance the MaxDistance to set
     */
    public void setMaxDistance(float MaxDistance) {
        this.MaxDistance = MaxDistance;
    }

    /**
     * @return the CruiseSpeed
     */
    public float getCruiseSpeed() {
        return CruiseSpeed;
    }

    /**
     * @param CruiseSpeed the CruiseSpeed to set
     */
    public void setCruiseSpeed(float CruiseSpeed) {
        this.CruiseSpeed = CruiseSpeed;
    }

    /**
     * @return the CruiseAltitude
     */
    public float getCruiseAltitude() {
        return CruiseAltitude;
    }

    /**
     * @param CruiseAltitude the CruiseAltitude to set
     */
    public void setCruiseAltitude(float CruiseAltitude) {
        this.CruiseAltitude = CruiseAltitude;
    }

    /**
     * @return the Length
     */
    public float getLength() {
        return Length;
    }

    /**
     * @param Length the Length to set
     */
    public void setLength(float Length) {
        this.Length = Length;
    }

    /**
     * @return the Wingspan
     */
    public float getWingspan() {
        return Wingspan;
    }

    /**
     * @param Wingspan the Wingspan to set
     */
    public void setWingspan(float Wingspan) {
        this.Wingspan = Wingspan;
    }

    /**
     * @return the FirstClassSeatNo
     */
    public int getFirstClassSeatNo() {
        return FirstClassSeatNo;
    }

    /**
     * @param FirstClassSeatNo the FirstClassSeatNo to set
     */
    public void setFirstClassSeatNo(int FirstClassSeatNo) {
        this.FirstClassSeatNo = FirstClassSeatNo;
    }

    /**
     * @return the BusinessClassSeatNo
     */
    public int getBusinessClassSeatNo() {
        return BusinessClassSeatNo;
    }

    /**
     * @param BusinessClassSeatNo the BusinessClassSeatNo to set
     */
    public void setBusinessClassSeatNo(int BusinessClassSeatNo) {
        this.BusinessClassSeatNo = BusinessClassSeatNo;
    }

    /**
     * @return the PremiumEconomyClassSeatNo
     */
    public int getPremiumEconomyClassSeatNo() {
        return PremiumEconomyClassSeatNo;
    }

    /**
     * @param PremiumEconomyClassSeatNo the PremiumEconomyClassSeatNo to set
     */
    public void setPremiumEconomyClassSeatNo(int PremiumEconomyClassSeatNo) {
        this.PremiumEconomyClassSeatNo = PremiumEconomyClassSeatNo;
    }

    /**
     * @return the EconomyClassSeatNo
     */
    public int getEconomyClassSeatNo() {
        return EconomyClassSeatNo;
    }

    /**
     * @param EconomyClassSeatNo the EconomyClassSeatNo to set
     */
    public void setEconomyClassSeatNo(int EconomyClassSeatNo) {
        this.EconomyClassSeatNo = EconomyClassSeatNo;
    }

    /**
     * @return the seatList
     */
    public ArrayList<Seat> getSeatList() {
        return seatList;
    }

    /**
     * @param seatList the seatList to set
     */
    public void setSeatList(ArrayList<Seat> seatList) {
        this.seatList = seatList;
    }

    /**
     * @return the seatMapId
     */
    public long getSeatMapId() {
        return seatMapId;
    }

    /**
     * @param seatMapId the seatMapId to set
     */
    public void setSeatMapId(long seatMapId) {
        this.seatMapId = seatMapId;
    }

    /**
     * @return the flightLogId
     */
    public long getFlightLogId() {
        return flightLogId;
    }

    /**
     * @param flightLogId the flightLogId to set
     */
    public void setFlightLogId(long flightLogId) {
        this.flightLogId = flightLogId;
    }

    /**
     * @return the MaintanencLogId
     */
    public long getMaintanencLogId() {
        return MaintanencLogId;
    }

    /**
     * @param MaintanencLogId the MaintanencLogId to set
     */
    public void setMaintanencLogId(long MaintanencLogId) {
        this.MaintanencLogId = MaintanencLogId;
    }

    /**
     * @return the TransactionLogId
     */
    public long getTransactionLogId() {
        return TransactionLogId;
    }

    /**
     * @param TransactionLogId the TransactionLogId to set
     */
    public void setTransactionLogId(long TransactionLogId) {
        this.TransactionLogId = TransactionLogId;
    }
    
}
