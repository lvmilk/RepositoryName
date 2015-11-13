/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.CommonInfra;

import Entity.CommonInfa.CabinCrew;
import Entity.CommonInfa.OfficeStaff;
import Entity.CommonInfa.GroundStaff;
import Entity.CommonInfa.CockpitCrew;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author LIU YUQI & LI HAO'
 */
@Local
public interface ManageAccountBeanLocal {
    
    
    public boolean validateLogin (String username, String password, String staffType);
    public void addAdmin(String username,String password, String stfType);
    public void addAccount(String username, String password,String email, String stfType, String firstName,String lastName, String stfLevel, Double salary);
    public void addPartnerAcc(String pid, String pPwd,String companyName, String email, String stfType);
    
    public void addCocpitAcc(String username, String password, String email ,String stfType, String firstName,String lastName, String stfLevel, Double salary,String licence );
    public boolean checkAccDuplicate(String username,String stfType);
    public boolean checkNameDuplicate(String username, String usernameEdited);
    
    public boolean checkPartenrIDDuplicate(String partnerID, String stfType);
    public boolean checkPartnerEmailDuplicate(String pEmail);
    
    
    //used when creating email
    public boolean checkEmailExists(String email);
    //used when editing email
    public boolean checkEmailDuplicate(String email, String emailEdited);
    public void editStaff(String username, String stfType, String password,String pswEdited,String email, String emailEdited, String firstName, String lastName, String stfLevel,Double salary, Double hourPay,Integer attempt, Integer locked);
    public void editCbCrew(String username, String stfType, String password,String pswEdited,String email, String emailEdited, String firstName, String lastName, String stfLevel,Double salary, Double hourPay,String secondLang, Integer attempt, Integer locked);
    public void editCpCrew(String username, String stfType, String password,String pswEdited,String email, String emailEdited, String firstName, String lastName, String stfLevel,Double salary, Double hourPay,String licence, Integer attempt, Integer locked);
    public void editProfile(String username, String stfType, String pswEdited,String email, String emailEdited);
    public boolean delAcc(List<OfficeStaff> selectedOffStf);
    public boolean delGrdAcc(List<GroundStaff> selectedGrdStf);
    public boolean delCabinAcc(List<CabinCrew> selectedCbCrew);
    public boolean delCockpitAcc(List<CockpitCrew> selectedCpCrew);
    public List<OfficeStaff> getAllOfficeStaff();
    public List<GroundStaff> getAllGoundStaff();
    public List<CabinCrew> getAllCabinCrew();
    public List<CockpitCrew> getAllCockpitCrew();
    
    public OfficeStaff getOfficeStaff(String username);
    public GroundStaff getGroundStaff(String username);
    public CabinCrew getCabinCrew(String username);
    public CockpitCrew getCockpitCrew(String username);

    public int getLockedOutStatus(String username, String stfType);
    
    public boolean validateDDSLogin(String username, String password);

    public void addCabinAcc(String username, String password, String email, String stfType, String firstName, String lastName, String stfLevel, Double salary, String secondLang);
    public String getDDSCompanyName(String userName);
}
