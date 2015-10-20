/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import Entity.CommonInfa.MsgReceiver;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author LI HAO
 */
@Named(value = "viewMsgInDetailMB")
@ViewScoped
public class ViewRcvInDetailManagedBean implements Serializable {

    private boolean replyStatus;
    private MsgReceiver msgDetail;

    @PostConstruct
    public void init() {
        replyStatus = false;
        setMsgDetail((MsgReceiver) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedMessage"));
        ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).removeAttribute("selectedMessage");
    }

    public void replyMessage(ActionEvent event) throws IOException {

        String path = "/CMIpages/replyMessage.xhtml";
        replyStatus = true;
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("repliedMessage", msgDetail);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("isReply", replyStatus);

        String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        FacesContext.getCurrentInstance().getExternalContext().redirect(url + path);

    }

    public String displayTime(Calendar calendarTime) {
        System.out.println("Calendar Time:" + calendarTime.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss");
        String time = sdf.format(calendarTime.getTime()).toString();
        return time;
    }

    /**
     * @return the msgDetail
     */
    public MsgReceiver getMsgDetail() {
        return msgDetail;
    }

    /**
     * @param msgDetail the msgDetail to set
     */
    public void setMsgDetail(MsgReceiver msgDetail) {
        this.msgDetail = msgDetail;
    }

}
