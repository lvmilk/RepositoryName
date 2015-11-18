/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.sun.mail.smtp.SMTPTransport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author wang
 */
@Named(value = "testPageManagedBean")
@ViewScoped
public class testPageManagedBean implements Serializable {
//private Map<String, Boolean> testMap=new HashMap<String, Boolean>();

    private List<Integer> testMap = new ArrayList< Integer>();
//private Boolean flag;
    private Integer flag = 0;
    private String inputString;
    private Boolean test;
    private String testStr = new String("asdfasdfasdf");
    private String testStr2 = new String("qwerqwerqwe");

//@PostConstruct
//
//public void init(){
//    testMap.add(0);
//    testMap.add(1);
//    
//}
//
//    public String getTestStr() {
//        return testStr;
//    }
//
//    public void setTestStr(String testStr) {
//        this.testStr = testStr;
//    }
//
//    public String getTestStr2() {
//        return testStr2;
//    }
//
//    public void setTestStr2(String testStr2) {
//        this.testStr2 = testStr2;
//    }
//
//    public String getInputString() {
//        return inputString;
//    }
//
//    // Add business logic below. (Right-click in editor and choose
//    // "Insert Code > Add Business Method")
//    public void setInputString(String inputString) {
//        this.inputString = inputString;
//    }
//
//
//    public Boolean getTest() {
//        return test;
//    }
//
//    public void setTest(Boolean test) {
//        this.test = test;
//    }
//
//    public List<Integer> getTestMap() {
//        return testMap;
//    }
//
//    public void setTestMap(List<Integer> testMap) {
//        this.testMap = testMap;
//    }
//
//
//    public Integer getFlag() {
//          System.out.println("getFlag: "+flag);
//        return flag;
//    }
//
//    public void setFlag(Integer fg) {
//         System.out.println("setFlag: "+fg);
//        this.flag = fg;
//    }
//    
//
//      public void onSelect(){
//          System.out.println("Flag: "+this.getFlag());
//          if (flag==null||flag==0)
//              test=false;
//          else 
//              test=true;
//          System.out.println("render: "+test);
//    }
//     
    public void sendEmail() {

        // Recipient's email ID needs to be mentioned.
        String to = "hanyuw1993@gmail.com";

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
            message.setSubject("This is the Subject Line!");

            // Now set the actual message
            message.setText("This is actual message");

            // Send message
            SMTPTransport t =(SMTPTransport)session.getTransport("smtps");
             t.connect("smtp.gmail.com", "snowky1993@gmail.com", "WHyq1993");
           t.sendMessage(message, message.getAllRecipients());
            // Transport.send(message);

            System.out.println("Sent message successfully....");
            t.close();
        } catch (MessagingException mex) {
            System.out.println("Error");
            mex.printStackTrace();
        }

    }

    /**
     * Creates a new instance of testPageManagedBean
     */
    public testPageManagedBean() {
    }

}
