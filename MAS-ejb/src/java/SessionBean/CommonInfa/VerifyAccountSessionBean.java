/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.CommonInfa;

import Entity.CommonInfa.CabinCrew;
import Entity.CommonInfa.OfficeStaff;
import Entity.CommonInfa.GroundStaff;
import Entity.CommonInfa.CockpitCrew;
import javax.ejb.Stateless;
import java.util.*;
import Entity.*;
import static java.time.Clock.system;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.CryptoHelper;

/**
 *
 * @author LI HAO
 */
@Stateless
public class VerifyAccountSessionBean implements VerifyAccountSessionBeanLocal {

    @PersistenceContext
    EntityManager em;
    private CryptoHelper cryptoHelper = CryptoHelper.getInstanceOf();
    String hPwd;

    public void resetPwd(String username, String password, String stfType) {
        if (stfType.equals("officeStaff")) {
            OfficeStaff officeStaff = em.find(OfficeStaff.class, username);
            hPwd = this.encrypt(username, password);
            officeStaff.setOffPassword(hPwd);
            em.merge(officeStaff);
            em.flush();

        } else if (stfType.equals("groundStaff")) {
            GroundStaff grdStaff = em.find(GroundStaff.class, username);
            hPwd = this.encrypt(username, password);
            grdStaff.setGrdPassword(hPwd);
            em.merge(grdStaff);
            em.flush();

        } else if (stfType.equals("cabin")) {
            CabinCrew cbCrew = em.find(CabinCrew.class, username);
            hPwd = this.encrypt(username, password);
            cbCrew.setCbPassword(hPwd);
            em.merge(cbCrew);
            em.flush();

        } else if (stfType.equals("cockpit")) {
            CockpitCrew cpCrew = em.find(CockpitCrew.class, username);
            hPwd = this.encrypt(username, password);
            cpCrew.setCpPassword(hPwd);
            em.merge(cpCrew);
            em.flush();
        }

    }

    public boolean validateAccInfo(String username, String email, String stfType) {
        if (stfType.equals("officeStaff")) {
            OfficeStaff officeStaff = em.find(OfficeStaff.class, username);
            if (officeStaff == null) {
                return false;
            } else {
                if (officeStaff.getEmail().equals(email)) {
                    return true;
                }
                return false;
            }

        } else if (stfType.equals("groundStaff")) {
            GroundStaff grdStaff = em.find(GroundStaff.class, username);

            if (grdStaff == null) {
                return false;
            } else {
                if (grdStaff.getEmail().equals(email)) {
                    return true;
                }
                return false;
            }

        } else if (stfType.equals("cabin")) {
            CabinCrew cbCrew = em.find(CabinCrew.class, username);

            if (cbCrew == null) {
                return false;
            } else {
                if (cbCrew.getEmail().equals(email)) {
                    return true;
                }
                return false;
            }

        } else if (stfType.equals("cockpit")) {
            CockpitCrew cpCrew = em.find(CockpitCrew.class, username);

            if (cpCrew == null) {
                return false;
            } else {
                if (cpCrew.getEmail().equals(email)) {
                    return true;
                }
                return false;
            }

        }

        return false;
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
}
