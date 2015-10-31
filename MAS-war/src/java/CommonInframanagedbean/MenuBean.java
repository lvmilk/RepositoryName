/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CommonInframanagedbean;

import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;

import org.primefaces.component.column.Column;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuItem;
import org.primefaces.model.menu.MenuModel;
import org.primefaces.model.menu.Submenu;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultSubMenu;

/**
 *
 * @author LIU YUQI'
 */
@Named(value = "MenuBean")
@Dependent
public class MenuBean {

    private MenuModel model;

    public MenuBean() {
        
    }
    public void init(){
        model = new DefaultMenuModel();
        
//First submenu
        DefaultSubMenu firstSubmenu = new DefaultSubMenu("Airline Planning");
        DefaultMenuItem item = new DefaultMenuItem("Fleet Planning");
        item.setUrl("http://www.primefaces.org");
        item.setIcon("ui-icon-home");
        firstSubmenu.addElement(item);
        model.addElement(firstSubmenu);
        
         item = new DefaultMenuItem("Route Planning");
        item.setUrl("http://www.primefaces.org");
        item.setIcon("ui-icon-home");
        firstSubmenu.addElement(item);
       
          item = new DefaultMenuItem("Flight scheduling");
        item.setUrl("http://www.primefaces.org");
        item.setIcon("ui-icon-home");
        firstSubmenu.addElement(item);
        
        
//Second submenu
        DefaultSubMenu secondSubmenu = new DefaultSubMenu("Airline Inventory Management");
        item = new DefaultMenuItem("Seat Arrangement");
         item.setUrl("./seat_aircraft.xhtml");
        item.setIcon("ui-icon-disk");
        
//        item.setCommand("#{MenuBean.save}");
//        item.setUpdate("messages");
        secondSubmenu.addElement(item);
        
        
        item = new DefaultMenuItem("Booking Class Management");
         item.setUrl("./addBookingClass.xhtml");
        item.setIcon("ui-icon-close");
//        item.setCommand("#{MenuBean.delete}");
//        item.setAjax(false);
        secondSubmenu.addElement(item);
        
        
        item = new DefaultMenuItem("Pricing");
         item.setUrl("http://www.primefaces.org");
       item.setIcon("ui-icon-search");
//        item.setCommand("#{MenuBean.redirect}");
        secondSubmenu.addElement(item);
        
            item = new DefaultMenuItem("Seat Inventory");
         item.setUrl("http://www.primefaces.org");
       item.setIcon("ui-icon-search");
//        item.setCommand("#{MenuBean.redirect}");
        secondSubmenu.addElement(item);
        
        
        
        model.addElement(secondSubmenu);
    }

    public MenuModel getModel() {
        return model;
    }

    public void redirect() {
    }

    public void save() {
    }

    public void delete() {
    }
}
