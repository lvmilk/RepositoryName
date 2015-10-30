/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.CommonInfa;

import Entity.CommonInfa.UserEntity;
import Entity.CommonInfa.UserLog;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author LI HAO
 */
@Stateless
public class UserLogSessionBean implements UserLogSessionBeanLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
    private EntityManager em;

    private UserLog userLog;
    private UserEntity userEntity;

    @Override
    public void createLog(String username, String description) {
        userEntity = em.find(UserEntity.class, username);
        if (userEntity == null) {
            System.out.println("UserLogSessionBean: createLog(): no such user");
        } else {
            Calendar cldTime = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            try {
                String logTime = sdf.format(cldTime.getTime());
                userLog = new UserLog();
                userLog.create(username, logTime, description);
                userLog.setUserEntity(userEntity);
//                userEntity.getUserLog().add(userLog);
                System.out.println("UserLogSessionBean: createLog(): user log created");
                em.persist(userLog);
                em.flush();
                System.out.println("UserLogSessionBean: createLog(): user log added");

            } catch (Exception e) {
                e.printStackTrace();
            }
//            List<UserLog> logList=(List<UserLog>) userEntity.getUserLog();

//            
        }
    }

    /**
     * @return the userLog
     */
    public UserLog getUserLog() {
        return userLog;
    }

    /**
     * @param userLog the userLog to set
     */
    public void setUserLog(UserLog userLog) {
        this.userLog = userLog;
    }

    /**
     * @return the userEntity
     */
    public UserEntity getUserEntity() {
        return userEntity;
    }

    /**
     * @param userEntity the userEntity to set
     */
    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

}
