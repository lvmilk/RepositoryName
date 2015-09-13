/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

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
        model = new DefaultMenuModel();
//First submenu
        DefaultSubMenu firstSubmenu = new DefaultSubMenu("Dynamic Submenu");
        DefaultMenuItem item = new DefaultMenuItem("External");
        item.setUrl("http://www.primefaces.org");
        item.setIcon("ui-icon-home");
        firstSubmenu.addElement(item);
        model.addElement(firstSubmenu);
//Second submenu
        //Second submenu
//        DefaultSubMenu secondSubmenu = new DefaultSubMenu("Dynamic Actions");
//        item = new DefaultMenuItem("Save");
//        item.setIcon("ui-icon-disk");
//        item.setCommand("#{menuBean.save}");
//        item.setUpdate("messages");
//        secondSubmenu.addElement(item);
//        item = new DefaultMenuItem("Delete");
//        item.setIcon("ui-icon-close");
//        item.setCommand("#{menuBean.delete}");
//        item.setAjax(false);
//        secondSubmenu.addElement(item);
//        item = new DefaultMenuItem("Redirect");
//        item.setIcon("ui-icon-search");
//        item.setCommand("#{menuBean.redirect}");
//        secondSubmenu.addElement(item);
//        model.addElement(secondSubmenu);
    }


public MenuModel getModel() {
        return model;
    }
    public void redirect(){}
    public void save(){}
    public void delete(){}
}
