/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMClientmanagedbean;

import com.sun.mail.smtp.SMTPTransport;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author wang
 */
@Named(value = "comb")
@ViewScoped
public class ContactManagedBean implements Serializable {

    private String email;
    private String subject;
    private String text;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void sendEmail() {

        // Recipient's email ID needs to be mentioned.
        String to = "welovemas@gmail.com";

        // Sender's email ID needs to be mentioned
        String from = "snowky1993@gmail.com";

        // Assuming you are sending email from localhost
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);
//        properties.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        properties.setProperty("mail.smtp.socketFactory.fallback", "false");
        properties.setProperty("mail.smtp.port", "465");
//        properties.setProperty("mail.smtp.socketFactory.port", "465");
        properties.setProperty("mail.smtps.auth", "true");
        properties.setProperty("mail.user", "snowky1993@gmail.com");
        properties.setProperty("mail.password", "WHyq1993");
        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);
//        Session session = Session.getDefaultInstance(properties,new javax.mail.Authenticator(){
//
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication("snowky1993@gmail.com", "WHyq1993");
//            }
//        });

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject(this.getSubject() + ": From " + this.getEmail());

            // Now set the actual message
            message.setText(this.getText());

            // Send message
            SMTPTransport t = (SMTPTransport) session.getTransport("smtps");
            t.connect("smtp.gmail.com", "snowky1993@gmail.com", "WHyq1993");
            t.sendMessage(message, message.getAllRecipients());
            // Transport.send(message);

            System.out.println("Sent message successfully....");
            t.close();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info : " + "Emails has been sent successfully!", ""));

        } catch (MessagingException mex) {
            System.out.println("Error");
            mex.printStackTrace();
        }

    }

    public void goToContactUs() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./contactus.xhtml");

    }

    public ContactManagedBean() {
    }

}
