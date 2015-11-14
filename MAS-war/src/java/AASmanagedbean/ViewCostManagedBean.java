/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AASmanagedbean;

import Entity.AAS.Expense;
import SessionBean.AAS.FinancialTrackingBeanLocal;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.primefaces.context.RequestContext;

/**
 *
 * @author Xi
 */
@Named(value = "VCMB")
@ViewScoped
public class ViewCostManagedBean implements Serializable{

    /**
     * Creates a new instance of ViewCostManagedBean
     */
     @EJB
    private FinancialTrackingBeanLocal ftb;
     
    private long costYear;
    private String costQuarter;
    private List<Long> yearList = new ArrayList<>();
      long currentYear = Calendar.getInstance().get(Calendar.YEAR);
       private List<Expense> expenseList = new ArrayList<>();
    
    public ViewCostManagedBean() {
    }
    
    @PostConstruct
    public void init() {
        for (int i = 0; i < 10; i++) {
            yearList.add(currentYear);
            currentYear--;
        }
              expenseList = (List<Expense>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("expenseList");
          if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("costYear") != null && FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("costQuarter") != null) {
            costYear = (long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("costYear");
            costQuarter = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("costQuarter");
        } else {
            costYear = 0;
            costQuarter = "0";
        }
    }
    
    public void viewCost()throws IOException {
        if (costQuarter.equals("0") || costYear == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Input", "Please select year and quarter ! "));
        } else {
             expenseList = ftb.getExpenseList(costYear, costQuarter);
             FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("expenseList", expenseList);
             FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("costYear", costYear);
             FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("costQuarter", costQuarter);
              FacesContext.getCurrentInstance().getExternalContext().redirect("./viewCostTable.xhtml");
        }
    }
    
    public void back() throws Exception {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewCostDetail.xhtml");
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


    public long getCostYear() {
        return costYear;
    }

    public void setCostYear(long costYear) {
        this.costYear = costYear;
    }

    public String getCostQuarter() {
        return costQuarter;
    }

    public void setCostQuarter(String costQuarter) {
        this.costQuarter = costQuarter;
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

    public List<Expense> getExpenseList() {
        return expenseList;
    }

    public void setExpenseList(List<Expense> expenseList) {
        this.expenseList = expenseList;
    }
    
    
        
}
