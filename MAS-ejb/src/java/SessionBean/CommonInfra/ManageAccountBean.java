/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.CommonInfra;

import Entity.CommonInfa.Agency;
import Entity.CommonInfa.CabinCrew;
import Entity.CommonInfa.UserEntity;
import Entity.CommonInfa.OfficeStaff;
import Entity.CommonInfa.AirAlliances;
import Entity.CommonInfa.GroundStaff;
import Entity.CommonInfa.AdminStaff;
import Entity.CommonInfa.CockpitCrew;
import javax.ejb.Stateless;
import java.util.*;
import Entity.*;
import Entity.AAS.Expense;
import Entity.AAS.Payroll;
import static java.time.Clock.system;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.CryptoHelper;

/**
 *
 * @author LIU YUQI & LI HAO'
 */
@Stateless
public class ManageAccountBean implements ManageAccountBeanLocal, ManageAccountBeanRemote {

    @PersistenceContext
    EntityManager em;
//    FFPMember newUser;
    AdminStaff admStaff;
    OfficeStaff offStaff;
    GroundStaff grdStaff;
    CabinCrew cbCrew;
    CockpitCrew cpCrew;
    Agency agency;
    AirAlliances alliance;
    Expense expense;
    Payroll payroll;
    private UserEntity userEntity;

    private CryptoHelper cryptoHelper = CryptoHelper.getInstanceOf();
    private String hPwd = new String();
    private Integer temp;
    private Integer locked;


    public ManageAccountBean() {

    }

    @Override
    public boolean checkPartenrIDDuplicate(String partnerID, String stfType) {
        Query query = null;
        List resultList;
        if (stfType.equals("agency")) {
            query = em.createQuery("SELECT u FROM Agency u WHERE u.agencyID = :inUserName and u.pType = :inStfType");
            query.setParameter("inUserName", partnerID);
            query.setParameter("inStfType", stfType);
            resultList = new ArrayList<Agency>();
        } else if (stfType.equals("alliance")) {
            query = em.createQuery("SELECT u FROM AirAlliances u WHERE u.allianceID = :inUserName and u.pType = :inStfType");
            query.setParameter("inUserName", partnerID);
            query.setParameter("inStfType", stfType);
            resultList = new ArrayList<AirAlliances>();
        }
        resultList = (List) query.getResultList();
        if (resultList.isEmpty()) {
            return false;

        } else {
            return true;
        }

    }

