/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AASmanagedbean;

import Entity.AAS.Expense;
import Entity.AAS.Revenue;
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
import java.text.DecimalFormat;
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
@Named(value = "FSMB")
@ViewScoped
public class FinancialStatementManagedBean implements Serializable {

    /**
     * Creates a new instance of FinancialStatementManagedBean
     */
    @EJB
    private FinancialTrackingBeanLocal ftb;
    @EJB
    private CrewSchedulingBeanLocal csb;

    private long year;
    private String quarter;
    private List<Long> yearList = new ArrayList<>();
    long currentYear = Calendar.getInstance().get(Calendar.YEAR);

    private String key;
    private Double revenue;
    private String revenueString;
    private Double cost;
    private Double total;

    private Double sum;
    private String sumString;

    private List<Expense> expenseList = new ArrayList<>();
    private List<Revenue> revenueList = new ArrayList<>();
    private List<String> keyList = new ArrayList<>();
    private Map<String, Double> payableMap;
    private Map<String, String> receivableMap;

    public FinancialStatementManagedBean() {
    }

    @PostConstruct
    public void init() {
        for (int i = 0; i < 10; i++) {
            yearList.add(currentYear);
            currentYear--;
        }
        keyList.add("REVENUES:");
        keyList.add("Gross Revenues (including ALL income)");

        keyList.add("EXPENSES:");
        keyList.add("Purchase Aircraft");
        keyList.add("Depreciation");
        keyList.add("Fuel Cost");
        keyList.add("Maintenance Cost");
        keyList.add("DDS Commission");
        keyList.add("Employee Salary");
        keyList.add("Other cost");
        keyList.add("TOTAL EXPENSES:");

        keyList.add("NET INCOME:");

        //   keyList = (List<String>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("keyList");
        revenueList = (List<Revenue>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("revenueList");
        expenseList = (List<Expense>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("expenseList");
        payableMap = (Map<String, Double>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("payableMap");
        receivableMap = (Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("receivableMap");
        key = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("key");
        total = (Double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("total");
        revenue = (Double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("revenue");
        revenueString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("revenueString");
        cost = (Double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cost");
        sum = (Double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sum");
        sumString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sumString");

        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("year") != null && FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("quarter") != null) {
            year = (long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("year");
            quarter = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("quarter");
        } else {
            year = 0;
            quarter = "0";
        }
    }

    public void generateFinancialReport() throws IOException {
        if (quarter.equals("0") || year == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Input", "Please select year and quarter ! "));
        } else {
            payableMap = new HashMap<String, Double>();
            receivableMap = new HashMap<String, String>();
            sum = 0.0;
            total = 0.0;
            revenue = 0.0;
            for (int i = 0; i < keyList.size(); i++) {

                key = keyList.get(i);
                if (key.equals("Gross Revenues (including ALL income)")) {
                    revenueList = ftb.getRevenueList(year, quarter);
                    for (int r = 0; r < revenueList.size(); r++) {
                        revenue = revenue + revenueList.get(r).getReceivable() - revenueList.get(r).getRefund();
                    }
                    revenueString = new BigDecimal(Double.toString(revenue)).toPlainString();
                    receivableMap.put(key, revenueString);

                } else if (key.equals("REVENUES:")) {

                } else if (key.equals("EXPENSES:")) {

                } else if (key.equals("NET INCOME:")) {
                    sum = revenue - total;
                    sumString = new BigDecimal(Double.toString(sum)).toPlainString();
                    receivableMap.put(key, sumString);
                } else if (key.equals("TOTAL EXPENSES:")) {
                    String totalString = new BigDecimal(Double.toString(total)).toPlainString();
                    receivableMap.put(key, "(" + totalString + ")");

                } else {
                    ///////////Expenses:
                    expenseList = ftb.getExpenseList(year, quarter);
                    if (key.equals("Purchase Aircraft")) {
                        Double cost1 = 0.0;
                        for (int e = 0; e < expenseList.size(); e++) {
                            if (expenseList.get(e).getCategory().equals("Purchase Aircraft")) {
                                cost1 = cost1 + expenseList.get(e).getPayable();
                            }
                        }
                        payableMap.put(key, cost1);
                        total = total + cost1;
                    } else if (key.equals("Depreciation")) {
                        Double cost2 = 0.0;
                        for (int e = 0; e < expenseList.size(); e++) {
                            if (expenseList.get(e).getCategory().equals("Depreciation")) {
                                cost2 = cost2 + expenseList.get(e).getPayable() / 4;
                            }
                        }
                        payableMap.put(key, cost2);
                        total = total + cost2;
                    } else if (key.equals("Fuel Cost")) {
                        Double cost3 = 0.0;
                        String cate = "Fuel Cost";
                        cost3 = cost3 + ftb.calculateNoDateExpense(cate, year, quarter);
                        payableMap.put(key, cost3);
                        total = total + cost3;
                    } else if (key.equals("Maintenance Cost")) {
                        Double cost4 = 0.0;
                        String cate = "Maintenance Cost";
                        cost4 = cost4 + ftb.calculateNoDateExpense(cate, year, quarter);
                        payableMap.put(key, cost4);
                        total = total + cost4;
                    } else if (key.equals("DDS Commission")) {
                        Double cost5 = 0.0;
                        for (int e = 0; e < expenseList.size(); e++) {
                            if (expenseList.get(e).getCategory().equals("DDS Commission")) {
                                cost5 = cost5 + expenseList.get(e).getPayable();
                            }
                        }
                        payableMap.put(key, cost5);
                        total = total + cost5;
                    } else if (key.equals("Employee Salary")) {
                        Double cost = 0.0;
                        for (int e = 0; e < expenseList.size(); e++) {
                            String cate = expenseList.get(e).getCategory();
                            if (cate.equals("Cabin Crew") || cate.equals("Cabin Leader") || cate.equals("Captain") || cate.equals("Pilot") || cate.equals("Ground Staff") || cate.equals("Office Staff")) {
                                cost = cost + ftb.calculateNoDateExpense(cate, year, quarter);
                            }
                        }
                        payableMap.put(key, cost);
                        total = total + cost;
                    } else {
                        ///////////Other Cost
                        Double cost7 = 0.0;
                        String cate = "Other Cost";
                        cost7 = cost7 + ftb.calculateNoDateExpense(cate, year, quarter);
                        payableMap.put(key, cost7);
                        total = total + cost7;
                    }
                }
            }
        }
        if (revenue == 0.0 && total == 0.0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "There is no record found in Year " + year + " Quarter " + quarter + " ! ", ""));
        } else {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("payableMap", payableMap);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("receivableMap", receivableMap);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("expenseList", expenseList);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("revenueList", revenueList);
            //   FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("keyList", keyList);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("key", key);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("revenue", revenue);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("revenueString", revenueString);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cost", cost);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("total", total);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("sum", sum);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("sumString", sumString);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("year", year);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("quarter", quarter);

            FacesContext.getCurrentInstance().getExternalContext().redirect("./displayFinancialStatement.xhtml");
        }
    }

    public void back() throws Exception {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./generateFinancialStatement.xhtml");
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public String getSumString() {
        return sumString;
    }

    public void setSumString(String sumString) {
        this.sumString = sumString;
    }

    public List<Expense> getExpenseList() {
        return expenseList;
    }

    public void setExpenseList(List<Expense> expenseList) {
        this.expenseList = expenseList;
    }

    public List<Revenue> getRevenueList() {
        return revenueList;
    }

    public void setRevenueList(List<Revenue> revenueList) {
        this.revenueList = revenueList;
    }

    public List<String> getKeyList() {
        return keyList;
    }

    public void setKeyList(List<String> keyList) {
        this.keyList = keyList;
    }

    public Map<String, Double> getPayableMap() {
        return payableMap;
    }

    public void setPayableMap(Map<String, Double> payableMap) {
        this.payableMap = payableMap;
    }

    public String getRevenueString() {
        return revenueString;
    }

    public void setRevenueString(String revenueString) {
        this.revenueString = revenueString;
    }

    public Map<String, String> getReceivableMap() {
        return receivableMap;
    }

    public void setReceivableMap(Map<String, String> receivableMap) {
        this.receivableMap = receivableMap;
    }

}
