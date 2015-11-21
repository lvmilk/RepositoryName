/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.CRMClient;

import Entity.ADS.Booker;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.CryptoHelper;

/**
 *
 * @author wang
 */
@Stateless
public class MemberRegistrationBean implements MemberRegistrationBeanLocal {

    @PersistenceContext
    EntityManager em;
    private CryptoHelper cryptoHelper = CryptoHelper.getInstanceOf();

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
   
    @Override
    public Long createMember(String title, String password, String firstName, String lastName, String passport, String email, String address, String contact, String dob) throws Exception {
        Long createdID = new Long(0);
        Booker newBooker = new Booker();
        newBooker.setTitle(title);
        newBooker.setPassport(passport);
        newBooker.setMiles(0.0);
        newBooker.setMemberStatus(true);
        newBooker.setSubscribe(true);
        if (password.length() >= 8 && password.length() <= 12) {
            String pattern = "((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,})";
            if (!password.matches(pattern)) {
                throw new Exception("Please note your password should at least contain one digit, one lowercase, one uppercase, one special character and no space");
            }

        } else {
            throw new Exception("Please note the length of password should be at least 8, at most 12");

        }
        newBooker.setFirstName(firstName);
        newBooker.setLastName(lastName);
        if (!email.contains("@")) {
            throw new Exception("Please enter valid email address");
        } else {
            newBooker.setEmail(email);
        }
        newBooker.setAddress(address);
        int contactLen = contact.length();
        for (int i = 0; i < contactLen; i++) {
            if (Character.isDigit(contact.charAt(i)) == false && contact.charAt(i) != '-') {
                throw new Exception("Please enter a valid contact number only contains digits and seperator '-'");
            }
        }

        newBooker.setContact(contact);

        newBooker.setDob(dob);

        try {
            em.persist(newBooker);
            em.flush();
        } catch (Exception ex) {
            throw new Exception("The email is used!");

        }
        Query query = em.createQuery("SELECT b FROM Booker b WHERE b.email=:bemail");
        query.setParameter("bemail", email);
        if (query.getResultList().isEmpty()) {
            throw new Exception("The member has not been created!");
        } else {
            Booker created = (Booker) query.getSingleResult();
            createdID = created.getId();
            newBooker.setPassword(this.encrypt(createdID.toString(), password));
            em.merge(newBooker);
            em.flush();
            return createdID;
        }
    }

    public String encrypt(String userID, String password) {
        String temp;
        if (!userID.isEmpty() && !password.isEmpty()) {
            System.out.println("*****The original password for " + userID + " is " + password + "*****");
            temp = cryptoHelper.doMD5Hashing(userID + password);
            return temp;
        }
        return password;
    }

}
