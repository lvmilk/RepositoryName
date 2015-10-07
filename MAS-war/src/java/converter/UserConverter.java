/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

/**
 *
 * @author LI HAO
 */

import Entity.CommonInfaEntity.UserEntity;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
 
 
@FacesConverter("userConverter")
public class UserConverter implements Converter {
 
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0) {
            List<UserEntity> users = (List<UserEntity>)fc.getExternalContext().getSessionMap().get("users");
            
            System.out.println("******"+ users);
            System.err.println("Converter: " + users.size());
            
            for(UserEntity ue:users)
            {
                if(ue.getUsername().equals(value))
                {
                   return ue;
                }
            }
            
            return null;
        }
        else {
            return null;
        }
    }
 
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((UserEntity) object).getUsername());
        }
        else {
            return null;
        }
    }   
}     
