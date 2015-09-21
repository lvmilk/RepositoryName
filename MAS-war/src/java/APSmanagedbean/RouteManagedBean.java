package APSmanagedbean;

import SessionBean.APS.RoutePlanningBeanLocal;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Xu
 */
@Named(value = "RouteManagedBean")
@ViewScoped
public class RouteManagedBean implements Serializable{

    @EJB
    private RoutePlanningBeanLocal rpb;

    private UIComponent uIComponent;

    private Double distance;
    private String originIATA;
    private String destIATA;

    public RouteManagedBean() {
    }

    public void addRoute() throws Exception{
        try{ 
            rpb.addRoute(originIATA, destIATA, distance);
            FacesContext.getCurrentInstance().getExternalContext().redirect("./addRouteSuccess.xhtml");
        } catch(Exception ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Route has already been added.", ""));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getOriginIATA() {
        return originIATA;
    }

    public void setOriginIATA(String originIATA) {
        this.originIATA = originIATA;
    }

    public String getDestIATA() {
        return destIATA;
    }

    public void setDestIATA(String destIATA) {
        this.destIATA = destIATA;
    }

    public UIComponent getuIComponent() {
        return uIComponent;
    }

    public void setuIComponent(UIComponent uIComponent) {
        this.uIComponent = uIComponent;
    }

}
