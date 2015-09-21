/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.CommonInfaSB;

import javax.ejb.Stateless;
import java.util.*;
import Entity.*;
import Entity.CommonInfaEntity.*;
import static java.time.Clock.system;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author LIU YUQI'
 */
@Stateless
public class manageAccount implements manageAccountLocal {

    @PersistenceContext
    EntityManager em;
//    FFPMember newUser;
    AdminStaff admStaff;
    OfficeStaff offStaff;
    GroundStaff grdStaff;
    CabinCrew cbCrew;
    CockpitCrew cpCrew;

    public manageAccount() {

    }

    @Override
    public boolean checkAccDuplicate(String username, String stfType) {
        Query query = null;
        if (stfType.equals("administrator")) {
            query = em.createQuery("SELECT u FROM AdminStaff u WHERE u.admName = :inUserName and u.stfType=:inStfType");
            query.setParameter("inUserName", username);
            query.setParameter("inStfType", stfType);
        } else if (stfType.equals("officeStaff")) {
            query = em.createQuery("SELECT u FROM OfficeStaff u WHERE u.offName = :inUserName and u.stfType=:inStfType");
            query.setParameter("inUserName", username);
            query.setParameter("inStfType", stfType);
        } else if (stfType.equals("groundStaff")) {
            query = em.createQuery("SELECT u FROM GroundStaff u WHERE u.grdName = :inUserName and u.stfType=:inStfType");
            query.setParameter("inUserName", username);
            query.setParameter("inStfType", stfType);
        } else if (stfType.equals("cabin")) {
            query = em.createQuery("SELECT u FROM CabinCrew u WHERE u.cbName = :inUserName and u.stfType=:inStfType");
            query.setParameter("inUserName", username);
            query.setParameter("inStfType", stfType);
        } else if (stfType.equals("cockpit")) {
            query = em.createQuery("SELECT u FROM CockpitCrew u WHERE u.cpName = :inUserName and u.stfType=:inStfType");
            query.setParameter("inUserName", username);
            query.setParameter("inStfType", stfType);
        }

        List resultList = new ArrayList<AdminStaff>();
        resultList = (List) query.getResultList();
        if (resultList.isEmpty()) {
            return false;

        } else {
            return true;
        }

    }

    @Override
    public void addAccount(String username, String password, String email, String stfType) {
//        newUser = new FFPMember();
        System.out.println("Currently in addAccount");
        if (stfType.equals("administrator")) {
            admStaff = new AdminStaff();
            admStaff.create(username, password, stfType);
            em.persist(admStaff);
        } else if (stfType.equals("officeStaff")) {
            offStaff = new OfficeStaff();
            offStaff.create(username, password, email, stfType);
            em.persist(offStaff);
        } else if (stfType.equals("groundStaff")) {
            grdStaff = new GroundStaff();
            grdStaff.create(username, password, email, stfType);
            em.persist(grdStaff);
        } else if (stfType.equals("cabin")) {
            System.out.println(stfType);
            cbCrew = new CabinCrew();
            cbCrew.create(username, password, email, stfType);
            em.persist(cbCrew);
        }

    }

    @Override
    public void addCocpitAcc(String username, String password, String email, String stfType, String licence) {
        cpCrew = new CockpitCrew();
        cpCrew.create(username, password, email, stfType, licence);
        em.persist(cpCrew);
    }

    @Override
    public boolean validateLogin(String username, String password, String stfType) {
        Query query = null;
        if (stfType.equals("administrator")) {
            query = em.createQuery("SELECT u FROM AdminStaff u WHERE u.admName = :inUserName and u.admPassword=:inPassWord and u.stfType=:inStfType");
            query.setParameter("inPassWord", password);
            query.setParameter("inUserName", username);
            query.setParameter("inStfType", stfType);
        } else if (stfType.equals("officeStaff")) {
            query = em.createQuery("SELECT u FROM OfficeStaff u WHERE u.offName = :inUserName and u.offPassword=:inPassWord and u.stfType=:inStfType");
            query.setParameter("inPassWord", password);
            query.setParameter("inUserName", username);
            query.setParameter("inStfType", stfType);
        } else if (stfType.equals("groundStaff")) {
            query = em.createQuery("SELECT u FROM GroundStaff u WHERE u.grdName = :inUserName and u.grdPassword=:inPassWord and u.stfType=:inStfType");
            query.setParameter("inPassWord", password);
            query.setParameter("inUserName", username);
            query.setParameter("inStfType", stfType);
        } else if (stfType.equals("cabin")) {
            query = em.createQuery("SELECT u FROM CabinCrew u WHERE u.cbName = :inUserName and u.cbPassword=:inPassWord and u.stfType=:inStfType");
            query.setParameter("inPassWord", password);
            query.setParameter("inUserName", username);
            query.setParameter("inStfType", stfType);
        } else if (stfType.equals("cockpit")) {
            query = em.createQuery("SELECT u FROM CockpitCrew u WHERE u.cpName = :inUserName and u.cpPassword=:inPassWord and u.stfType=:inStfType");
            query.setParameter("inPassWord", password);
            query.setParameter("inUserName", username);
            query.setParameter("inStfType", stfType);
        }

        List resultList = new ArrayList<AdminStaff>();
        resultList = (List) query.getResultList();
        if (resultList.isEmpty()) {
            return false;

        } else {
            return true;
        }

    }

    @Override
    public boolean delAcc(List<OfficeStaff> selectedOffStf) {
        if (selectedOffStf.size() > 0) {
            for (int i = 0; i < selectedOffStf.size(); i++) {
                String pKey = selectedOffStf.get(i).getOffName();
                OfficeStaff oStaff = em.find(OfficeStaff.class,pKey);

                em.remove(oStaff);

            }

            return true;

        }
        return false;


    }

    @Override
    public List<OfficeStaff> getAllOfficeStaff() {
        Query query = em.createQuery("SELECT a FROM OfficeStaff a ");
        List<OfficeStaff> resultList = (List) query.getResultList();
        if (resultList.isEmpty()) {
            System.out.println("List is empty");
        } else {
            System.out.println("List data exists");
        }

        return resultList;
    }

    @Override
    public List<GroundStaff> getAllGoundStaff() {
        Query query = em.createQuery("SELECT a FROM GroundStaff a ");
        List<GroundStaff> resultList = (List) query.getResultList();
        if (resultList.isEmpty()) {
            System.out.println("List is empty");
        } else {
            System.out.println("List data exists");
        }

        return resultList;
    }

    @Override
    public List<CabinCrew> getAllCabinCrew() {
        Query query = em.createQuery("SELECT a FROM CabinCrew a ");
        List<CabinCrew> resultList = (List) query.getResultList();
        if (resultList.isEmpty()) {
            System.out.println("List is empty");
        } else {
            System.out.println("List data exists");
        }

        return resultList;
    }

    @Override
    public List<CockpitCrew> getAllCockpitCrew() {
        Query query = em.createQuery("SELECT a FROM CockpitCrew a ");
        List<CockpitCrew> resultList = (List) query.getResultList();
        if (resultList.isEmpty()) {
            System.out.println("List is empty");
        } else {
            System.out.println("List data exists");
        }

        return resultList;
    }

}
