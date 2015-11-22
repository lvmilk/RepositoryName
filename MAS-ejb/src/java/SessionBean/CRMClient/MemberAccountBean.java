/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.CRMClient;

import Entity.ADS.Booker;
import Entity.ADS.Reservation;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.CryptoHelper;

/**
 *
 * @author Xi
 */
@Stateless
public class MemberAccountBean implements MemberAccountBeanLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
    EntityManager em;

    Booker booker;
    private CryptoHelper cryptoHelper = CryptoHelper.getInstanceOf();
    String hPwd = new String();

    public MemberAccountBean() {
    }

    @Override
    public Boolean validateMember(String memberId, String password) {
        hPwd = this.encrypt(memberId, password);
        System.out.println("CRMMemberAccountBean: validatelogin:" + hPwd);
        System.out.println("CRMMemberAccountBean: validatelogin:" + password);
        List<Booker> resultList = new ArrayList<>();

        Long memberLong = Long.valueOf(memberId);
        Query query = em.createQuery("SELECT b FROM Booker b WHERE b.id =:memberLong and b.password=:password");
        query.setParameter("password", hPwd);
        query.setParameter("memberLong", memberLong);
        resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return false;
        } else {
            return true;
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

    @Override
    public List<Reservation> getAllReservation(Long memberId) throws Exception {
        List<Reservation> rsvList = new ArrayList<>();
        booker = em.find(Booker.class, memberId);
        if (booker != null) {
            rsvList = booker.getRsvList();
            if (rsvList != null) {
                return rsvList;
            } else {
                throw new Exception("There is no reservation linked with this member " + memberId);
            }
        } else {
            throw new Exception("Sorry! No such a Member!");
        }
    }

    @Override
    public Booker getThisMember(Long memberId) throws Exception {
        booker = em.find(Booker.class, memberId);
        if (booker != null) {
            return booker;
        } else {
            throw new Exception("Sorry! No such a Member!");
        }
    }

    @Override
    public void editBooker(Long id, String title, String password, String passport, String address, String contact, String dob, boolean sub) throws Exception {
        booker = em.find(Booker.class, id);
        if (booker != null) {
            booker.setTitle(title);
            booker.setPassport(passport);
            booker.setAddress(address);
            booker.setSubscribe(sub);
            if (password.length() >= 8 && password.length() <= 12) {
                String pattern = "((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,})";
                if (!password.matches(pattern)) {
                    throw new Exception("Please note your password should at least contain one digit, one lowercase, one uppercase, one special character and no space");
                }

            } else {
                throw new Exception("Please note the length of password should be at least 8, at most 12");

            }
            int contactLen = contact.length();
            for (int i = 0; i < contactLen; i++) {
                if (Character.isDigit(contact.charAt(i)) == false && contact.charAt(i) != '-') {
                    throw new Exception("Please enter a valid contact number only contains digits and seperator '-'");
                }
            }
            booker.setContact(contact);
            booker.setDob(dob);
            booker.setPassword(this.encrypt(id.toString(), password));
            em.merge(booker);
            em.flush();
        } else {
            throw new Exception("Sorry! No such a Member!");
        }
    }
}
