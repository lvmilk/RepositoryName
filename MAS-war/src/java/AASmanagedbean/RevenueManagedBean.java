/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AASmanagedbean;

import Entity.ADS.Ticket;
import Entity.APS.Aircraft;
import Entity.APS.AircraftType;
import SessionBean.AAS.FinancialTrackingBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

//import com.lowagie.text.BadElementException;
//import com.lowagie.text.Document;
//import com.lowagie.text.DocumentException;
//import com.lowagie.text.Image;
//import com.lowagie.text.PageSize;
//import java.io.File;
//import java.io.IOException;
//import java.io.Serializable;
//import java.util.List;
//import javax.annotation.PostConstruct;
//import javax.faces.context.FacesContext;
//import javax.servlet.ServletContext;
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFCellStyle;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.hssf.util.HSSFColor;
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

    private List<Ticket> ticketList = new ArrayList<>();
    private Ticket ticket = new Ticket();

    private String bookSystem;
    private Date bookDate;
    private String channel;
    private Double revenue;
    private Double commission;

    private long year;
    private String quarter = "";

    private List<String> channelList = new ArrayList<>();
    private Map<String, Double> saleMap;
    private Map<String,Double> commissionMap;
    private List<Long> yearList = new ArrayList<>();
    long currentYear = Calendar.getInstance().get(Calendar.YEAR);

    @PostConstruct
    public void init() {
        for (int i = 0; i < 10; i++) {
            yearList.add(currentYear);
            currentYear--;
        }
        ticketList = ftb.getAllTicket();
        channelList.add("ARS");
        channelList.add("DDS");
        channelList.add("GDS");
        channelList.add("HOTEL");
        channelList.add("CAR RENTAL");
        channelList.add("HIGH-SPEED RAILWAY");
        saleMap = (Map<String, Double>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("saleMap");
        commissionMap = (Map<String, Double>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("commissionMap");
    }

    public void calculateRevenue() throws IOException {
        FacesMessage msg;
        if (!quarter.equals("0") && year != 0) {
            msg = new FacesMessage("Selected Quarter in Year", "You select to view quarter" + quarter + " of " + year);
            System.out.println("AAS:RMB: displayCheck()select to view quarter " + quarter + " of " + year);
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "Please select year and quarter.");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        saleMap = new HashMap<String, Double>();
        commissionMap = new HashMap<String, Double>();
        for (int i = 0; i < channelList.size(); i++) {
            channel = channelList.get(i);
            if (channel.equals("ARS") || channel.equals("DDS") || channel.equals("GDS")) {
                revenue = ftb.totalTicketSale(channel, year, quarter);
                System.out.println("AAS:RMB: calculateRevenue() for Book System: select to view " + channel + " revenue: " + revenue + " for period: year " + year + " and quarter " + quarter);
                saleMap.put(channel, revenue);
            } else {
                saleMap.put(channel, 0.0);
            }
            if(channel.equals("ARS") || channel.equals("DDS")){
                commissionMap.put(channel, 0.0);
            }
            else
            {
                commission = 0.1*ftb.chargedCommission(channel,year,quarter); //charge for 10% of sales
                 System.out.println("AAS:RMB: calculateRevenue() commissions for channel: select to view " + channel + " commission: " + commission + " for period: year " + year + " and quarter " + quarter);
                commissionMap.put(channel, commission);
            }
        }
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("saleMap", saleMap);
         FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("commissionMap", commissionMap);
        System.out.println("AAS:RMB: calculateRevenue() maps: " + saleMap +"  "+ commissionMap);
        FacesContext.getCurrentInstance().getExternalContext().redirect("./displayRevenueReport.xhtml");
    }

//    public void postProcessXLS(Object document) {
//        HSSFWorkbook wb = (HSSFWorkbook) document;
//        HSSFSheet sheet = wb.getSheetAt(0);
//        HSSFRow header = sheet.getRow(0);
//         
//        HSSFCellStyle cellStyle = wb.createCellStyle();  
//        cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
//        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//         
//        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
//            HSSFCell cell = header.getCell(i);
//             
//            cell.setCellStyle(cellStyle);
//        }
//    }
//     
//    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
//        Document pdf = (Document) document;
//        pdf.open();
//        pdf.setPageSize(PageSize.A4);
// 
//        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
//        String logo = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "demo" + File.separator + "images" + File.separator + "prime_logo.png";
//         
//        pdf.add(Image.getInstance(logo));
//    }
    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public List<String> getChannelList() {
        return channelList;
    }

    public void setChannelList(List<String> channelList) {
        this.channelList = channelList;
    }

    public String getBookSystem() {
        return bookSystem;
    }

    public void setBookSystem(String bookSystem) {
        this.bookSystem = bookSystem;
    }

    public Date getBookDate() {
        return bookDate;
    }

    public void setBookDate(Date bookDate) {
        this.bookDate = bookDate;
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

}
