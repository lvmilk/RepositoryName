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
    
    //used when creating email
    public boolean checkEmailExists(String email);
    //used when editing email
    public boolean checkEmailDuplicate(String email, String emailEdited);
    public void editStaff(String username, String stfType, String password,String pswEdited, String emailEdited);
    public void editCpCrew(String username, String stfType, String password,String pswEdited, String emailEdited, String licence);
    public boolean delAcc(List<OfficeStaff> selectedOffStf);
    public boolean delGrdAcc(List<GroundStaff> selectedGrdStf);
    public boolean delCabinAcc(List<CabinCrew> selectedCbCrew);
    public boolean delCockpitAcc(List<CockpitCrew> selectedCpCrew);
    public List<OfficeStaff> getAllOfficeStaff();
    public List<GroundStaff> getAllGoundStaff();
    public List<CabinCrew> getAllCabinCrew();
    public List<CockpitCrew> getAllCockpitCrew();
}
