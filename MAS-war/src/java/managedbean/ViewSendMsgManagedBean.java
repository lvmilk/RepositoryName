/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import Entity.CommonInfaEntity.MsgReceiver;
import Entity.CommonInfaEntity.MsgSender;
import SessionBean.CommonInfaSB.MsgSessionBeanLocal;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
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
@Named(value = "viewSendMsgMB")
@ViewScoped
public class ViewSendMsgManagedBean implements Serializable {

    @EJB
    private MsgSessionBeanLocal msbl;
    
    private String temp;

    private String currentUsername;
    private List<MsgSender> sendMsgList;
    private MsgSender selectedMsg;
    private List<MsgSender> checkedMessageList;

    @PostConstruct
    public void init() {
        try {
            temp = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("UserId");
            if (!temp.equals("admin")) {
                setCurrentUsername((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("UserId"));
                setSendMsgList((List<MsgSender>) msbl.viewSendMessage(getCurrentUsername()));
                System.out.println("ViewSendMsgManagedBean: MessageSize:" + getSendMsgList().size());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void readMessage(MsgSender message) throws Exception {
        selectedMsg = message;

        String path = "/CMIpages/viewSendMsgInDetail.xhtml";
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedSendMessage", selectedMsg);

        String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        FacesContext.getCurrentInstance().getExternalContext().redirect(url + path);
        System.out.println("Direct to send view in detail page");

    }

    public String displayTime(Calendar calendarTime) {
        System.err.println("Calendar Time:" + calendarTime.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss");
        String time = sdf.format(calendarTime.getTime()).toString();
        return time;
    }

    public void toDeleteMsg(List<MsgSender> messageList) throws Exception {
        RequestContext context = RequestContext.getCurrentInstance();
        if (messageList.isEmpty()) {
            context.execute("alert('Please select message(s) first.');");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select messages first. ", ""));
        } else {
            context.execute("PF('dlgSendMsg').show()");
        }
    }

    public void deleteMessage(List<MsgSender> messageList) throws Exception {

        for (MsgSender msg : messageList) {
            msbl.deleteSendMessage(msg.getSentMsgId());
        }

        sendMsgList = (List<MsgSender>) msbl.viewSendMessage(currentUsername);

        String path = "/CMIpages/viewSendMessage.xhtml";
        String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        FacesContext.getCurrentInstance().getExternalContext().redirect(url + path);

    }

    /**
     * @return the currentUsername
     */
    public String getCurrentUsername() {
        return currentUsername;
    }

    /**
     * @param currentUsername the currentUsername to set
     */
    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }

    /**
     * @return the sendMsgList
     */
    public List<MsgSender> getSendMsgList() {
        return sendMsgList;
    }

    /**
     * @param sendMsgList the sendMsgList to set
     */
    public void setSendMsgList(List<MsgSender> sendMsgList) {
        this.sendMsgList = sendMsgList;
    }

    /**
     * @return the selectedMsg
     */
    public MsgSender getSelectedMsg() {
        return selectedMsg;
    }

    /**
     * @param selectedMsg the selectedMsg to set
     */
    public void setSelectedMsg(MsgSender selectedMsg) {
        this.selectedMsg = selectedMsg;
    }

    /**
     * @return the checkedMessageList
     */
    public List<MsgSender> getCheckedMessageList() {
        return checkedMessageList;
    }

    /**
     * @param checkedMessageList the checkedMessageList to set
     */
    public void setCheckedMessageList(List<MsgSender> checkedMessageList) {
        this.checkedMessageList = checkedMessageList;
    }

}
