/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CommonInframanagedbean;

import Entity.CommonInfa.MsgReceiver;
import SessionBean.CommonInfra.MsgBeanLocal;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import helper.msgHelper;

/**
 *
 * @author LI HAO
 */
@Named(value = "msgPollMB")
@ViewScoped
public class MsgPollManagedBean implements Serializable {

    private int msgCount;
    private int helperCount;
    private String currentUserName;
    private String temp;
    @EJB
    private MsgBeanLocal msbl;

    private List<MsgReceiver> rcvMsgList;
    private msgHelper mh = new msgHelper();

    @PostConstruct
    public void init() {
        temp = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("UserId");
        if (!temp.equals("admin")) {
            setCurrentUserName((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("UserId"));

            setRcvMsgList((List<MsgReceiver>) msbl.viewReceiveMessage(getCurrentUserName()));
            System.out.println("msgPollMB: MessageSize in init :" + getRcvMsgList().size());
            msgCount = getRcvMsgList().size();
            mh.setMsgCount(msgCount);
        }

    }

    public void msgIncrement() {
        currentUserName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("UserId");
        if (!currentUserName.equals("admin")) {
            rcvMsgList = (List<MsgReceiver>) msbl.viewReceiveMessage(currentUserName);
            msgCount = rcvMsgList.size();
            helperCount = mh.getMsgCount();
            System.out.println("msgPollMB: MessageSize in msgIncrement :" + getRcvMsgList().size());
            if (msgCount > helperCount) {
                System.out.println("*************It is already inside the loop************");
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage("You have a new message"));
            }
        }
    }

    /**
     * @return the currentUserName
     */
    public String getCurrentUserName() {
        return currentUserName;
    }

    /**
     * @param currentUserName the currentUserName to set
     */
    public void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }

    /**
     * @return the rcvMsgList
     */
    public List<MsgReceiver> getRcvMsgList() {
        return rcvMsgList;
    }

    /**
     * @param rcvMsgList the rcvMsgList to set
     */
    public void setRcvMsgList(List<MsgReceiver> rcvMsgList) {
        this.rcvMsgList = rcvMsgList;
    }
}
