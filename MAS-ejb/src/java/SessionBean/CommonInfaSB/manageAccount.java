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
import util.CryptoHelper;

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
    
    private CryptoHelper cryptoHelper = CryptoHelper.getInstanceOf();
    String hPwd;

    public manageAccount() {

    }

    @Override
    public void addAdmin(String username, String password, String stfType) {
        if (stfType.equals("administrator")) {
            admStaff = new AdminStaff();
            admStaff.create(username, password, stfType);
            em.persist(admStaff);
        }
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
        hPwd=this.encrypt(username, password);
        
        if (stfType.equals("administrator")) {
            admStaff = new AdminStaff();
            admStaff.create(username, password, stfType);
            em.persist(admStaff);
        } else if (stfType.equals("officeStaff")) {
            offStaff = new OfficeStaff();
            offStaff.create(username, hPwd, email, stfType);
            em.persist(offStaff);
        } else if (stfType.equals("groundStaff")) {
            grdStaff = new GroundStaff();
            grdStaff.create(username, hPwd, email, stfType);
            em.persist(grdStaff);
        } else if (stfType.equals("cabin")) {
            System.out.println(stfType);
            cbCrew = new CabinCrew();
            cbCrew.create(username, hPwd, email, stfType);
            em.persist(cbCrew);
        }

    }

    @Override
    public void addCocpitAcc(String username, String password, String email, String stfType, String licence) {
        cpCrew = new CockpitCrew();
        hPwd=this.encrypt(username, password);
        cpCrew.create(username, hPwd, email, stfType, licence);
        em.persist(cpCrew);
    }
    
    public String encrypt(String username, String password)
    {
        String temp;
        if(!username.isEmpty() && !password.isEmpty())
        {
            System.out.println("*****The original password is "+ password +"*****");
            temp=cryptoHelper.doMD5Hashing(username+password);
            return temp;
        }
        return password;
    }

    public boolean checkNameDuplicate(String username, String usernameEdited) {
        if (username.equals(usernameEdited)) {
            return false;
        } else {
            Query query = em.createQuery("SELECT u FROM OfficeStaff u WHERE u.offName = :inUserName");
            query.setParameter("inUserName", usernameEdited);
            List resultList = new ArrayList<OfficeStaff>();
            resultList = (List) query.getResultList();

            if (resultList.isEmpty()) {
                return false;
            } else {
                return true;
            }
        }
    }

    public boolean checkEmailDuplicate(String email, String emailEdited) {
        if (email.equals(emailEdited)) {
            return false;
        } else {
            Query query = em.createQuery("SELECT u FROM OfficeStaff u WHERE u.email = :inUserEmail");
            query.setParameter("inUserEmail", emailEdited);
            List resultList = new ArrayList<OfficeStaff>();
            resultList = (List) query.getResultList();

            if (resultList.isEmpty()) {
                return false;
            } else {
                return true;
            }
        }
    }

    public void editOfficeStaff(String usernameEdited, String stfType, String password, String emailEdited) {
        OfficeStaff officeStaff = em.find(OfficeStaff.class, usernameEdited);
        if (officeStaff == null) {
            addAccount(usernameEdited, stfType, password, emailEdited);
        } else {
            officeStaff.setOffName(usernameEdited);
            officeStaff.setStfType(stfType);
            officeStaff.setOffPassword(password);
            officeStaff.setEmail(emailEdited);

            em.merge(officeStaff);
            em.flush();
        }

    }

    @Override
    public boolean validateLogin(String username, String password, String stfType) {
        Query query = null;
        hPwd=this.encrypt(username, password);
        if (stfType.equals("administrator")) {
            query = em.createQuery("SELECT u FROM AdminStaff u WHERE u.admName = :inUserName and u.admPassword=:inPassWord and u.stfType=:inStfType");
            query.setParameter("inPassWord", password);
            query.setParameter("inUserName", username);
            query.setParameter("inStfType", stfType);
        } else if (stfType.equals("officeStaff")) {
            query = em.createQuery("SELECT u FROM OfficeStaff u WHERE u.offName = :inUserName and u.offPassword=:inPassWord and u.stfType=:inStfType");
            query.setParameter("inPassWord", hPwd);
            query.setParameter("inUserName", username);
            query.setParameter("inStfType", stfType);
        } else if (stfType.equals("groundStaff")) {
            query = em.createQuery("SELECT u FROM GroundStaff u WHERE u.grdName = :inUserName and u.grdPassword=:inPassWord and u.stfType=:inStfType");
            query.setParameter("inPassWord", hPwd);
            query.setParameter("inUserName", username);
            query.setParameter("inStfType", stfType);
        } else if (stfType.equals("cabin")) {
            query = em.createQuery("SELECT u FROM CabinCrew u WHERE u.cbName = :inUserName and u.cbPassword=:inPassWord and u.stfType=:inStfType");
            query.setParameter("inPassWord", hPwd);
            query.setParameter("inUserName", username);
            query.setParameter("inStfType", stfType);
        } else if (stfType.equals("cockpit")) {
            query = em.createQuery("SELECT u FROM CockpitCrew u WHERE u.cpName = :inUserName and u.cpPassword=:inPassWord and u.stfType=:inStfType");
            query.setParameter("inPassWord", hPwd);
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
        System.out.println("Currently in delAcc");
        if (selectedOffStf.size() > 0) {
            for (int i = 0; i < selectedOffStf.size(); i++) {
                String pKey = selectedOffStf.get(i).getOffName();
                OfficeStaff oStaff = em.find(OfficeStaff.class, pKey);

                em.remove(oStaff);

            }
            
            System.out.println("return true in delAcc");
            return true;

        }
        return false;

    }

    @Override
    public boolean delGrdAcc(List<GroundStaff> selectedGrdStf) {
        if (selectedGrdStf.size() > 0) {
            for (int i = 0; i < selectedGrdStf.size(); i++) {
                String pKey = selectedGrdStf.get(i).getGrdName();
                GroundStaff gStaff = em.find(GroundStaff.class, pKey);
                em.remove(gStaff);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean delCabinAcc(List<CabinCrew> selectedCbCrew) {
        if (selectedCbCrew.size() > 0) {
            for (int i = 0; i < selectedCbCrew.size(); i++) {
                String pKey = selectedCbCrew.get(i).getCbName();
                CabinCrew cbCrew = em.find(CabinCrew.class, pKey);
                em.remove(cbCrew);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean delCockpitAcc(List<CockpitCrew> selectedCpCrew) {
        if (selectedCpCrew.size() > 0) {
            for (int i = 0; i < selectedCpCrew.size(); i++) {
                String pKey = selectedCpCrew.get(i).getCpName();
                CockpitCrew cpCrew = em.find(CockpitCrew.class, pKey);
                em.remove(cpCrew);
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
            System.out.println(resultList.get(0).getOffName());
            System.out.println(resultList.get(0).getStfType());
            System.out.println(resultList.get(0).getOffPassword());
            System.out.println(resultList.get(0).getEmail());
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
