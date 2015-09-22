/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.CommonInfaSB;

import Entity.CommonInfaEntity.*;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author LIU YUQI'
 */
@Local
public interface manageAccountLocal {
    
    
    public boolean validateLogin (String username, String password, String staffType);
    public void addAdmin(String username,String password, String stfType);
    public void addAccount(String username, String password,String email, String stfType);
    public void addCocpitAcc(String username, String password, String email ,String stfType, String licence );
    public boolean checkAccDuplicate(String username,String stfType);
    public boolean checkNameDuplicate(String username, String usernameEdited);
    public boolean checkEmailDuplicate(String email, String emailEdited);
    public void editOfficeStaff(String usernameEdited, String stfType, String password, String emailEdited);
    public boolean delAcc(List<OfficeStaff> selectedOffStf);
    public List<OfficeStaff> getAllOfficeStaff();
    public List<GroundStaff> getAllGoundStaff();
    public List<CabinCrew> getAllCabinCrew();
    public List<CockpitCrew> getAllCockpitCrew();
}
