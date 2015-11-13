/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AASmanagedbean;

import Entity.AAS.Expense;
import SessionBean.AAS.FinancialTrackingBeanLocal;
import SessionBean.AFOS.CrewSchedulingBeanLocal;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Named(value = "EMB2")
@ViewScoped
public class ExpenseManagedBean2 implements Serializable {

    /**
     * Creates a new instance of ExpenseManagedBean
     */
    @EJB
    private FinancialTrackingBeanLocal ftb;
    @EJB
    private CrewSchedulingBeanLocal csb;

    private List<Long> yearList = new ArrayList<>();
    long currentYear = Calendar.getInstance().get(Calendar.YEAR);
    private long expenseYear2;
    private String expenseQuarter2;
    private List<String> categoryList = new ArrayList<>();
    private Map<String, String> typeMap;
    private Map<String,Integer>amountMap;
    private Map<String, Double> payableMap;
    private String category;
    private String type;
    private Integer amount;
    private Double payable;
    private Double total;
    private String totalString;

    public ExpenseManagedBean2() {
    }

    @PostConstruct
    public void init() {
        for (int i = 0; i < 10; i++) {
            yearList.add(currentYear);
            currentYear--;
        }
        categoryList.add("DDS Commission");
        categoryList.add("Cabin Crew");
        categoryList.add("Cabin Leader");
        categoryList.add("Captain");
        categoryList.add("Pilot");
        categoryList.add("Office Staff");
        categoryList.add("Ground Staff");
        typeMap = (Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("typeMap");
        amountMap = (Map<String, Integer>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("amountMap");
        payableMap = (Map<String, Double>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("payableMap");
        total = (Double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("total");
        totalString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("totalString");
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("expenseYear2") != null && FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("expenseQuarter2") != null) {
            expenseYear2 = (long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("expenseYear2");
            expenseQuarter2 = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("expenseQuarter2");
        } else {
            expenseYear2 = 0;
            expenseQuarter2 = "0";
        }
    }

    public void calculateFixedCost() throws IOException {
        System.out.println("AAS:EMB2:Input testing Year: " + expenseYear2 + " Quarter: " + expenseQuarter2);
        if (expenseQuarter2.equals("0") || expenseYear2 == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Input", "Please select year and quarter ! "));
        } else {
            typeMap = new HashMap<String, String>();
            payableMap = new HashMap<String, Double>();
            amountMap = new HashMap<String, Integer>();
            total = 0.0;
            for (int i = 0; i < categoryList.size(); i++) {
                payable = 0.0;
                category = categoryList.get(i);
                type = "Variable Operation Cost";
                typeMap.put(category, type);
                if (category.equals("DDS Commission")) {
                    payable = ftb.calculateExpense(category, expenseYear2, expenseQuarter2);
                    payableMap.put(category, payable);
                } else if (category.equals("Cabin Crew")){
                    amount = csb.getAllCabinCrew().size();
                    amountMap.put(category, amount);
                    payable = ftb.calculateNoDateExpense(category, expenseYear2, expenseQuarter2);
                    payableMap.put(category, payable);
                }else if (category.equals("Cabin Leader")){
                    amount = csb.getAllCabinLeader().size();
                    amountMap.put(category, amount);
                    payable = ftb.calculateNoDateExpense(category, expenseYear2, expenseQuarter2);
                    payableMap.put(category, payable);
                }else if (category.equals("Captain")){
                    amount = csb.getAllCaptain().size();
                    amountMap.put(category, amount);
                    payable = ftb.calculateNoDateExpense(category, expenseYear2, expenseQuarter2);
                    payableMap.put(category, payable);
                }else if (category.equals("Pilot")){
                    amount = csb.getAllPilot().size();
                    amountMap.put(category, amount);
                    payable = ftb.calculateNoDateExpense(category, expenseYear2, expenseQuarter2);
                    payableMap.put(category, payable);
                }else if (category.equals("Ground Staff")){
                    amount = csb.getAllGroundStaff().size();
                    amountMap.put(category, amount);
                    payable = ftb.calculateNoDateExpense(category, expenseYear2, expenseQuarter2);
                    payableMap.put(category, payable);
                }else{
                    //////for office staff
                    amount = csb.getAllOfficeStaff().size();
                    amountMap.put(category, amount);
                    payable = ftb.calculateNoDateExpense(category, expenseYear2, expenseQuarter2);
                    payableMap.put(category, payable);
                }
                total = total + payable;
                totalString = BigDecimal.valueOf(total).toPlainString();
            }
            if (total == 0.0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "There is no record found in Year " + expenseYear2 + " Quarter " + expenseQuarter2 + " ! ", ""));
            } else {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("expenseYear2", expenseYear2);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("expenseQuarter2", expenseQuarter2);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("typeMap", typeMap);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("amountMap", amountMap);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("payableMap", payableMap);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("total", total);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("totalString", totalString);
                FacesContext.getCurrentInstance().getExternalContext().redirect("./displayExpenseReport2.xhtml");
            }
        }
    }

    public void back() throws Exception {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./generateExpenseReport2.xhtml");
    }

    public void exporterSummary() {

        Map<String, Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        options.put("draggable", false);
        options.put("modal", true);
        RequestContext.getCurrentInstance().openDialog("dialogExpenseSummary2", options, null);
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

    public Map<String, Integer> getAmountMap() {
        return amountMap;
    }

    public void setAmountMap(Map<String, Integer> amountMap) {
        this.amountMap = amountMap;
    }

    public String getTotalString() {
        return totalString;
    }

    public void setTotalString(String totalString) {
        this.totalString = totalString;
    }

    public List<String> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<String> categoryList) {
        this.categoryList = categoryList;
    }

    public Map<String, String> getTypeMap() {
        return typeMap;
    }

    public void setTypeMap(Map<String, String> typeMap) {
        this.typeMap = typeMap;
    }

    public Map<String, Double> getPayableMap() {
        return payableMap;
    }

    public void setPayableMap(Map<String, Double> payableMap) {
        this.payableMap = payableMap;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPayable() {
        return payable;
    }

    public void setPayable(Double payable) {
        this.payable = payable;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public long getExpenseYear2() {
        return expenseYear2;
    }

    public void setExpenseYear2(long expenseYear2) {
        this.expenseYear2 = expenseYear2;
    }

    public String getExpenseQuarter2() {
        return expenseQuarter2;
    }

    public void setExpenseQuarter2(String expenseQuarter2) {
        this.expenseQuarter2 = expenseQuarter2;
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

}
