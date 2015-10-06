package APSmanagedbean;

import Entity.APS.Aircraft;
import Entity.APS.AircraftType;
import SessionBean.APS.FleetPlanningBeanLocal;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.chart.XYChart;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LegendPlacement;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author Xi
 */
@Named(value = "FEMB")
@ViewScoped
public class FleetEstimateManagedBean implements Serializable {

    @EJB
    private FleetPlanningBeanLocal fpb;

    private Date selectedDate;
    private Date date = new Date();
    private String type;
    private Integer year;
    private Integer size;
    private Integer aYear;
    boolean visible;

    private List<String> dateList = new ArrayList<String>();
    private List<Aircraft> aircraftList = new ArrayList<>();
    private List<AircraftType> typeList = new ArrayList<>();
    private List<String> typeKeyList = new ArrayList<>();
    private List<Aircraft> aircraftKeyList;

    private LineChartModel animatedModel;

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public FleetEstimateManagedBean() {
    }

    @PostConstruct
    public void init() {
        typeList = fpb.getAllAircraftType();
        System.out.println(typeList);
        for (int i = 0; i < typeList.size(); i++) {
            type = typeList.get(i).getType();
            typeKeyList.add(type);
        }

        createAnimatedModel();
    }

    public LineChartModel getAnimatedModel() {
        return animatedModel;
    }

    private void createAnimatedModel() {
        animatedModel = initLinearModel();
        animatedModel.setTitle("Line Chart of Fleet Structure");
        animatedModel.setAnimate(true);
        animatedModel.setLegendPosition("e");
        animatedModel.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
        animatedModel.setShowPointLabels(true);
        animatedModel.getAxes().put(AxisType.X, new CategoryAxis("Years"));
        Axis yAxis = animatedModel.getAxis(AxisType.Y);
        yAxis.setTickInterval("1");
        yAxis.setLabel("Size Number");
        yAxis.setMin(0);
        yAxis.setMax(10);
    }

    private LineChartModel initLinearModel() {
        LineChartModel model = new LineChartModel();
        aircraftList = new ArrayList<>();
        System.out.println("In initLinearModel() type list" + typeList);
        for (int i = 0; i < typeList.size(); i++) {
            year = (Calendar.getInstance().get(Calendar.YEAR)); //got
            System.out.println("initLinearModel() Current year: " + year);
            type = typeList.get(i).getType();
            System.out.println("initLinearModel() type: " + type); //got
            aircraftList = fpb.getThisTypeAircraft(type);
            size=aircraftList.size();  // in case of IndirectList: not instantiated
            LineChartSeries thisSeries = new LineChartSeries();
            System.out.println("initLinearModel() aircraft list: " + aircraftList); // got
            thisSeries.setLabel(type);

            for (int j = 0; j < 20; j++) {
                for (int q = 0; q < aircraftList.size(); q++) {     //stop when there is no aircraft for this type
                    System.out.println(q);
                    aYear = Integer.parseInt(aircraftList.get(q).getRetireDate().substring(0, 4)); //retire year
                    System.out.println(aircraftList.get(q).getRegistrationNo() + " lease ends in " + aYear);
                    if (aYear < year) {
                        if (aircraftList.size() > 0) {
                            System.out.println(aircraftList.get(q) + " is expirated lease!"); // this one is retired
                            aircraftList.remove(q); //remove the retired one
                            q = -1;    // next loop +1 --> back to the first one of the list
                        } 
                        System.out.println(type + " " + year + " " + aircraftList.size());
                    }
                    thisSeries.set(year, aircraftList.size());
                }
                year = year + 1;
                System.out.println("year passing to " + year);
            }
            model.addSeries(thisSeries);
        }
        return model;
    }

    public void chooseFleet() throws Exception, ParseException {
        aircraftKeyList = new ArrayList<>();
        aircraftList = fpb.getThisTypeAircraft(type);
        System.out.println(selectedDate);
        System.out.println(date);
        if (selectedDate.after(date)) {
            for (int i = 0; i < aircraftList.size(); i++) {
                if (selectedDate.before(df.parse(aircraftList.get(i).getRetireDate()))) {
                    aircraftKeyList.add(aircraftList.get(i));
                    visible = true;
                }
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Please choose a future date!"));
        }
        System.out.println(aircraftKeyList);
        System.out.println(visible);
    }

    public Date getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getDateList() {
        return dateList;
    }

    public void setDateList(List<String> dateList) {
        this.dateList = dateList;
    }

    public List<Aircraft> getAircraftList() {
        return aircraftList;
    }

    public void setAircraftList(List<Aircraft> aircraftList) {
        this.aircraftList = aircraftList;
    }

    public List<AircraftType> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<AircraftType> typeList) {
        this.typeList = typeList;
    }

    public List<String> getTypeKeyList() {
        return typeKeyList;
    }

    public void setTypeKeyList(List<String> typeKeyList) {
        this.typeKeyList = typeKeyList;
    }

    public List<Aircraft> getAircraftKeyList() {
        return aircraftKeyList;
    }

    public void setAircraftKeyList(List<Aircraft> aircraftKeyList) {
        this.aircraftKeyList = aircraftKeyList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

}
