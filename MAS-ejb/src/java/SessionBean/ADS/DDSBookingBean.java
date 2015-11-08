/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.ADS;

import Entity.ADS.Booker;
import Entity.CommonInfa.Agency;
import java.io.IOException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author LI HAO
 */
@Stateless
public class DDSBookingBean implements DDSBookingBeanLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public boolean setAgency_Booker(String username, Booker booker) {
        String bookerEmail = booker.getEmail();
        Query query = em.createQuery("SELECT b FROM Booker b WHERE b.email=:bookerEmail").setParameter("bookerEmail", bookerEmail);
        if (query.getResultList().isEmpty()) {
            return false;
        } else {
            booker = (Booker) query.getResultList().get(0);
        }
        Agency agency;
        agency = em.find(Agency.class, username);
        if (agency != null) {
            booker.setAgency(agency);
            em.persist(booker);
            agency.getBkList().add(booker);
            em.merge(agency);
            em.flush();
            
        } else {
            return false;
        }
        
        return true;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
