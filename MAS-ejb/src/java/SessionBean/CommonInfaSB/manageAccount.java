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
    
    
    public manageAccount(){
    
    }

    @Override
    public void addAccount(String username, String password, String email,String stfType) {
//        newUser = new FFPMember();
        System.out.println("Currently in addAccount");
        if(stfType.equals("administrator"))
        {
            admStaff = new AdminStaff();
            admStaff.create(username, password, stfType);
            em.persist(admStaff);
        }
        else if (stfType.equals("officeStaff"))
        {
            offStaff=new OfficeStaff();
            offStaff.create(stfType, password,email,stfType);
            em.persist(offStaff);
        }
        else if (stfType.equals("groundStaff"))
        {
            grdStaff=new GroundStaff();
            
            
        }
        else if(stfType.equals("cabin"))
        {
            System.out.println(stfType);
            cbCrew= new CabinCrew();
            cbCrew.create(username, password, email, stfType);
            em.persist(cbCrew);
        }

    }
   
    @Override
    public void addCocpitAcc(String username, String password, String email, String stfType, String licence)
    {
        
    }
    
    @Override
    public boolean validateLogin(String username, String password, String stfType) {
        Query query = em.createQuery("SELECT u FROM AdminStaff u WHERE u.admName = :inUserName and u.admPassword=:inPassWord and u.stfType=:inStfType");
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
