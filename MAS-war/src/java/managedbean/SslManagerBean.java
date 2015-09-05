/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import java.io.IOException;
import javafx.event.ActionEvent;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author LI HAO
 */
@Named(value = "testSslManagedBean")
@RequestScoped
public class SslManagerBean {

    public void startSsl(ActionEvent event) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
    }

    public void endSsl(ActionEvent event) throws IOException {
        String serverName = FacesContext.getCurrentInstance().getExternalContext().getRequestServerName();
        String serverPort = "8080";
        FacesContext.getCurrentInstance().getExternalContext().redirect("http://" + serverName + ":" + serverPort + "staffMain.xhtml");
    }

}
