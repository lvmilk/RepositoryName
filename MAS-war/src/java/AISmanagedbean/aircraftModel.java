/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AISmanagedbean;

import java.io.IOException;
import java.util.ArrayList;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author LIU YUQI'
 */
@Named(value = "aircraftModel")
@RequestScoped

public class aircraftModel {

    private String model;
    
   public aircraftModel() {
       model=new String();
    }
    
  public String getModel(){
  
  return model;
  }
  
  public void setModel(String Airmodel){
  this.model=Airmodel;
  }
  
  public void checkModel() throws IOException{
     
    
  if(model!=null && !(model.isEmpty())){
      
       System.out.println(" Model is "+model);
      FacesContext.getCurrentInstance().getExternalContext().redirect("DisplayAircraftSeat.xhtml");
      
    
  }
  else
  {
       System.out.println("No model chosen");
    //  FacesContext.getCurrentInstance().addMessage("choose one model", null);
  } 
}
}
