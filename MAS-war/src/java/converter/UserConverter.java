/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package converter;


import Entity.CommonInfa.UserEntity;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
 
 
@FacesConverter("userConverter")
public class UserConverter implements Converter {
 
    public Object getAsObject(FacesContext fc, UIComponent uic, String value)  {
         System.out.println("hohohohoohohohohohohoh");
        if(value != null && value.trim().length() > 0) {
            List<UserEntity> users = (List<UserEntity>)fc.getExternalContext().getSessionMap().get("users");
            
            System.out.println("******this is"+ users);
            System.err.println("Converter: " + users.size());
            
            for(UserEntity ue:users)
            {
                if(ue.getUsername().equals(value))
                {
                    System.out.println("matched value is "+value);
                   return ue;
                }
            }
            System.out.println("no value matched");
            return null;
        }
        else {
            System.out.println("value is null");
            return null;
        }
    }
 
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            System.out.println("******this is: haha"+String.valueOf(((UserEntity) object).getUsername()));
            return String.valueOf(((UserEntity) object).getUsername());
        }
        else {
            return null;
        }
    }   
}     
