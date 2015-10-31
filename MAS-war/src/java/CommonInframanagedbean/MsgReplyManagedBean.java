/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CommonInframanagedbean;

import Entity.CommonInfa.MsgReceiver;
import SessionBean.CommonInfra.MsgBeanLocal;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author LI HAO
 */
@Named(value = "msgReplyMB")
@ViewScoped
public class MsgReplyManagedBean implements Serializable {

    @EJB
    private MsgBeanLocal msbl;

    private MsgReceiver replyMsg;
    private String rcvUsername;
    private String senderUsername;
    private String msgTitle;
    private String msgContent;
    private ArrayList<String> rcvNameLists;

    @PostConstruct
    public void init() {

        replyMsg = (MsgReceiver) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("repliedMessage");
        ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).removeAttribute("repliedMessage");
        rcvUsername = replyMsg.getSenderName();
        System.out.println("MsgReplyManagedBean: receiveId:" + getRcvUsername());
        setSenderUsername(getReplyMsg().getReceiver().getUsername());
        System.out.println("MsgReplyManagedBean: senderId:" + getSenderUsername());
        setMsgTitle("Re: " + getReplyMsg().getMsgTitle());
        setMsgContent("Reply:" + getReplyMsg().getMsgContent());

    }

    public void sendMessage(ActionEvent event) throws Exception {
        rcvNameLists = new ArrayList<String>();

        rcvNameLists.add(rcvUsername);


        msbl.sendMessage(senderUsername, msgTitle, msgContent, rcvNameLists);
        msbl.replyMessage(replyMsg.getRcvMsgId());

        System.err.println("sendMessage(): Message Sent ");
        FacesContext context = FacesContext.getCurrentInstance();

        context.addMessage(null, new FacesMessage("Message", "Message is sent successfully."));
        msgTitle = null;

    }

    /**
     * @return the replyMsg
     */
    public MsgReceiver getReplyMsg() {
        return replyMsg;
    }

    /**
     * @param replyMsg the replyMsg to set
     */
    public void setReplyMsg(MsgReceiver replyMsg) {
        this.replyMsg = replyMsg;
    }

    /**
     * @return the rcvUsername
     */
    public String getRcvUsername() {
        return rcvUsername;
    }

    /**
     * @param rcvUsername the rcvUsername to set
     */
    public void setRcvUsername(String rcvUsername) {
        this.rcvUsername = rcvUsername;
    }

    /**
     * @return the senderUsername
     */
    public String getSenderUsername() {
        return senderUsername;
    }

    /**
     * @param senderUsername the senderUsername to set
     */
    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
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
     * @return the rcvNameLists
     */
    public ArrayList<String> getRcvNameLists() {
        return rcvNameLists;
    }

    /**
     * @param rcvNameLists the rcvNameLists to set
     */
    public void setRcvNameLists(ArrayList<String> rcvNameLists) {
        this.rcvNameLists = rcvNameLists;
    }

}
