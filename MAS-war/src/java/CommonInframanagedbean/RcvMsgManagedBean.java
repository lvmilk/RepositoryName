/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CommonInframanagedbean;

import Entity.CommonInfa.MsgReceiver;
import SessionBean.CommonInfra.MsgBeanLocal;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 *
 * @author LI HAO
 */
@Named(value = "rcvMsgMB")
@ViewScoped
public class RcvMsgManagedBean implements Serializable {

    private String currentUserName;
    private String temp;

    @EJB
    private MsgBeanLocal msbl;

    private List<MsgReceiver> rcvMsgList;
    private MsgReceiver selectedMessage;
    private List<MsgReceiver> checkedMessageList;

    @PostConstruct
    public void init() {
        try {
            temp = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("UserId");
            if (!temp.equals("admin")) {
                setCurrentUserName((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("UserId"));
                setRcvMsgList((List<MsgReceiver>) msbl.viewReceiveMessage(getCurrentUserName()));
                System.out.println("RcvMsgManagedBean: MessageSize:" + getRcvMsgList().size());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @PreDestroy
    public void destroy() {

    }

    public void readMessage(MsgReceiver message) throws Exception {
        setSelectedMessage(message);

        Boolean isOpened;
        isOpened = getSelectedMessage().getReadStatus();

        if (!isOpened) {
            System.err.println("my first time read");
            msbl.readReceiveMessage(getSelectedMessage().getRcvMsgId());
        }

        String path = "/CMIpages/viewRcvMsgInDetail.xhtml";
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedMessage", getSelectedMessage());

        String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        FacesContext.getCurrentInstance().getExternalContext().redirect(url + path);

    }

    public void toDeleteMsg(List<MsgReceiver> messageList) throws Exception {
        RequestContext context = RequestContext.getCurrentInstance();
        if (messageList.isEmpty()) {
            context.execute("alert('Please select message(s) first.');");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select messages first. ", ""));
        } else {
            context.execute("PF('dlgRcvMsg').show()");
        }
    }

    public void deleteMessage(List<MsgReceiver> messageList) throws Exception {

        for (MsgReceiver msg : messageList) {
            msbl.deleteReceiveMessage(msg.getRcvMsgId());
        }

        setRcvMsgList((List<MsgReceiver>) msbl.viewReceiveMessage(getCurrentUserName()));

        String path = "/CMIpages/viewReceivedMessage.xhtml";
        String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        FacesContext.getCurrentInstance().getExternalContext().redirect(url + path);

    }

    public String displayTime(Calendar calendarTime) {
        System.err.println("Calendar Time:" + calendarTime.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss");
        String time = sdf.format(calendarTime.getTime()).toString();
        return time;
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

    /**
     * @return the selectedMessage
     */
    public MsgReceiver getSelectedMessage() {
        return selectedMessage;
    }

    /**
     * @param selectedMessage the selectedMessage to set
     */
    public void setSelectedMessage(MsgReceiver selectedMessage) {
        this.selectedMessage = selectedMessage;
    }

    /**
     * @return the checkedMessageList
     */
    public List<MsgReceiver> getCheckedMessageList() {
        return checkedMessageList;
    }

    /**
     * @param checkedMessageList the checkedMessageList to set
     */
    public void setCheckedMessageList(List<MsgReceiver> checkedMessageList) {
        this.checkedMessageList = checkedMessageList;
    }
}
