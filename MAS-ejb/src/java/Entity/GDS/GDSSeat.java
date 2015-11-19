/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.GDS;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 *
 * @author LI HAO
 */
@Entity
@XmlAccessorType( XmlAccessType.FIELD)
public class GDSSeat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String seatNo;
    private Integer rowNo;
    private char colNo;
    
    private String status;
    private String cabinClass;


    @ManyToOne
    private GDSFlight flight;

    @OneToOne
    private GDSTicket ticket;

    public GDSSeat() {
    }

    public void createSeat(String seatNo,Integer rowNo, char colNo, String status,String cabinClass ){
        this.seatNo=seatNo;
        this.rowNo=rowNo;
        this.colNo=colNo;
        this.status=status;
        this.cabinClass=cabinClass;
        
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public Integer getRowNo() {
        return rowNo;
    }

    public void setRowNo(Integer rowNo) {
        this.rowNo = rowNo;
    }

    public char getColNo() {
        return colNo;
    }

    public void setColNo(char colNo) {
        this.colNo = colNo;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCabinClass() {
        return cabinClass;
    }

    public void setCabinClass(String cabinClass) {
        this.cabinClass = cabinClass;
    }

    public GDSFlight getFlight() {
        return flight;
    }

    public void setFlight(GDSFlight flight) {
        this.flight = flight;
    }

    public GDSTicket getTicket() {
        return ticket;
    }

    public void setTicket(GDSTicket ticket) {
        this.ticket = ticket;
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
        if (!(object instanceof GDSSeat)) {
            return false;
        }
        GDSSeat other = (GDSSeat) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.GDS.GDSSeat[ id=" + id + " ]";
    }

}
