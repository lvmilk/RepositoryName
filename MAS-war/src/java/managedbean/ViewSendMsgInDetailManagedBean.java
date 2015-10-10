/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import Entity.CommonInfaEntity.MsgSender;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author LI HAO
 */
@Named(value = "viewSendMsgInDetailMB")
@ViewScoped
public class ViewSendMsgInDetailManagedBean implements Serializable {

    private MsgSender msgDetail;
    private boolean replyStatus;

    @PostConstruct
    public void init() {
        replyStatus = false;
        msgDetail = (MsgSender) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedSendMessage");
        ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).removeAttribute("selectedSendMessage");
        System.out.println("ViewSendMsgInDetailManagedBean : " + msgDetail.getSentMsgId());
    }

    public String displayTime(Calendar calendarTime) {
        System.err.println("Calendar Time:" + calendarTime.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss");
        String time = sdf.format(calendarTime.getTime()).toString();
        return time;
    }

    /**
     * @return the msgDetail
     */
    public MsgSender getMsgDetail() {
        return msgDetail;
    }

    /**
     * @param msgDetail the msgDetail to set
     */
    public void setMsgDetail(MsgSender msgDetail) {
        this.msgDetail = msgDetail;
    }

    /**
     * @return the replyStatus
     */
    public boolean isReplyStatus() {
        return replyStatus;
    }

    /**
     * @param replyStatus the replyStatus to set
     */
    public void setReplyStatus(boolean replyStatus) {
        this.replyStatus = replyStatus;
    }

}
