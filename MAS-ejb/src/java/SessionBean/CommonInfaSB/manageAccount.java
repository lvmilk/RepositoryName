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
    EntityManager entityManager;
//    FFPMember newUser;
    AdminStaff admStaff;
    
    
    public manageAccount(){
    
    }

    @Override
    public void addAccount(String username, String password, String stfType) {
//        newUser = new FFPMember();
        admStaff = new AdminStaff();
        admStaff.create(username, password, stfType);
        entityManager.persist(admStaff);

    }

    @Override
    public boolean validateLogin(String username, String password, String stfType) {
        Query query = entityManager.createQuery("SELECT u FROM AdminStaff u WHERE u.admName = :inUserName and u.admPassword=:inPassWord and u.admType=:inStfType");
        query.setParameter("inPassWord", password);
        query.setParameter("inUserName", username);
        query.setParameter("inStfType", stfType);
    //    SystemUser user = null;


        List resultList = new ArrayList<AdminStaff>();
        resultList = (List) query.getResultList();
        if (resultList.isEmpty()) {
            return false;

        } else {
            return true;
        }

    }
    
}
