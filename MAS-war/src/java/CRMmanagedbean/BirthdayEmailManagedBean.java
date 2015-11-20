/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMmanagedbean;

import Entity.ADS.Booker;
import SessionBean.CRM.CustomerRelationshipBeanLocal;
import com.sun.mail.smtp.SMTPTransport;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
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
@Named(value = "bemb")
@ViewScoped
public class BirthdayEmailManagedBean implements Serializable {

    @EJB
    CustomerRelationshipBeanLocal bebl;

    private Date currentDate;
    private List<Booker> birthdayMemberList = new ArrayList<>();

    @PostConstruct
    public void init() {
        currentDate = new Date();
        this.displayMember(currentDate);
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public List<Booker> getBirthdayMemberList() {
        return birthdayMemberList;
    }

    public void setBirthdayMemberList(List<Booker> birthdayMemberList) {
        this.birthdayMemberList = birthdayMemberList;
    }

    /**
     * Creates a new instance of BirthdayEmailManagedBean
     */
    public void displayMember(Date date) {
        try {
            this.setBirthdayMemberList(bebl.getCurrentMonthBabies(date));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));

        }
    }

    public void sendEamil() {
        if (birthdayMemberList.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + "No booker celebrates birthday in this month", ""));
        } else {
            for (Booker temp : birthdayMemberList) {
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
                    message.setSubject("Happy Birthday From Merlion Airlines");

                    BodyPart messageBodyPart = new MimeBodyPart();

                    // Fill the message
                    messageBodyPart.setText("Dear " + temp.getFirstName() + ", on your birthday you are wished all that you hope for, all that you dream of, all that makes you happy! Happy Birthday! ");
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
                    t.connect("smtp.gmail.com", "welovemas@gmail.com", "iloveis3102");
                    t.sendMessage(message, message.getAllRecipients());
                    // Transport.send(message);

                    System.out.println("Sent message successfully....");
                    t.close();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info : " + "Emails has been sent to" + temp.getFirstName() + " " + temp.getLastName() + "successfully!", ""));

                } catch (MessagingException mex) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occured : " + "Emails has not been sent to" + temp.getFirstName() + " " + temp.getLastName(), ""));

                    System.out.println("Error");
                    mex.printStackTrace();
                }

            }
        }
    }

    public BirthdayEmailManagedBean() {
    }

}
