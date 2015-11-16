/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AASmanagedbean;

import Entity.AFOS.StaffLeave;
import SessionBean.AAS.ResourceTrackingBeanLocal;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.ServletContext;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 *
 * @author Xi
 */
@Named(value = "LRMB")
@ViewScoped
public class LeaveReportManagedBean implements Serializable {

    /**
     * Creates a new instance of LeaveReportManagedBean
     */
    @EJB
    ResourceTrackingBeanLocal rtb;

    private Date startDate;
    private Date endDate;
    private String startString;
    private String endString;
    private List<StaffLeave> leaveList = new ArrayList<>();

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public LeaveReportManagedBean() {
    }

    @PostConstruct
    public void init() {
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("startDate") != null && FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("endDate") != null) {
            startDate = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("startDate");
            endDate = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("endDate");
        } else {
            System.out.print("AAS:LRMB:getting startDate and endDate...");
        }
          leaveList = (List<StaffLeave>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("leaveList");
          startString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("startString");
            endString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("endString");
    }

    public void getAllLeave() throws IOException {
        if (startDate == null && endDate == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Input", "Please select start date and end date ! "));
        } else {
            leaveList = rtb.getAllLeave(startDate, endDate);
            startString = df.format(startDate);
            endString = df.format(endDate);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("startDate", startDate);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("endDate", endDate);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("startString", startString);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("endString", endString);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("leaveList", leaveList);
            FacesContext.getCurrentInstance().getExternalContext().redirect("./displayLeaveReport.xhtml");
        }
    }

    public void back() throws Exception {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewLeave.xhtml");
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<StaffLeave> getLeaveList() {
        return leaveList;
    }

    public void setLeaveList(List<StaffLeave> leaveList) {
        this.leaveList = leaveList;
    }

    public String getStartString() {
        return startString;
    }

    public void setStartString(String startString) {
        this.startString = startString;
    }

    public String getEndString() {
        return endString;
    }

    public void setEndString(String endString) {
        this.endString = endString;
    }

}
