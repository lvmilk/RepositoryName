/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.CommonInfra;

import Entity.CommonInfa.MsgReceiver;
import Entity.CommonInfa.MsgSender;
import Entity.CommonInfa.UserEntity;
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
public class MsgBean implements MsgBeanLocal,MsgBeanRemote {

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
            System.out.println("message sessionBean sendMessage(): " + sender.getUsername());

            //set the senderName
            String senderName = sender.getUsername();
            ArrayList<String> rcvNameArray = new ArrayList<String>();

            //set the time
            Calendar sendTime = Calendar.getInstance();

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            System.out.println("sessionbean internal message module sendMessage(): time : " + sendTime.getTime());
            System.out.println("sessionbean internal message module sendMessage(): title : " + msgTitle);
            System.out.println("sessionbean internal message module sendMessage(): content : " + msgContent);

            //initialize a sendmessage entity
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

                    String displayReceiverIdAndName = "(" + receiver.getUsername() + ")";
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
    public void readReceiveMessage(Long receiveMessageId) throws Exception {

    }

    @Override
    public void replyMessage(Long rcvMsgId) throws Exception {
        MsgReceiver rcvMsg = em.find(MsgReceiver.class, rcvMsgId);
        if (rcvMsg == null) {
            throw new Exception("Received Message is not found!");
        } else {
            System.out.println("session bean readReceiveMessage: update state!");
            rcvMsg.setReplied(true);
        }
        em.flush();
    }

    @Override
    public void deleteSendMessage(Long sendMsgId) throws Exception {
        MsgSender sendMessage = em.find(MsgSender.class, sendMsgId);
        if (sendMessage == null) {
            throw new Exception("Sent Message is not found!");
        } else {
            System.out.println("session bean deleteSentMessage: update state!");
            sendMessage.setDelStatus(true);
            System.out.println("session bean deleteSentMessage: update update!");
        }
        em.flush();
    }

    @Override
    public void deleteReceiveMessage(Long rcvMsgId) throws Exception {
        MsgReceiver receiveMessage = em.find(MsgReceiver.class, rcvMsgId);
        if (receiveMessage == null) {
            throw new Exception("Received Message is not found!");
        } else {

            receiveMessage.setDelStatus(true);
        }

        em.flush();
    }

    @Override
    public Collection<MsgSender> viewSendMessage(String senderName) throws Exception {
        Collection<MsgSender> sendMessageList = new ArrayList<MsgSender>();
        UserEntity sender = em.find(UserEntity.class, senderName);
        if (sender == null) {
            throw new Exception("User Id is not found!");
        }

        for (MsgSender notDeletedMessage : sender.getSendMessage()) {
            if (!notDeletedMessage.getDelStatus()) {
                sendMessageList.add(notDeletedMessage);
            }
        }

        return sendMessageList;
    }

    @Override
    public Collection<MsgReceiver> viewReceiveMessage(String rcvUsername) {
        Collection<MsgReceiver> rcvMsgList = new ArrayList<MsgReceiver>();
        UserEntity rcv = em.find(UserEntity.class, rcvUsername);
        if (rcv == null) {
            return null;
        }
        System.out.println("InternalMessageModule: ---> rcvUsername: " + rcv.getUsername());

        for (MsgReceiver notDeletedMessage : rcv.getReceiveMessage()) {
            if (!notDeletedMessage.getDelStatus()) {
                rcvMsgList.add(notDeletedMessage);
            }
        }
        System.out.println("InternalMessageModule: ---> messageSize: " + rcvMsgList.size());
        return rcvMsgList;
    }

}
