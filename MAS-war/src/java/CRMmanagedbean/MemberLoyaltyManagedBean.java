/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMmanagedbean;

import Entity.ADS.Booker;
import Entity.ADS.Payment;
import Entity.ADS.Reservation;
import SessionBean.CRM.CustomerRelationshipBeanLocal;
import com.sun.mail.smtp.SMTPTransport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author wang
 */
@Named(value = "mlmb")
@ViewScoped
public class MemberLoyaltyManagedBean implements Serializable {

    @EJB
    CustomerRelationshipBeanLocal crbl;
    List<Booker> allBookers = new ArrayList<>();
    List<Double> weighted = new ArrayList<>();
    List<Booker> lvl1Bookers = new ArrayList<>();  //20%
    List<Booker> lvl2Bookers = new ArrayList<>();  //30%
    List<Booker> lvl3Bookers = new ArrayList<>();   //50%
    Map<Long, Double> moneyMap = new HashMap<>();
    Map<Long, Integer> frequencyMap = new HashMap<>();
    Map<Long, Double> mileMap = new HashMap<>();
    Integer viewWhich = 0;

    @PostConstruct
    public void init() {
        this.displayMember();
        this.computeWeighted();
        this.classifyBookers();

    }

    public void displayMember() {
        try {
            this.setAllBookers(crbl.getAllMembers());
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));

        }
    }

    public void computeWeighted() {
        try {
            List<Payment> paymentList = crbl.getPayment();
            for (Booker temp : allBookers) {
                Integer fqCounter = 0;
                Double paymentCounter = 0.0;
                mileMap.put(temp.getId(), temp.getMiles());
                List<Reservation> rsvList = crbl.getReservation(temp.getId());
                if (rsvList.isEmpty()) {
                    moneyMap.put(temp.getId(), paymentCounter);
                    frequencyMap.put(temp.getId(), fqCounter);
                } else {
                    for (Reservation rsvTemp : rsvList) {
                        fqCounter++;
                        for (Payment pTemp : paymentList) {
                            if (pTemp.getReservation().getId().equals(rsvTemp.getId())) {
                                paymentCounter = paymentCounter + rsvTemp.getPayment().getTotalPrice();
                            }
                        }

                    }
                    moneyMap.put(temp.getId(), paymentCounter);
                    frequencyMap.put(temp.getId(), fqCounter);
                }
                weighted.add(moneyMap.get(temp.getId()) + frequencyMap.get(temp.getId()) + mileMap.get(temp.getId()));
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred in computeWeighted: " + ex.getMessage(), ""));
        }
    }

    public void classifyBookers() {
        try {
            List<Booker> sorted = new ArrayList<>();

            Collections.sort(weighted);

            for (Double doubleTemp : weighted) {
                for (Booker temp : allBookers) {
                    if (doubleTemp == (moneyMap.get(temp.getId()) + frequencyMap.get(temp.getId()) + mileMap.get(temp.getId()))) {
                        sorted.add(temp);
                    }
                }
            }
            System.out.println("sorted size:" + sorted);

            Integer lvl3size = new Double(sorted.size() * 0.2).intValue();
            Integer lvl2size = new Double(sorted.size() * 0.3).intValue();
            Integer lvl1size = sorted.size() - lvl3size - lvl2size;
            System.out.println("sizes: " + lvl1size + " " + lvl2size + " " + lvl3size);
            for (int i = 0; i < sorted.size(); i++) {
                if (i < lvl1size) {
                    lvl1Bookers.add(sorted.get(i));
                } else if (i >= lvl1size && i < (lvl1size + lvl2size)) {
                    lvl2Bookers.add(sorted.get(i));
                } else if (i >= (lvl1size + lvl2size)) {
                    lvl3Bookers.add(sorted.get(i));
                }

            }

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred in classifyBookers: " + ex.getMessage(), ""));
        }
    }

    public void sendEamil() {
        List<Booker> mailList = new ArrayList<>();
        if (viewWhich == 1) {
            mailList = lvl1Bookers;
        } else if (viewWhich == 2) {
            mailList = lvl2Bookers;
        } else if (viewWhich == 3) {
            mailList = lvl3Bookers;
        } 

        if (mailList.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + "No booker is in this level!", ""));
        } else {
            for (Booker temp : mailList) {
                // Recipient's email ID needs to be mentioned.
                String to = temp.getEmail();

                // Sender's email ID needs to be mentioned
                String from = "welovemas@gmail.com";

                // Assuming you are sending email from localhost
                String host = "smtp.gmail.com";

                // Get system properties
                Properties properties = System.getProperties();

                // Setup mail server
                properties.setProperty("mail.smtp.host", host);
                properties.setProperty("mail.smtp.socketFactory.fallback", "false");
                properties.setProperty("mail.smtp.port", "465");
                properties.setProperty("mail.smtps.auth", "true");
                 properties.setProperty("mail.user", "snowky1993@gmail.com");
                properties.setProperty("mail.password", "WHyq1993");
                // Get the default Session object.
                Session session = Session.getDefaultInstance(properties);

                try {
                    // Create a default MimeMessage object.
                    MimeMessage message = new MimeMessage(session);

                    // Set From: header field of the header.
                    message.setFrom(new InternetAddress(from));

                    // Set To: header field of the header.
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

                    // Set Subject: header field
                    message.setSubject("Greeting from Merlione Airlines");

                    BodyPart messageBodyPart = new MimeBodyPart();

                    // Fill the message
                    messageBodyPart.setText("Dear " + temp.getFirstName() + ", thank you for being a loyal customer of Merlion Airlines. Please check below the recent deals and enjoy!");
                    Multipart multipart = new MimeMultipart();
                    multipart.addBodyPart(messageBodyPart);

                    // Attachment
//                    messageBodyPart = new MimeBodyPart();
//                    String filename = "file:///Users/wang/NetBeansProjects/MAS/MAS-war/web/images/Happybirthday.jpg";
////                    String path = "file:///Users/wang/NetBeansProjects/MAS/MAS-war/web/images/Happybirthday.jpg";
////                    String base = "file:///Users/wang/NetBeansProjects/MAS/MAS-war/src/java/CRMmanagedbean/BirthdayEmailManagedBean.java";
////                    String filename = new File(base).toURI().relativize(new File(path).toURI()).getPath();
////                    System.out.println("Path: "+filename);
//                    DataSource source = new FileDataSource(filename);
//                    messageBodyPart.setDataHandler(new DataHandler(source));
//                    messageBodyPart.setFileName(filename);
//                    multipart.addBodyPart(messageBodyPart);
                    message.setContent(multipart);
                    // Send message
                    SMTPTransport t = (SMTPTransport) session.getTransport("smtps");
                    t.connect("smtp.gmail.com", "snowky1993@gmail.com", "WHyq1993");
                    t.sendMessage(message, message.getAllRecipients());
                    // Transport.send(message);

                    System.out.println("Sent message successfully....");
                    t.close();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info : " + "Emails has been sent to" + temp.getFirstName() + " " + temp.getLastName() + "successfully!", ""));

                } catch (MessagingException mex) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occured : " + "Emails has not been sent to " + temp.getFirstName() + " " + temp.getLastName(), ""));
                    System.out.println("Error");
                    //    mex.printStackTrace();
                }

            }
        }
    }

    public List<Booker> getAllBookers() {
        return allBookers;
    }

    public void setAllBookers(List<Booker> allBookers) {
        this.allBookers = allBookers;
    }

    public List<Booker> getLvl1Bookers() {
        return lvl1Bookers;
    }

    public void setLvl1Bookers(List<Booker> lvl1Bookers) {
        this.lvl1Bookers = lvl1Bookers;
    }

    public List<Booker> getLvl2Bookers() {
        return lvl2Bookers;
    }

    public void setLvl2Bookers(List<Booker> lvl2Bookers) {
        this.lvl2Bookers = lvl2Bookers;
    }

    public List<Booker> getLvl3Bookers() {
        return lvl3Bookers;
    }

    public void setLvl3Bookers(List<Booker> lvl3Bookers) {
        this.lvl3Bookers = lvl3Bookers;
    }

    public List<Double> getWeighted() {
        return weighted;
    }

    public void setWeighted(List<Double> weighted) {
        this.weighted = weighted;
    }

    public Map<Long, Double> getMoneyMap() {
        return moneyMap;
    }

    public void setMoneyMap(Map<Long, Double> moneyMap) {
        this.moneyMap = moneyMap;
    }

    public Map<Long, Integer> getFrequencyMap() {
        return frequencyMap;
    }

    public void setFrequencyMap(Map<Long, Integer> frequencyMap) {
        this.frequencyMap = frequencyMap;
    }

    public Map<Long, Double> getMileMap() {
        return mileMap;
    }

    public void setMileMap(Map<Long, Double> mileMap) {
        this.mileMap = mileMap;
    }

    public Integer getViewWhich() {
        return viewWhich;
    }

    public void setViewWhich(Integer viewWhich) {
        this.viewWhich = viewWhich;
    }

    /**
     * Creates a new instance of MemberLoyaltyManagedBean
     */
    public MemberLoyaltyManagedBean() {
    }

}
