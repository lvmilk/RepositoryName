/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.ADS;

import Entity.ADS.Booker;
import java.util.ArrayList;
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
public class BookerBean implements BookerBeanLocal {

    @PersistenceContext
    EntityManager em;
    
    public void editThisBooker(Booker bookPerson){
        System.out.println("bookPerson.email is "+bookPerson.getEmail());
        Booker booker = em.find(Booker.class, bookPerson.getId());
        booker.setTitle(bookPerson.getTitle());
        booker.setFirstName(bookPerson.getFirstName());
        booker.setLastName(bookPerson.getLastName());
        booker.setAddress(bookPerson.getAddress());
        booker.setEmail(bookPerson.getEmail());
        booker.setContact(bookPerson.getContact());
        booker.setDob(bookPerson.getDob());
        booker.setMiles(bookPerson.getMiles());
        booker.setPassport(bookPerson.getPassport());
        booker.setMemberStatus(bookPerson.isMemberStatus());

        em.merge(booker);
        em.flush();
    
    
    }

    @Override
    public List<Booker> getAllBooker() {
        Query query = em.createQuery("SELECT a FROM Booker a ");
        List<Booker> resultList = (List) query.getResultList();

        return resultList;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public boolean checkEmailDuplicate(String email, String emailEdited) {
        if (email.equals(emailEdited)) {
            return false;
        } else {
            return checkEmailExists(emailEdited);
        }
    }

    @Override
    public boolean checkEmailExists(String email) {
        Query query = null;

        query = em.createQuery("SELECT u FROM Booker u WHERE u.email = :inUserEmail");
        query.setParameter("inUserEmail", email);
        return checkList(query);

    }

    @Override
    public boolean checkPassportExists(String passport) {
        Query query = null;

        query = em.createQuery("SELECT u FROM Booker u WHERE u.passport = :inUserPassport");
        query.setParameter("inUserPassport", passport);
        return checkList(query);

    }

    @Override
    public void editBooker(Long Id, String title, String firstName, String lastName, String address, String email, String contactNo, String dob, Double miles, String passport, boolean memberStatus) {
        Booker booker = em.find(Booker.class, Id);
        booker.setTitle(title);
        booker.setFirstName(firstName);
        booker.setLastName(lastName);
        booker.setAddress(address);
        booker.setEmail(email);
        booker.setContact(contactNo);
        booker.setDob(dob);
        booker.setMiles(miles);
        booker.setPassport(passport);
        booker.setMemberStatus(memberStatus);

        em.merge(booker);
        em.flush();

    }

    private boolean checkList(Query query) {
        List resultList = new ArrayList();
        resultList = (List) query.getResultList();
        if (resultList.isEmpty()) {
            return false;

        } else {
            return true;
        }
    }

    @Override
    public Long retrieveBookerID(String email) {
        Query query = null;
        Long temp=new Long(0);

        query = em.createQuery("SELECT u FROM Booker u WHERE u.email = :inUserEmail");
        query.setParameter("inUserEmail", email);
        List<Booker> resultList= query.getResultList();
        if(!resultList.isEmpty())
        {
            return resultList.get(0).getId();
        }
        return temp;
    }

    @Override
    public Booker retrieveBooker(Long Id) {
        Booker booker=em.find(Booker.class, Id);
        return booker;
    }
}