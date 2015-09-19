package SessionBean.APS;

import Entity.APS.Aircraft;
import Entity.APS.AircraftType;
import Exception.MASException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author victor/lucy
 */

public class AircraftHandler{
   
    EntityManager em;
    
    public void setEm(EntityManager em){
        this.em = em;
    }
    
/**************AircraftType**************/
    
    public void addAircraftType(AircraftType at){
        em.persist(at);
        em.flush();
    }

    
    public boolean deleteAircraftType(AircraftType at){
        em.remove(at);
        em.flush();
        return true;
    }

    
    public List<AircraftType> findAircraftType(String type){
        Query query = em.createQuery
            ("SELECT AT FROM AircraftType AT WHERE "
                    + "AT.type = :type");
        query.setParameter("type", type);
        List<AircraftType> result = query.getResultList();
        return result;
    }

    
    public boolean hasAircraftType(String type){
        List find = this.findAircraftType(type);
        return !find.isEmpty();
    }
   
    
    public void updateAircraftType(AircraftType at) {
        //em.refresh(at);
        em.flush();
    }

    
    public AircraftType findAircraftType(long id) throws MASException {
        AircraftType result = null;
        result = em.find(AircraftType.class, id);
        return result;
    }

    
    public List<AircraftType> listAircraftType() {
        Query query = em.createQuery
            ("SELECT AT FROM AircraftType AT");
        List<AircraftType> result = query.getResultList();
        return result;
    }
    
    
/**************Aircraft**************/
    
    public void addAircraft(Aircraft af){
        em.persist(af);
        em.flush();
    }

    
    public boolean deleteAircraft(Aircraft af){
        em.remove(af);
        em.flush();
        return true;
    }

    
    public List<Aircraft> findAircraft(String registrationNumber){
        Query query = em.createQuery
            ("SELECT AF FROM Aircraft AF WHERE "
                    + "AF.registrationNo = :registrationNo");
        query.setParameter("registrationNo", registrationNumber);
        List<Aircraft> result = query.getResultList();
        return result;
    }

    
    public boolean hasAircraft(String registrationNumber){
        List find = this.findAircraft(registrationNumber);
        return !find.isEmpty();
    }

    
    public void updateAircraft(Aircraft af) {
        em.flush();
    }

    
    public Aircraft findAircraft(long id) throws MASException {
        Aircraft result = null;
        result = em.find(Aircraft.class, id);
        return result;
    }

    
    public List<Aircraft> listAircraft() {
        Query query = em.createQuery
            ("SELECT AF FROM Aircraft AF");
        List<Aircraft> result = query.getResultList();
        return result;
    }


}
