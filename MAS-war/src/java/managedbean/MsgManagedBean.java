/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import Entity.CommonInfaEntity.UserEntity;
import SessionBean.CommonInfaSB.MsgSessionBeanLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author LI HAO
 */
@Named(value = "msgManagedBean")
@ViewScoped
public class MsgManagedBean implements Serializable {

    @EJB
    private MsgSessionBeanLocal msbl;

    private List<UserEntity> users;
    private List<UserEntity> selectedUsers;
    private String currentUserName;
    private String msgContent;
    private String rcvUsernames;
    private ArrayList rcvUsernamesList;

    private String msgTitle;

    public MsgManagedBean() {

    }

    @PostConstruct
    public void init() {

        currentUserName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("UserId");
        users = msbl.getAllUsers();
        System.err.println("Internal Message Manage Bean: all user Size" + users.size());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("users", users);

    }

    @PreDestroy
    public void destroy() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("users");
    }

    public List<UserEntity> usersList(String query) {

        List<UserEntity> filteredUsers = new ArrayList<>();

        for (UserEntity u : users) {
            String searchString = u.getUsername();

            if (searchString.toLowerCase().contains(query.toLowerCase())) {
                filteredUsers.add(u);
                System.out.println("add user successfully:"+u );
                System.out.println("filtered users:"+filteredUsers);
            }
        }
        
        return filteredUsers;
    }

    public void sendMessage(ActionEvent event) throws Exception {
        rcvUsernamesList = new ArrayList<String>();

        System.out.println("****haha");

        for (UserEntity ue : selectedUsers) {
            System.err.println("userEntityString: send to --> " + ue.getUsername());
            rcvUsernamesList.add(ue.getUsername());
            System.out.println("****hehe");
        }

        msbl.sendMessage(currentUserName, msgTitle, msgContent, rcvUsernamesList);
        System.err.println("sendMessage(): Message Sent ");
        FacesContext context = FacesContext.getCurrentInstance();

        context.addMessage(null, new FacesMessage("Message", "Message is sent successfully."));

        msgTitle = null;
        selectedUsers.clear();

    }

    /**
     * @return the users
     */
    public List<UserEntity> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }

    /**
     * @return the selectedUsers
     */
    public List<UserEntity> getSelectedUsers() {
        System.out.println("*******selected user is: "+ selectedUsers);
        return selectedUsers;
    }

    /**
     * @param selectedUsers the selectedUsers to set
     */
    public void setSelectedUsers(List<UserEntity> selectedUsers) {
        this.selectedUsers = selectedUsers;
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
}
