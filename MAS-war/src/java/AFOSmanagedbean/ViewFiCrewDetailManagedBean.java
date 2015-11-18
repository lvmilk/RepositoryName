/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AFOSmanagedbean;

import Entity.APS.FlightInstance;
import Entity.CommonInfa.CabinCrew;
import Entity.CommonInfa.CockpitCrew;
import SessionBean.AFOS.CrewSchedulingBeanLocal;
import SessionBean.APS.FlightSchedulingBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Xu
 */
@Named(value = "VFCDMB")
@ViewScoped
public class ViewFiCrewDetailManagedBean implements Serializable {

    @EJB
    private CrewSchedulingBeanLocal csb;

    @EJB
    private FlightSchedulingBeanLocal fsb;

    private Long fiId;
    private FlightInstance fi;
    private List<CabinCrew> cabinleaderList = new ArrayList<>();
    private List<CabinCrew> cabincrewList = new ArrayList<>();
    private List<CockpitCrew> captainList = new ArrayList<>();
    private List<CockpitCrew> pilotList = new ArrayList<>();
    private List<CabinCrew> cabinList = new ArrayList<>();
    private List<CockpitCrew> cockpitList = new ArrayList<>();

    private List<CabinCrew> idleCabincrew = new ArrayList<>();
    private List<CabinCrew> idleCabinleader = new ArrayList<>();
    private List<CockpitCrew> idleCaptain = new ArrayList<>();
    private List<CockpitCrew> idlePilot = new ArrayList<>();
//    private List<CabinCrew> idleCabin = new ArrayList<>();
//    private List<CockpitCrew> idleCockpit = new ArrayList<>();

    private String removeCc;
    private String removeCp;

    private String addCrewType;
    private List<String> crewTypeList = new ArrayList<>();
    private String addCrewId;
    private List<String> addCrewIdList = new ArrayList<>();

    public ViewFiCrewDetailManagedBean() {
    }

    @PostConstruct
    public void init() {
        fiId = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("fiId");
        fi = fsb.findFlight(fiId);
        initCPCC();
        initIdleCPCC();
        crewTypeList.add("Captain");
        crewTypeList.add("Pilot");
        crewTypeList.add("Cabin Leader");
        crewTypeList.add("Cabin Crew");
    }

    public void initCPCC() {
        cabinList = fi.getCabinList();
        cockpitList = fi.getCockpitList();
        for (CabinCrew cc : cabinList) {
            if (cc.getStfLevel().equalsIgnoreCase("Cabin Leader")) {
                cabinleaderList.add(cc);
            } else {
                cabincrewList.add(cc);
            }
        }
        for (CockpitCrew cc : cockpitList) {
            if (cc.getStfLevel().equalsIgnoreCase("Captain")) {
                captainList.add(cc);
            } else {
                pilotList.add(cc);
            }
        }
        System.out.println("initCPCC() cabinleaderList " + cabinleaderList);
        System.out.println("initCPCC() cabincrewList " + cabincrewList);
        System.out.println("initCPCC() captainList " + captainList);
        System.out.println("initCPCC() pilotList " + pilotList);
    }

    public void initIdleCPCC() {
        idleCabincrew = csb.getIdleCabin("Cabin Crew", fi);
        idleCabinleader = csb.getIdleCabin("Cabin Leader", fi);
        idleCaptain = csb.getIdleLicensedCockpit("Captain", fi);
        idlePilot = csb.getIdleLicensedCockpit("Pilot", fi);
    }

