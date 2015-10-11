/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import Entity.CommonInfaEntity.MsgReceiver;
import SessionBean.CommonInfaSB.MsgSessionBeanLocal;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author LI HAO
 */
@Named(value = "msgPollMB")
@ViewScoped
public class MsgPollManagedBean implements Serializable {

    private String currentUserName;
    @EJB
    private MsgSessionBeanLocal msbl;

    private List<MsgReceiver> rcvMsgList;

    @PostConstruct
    public void init() {

        setCurrentUserName((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("UserId"));
        setRcvMsgList((List<MsgReceiver>) msbl.viewReceiveMessage(getCurrentUserName()));
        System.out.println("msgPollMB: MessageSize:" + getRcvMsgList().size());

    }

    public void msgIncrement() {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("You have a new message"));
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
