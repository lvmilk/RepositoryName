/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.CommonInfa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author LI HAO
 */
@Entity
public class MsgSender implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sentMsgId;
    
    private String msgTitle;
    private String msgContent;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Calendar cldTime;
    

    private String senderName;
    private Boolean delStatus;  
    private ArrayList<String> rcvNames;
    
    
    @ManyToOne
    private UserEntity sender;
   
    
    @OneToMany (cascade = {CascadeType.PERSIST},mappedBy = "message")
    private Collection<MsgReceiver> receiveMessages;
    
    public void create(String senderName, String msgTitle, String msgContent, Calendar cldTime)
    {
        this.senderName=senderName;
        this.msgTitle=msgTitle;
        this.msgContent=msgContent;
        this.cldTime=cldTime;
        this.delStatus=false;
    }
    

    public Long getSentMsgId() {
        return sentMsgId;
    }

    public void setSentMsgId(Long sentMsgId) {
        this.sentMsgId = sentMsgId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sentMsgId != null ? sentMsgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the sentMsgId fields are not set
        if (!(object instanceof MsgSender)) {
            return false;
        }
        MsgSender other = (MsgSender) object;
        if ((this.sentMsgId == null && other.sentMsgId != null) || (this.sentMsgId != null && !this.sentMsgId.equals(other.sentMsgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.CommonInfaEntity.msgSender[ id=" + sentMsgId + " ]";
    }

    /**
     * @return the msgTitle
     */
    public String getMsgTitle() {
        return msgTitle;
    }

    /**
     * @param msgTitle the msgTitle to set
     */
    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    /**
     * @return the msgContent
     */
    public String getMsgContent() {
        return msgContent;
    }

    /**
     * @param msgContent the msgContent to set
     */
    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    /**
     * @return the cldTime
     */
    public Calendar getCldTime() {
        return cldTime;
    }

    /**
     * @param cldTime the cldTime to set
     */
    public void setCldTime(Calendar cldTime) {
        this.cldTime = cldTime;
    }


    /**
     * @return the senderName
     */
    public String getSenderName() {
        return senderName;
    }

    /**
     * @param senderName the senderName to set
     */
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    /**
     * @return the delStatus
     */
    public Boolean getDelStatus() {
        return delStatus;
    }

    /**
     * @param delStatus the delStatus to set
     */
    public void setDelStatus(Boolean delStatus) {
        this.delStatus = delStatus;
    }

    /**
     * @return the rcvNames
     */
    public ArrayList<String> getRcvNames() {
        return rcvNames;
    }

    /**
     * @param rcvNames the rcvNames to set
     */
    public void setRcvNames(ArrayList<String> rcvNames) {
        this.rcvNames = rcvNames;
    }

    /**
     * @return the sender
     */
    public UserEntity getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(UserEntity sender) {
        this.sender = sender;
    }

    /**
     * @return the receiveMessages
     */
    public Collection<MsgReceiver> getReceiveMessages() {
        return receiveMessages;
    }

    /**
     * @param receiveMessages the receiveMessages to set
     */
    public void setReceiveMessages(Collection<MsgReceiver> receiveMessages) {
        this.receiveMessages = receiveMessages;
    }
    
}
