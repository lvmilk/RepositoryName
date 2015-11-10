/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AASmanagedbean;

import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;


/**
 *
 * @author Xi
 */
@ManagedBean
@ApplicationScoped
@Named(value = "EMB")
public class ExporterManagedBean implements Serializable{

    private Boolean customExporter;
    /**
     * Creates a new instance of ExporterManagedBean
     */
    public ExporterManagedBean() {
         customExporter=false;
    }
    
    public Boolean getCustomExporter() {
        return customExporter;
    }

    public void setCustomExporter(Boolean customExporter) {
        this.customExporter = customExporter;
    }

}
