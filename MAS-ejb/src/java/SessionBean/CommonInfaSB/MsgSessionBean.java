/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.CommonInfaSB;

import Entity.CommonInfaEntity.MsgReceiver;
import Entity.CommonInfaEntity.MsgSender;
import Entity.CommonInfaEntity.UserEntity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author LI HAO
 */
@Stateless
public class MsgSessionBean implements MsgSessionBeanLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<UserEntity> getAllUsers() {
        ArrayList<UserEntity> userList = new ArrayList<UserEntity>();
        Query q = em.createQuery("Select u from UserEntity u");
        for (Object o : q.getResultList()) {
            UserEntity user = (UserEntity) o;
            userList.add(user);
        }
        return userList;
    }

    @Override
    public void sendMessage(String username, String msgTitle, String msgContent, ArrayList<String> rcvNames) throws Exception {
        UserEntity sender = em.find(UserEntity.class, username);
        if (sender == null) {
            throw new Exception("Sender is not found!");
        } else {
            System.out.println("message sessionBean sendMessage(): getUserName: " + sender.getUsername());

            //initialise the new message
            //get the list of receive
            //set the senderName
            String senderName = sender.getUsername();
            ArrayList<String> rcvNameArray = new ArrayList<String>();

            //set the time
            Calendar sendTime = Calendar.getInstance();
//            sendTime.getTime();
//
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
//            String time = sdf.format(sendTime.getTime()).toString();
            System.out.println("sessionbean internal message module sendMessage(): time : " + sendTime.getTime());
            System.out.println("sessionbean internal message module sendMessage(): title : " + msgTitle);
            System.out.println("sessionbean internal message module sendMessage(): content : " +  msgContent);

            //instanlise a sendmessage entity
            MsgSender sendNewMessage = new MsgSender();
            sendNewMessage.create(senderName, msgTitle, msgContent, sendTime);
            em.persist(sendNewMessage);
            em.flush();

            sender.getSendMessage().add(sendNewMessage);
            em.flush();

            sendNewMessage.setSender(sender);
            em.flush();

            System.err.println("sessionbean internal message module setMessageSender(): here message has been set in Internal Message Entity under a sender");
            Integer i;
            Collection<MsgReceiver> receiverMessageList = new ArrayList<MsgReceiver>();
            for (i = 0; i < rcvNames.size(); i++) {
                UserEntity receiver = em.find(UserEntity.class, rcvNames.get(i));
                if (receiver == null) {
                    throw new Exception("Receiver is not found!");
                } else {

                    String displayReceiverIdAndName = "(" + receiver.getUsername()+ ")";
                    rcvNameArray.add(displayReceiverIdAndName);
                    MsgReceiver receiveMessage = new MsgReceiver();
                    receiveMessage.create(sendNewMessage);
                    em.persist(receiveMessage);
                    em.flush();
                    System.out.println("sessionbean internal message module sendMessage(): recieve message time : " + sdf.format(receiveMessage.getCldTime().getTime()).toString());

                    receiver.getReceiveMessage().add(receiveMessage);
                    em.flush();

                    receiveMessage.setReceiver(receiver);
                    em.flush();

                    System.err.println("sessionbean internal message module sendMessage(): receiverId: " + receiveMessage.getReceiver().getUsername());

                    receiverMessageList.add(receiveMessage);
                    em.flush();

                }

            }
            sendNewMessage.setRcvNames(rcvNameArray);
            System.out.println("sessionbean internal message module sendMessage(): time : " + sdf.format(sendNewMessage.getCldTime().getTime()).toString());
            sendNewMessage.setReceiveMessages(receiverMessageList);
            em.flush();

        }
    }
    
    @Override
    public void readReceiveMessage(Long receiveMessageId) throws Exception
    {
        
    }
    
    @Override
    public void replyMessage(Long receiveMessageId) throws Exception
    {
        
    }
    
    
    public void deleteSendMessage(Long sendMessageId) throws Exception
    {
        
    }
    
    
    public void deleteReceiveMessage(Long receiveMessageId) throws Exception
    {
    }
    
    
    
//    public Collection<MsgSender> viewSendMessage(String senderId) throws Exception
//    {
//        
//    }
//    public Collection<MsgReceiver> viewReceiveMessage(String receiverId)
//    {
//    }
    
    
}
