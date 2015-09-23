///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package APSmanagedbean;
//
//import Entity.APS.Airport;
//import SessionBean.APS.RoutePlanningBeanLocal;
//import javax.annotation.PostConstruct;
//import javax.ejb.EJB;
//import javax.faces.context.FacesContext;
//import javax.inject.Named;
//import javax.faces.view.ViewScoped;
//
///**
// *
// * @author Xu
// */
//@Named(value = "viewAirportDetailManagedBean")
//@ViewScoped
//public class ViewAirportDetailManagedBean {
//
//    @EJB
//    private RoutePlanningBeanLocal rpb;
//    private Airport viewAp = new Airport();
//
//    @PostConstruct
//    public void init() {
//        viewAp = (Airport) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("viewAp");
//    }
//
//    public ViewAirportDetailManagedBean() {
//    }
//    
//    public void viewAirportDetail() {
//        
//    }
//
//}
