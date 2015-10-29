/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.ADS;

import Entity.ADS.Member;
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
public class MemberSessionBean implements MemberSessionBeanLocal {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<Member> getAllMember() {
        Query query = em.createQuery("SELECT a FROM Member a ");
        List<Member> resultList = (List) query.getResultList();

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

        query = em.createQuery("SELECT u FROM Member u WHERE u.email = :inUserEmail");
        query.setParameter("inUserEmail", email);
        return checkList(query);

    }

    @Override
    public boolean checkPassportExists(String passport) {
        Query query = null;

        query = em.createQuery("SELECT u FROM Member u WHERE u.passport = :inUserPassport");
        query.setParameter("inUserPassport", passport);
        return checkList(query);

    }

    @Override
    public void editMember(Long memberId,String title,String firstName,String lastName,String address,String email,String contactNo,String dob,Double miles,String passport,boolean memberStatus) {
        Member member=em.find(Member.class, memberId);
        member.setTitle(title);
        member.setFirstName(firstName);
        member.setLastName(lastName);
        member.setAddress(address);
        member.setEmail(email);
        member.setContact(contactNo);
        member.setDob(dob);
        member.setMiles(miles);
        member.setPassport(passport);
        member.setMemberStatus(memberStatus);
        
        em.merge(member);
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
}