    @Override
    public boolean checkPartnerEmailDuplicate(String pEmail) {
        Boolean agBl, alBl;
        agBl = checkAgEmail(pEmail);
        alBl = checkAlEmail(pEmail);

        if (agBl == true || alBl == true) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void addPartnerAcc(String pid, String pPwd, String companyName, String email, String stfType, String iata) {
        System.out.println("Currently in create partner account");
        hPwd = this.encrypt(pid, pPwd);
        if (stfType.equals("agency")) {
            agency = new Agency();
            agency.createAgencyAcc(pid, hPwd, companyName, email, stfType);
            em.persist(agency);
        } else if (stfType.equals("alliance")) {
            alliance = new AirAlliances();
            alliance.createAllianceAcc(pid, hPwd, companyName, email, stfType);
            em.persist(alliance);
            em.flush();

        }
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
    public boolean checkEmailExists(String email) {
        Boolean offBl, grdBl, cpBl, cbBl;
        offBl = checkOffEmail(email);
        grdBl = checkGrdEmail(email);
        cpBl = checkCbEmail(email);
        cbBl = checkCpEmail(email);
        if (offBl == true || grdBl == true || cpBl == true || cbBl == true) {
            return true;
        } else {
            return false;
        }

    }

    private boolean checkOffEmail(String email) {
        Query query = null;

        query = em.createQuery("SELECT u FROM OfficeStaff u WHERE u.email = :inUserEmail");
        query.setParameter("inUserEmail", email);
        return checkList(query);
    }

    private boolean checkGrdEmail(String email) {
        Query query = null;

        query = em.createQuery("SELECT u FROM GroundStaff u WHERE u.email = :inUserEmail");
        query.setParameter("inUserEmail", email);
        return checkList(query);
    }

    private boolean checkCbEmail(String email) {
        Query query = null;

        query = em.createQuery("SELECT u FROM CabinCrew u WHERE u.email = :inUserEmail");
        query.setParameter("inUserEmail", email);
        return checkList(query);
    }

    private boolean checkCpEmail(String email) {
        Query query = null;

        query = em.createQuery("SELECT u FROM CockpitCrew u WHERE u.email = :inUserEmail");
        query.setParameter("inUserEmail", email);
        return checkList(query);
    }

    private boolean checkAgEmail(String email) {
        Query query = null;

        query = em.createQuery("SELECT u FROM Agency u WHERE u.email = :inUserEmail");
        query.setParameter("inUserEmail", email);
        return checkList(query);
    }

    private boolean checkAlEmail(String email) {
        Query query = null;

        query = em.createQuery("SELECT u FROM AirAlliances u WHERE u.email = :inUserEmail");
        query.setParameter("inUserEmail", email);
        return checkList(query);

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
    public boolean checkAccDuplicate(String username, String stfType) {
        Query query = null;
        List resultList;
        if (stfType.equals("administrator")) {
            query = em.createQuery("SELECT u FROM AdminStaff u WHERE u.admName = :inUserName and u.stfType=:inStfType");
            query.setParameter("inUserName", username);
            query.setParameter("inStfType", stfType);
            resultList = new ArrayList<AdminStaff>();
        } else if (stfType.equals("officeStaff")) {
            query = em.createQuery("SELECT u FROM OfficeStaff u WHERE u.offName = :inUserName and u.stfType=:inStfType");
            query.setParameter("inUserName", username);
            query.setParameter("inStfType", stfType);
            resultList = new ArrayList<OfficeStaff>();
        } else if (stfType.equals("groundStaff")) {
            query = em.createQuery("SELECT u FROM GroundStaff u WHERE u.grdName = :inUserName and u.stfType=:inStfType");
            query.setParameter("inUserName", username);
            query.setParameter("inStfType", stfType);
            resultList = new ArrayList<GroundStaff>();
        } else if (stfType.equals("cabin")) {
            query = em.createQuery("SELECT u FROM CabinCrew u WHERE u.cbName = :inUserName and u.stfType=:inStfType");
            query.setParameter("inUserName", username);
            query.setParameter("inStfType", stfType);
            resultList = new ArrayList<CabinCrew>();
        } else if (stfType.equals("cockpit")) {
            query = em.createQuery("SELECT u FROM CockpitCrew u WHERE u.cpName = :inUserName and u.stfType=:inStfType");
            query.setParameter("inUserName", username);
            query.setParameter("inStfType", stfType);
            resultList = new ArrayList<CockpitCrew>();
        }

        resultList = (List) query.getResultList();
        if (resultList.isEmpty()) {
            return false;

        } else {
            return true;
        }

    }

    @Override
    public void addAccount(String username, String password, String email, String stfType, String firstName, String lastName, String stfLevel, Double salary) {
//        newUser = new FFPMember();
        System.out.println("Currently in addAccount");
        hPwd = this.encrypt(username, password);

        if (stfType.equals("administrator")) {
            admStaff = new AdminStaff();
            admStaff.create(username, password, stfType);
            em.persist(admStaff);
        } else if (stfType.equals("officeStaff")) {
            offStaff = new OfficeStaff();
            userEntity = new UserEntity();
            offStaff.create(username, hPwd, email, stfType, firstName, lastName, stfLevel, salary);
            userEntity.create(username, email);
            offStaff.setUser(userEntity);
            userEntity.setOffStaff(offStaff);
            em.persist(offStaff);
            ///////////////////////////////////
            expense = new Expense();
            expense.setCategory("Office Staff");
            expense.setType("Variable Operation Cost");
            expense.setPayable(salary);
            expense.setCostSource(username);
            em.persist(expense);
            em.flush();
            payroll = new Payroll();
            payroll.setName(username);
            payroll.setSalary(salary);
            payroll.setBonus(0.0);
            em.persist(payroll);
            em.flush();
        } else if (stfType.equals("groundStaff")) {
            grdStaff = new GroundStaff();
            userEntity = new UserEntity();
            grdStaff.create(username, hPwd, email, stfType, firstName, lastName, stfLevel, salary);
            userEntity.create(username, email);
            grdStaff.setUser(userEntity);
            userEntity.setGrdStaff(grdStaff);
            em.persist(grdStaff);
            expense = new Expense();
            expense.setCategory("Ground Staff");
            expense.setType("Variable Operation Cost");
            expense.setPayable(salary);
            expense.setCostSource(username);
            em.persist(expense);
            em.flush();
            payroll = new Payroll();
            payroll.setName(username);
            payroll.setSalary(salary);
            payroll.setBonus(0.0);
            em.persist(payroll);
            em.flush();
        }
//        else if (stfType.equals("cabin")) {
//            System.out.println(stfType);
//            cbCrew = new CabinCrew();
//            userEntity = new UserEntity();
//            cbCrew.create(username, hPwd, email, stfType, firstName,lastName, stfLevel, salary);
//            userEntity.create(username, email);
//            cbCrew.setUser(userEntity);
//            userEntity.setCbCrew(cbCrew);
//            em.persist(cbCrew);
//        }

    }

    @Override
    public void addCabinAcc(String username, String password, String email, String stfType, String firstName, String lastName, String stfLevel, Double salary, String secondLang) {
        cbCrew = new CabinCrew();
        userEntity = new UserEntity();
        hPwd = this.encrypt(username, password);
        cbCrew.create(username, hPwd, email, stfType, firstName, lastName, stfLevel, salary, secondLang);
        userEntity.create(username, email);
        cbCrew.setUser(userEntity);
        userEntity.setCbCrew(cbCrew);
        em.persist(cbCrew);
        ///////////////////////////////////////
        expense = new Expense();
        expense.setCategory(stfLevel);
        expense.setType("Variable Operation Cost");
        expense.setPayable(salary);
        expense.setCostSource(username);
        em.persist(expense);
        em.flush();
        payroll = new Payroll();
        payroll.setName(username);
        payroll.setSalary(salary);
        payroll.setBonus(0.0);
        em.persist(payroll);
        em.flush();
    }

    @Override
    public void addCocpitAcc(String username, String password, String email, String stfType, String firstName, String lastName, String stfLevel, Double salary, String licence) {
        cpCrew = new CockpitCrew();
        userEntity = new UserEntity();
        hPwd = this.encrypt(username, password);
        cpCrew.create(username, hPwd, email, stfType, firstName, lastName, stfLevel, salary, licence);
        userEntity.create(username, email);
        cpCrew.setUser(userEntity);
        userEntity.setCpCrew(cpCrew);
        em.persist(cpCrew);
        ////////////////
        expense = new Expense();
        expense.setCategory(stfLevel);
        expense.setType("Variable Operation Cost");
        expense.setPayable(salary);
        expense.setCostSource(username);
        em.persist(expense);
        em.flush();
        payroll = new Payroll();
        payroll.setName(username);
        payroll.setSalary(salary);
        payroll.setBonus(0.0);
        em.persist(payroll);
        em.flush();
    }

    public String encrypt(String username, String password) {
        String temp;
        if (!username.isEmpty() && !password.isEmpty()) {
            System.out.println("*****The original password for " + username + " is " + password + "*****");
            temp = cryptoHelper.doMD5Hashing(username + password);
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

    @Override
    public boolean checkEmailDuplicate(String email, String emailEdited) {
        if (email.equals(emailEdited)) {
            return false;
        } else {
            return checkEmailExists(emailEdited);
        }
    }

    @Override
    public void editCbCrew(String username, String stfType, String password, String pswEdited, String email, String emailEdited, String firstName, String lastName, String stfLevel, Double salary, Double hourPay, String secondLang, Integer attempt, Integer locked) {

        CabinCrew cbCrew = em.find(CabinCrew.class, username);

        cbCrew.setCbName(username);
        cbCrew.setStfType(stfType);
        cbCrew.setEmail(emailEdited);
        cbCrew.setFirstName(firstName);
        cbCrew.setLastName(lastName);
        cbCrew.setStfLevel(stfLevel);
        cbCrew.setSalary(salary);
        cbCrew.setHourPay(hourPay);
        cbCrew.setSecondLang(secondLang);
        cbCrew.setAttempt(attempt);
        cbCrew.setLocked(locked);
        if (password.equals(pswEdited)) {
            System.out.println("***Password does not changed***");
            cbCrew.setCbPassword(password);
        } else {
            hPwd = this.encrypt(username, pswEdited);
            cbCrew.setCbPassword(hPwd);
        }
        cbCrew.getUser().setComEmail(emailEdited);

        em.merge(cbCrew);
        em.flush();
        //////////////////////////////
        Query q1 = em.createQuery("SELECT e FROM Expense e where e.costSource=:username");
        q1.setParameter("username", username);
        if (q1.getResultList().isEmpty()) {
            System.out.println("There is no existing expense related to this staff " + username);
        } else {
            expense = (Expense) q1.getResultList().get(0);
            expense.setPayable(salary);
            em.merge(expense);
            em.flush();
        }
        Query q2 = em.createQuery("SELECT p FROM Payroll p where p.name=:username");
        q2.setParameter("username", username);
        if (q2.getResultList().isEmpty()) {
            System.out.println("There is no existing expense related to this staff " + username);
        } else {
            payroll = (Payroll) q2.getResultList().get(0);
            payroll.setSalary(salary);
            payroll.setBonus(hourPay);
            em.merge(payroll);
            em.flush();
        }
    }

    @Override
    public void editCpCrew(String username, String stfType, String password, String pswEdited, String email, String emailEdited, String firstName, String lastName, String stfLevel, Double salary, Double hourPay, String licence, Integer attempt, Integer locked) {
        CockpitCrew cpCrew = em.find(CockpitCrew.class, username);

        cpCrew.setCpName(username);
        cpCrew.setStfType(stfType);
        cpCrew.setEmail(emailEdited);
        cpCrew.setFirstName(firstName);
        cpCrew.setLastName(lastName);
        cpCrew.setStfLevel(stfLevel);
        cpCrew.setSalary(salary);
        cpCrew.setHourPay(hourPay);
        cpCrew.setLicence(licence);
        cpCrew.setAttempt(attempt);
        cpCrew.setLocked(locked);
        if (password.equals(pswEdited)) {
            System.out.println("***Password does not changed***");
            cpCrew.setCpPassword(password);
        } else {
            hPwd = this.encrypt(username, password);
            cpCrew.setCpPassword(hPwd);
        }
        cpCrew.getUser().setComEmail(emailEdited);

        em.merge(cpCrew);
        em.flush();

        //////////////////////////////
        Query q1 = em.createQuery("SELECT e FROM Expense e where e.costSource=:username");
        q1.setParameter("username", username);
        if (q1.getResultList().isEmpty()) {
            System.out.println("There is no existing expense related to this staff " + username);
        } else {
            expense = (Expense) q1.getResultList().get(0);
            expense.setPayable(salary);
            em.merge(expense);
            em.flush();
        }
        Query q2 = em.createQuery("SELECT p FROM Payroll p where p.name=:username");
        q2.setParameter("username", username);
        if (q2.getResultList().isEmpty()) {
            System.out.println("There is no existing expense related to this staff " + username);
        } else {
            payroll = (Payroll) q2.getResultList().get(0);
            payroll.setSalary(salary);
            payroll.setBonus(hourPay);
            em.merge(payroll);
            em.flush();
        }
    }

    @Override
    public int getLockedOutStatus(String username, String stfType) {
        if (stfType.equals("officeStaff")) {
            OfficeStaff officeStaff = em.find(OfficeStaff.class, username);
            if (officeStaff != null) {
                if (officeStaff.getLocked() == 1) {
                    return 1;
                }
            }

        } else if (stfType.equals("groundStaff")) {
            GroundStaff grdStaff = em.find(GroundStaff.class, username);
            if (grdStaff != null) {
                if (grdStaff.getLocked() == 1) {
                    return 1;
                }
            }

        } else if (stfType.equals("cabin")) {
            CabinCrew cbCrew = em.find(CabinCrew.class, username);
            if (cbCrew != null) {
                if (cbCrew.getLocked() == 1) {
                    return 1;
                }
            }

        } else if (stfType.equals("cockpit")) {
            CockpitCrew cpCrew = em.find(CockpitCrew.class, username);
            if (cpCrew != null) {
                if (cpCrew.getLocked() == 1) {
                    return 1;
                }
            }
        }

        return 0;
    }

    @Override
    public void editProfile(String username, String stfType, String pswEdited, String email, String emailEdited) {
        if (stfType.equals("officeStaff")) {
            OfficeStaff officeStaff = em.find(OfficeStaff.class, username);
            if (pswEdited.isEmpty()) {
                System.out.println("***Password does not changed***");
            } else {
                hPwd = this.encrypt(username, pswEdited);
                officeStaff.setOffPassword(hPwd);
            }
            officeStaff.setEmail(emailEdited);
            officeStaff.getUser().setComEmail(emailEdited);

            em.merge(officeStaff);
            em.flush();
        } else if (stfType.equals("groundStaff")) {
            GroundStaff grdStaff = em.find(GroundStaff.class, username);
            if (pswEdited.isEmpty()) {
                System.out.println("***Password does not changed***");
            } else {
                hPwd = this.encrypt(username, pswEdited);
                grdStaff.setGrdPassword(hPwd);
            }
            grdStaff.setEmail(emailEdited);
            grdStaff.getUser().setComEmail(emailEdited);

            em.merge(grdStaff);
            em.flush();
        } else if (stfType.equals("cabin")) {
            CabinCrew cbCrew = em.find(CabinCrew.class, username);
            if (pswEdited.isEmpty()) {
                System.out.println("***Password does not changed***");
            } else {
                hPwd = this.encrypt(username, pswEdited);
                cbCrew.setCbPassword(hPwd);
            }
            cbCrew.setEmail(emailEdited);
            cbCrew.getUser().setComEmail(emailEdited);

            em.merge(cbCrew);
            em.flush();
        } else if (stfType.equals("cockpit")) {
            CockpitCrew cpCrew = em.find(CockpitCrew.class, username);
            if (pswEdited.isEmpty()) {
                System.out.println("***Password does not changed***");
            } else {
                hPwd = this.encrypt(username, pswEdited);
                cpCrew.setCpPassword(hPwd);
            }
            cpCrew.setEmail(emailEdited);
            cpCrew.getUser().setComEmail(emailEdited);

            em.merge(cpCrew);
            em.flush();
        }
    }

    @Override
    public void editStaff(String username, String stfType, String password, String pswEdited, String email, String emailEdited, String firstName, String lastName, String stfLevel, Double salary, Double hourPay, Integer attempt, Integer locked) {

        if (stfType.equals("officeStaff")) {
            OfficeStaff officeStaff = em.find(OfficeStaff.class, username);
            UserEntity userEntity = em.find(UserEntity.class, email);
            officeStaff.setOffName(username);
            officeStaff.setStfType(stfType);
            officeStaff.setFirstName(firstName);
            officeStaff.setLastName(lastName);
            officeStaff.setStfLevel(stfLevel);
            officeStaff.setSalary(salary);
            officeStaff.setHourPay(hourPay);
            officeStaff.setAttempt(attempt);
            officeStaff.setLocked(locked);
            if (password.equals(pswEdited)) {
                System.out.println("***Password does not changed***");
                officeStaff.setOffPassword(password);
            } else {
                hPwd = this.encrypt(username, pswEdited);
                System.out.println("inside editstaff" + hPwd);
                System.out.println("inside editstaff" + password);
                officeStaff.setOffPassword(hPwd);
            }
            officeStaff.setEmail(emailEdited);
            officeStaff.getUser().setComEmail(emailEdited);

            em.merge(officeStaff);
            em.flush();
        } else if (stfType.equals("groundStaff")) {
            GroundStaff grdStaff = em.find(GroundStaff.class, username);

            grdStaff.setGrdName(username);
            grdStaff.setEmail(emailEdited);
            grdStaff.setStfType(stfType);
            grdStaff.setFirstName(firstName);
            grdStaff.setLastName(lastName);
            grdStaff.setStfLevel(stfLevel);
            grdStaff.setSalary(salary);
            grdStaff.setHourPay(hourPay);
            grdStaff.setAttempt(attempt);
            grdStaff.setLocked(locked);
            if (password.equals(pswEdited)) {
                System.out.println("***Password does not changed***");
                grdStaff.setGrdPassword(password);
            } else {
                hPwd = this.encrypt(username, pswEdited);
                grdStaff.setGrdPassword(hPwd);
            }
            grdStaff.getUser().setComEmail(emailEdited);

            em.merge(grdStaff);
            em.flush();

        }
        //////////////////////////////
        Query q1 = em.createQuery("SELECT e FROM Expense e where e.costSource=:username");
        q1.setParameter("username", username);
        if (q1.getResultList().isEmpty()) {
            System.out.println("There is no existing expense related to this staff " + username);
        } else {
            expense = (Expense) q1.getResultList().get(0);
            expense.setPayable(salary);
            em.merge(expense);
            em.flush();
        }
        Query q2 = em.createQuery("SELECT p FROM Payroll p where p.name=:username");
        q2.setParameter("username", username);
        if (q2.getResultList().isEmpty()) {
            System.out.println("There is no existing expense related to this staff " + username);
        } else {
            payroll = (Payroll) q2.getResultList().get(0);
            payroll.setSalary(salary);
            payroll.setBonus(hourPay);
            em.merge(payroll);
            em.flush();
        }
    }

    @Override
    public boolean validateLogin(String username, String password, String stfType) {
        Query query = null;

        hPwd = this.encrypt(username, password);
        System.out.println("validatelogin:" + hPwd);
        System.out.println("validatelogin:" + password);
        if (stfType.equals("administrator")) {
            query = em.createQuery("SELECT u FROM AdminStaff u WHERE u.admName = :inUserName and u.admPassword=:inPassWord and u.stfType=:inStfType");
            query.setParameter("inPassWord", password);
            query.setParameter("inUserName", username);
            query.setParameter("inStfType", stfType);
            List resultList = new ArrayList<AdminStaff>();
            resultList = (List) query.getResultList();
            if (resultList.isEmpty()) {
                return false;

            } else {
                return true;
            }

        } else if (stfType.equals("officeStaff")) {
            query = em.createQuery("SELECT u FROM OfficeStaff u WHERE u.offName = :inUserName and u.offPassword=:inPassWord and u.stfType=:inStfType");
            query.setParameter("inPassWord", hPwd);
            query.setParameter("inUserName", username);
            query.setParameter("inStfType", stfType);
            List resultList = new ArrayList<OfficeStaff>();
            resultList = (List) query.getResultList();
            if (resultList.isEmpty()) {
                offStaff = this.getOfficeStaff(username);
                if (offStaff != null) {
                    temp = offStaff.getAttempt();
                    temp++;
                    if (temp > 3) {
                        offStaff.setLocked(1);
                    }

                    offStaff.setAttempt(temp);
                    em.merge(offStaff);
                    em.flush();
                }
                return false;

            } else {
                offStaff = this.getOfficeStaff(username);
                locked = offStaff.getLocked();
                if (locked == 1) {
                    return false;
                }
                offStaff.setAttempt(0);
                em.merge(offStaff);
                em.flush();
                return true;
            }
        } else if (stfType.equals("groundStaff")) {
            query = em.createQuery("SELECT u FROM GroundStaff u WHERE u.grdName = :inUserName and u.grdPassword=:inPassWord and u.stfType=:inStfType");
            query.setParameter("inPassWord", hPwd);
            query.setParameter("inUserName", username);
            query.setParameter("inStfType", stfType);
            List resultList = new ArrayList<GroundStaff>();
            resultList = (List) query.getResultList();
            if (resultList.isEmpty()) {
                grdStaff = this.getGroundStaff(username);
                if (grdStaff != null) {
                    temp = grdStaff.getAttempt();
                    temp++;
                    if (temp > 3) {
                        grdStaff.setLocked(1);
                    }

                    grdStaff.setAttempt(temp);
                    em.merge(grdStaff);
                    em.flush();
                }
                return false;

            } else {
                grdStaff = this.getGroundStaff(username);
                locked = grdStaff.getLocked();
                if (locked == 1) {
                    return false;
                }
                grdStaff.setAttempt(0);
                em.merge(grdStaff);
                em.flush();
                return true;
            }
        } else if (stfType.equals("cabin")) {
            query = em.createQuery("SELECT u FROM CabinCrew u WHERE u.cbName = :inUserName and u.cbPassword=:inPassWord and u.stfType=:inStfType");
            query.setParameter("inPassWord", hPwd);
            query.setParameter("inUserName", username);
            query.setParameter("inStfType", stfType);
            List resultList = new ArrayList<CabinCrew>();
            resultList = (List) query.getResultList();
            if (resultList.isEmpty()) {
                cbCrew = this.getCabinCrew(username);
                if (cbCrew != null) {
                    temp = cbCrew.getAttempt();
                    temp++;
                    if (temp > 3) {
                        cbCrew.setLocked(1);
                    }

                    cbCrew.setAttempt(temp);
                    em.merge(cbCrew);
                    em.flush();
                }
                return false;

            } else {
                cbCrew = this.getCabinCrew(username);
                locked = cbCrew.getLocked();
                if (locked == 1) {
                    return false;
                }
                cbCrew.setAttempt(0);
                em.merge(cbCrew);
                em.flush();
                return true;
            }
        } else if (stfType.equals("cockpit")) {
            query = em.createQuery("SELECT u FROM CockpitCrew u WHERE u.cpName = :inUserName and u.cpPassword=:inPassWord and u.stfType=:inStfType");
            query.setParameter("inPassWord", hPwd);
            query.setParameter("inUserName", username);
            query.setParameter("inStfType", stfType);
            List resultList = new ArrayList<CockpitCrew>();
            resultList = (List) query.getResultList();
            if (resultList.isEmpty()) {
                cpCrew = this.getCockpitCrew(username);
                if (cpCrew != null) {
                    temp = cpCrew.getAttempt();
                    temp++;
                    if (temp > 3) {
                        cpCrew.setLocked(1);
                    }

                    cpCrew.setAttempt(temp);
                    em.merge(cpCrew);
                    em.flush();
                }
                return false;

            } else {
                cpCrew = this.getCockpitCrew(username);
                locked = cpCrew.getLocked();
                if (locked == 1) {
                    return false;
                }
                cpCrew.setAttempt(0);
                em.merge(cpCrew);
                em.flush();
                return true;
            }
        } else {
            return false;
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

    @Override
    public OfficeStaff getOfficeStaff(String username) {
        OfficeStaff officeStaff = em.find(OfficeStaff.class, username);
        return officeStaff;
    }

    @Override
    public GroundStaff getGroundStaff(String username) {
        GroundStaff grdStaff = em.find(GroundStaff.class, username);
        return grdStaff;
    }

    @Override
    public CabinCrew getCabinCrew(String username) {
        CabinCrew cbCrew = em.find(CabinCrew.class, username);
        return cbCrew;
    }

    @Override
    public CockpitCrew getCockpitCrew(String username) {
        CockpitCrew cpCrew = em.find(CockpitCrew.class, username);
        return cpCrew;
    }

    @Override
    public boolean validateDDSLogin(String username, String password) {
        Query query = null;

        hPwd = this.encrypt(username, password);
        System.out.println("validatelogin:" + hPwd);
        System.out.println("validatelogin:" + password);

        query = em.createQuery("SELECT u FROM Agency u WHERE u.agencyID = :inUserName and u.agenPwd= :inPassWord ");
        query.setParameter("inPassWord", hPwd);
        query.setParameter("inUserName", username);
        List resultList = new ArrayList<Agency>();
        resultList = (List) query.getResultList();
        if (resultList.isEmpty()) {
            return false;

        } else {
            return true;
        }

    }

    @Override
    public String getDDSCompanyName(String userName) {
        Agency tempAg = new Agency();
        tempAg = em.find(Agency.class, userName);

        if (tempAg != null) {
            return tempAg.getName();
        } else {
            return null;
        }

    }

    //////////////////////////////
    @Override
    public String getStaffName(String username, String type) {
        String name = "";
        if (type.equals("administrator")) {
            Query q1 = em.createQuery("SELECT u FROM AdminStaff u WHERE u.admName=:username and u.stfType=:type");
            q1.setParameter("username", username);
            q1.setParameter("type", type);
            List<AdminStaff> resultList = new ArrayList<>();
            resultList = (List) q1.getResultList();
            if (!resultList.isEmpty()) {
                name = resultList.get(0).getAdmName();
            }
        } else if (type.equals("officeStaff")) {
            Query q1 = em.createQuery("SELECT u FROM OfficeStaff u WHERE u.offName=:username and u.stfType=:type");
            q1.setParameter("username", username);
            q1.setParameter("type", type);
            List<OfficeStaff> resultList = new ArrayList<>();
            resultList = (List) q1.getResultList();
            if (!resultList.isEmpty()) {
                name = resultList.get(0).getFirstName();
            }
        } else if (type.equals("groundStaff")) {
            Query q1 = em.createQuery("SELECT u FROM GroundStaff u WHERE u.grdName=:username and u.stfType=:type");
            q1.setParameter("username", username);
            q1.setParameter("type", type);
            List<GroundStaff> resultList = new ArrayList<>();
            resultList = (List) q1.getResultList();
            if (!resultList.isEmpty()) {
                name = resultList.get(0).getFirstName();
            }

        } else if (type.equals("cabin")) {
            Query q1 = em.createQuery("SELECT u FROM CabinCrew u WHERE u.cbName=:username and u.stfType=:type");
            q1.setParameter("username", username);
            q1.setParameter("type", type);
            List<CabinCrew> resultList = new ArrayList<>();
            resultList = (List) q1.getResultList();
            if (!resultList.isEmpty()) {
                name = resultList.get(0).getFirstName();
            }
        } else if (type.equals("cockpit")) {
            Query q1 = em.createQuery("SELECT u FROM CockpitCrew u WHERE u.cpName=:username and u.stfType=:type");
            q1.setParameter("username", username);
            q1.setParameter("type", type);
            List<CockpitCrew> resultList = new ArrayList<>();
            resultList = (List) q1.getResultList();
            if (!resultList.isEmpty()) {
                name = resultList.get(0).getFirstName();
            }
        }else{
            name = type;
        }
        return name;
    }
}
