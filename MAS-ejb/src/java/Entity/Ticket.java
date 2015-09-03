/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author victor
 */
@Entity
public class Ticket implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String ticketNo;
    @ManyToOne
    private Itinerary itinerary;
    
    private String operationStatus;
    private String ValidStartDate;
    private String ValidUntilDate;
    private String IssueDate;
    private String IssuePlace;
    private String promoCode;
    private String TotalFares;
    private String TotalTaxs;
    private String TotalDue;
    private String TotalPaid;
    
    @ManyToMany
    private Collection<Payment> paymentList;
    
    @OneToMany
    private Collection<Ticket> changedFromList;

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
        if (!(object instanceof Ticket)) {
            return false;
        }
        Ticket other = (Ticket) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Ticket[ id=" + id + " ]";
    }

    /**
     * @return the ticketNo
     */
    public String getTicketNo() {
        return ticketNo;
    }

    /**
     * @param ticketNo the ticketNo to set
     */
    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    /**
     * @return the itinerary
     */
    public Itinerary getItinerary() {
        return itinerary;
    }

    /**
     * @param itinerary the itinerary to set
     */
    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    /**
     * @return the operationStatus
     */
    public String getOperationStatus() {
        return operationStatus;
    }

    /**
     * @param operationStatus the operationStatus to set
     */
    public void setOperationStatus(String operationStatus) {
        this.operationStatus = operationStatus;
    }

    /**
     * @return the ValidStartDate
     */
    public String getValidStartDate() {
        return ValidStartDate;
    }

    /**
     * @param ValidStartDate the ValidStartDate to set
     */
    public void setValidStartDate(String ValidStartDate) {
        this.ValidStartDate = ValidStartDate;
    }

    /**
     * @return the ValidUntilDate
     */
    public String getValidUntilDate() {
        return ValidUntilDate;
    }

    /**
     * @param ValidUntilDate the ValidUntilDate to set
     */
    public void setValidUntilDate(String ValidUntilDate) {
        this.ValidUntilDate = ValidUntilDate;
    }

    /**
     * @return the IssueDate
     */
    public String getIssueDate() {
        return IssueDate;
    }

    /**
     * @param IssueDate the IssueDate to set
     */
    public void setIssueDate(String IssueDate) {
        this.IssueDate = IssueDate;
    }

    /**
     * @return the IssuePlace
     */
    public String getIssuePlace() {
        return IssuePlace;
    }

    /**
     * @param IssuePlace the IssuePlace to set
     */
    public void setIssuePlace(String IssuePlace) {
        this.IssuePlace = IssuePlace;
    }

    /**
     * @return the promoCode
     */
    public String getPromoCode() {
        return promoCode;
    }

    /**
     * @param promoCode the promoCode to set
     */
    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    /**
     * @return the TotalFares
     */
    public String getTotalFares() {
        return TotalFares;
    }

    /**
     * @param TotalFares the TotalFares to set
     */
    public void setTotalFares(String TotalFares) {
        this.TotalFares = TotalFares;
    }

    /**
     * @return the TotalTaxs
     */
    public String getTotalTaxs() {
        return TotalTaxs;
    }

    /**
     * @param TotalTaxs the TotalTaxs to set
     */
    public void setTotalTaxs(String TotalTaxs) {
        this.TotalTaxs = TotalTaxs;
    }

    /**
     * @return the TotalDue
     */
    public String getTotalDue() {
        return TotalDue;
    }

    /**
     * @param TotalDue the TotalDue to set
     */
    public void setTotalDue(String TotalDue) {
        this.TotalDue = TotalDue;
    }

    /**
     * @return the TotalPaid
     */
    public String getTotalPaid() {
        return TotalPaid;
    }

    /**
     * @param TotalPaid the TotalPaid to set
     */
    public void setTotalPaid(String TotalPaid) {
        this.TotalPaid = TotalPaid;
    }

    /**
     * @return the paymentList
     */
    public Collection<Payment> getPaymentList() {
        return paymentList;
    }

    /**
     * @param paymentList the paymentList to set
     */
    public void setPaymentList(Collection<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    /**
     * @return the changedFromList
     */
    public Collection<Ticket> getChangedFromList() {
        return changedFromList;
    }

    /**
     * @param changedFromList the changedFromList to set
     */
    public void setChangedFromList(Collection<Ticket> changedFromList) {
        this.changedFromList = changedFromList;
    }
    
}
