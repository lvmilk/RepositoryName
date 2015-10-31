/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.CommonInfra;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;

/**
 *
 * @author wang
 */
@Startup
@Singleton
@LocalBean
public class DataImportBean {
    private String status;
    
 @PostConstruct
  void init () {
    status = "Ready";
    
  }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
