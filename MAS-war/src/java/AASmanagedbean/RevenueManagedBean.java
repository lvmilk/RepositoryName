/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AASmanagedbean;

import Entity.AAS.Revenue;
import SessionBean.AAS.FinancialTrackingBeanLocal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Xi
 */
@Named(value = "RMB")
@ViewScoped
public class RevenueManagedBean implements Serializable {

    /**
     * Creates a new instance of RevenueManagedBean
     */
    @EJB
    private FinancialTrackingBeanLocal ftb;

    private String channel;
    private Double revenue;
    private Double commission;
    private Double sum;
    private Double total;

    private long year;
    private String quarter;

    private List<Revenue> revenueList = new ArrayList<>();
    private List<String> channelList = new ArrayList<>();
    private Map<String, Double> saleMap;
    private Map<String, Double> commissionMap;
    private Map<String, Double> sumMap;
    private List<Long> yearList = new ArrayList<>();
    long currentYear = Calendar.getInstance().get(Calendar.YEAR);

    public RevenueManagedBean() {

    }

    @PostConstruct
    public void init() {
        for (int i = 0; i < 10; i++) {
            yearList.add(currentYear);
            currentYear--;
        }
        channelList.add("ARS");
        channelList.add("DDS");
        channelList.add("GDS");
        channelList.add("HOTEL");
        channelList.add("CAR RENTAL");
        channelList.add("HIGH-SPEED RAILWAY");
        saleMap = (Map<String, Double>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("saleMap");
        commissionMap = (Map<String, Double>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("commissionMap");
        sumMap = (Map<String, Double>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sumMap");
        total = (Double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("total");
        revenueList = (List<Revenue>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("revenueList");
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("year") != null && FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("quarter") != null) {
            year = (long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("year");
            quarter = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("quarter");
        } else {
            year = 0;
            quarter = "0";
        }
    }

    public void calculateRevenue() throws IOException {
        System.out.println("AAS:RMB:TESTING 1 Year and Quarter: " + year + " " + quarter);
        if (quarter.equals("0") || year == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Input", "Please select year and quarter ! "));
        } else {
            saleMap = new HashMap<String, Double>();
            commissionMap = new HashMap<String, Double>();
            sumMap = new HashMap<String, Double>();
            total = 0.0;
            for (int i = 0; i < channelList.size(); i++) {
                sum = 0.0;
                revenue = 0.0;
                commission = 0.0;
                channel = channelList.get(i);

                if (channel.equals("ARS") || channel.equals("DDS") || channel.equals("GDS")) {
                    revenue = ftb.calculateRevenue(channel, year, quarter);
                    saleMap.put(channel, revenue);
                } else {
                    saleMap.put(channel, 0.0);
                }

                if (channel.equals("ARS") || channel.equals("DDS")) {
                    commissionMap.put(channel, 0.0);
                } else {
                    commission = 0.1 * ftb.calculateRevenue(channel, year, quarter); //charge for 10% of sales
                    commissionMap.put(channel, commission);
                }
                sum = revenue + commission;
                total = total + sum;
                sumMap.put(channel, sum);
            }
            if (total == 0.0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "There is no record found in Year " + year + " Quarter " + quarter + " ! ", ""));
            } else {
                revenueList = ftb.getRevenueList(year, quarter);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("year", year);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("quarter", quarter);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("saleMap", saleMap);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("commissionMap", commissionMap);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("sumMap", sumMap);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("total", total);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("revenueList", revenueList);
                FacesContext.getCurrentInstance().getExternalContext().redirect("./displayRevenueReport.xhtml");
            }
        }
    }

    public void back() throws Exception {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./generateRevenueReport.xhtml");
    }

    public void exporterSummary() {

        Map<String, Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        options.put("draggable", false);
        options.put("modal", true);
        RequestContext.getCurrentInstance().openDialog("dialogRevenueSummary", options, null);
    }

    public void exporterDetail() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("resizable", true);
        options.put("draggable", false);
        options.put("modal", true);
        options.put("width", 700);
        options.put("height", 500);
        RequestContext.getCurrentInstance().openDialog("dialogRevenueDetail", options, null);
    }

    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);

        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
            HSSFCell cell = header.getCell(i);
            cell.setCellStyle(cellStyle);
        }
    }

    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String logo = servletContext.getRealPath("") + File.separator + "images" + File.separator + "logo.PNG";
        Image img = Image.getInstance(logo);
        img.scaleAbsolute(100f, 50f);
        pdf.add(img);
    }

    public List<String> getChannelList() {
        return channelList;
    }

    public void setChannelList(List<String> channelList) {
        this.channelList = channelList;
    }

    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

    public long getYear() {
        return year;
    }

    public void setYear(long year) {
        this.year = year;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public Map<String, Double> getSaleMap() {
        return saleMap;
    }

    public void setSaleMap(Map<String, Double> saleMap) {
        this.saleMap = saleMap;
    }

    public List<Long> getYearList() {
        return yearList;
    }

    public void setYearList(List<Long> yearList) {
        this.yearList = yearList;
    }

    public long getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(long currentYear) {
        this.currentYear = currentYear;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public Map<String, Double> getCommissionMap() {
        return commissionMap;
    }

    public void setCommissionMap(Map<String, Double> commissionMap) {
        this.commissionMap = commissionMap;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public Map<String, Double> getSumMap() {
        return sumMap;
    }

    public void setSumMap(Map<String, Double> sumMap) {
        this.sumMap = sumMap;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<Revenue> getRevenueList() {
        return revenueList;
    }

    public void setRevenueList(List<Revenue> revenueList) {
        this.revenueList = revenueList;
    }

}
