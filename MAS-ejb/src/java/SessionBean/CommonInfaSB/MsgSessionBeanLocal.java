/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.CommonInfaSB;

import Entity.CommonInfaEntity.MsgReceiver;
import Entity.CommonInfaEntity.MsgSender;
import Entity.CommonInfaEntity.UserEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author LI HAO
 */
@Local
public interface MsgSessionBeanLocal {
    
    public List<UserEntity> getAllUsers();
    public void sendMessage(String username, String msgTitle, String msgContent,  ArrayList<String> rcvUserNames) throws Exception;
    public void readReceiveMessage(Long receiveMessageId) throws Exception;
    public void replyMessage(Long receiveMessageId) throws Exception;
    public void deleteSendMessage(Long sendMessageId) throws Exception;  
    public void deleteReceiveMessage(Long receiveMessageId) throws Exception;
    public Collection<MsgSender> viewSendMessage(String senderId) throws Exception;
    public Collection<MsgReceiver> viewReceiveMessage(String receiverId);
}
