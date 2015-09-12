/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.CommonInfaSB;

import javax.ejb.Stateless;
import java.util.*;
import Entity.FFPMember;
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
    FFPMember newUser;
    
    public manageAccount(){
    
    }

    @Override
    public void addAccount(String username, String password) {
        newUser = new FFPMember();
        entityManager.persist(newUser);

    }

    @Override
    public boolean validateLogin(String username, String password, String type) {
        Query query = entityManager.createQuery("SELECT u FROM FFPMember u WHERE u.username = :inUserName and u.password=:inPassWord");
        query.setParameter("inPassWord", password);
        query.setParameter("inUserName", username);
    //    SystemUser user = null;


        List resultList = new ArrayList<FFPMember>();
        resultList = (List) query.getResultList();
        if (resultList.isEmpty()) {
            return false;

        } else {
            return true;
        }

    }
    
}
