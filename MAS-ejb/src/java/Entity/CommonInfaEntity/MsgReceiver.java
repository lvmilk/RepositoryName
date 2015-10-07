/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.CommonInfaEntity;

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
public class MsgReceiver implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long rcvMsgId;

    private String msgTitle;
    private String msgContent;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Calendar cldTime;

    private Boolean delStatus;
    private Boolean readStatus;
    private String senderName;
    
    private Boolean replied;

    @ManyToOne
    private MsgSender message;

    @ManyToOne
    private UserEntity receiver;
    
    public void create(MsgSender message)
    {
        this.message = message;
        this.senderName = message.getSender().getUsername();
        this.msgContent = message.getMsgContent();
        this.msgTitle = message.getMsgTitle();
        this.cldTime = message.getCldTime();
        this.delStatus=false;
        this.readStatus=false;
    }
    
    
    public Long getRcvMsgId() {
        return rcvMsgId;
    }

    public void setRcvMsgId(Long rcvMsgId) {
        this.rcvMsgId = rcvMsgId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rcvMsgId != null ? rcvMsgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the rcvMsgId fields are not set
        if (!(object instanceof MsgReceiver)) {
            return false;
        }
        MsgReceiver other = (MsgReceiver) object;
        if ((this.rcvMsgId == null && other.rcvMsgId != null) || (this.rcvMsgId != null && !this.rcvMsgId.equals(other.rcvMsgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.CommonInfaEntity.msgReceiver[ id=" + rcvMsgId + " ]";
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
     * @return the readStatus
     */
    public Boolean getReadStatus() {
        return readStatus;
    }

    /**
     * @param readStatus the readStatus to set
     */
    public void setReadStatus(Boolean readStatus) {
        this.readStatus = readStatus;
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
     * @return the replied
     */
    public Boolean getReplied() {
        return replied;
    }

    /**
     * @param replied the replied to set
     */
    public void setReplied(Boolean replied) {
        this.replied = replied;
    }

    /**
     * @return the message
     */
    public MsgSender getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(MsgSender message) {
        this.message = message;
    }

    /**
     * @return the receiver
     */
    public UserEntity getReceiver() {
        return receiver;
    }

    /**
     * @param receiver the receiver to set
     */
    public void setReceiver(UserEntity receiver) {
        this.receiver = receiver;
    }

}