    public void setCpCrewInfo() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        removeCp = params.get("removeCp");
        System.out.println("Call this method? cp = " + removeCp);
    }

    public void setCcCrewInfo() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        removeCc = params.get("removeCc");
        System.out.println("Call this method? cc = " + removeCc);
    }

    public void deleteCpFromFlight() {
        CockpitCrew cp = csb.findCPById(removeCp);
        csb.removeCpFromFlight(fi, cp);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Remove cockpit crew success", "Cockpit crew " + cp.getCpName() + " has been remove from flight " + fi));
    }

    public void deleteCcFromFlight() {
        System.out.println("AAAAA here removeCc in managed bean");
        CabinCrew cc = csb.findCCById(removeCc);
        System.out.println("AAAAA here removeCc in managed bean *** cc == " + cc.getCbName());
        csb.removeCcFromFlight(fi, cc);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Remove cabin crew success", "Cabin crew " + cc.getCbName() + " has been remove from flight " + fi));
    }

    public void addCrewToFlight() throws Exception {
        try {
            if (csb.getCrewType(addCrewId).equalsIgnoreCase("Cockpit")) {
                csb.addCPToFlight(addCrewId, fiId);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Add cockpit crew success", "Cockpit crew " + addCrewId + " has been added to the flight " + fi));
            }
            if (csb.getCrewType(addCrewId).equalsIgnoreCase("Cabin")) {
                csb.addCCToFlight(addCrewId, fiId);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Add cabin crew success", "Cabin crew " + addCrewId + " has been added to the flight " + fi));
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    public void onCrewTypeChange() {
        switch (addCrewType) {
            case "Captain": {
                addCrewIdList = new ArrayList<String>();
                for (CockpitCrew cc : idleCaptain) {
                    addCrewIdList.add(cc.getCpName());
                }
                break;
            }
            case "Pilot": {
                addCrewIdList = new ArrayList<String>();
                for (CockpitCrew cc : idlePilot) {
                    addCrewIdList.add(cc.getCpName());
                }
                break;
            }
            case "Cabin Leader": {
                addCrewIdList = new ArrayList<String>();
                for (CabinCrew cc : idleCabinleader) {
                    addCrewIdList.add(cc.getCbName());
                }
                break;
            }
            case "Cabin Crew": {
                addCrewIdList = new ArrayList<String>();
                for (CabinCrew cc : idleCabincrew) {
                    addCrewIdList.add(cc.getCbName());
                }
                break;
            }
        }
    }

    public void viewFCDetailBack() throws Exception {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewFiCrew.xhtml");
    }

    public void refresh() throws IOException {
        System.out.println("REFRESH!");
        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewFiCrewDetail.xhtml");
    }

    public FlightInstance getFi() {
        return fi;
    }

    public void setFi(FlightInstance fi) {
        this.fi = fi;
    }

    public List<CabinCrew> getCabinleaderList() {
        return cabinleaderList;
    }

    public void setCabinleaderList(List<CabinCrew> cabinleaderList) {
        this.cabinleaderList = cabinleaderList;
    }

    public List<CabinCrew> getCabincrewList() {
        return cabincrewList;
    }

    public void setCabincrewList(List<CabinCrew> cabincrewList) {
        this.cabincrewList = cabincrewList;
    }

    public List<CockpitCrew> getCaptainList() {
        return captainList;
    }

    public void setCaptainList(List<CockpitCrew> captainList) {
        this.captainList = captainList;
    }

    public List<CockpitCrew> getPilotList() {
        return pilotList;
    }

    public void setPilotList(List<CockpitCrew> pilotList) {
        this.pilotList = pilotList;
    }

    public List<CabinCrew> getCabinList() {
        return cabinList;
    }

    public void setCabinList(List<CabinCrew> cabinList) {
        this.cabinList = cabinList;
    }

    public List<CockpitCrew> getCockpitList() {
        return cockpitList;
    }

    public void setCockpitList(List<CockpitCrew> cockpitList) {
        this.cockpitList = cockpitList;
    }

    public String getRemoveCc() {
        return removeCc;
    }

    public void setRemoveCc(String removeCc) {
        this.removeCc = removeCc;
    }

    public String getRemoveCp() {
        return removeCp;
    }

    public void setRemoveCp(String removeCp) {
        this.removeCp = removeCp;
    }

    public Long getFiId() {
        return fiId;
    }

    public void setFiId(Long fiId) {
        this.fiId = fiId;
    }

    public List<CabinCrew> getIdleCabincrew() {
        return idleCabincrew;
    }

    public void setIdleCabincrew(List<CabinCrew> idleCabincrew) {
        this.idleCabincrew = idleCabincrew;
    }

    public List<CabinCrew> getIdleCabinleader() {
        return idleCabinleader;
    }

    public void setIdleCabinleader(List<CabinCrew> idleCabinleader) {
        this.idleCabinleader = idleCabinleader;
    }

    public List<CockpitCrew> getIdleCaptain() {
        return idleCaptain;
    }

    public void setIdleCaptain(List<CockpitCrew> idleCaptain) {
        this.idleCaptain = idleCaptain;
    }

    public List<CockpitCrew> getIdlePilot() {
        return idlePilot;
    }

    public void setIdlePilot(List<CockpitCrew> idlePilot) {
        this.idlePilot = idlePilot;
    }

//    public List<CabinCrew> getIdleCabin() {
//        return idleCabin;
//    }
//
//    public void setIdleCabin(List<CabinCrew> idleCabin) {
//        this.idleCabin = idleCabin;
//    }
//
//    public List<CockpitCrew> getIdleCockpit() {
//        return idleCockpit;
//    }
//
//    public void setIdleCockpit(List<CockpitCrew> idleCockpit) {
//        this.idleCockpit = idleCockpit;
//    }
    public String getAddCrewType() {
        return addCrewType;
    }

    public void setAddCrewType(String addCrewType) {
        this.addCrewType = addCrewType;
    }

    public List<String> getCrewTypeList() {
        return crewTypeList;
    }

    public void setCrewTypeList(List<String> crewTypeList) {
        this.crewTypeList = crewTypeList;
    }

    public String getAddCrewId() {
        return addCrewId;
    }

    public void setAddCrewId(String addCrewId) {
        this.addCrewId = addCrewId;
    }

    public List<String> getAddCrewIdList() {
        return addCrewIdList;
    }

    public void setAddCrewIdList(List<String> addCrewIdList) {
        this.addCrewIdList = addCrewIdList;
    }

}
