/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMClientmanagedbean;

import Entity.ADS.Booker;
import SessionBean.CRMClient.MemberRegistrationBeanLocal;
import com.sun.mail.smtp.SMTPTransport;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
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
@Named(value = "mrmb")
@ViewScoped
public class MemberRegistrationManagedBean implements Serializable {

    @EJB
    MemberRegistrationBeanLocal mrb;

    private String title;
    private String password;
    private String email;
    private String lastName;
    private String firstName;
    private String address;
    private String contact;
    private String passport;
    private String dob;
    private Date dobDateType;
    private Long createdID;
    private Date maxDate=new Date();
    public void createMember() {
        try {
            DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
            this.setDob(df1.format(dobDateType));
           createdID= mrb.createMember(title, password, firstName, lastName, passport, email, address, contact, dob);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "A member account has been created successfully!", "Member ID is: "+createdID));
            this.sendEamil();
// FacesContext.getCurrentInstance().getExternalContext().redirect("./onlineCheckin2.xhtml");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));

        }
    }

    public void sendEamil() {
        if (email.length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + "No email address detected", ""));
        } else {
                // Recipient's email ID needs to be mentioned.
                String to = email;

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
                    message.setSubject("An account has been created for you!");

                    BodyPart messageBodyPart = new MimeBodyPart();

                    // Fill the message
                    messageBodyPart.setText("Dear " + this.getFirstName() + ", an account is successfully created for you!  You TFP member ID is  "+this.createdID+". Enjoy your journey with Merlion Airlines!");
                    Multipart multipart = new MimeMultipart();
                    multipart.addBodyPart(messageBodyPart);

                    message.setContent(multipart);
                    // Send message
                    SMTPTransport t = (SMTPTransport) session.getTransport("smtps");
                    t.connect("smtp.gmail.com", "snowky1993@gmail.com", "WHyq1993");
                    t.sendMessage(message, message.getAllRecipients());
                    // Transport.send(message);

                    System.out.println("Sent message successfully....");
                    t.close();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info : " + "An comfirmation email has been sent to you successfully!", ""));

                } catch (MessagingException mex) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occured : " + "Emails has not been sent to you successfully", ""));

                    System.out.println("Error");
                    mex.printStackTrace();
                }

            
        }
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }
    
    public void goToRegister() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./memberRegistration.xhtml");

    }

    public Long getCreatedID() {
        return createdID;
    }

    public void setCreatedID(Long createdID) {
        this.createdID = createdID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDobDateType() {
        return dobDateType;
    }

    public void setDobDateType(Date dobDateType) {
        this.dobDateType = dobDateType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    /**
     * Creates a new instance of MemberRegistrationManagedBean
     */
    public MemberRegistrationManagedBean() {
    }

}
