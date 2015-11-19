/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMClientmanagedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Xu
 */
@Named(value = "GMB")
@SessionScoped
public class GalleriaManagedBean implements Serializable {

    private List<String> images;

    public GalleriaManagedBean() {
    }

    @PostConstruct
    public void init() {
        images = new ArrayList<String>();
        for (int i = 1; i <= 5; i++) {
            images.add("homepage" + i + ".jpg");
        }
    }
 
    public List<String> getImages() {
        return images;
    }
}
