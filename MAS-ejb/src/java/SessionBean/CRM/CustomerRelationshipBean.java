/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.CRM;

import Entity.ADS.Booker;
import Entity.ADS.Payment;
import Entity.ADS.Reservation;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author wang
 */
@Stateless
public class CustomerRelationshipBean implements CustomerRelationshipBeanLocal {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<Booker> getCurrentMonthBabies(Date currentDate) throws Exception {
        Query query = em.createQuery("SELECT b FROM Booker b where b.memberStatus=:bstatus");
        query.setParameter("bstatus", true);
        List<Booker> birthdayMemberList = new ArrayList<>();

        Calendar c1 = Calendar.getInstance();
        c1.setTime(currentDate);
        Integer currentMonth = c1.get(Calendar.MONTH);
        List<Booker> memberList = (List<Booker>) query.getResultList();
        if (memberList.isEmpty()) {
            throw new Exception("No member in database");
        } else {
            for (Booker temp : memberList) {
                DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
                Date dateTemp = df1.parse(temp.getDob());
                c1.setTime(dateTemp);
                Integer monthTemp = c1.get(Calendar.MONTH);
                if (Objects.equals(monthTemp, currentMonth)) {
                    birthdayMemberList.add(temp);
                }
            }
        }
        if (birthdayMemberList.isEmpty()) {
            throw new Exception("No member celebrates birthday in this month!");
        } else {
            return birthdayMemberList;
        }
    }

    public List<Booker> getAllMembers() throws Exception {
        Query query = em.createQuery("SELECT b FROM Booker b where b.memberStatus=:bstatus");
        query.setParameter("bstatus", true);

        List<Booker> memberList = (List<Booker>) query.getResultList();
        if (memberList.isEmpty()) {
            throw new Exception("No member in database");
        } else {
            return memberList;
        }

    }

    public List<Reservation> getReservation(Long memberID) {
        Query query = em.createQuery("SELECT b FROM Reservation b where b.booker.id=:bid");
        query.setParameter("bid", memberID);
        List<Reservation> reservationList = new ArrayList<>(query.getResultList());
        return reservationList;

    }
    
    public List<Payment> getPayment() {
        Query query = em.createQuery("SELECT b FROM Payment b");
        List<Payment> paymentList = new ArrayList<>(query.getResultList());
        return paymentList;

    }
   
//    public Booker getBooker(Long bookerID){
//       
//        return  (Booker) em.find(Booker.class, bookerID);
//    }
    
//    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
