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
import org.primefaces.context.RequestContext;

/**
 *
 * @author Xi
 */
@Named(value = "VCMB")
@ViewScoped
public class ViewCostManagedBean implements Serializable {

    /**
     * Creates a new instance of ViewCostManagedBean
     */
    @EJB
    private FinancialTrackingBeanLocal ftb;
    @EJB
    private CrewSchedulingBeanLocal csb;

    private long costYear;
    private String costQuarter;
    private List<Long> yearList = new ArrayList<>();
    long currentYear = Calendar.getInstance().get(Calendar.YEAR);

    private Long key;
    private String category;
    private String type;
    private Double payable;
    private Date payDate;
    private Double amount;
    private Double total;
    private String costSource;

    private Double sum;
    private String sumString;

    private List<Expense> expenseList = new ArrayList<>();
    private List<Long> keyList = new ArrayList<>();
    private Map<Long, String> cateMap;
    private Map<Long, String> typeMap;
    private Map<Long, Double> payableMap;
    private Map<Long, Date> dateMap;
    private Map<Long, Double> amountMap;
    private Map<Long, Double> totalMap;
    private Map<Long, String> sourceMap;

    public ViewCostManagedBean() {
    }

    @PostConstruct
    public void init() {
        for (int i = 0; i < 10; i++) {
            yearList.add(currentYear);
            currentYear--;
        }
        expenseList = (List<Expense>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("expenseList");
        keyList = (List<Long>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("keyList");
        cateMap = (Map<Long, String>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cateMap");
        typeMap = (Map<Long, String>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("typeMap");
        payableMap = (Map<Long, Double>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("payableMap");
        dateMap = (Map<Long, Date>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dateMap");
        amountMap = (Map<Long, Double>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("amountMap");
        totalMap = (Map<Long, Double>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("totalMap");
        sourceMap = (Map<Long, String>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sourceMap");
        sum = (Double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sum");
        sumString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sumString");

        total = (Double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("total");
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("costYear") != null && FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("costQuarter") != null) {
            costYear = (long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("costYear");
            costQuarter = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("costQuarter");
        } else {
            costYear = 0;
            costQuarter = "0";
        }
    }

    public void viewCost() throws IOException {
        if (costQuarter.equals("0") || costYear == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Input", "Please select year and quarter ! "));
        } else {
            expenseList = ftb.getExpenseList(costYear, costQuarter);
            keyList = new ArrayList<>();
            for (int i=0;i<expenseList.size();i++){
                key = expenseList.get(i).getId();
                keyList.add(key);
            }
            sum = 0.0;
            cateMap = new HashMap<Long, String>();
            typeMap = new HashMap<Long, String>();
            payableMap = new HashMap<Long, Double>();
            sourceMap = new HashMap<Long, String>();
            dateMap = new HashMap<Long, Date>();
            amountMap = new HashMap<Long, Double>();
            totalMap = new HashMap<Long, Double>();

            for (int i = 0; i < expenseList.size(); i++) {
                total = 0.0;
                amount = 0.0;
                key = expenseList.get(i).getId();
                ///////static values
                category = expenseList.get(i).getCategory();
                type = expenseList.get(i).getType();
                payable = expenseList.get(i).getPayable();
                costSource = expenseList.get(i).getCostSource();
                cateMap.put(key, category);
                typeMap.put(key, type);
                payableMap.put(key, payable);
                sourceMap.put(key, costSource);
                //dateMap.put(key, new Date());
                ///////dependent values
                if (type.equals("Sunk Cost")) {
                    if (category.equals("Purchase Aircraft")) {
                        payDate = expenseList.get(i).getPaymentDate();
                        amount = 1.0;
                        total = payable * amount;
                        dateMap.put(key, payDate);
                        amountMap.put(key, amount);
                        totalMap.put(key, total);
                    } else if (category.equals("Depreciation")) {
                        amount = 0.25;
                        total = payable * amount;
                        amountMap.put(key, amount);
                        totalMap.put(key, total);
                    } else {
                        ///////for maintenance cost & fuel cost
                        total = ftb.calculateNoDateExpense(category, costYear, costQuarter);
                        amount = total / payable;
                        amountMap.put(key, amount);
                        totalMap.put(key, total);
                    }
                } else if (type.equals("Fixed Operation Cost")) {
                    total = ftb.calculateNoDateExpense(category, costYear, costQuarter);
                    amount = 4.0;
                    amountMap.put(key, amount);
                    totalMap.put(key, total);
                } else {
                    //////for Variable Operation Cost
                    if (category.equals("DDS Commission")) {
                        payDate = expenseList.get(i).getPaymentDate();
                        amount = 1.0;
                        total = payable * amount;
                        dateMap.put(key, payDate);
                        amountMap.put(key, amount);
                        totalMap.put(key, total);
                    } else {
                        ///////////for 6 kinds of staff
                        total = ftb.calculateNoDateExpense(category, costYear, costQuarter);
                        amount = 4.0;
                        amountMap.put(key, amount);
                        totalMap.put(key, total);
                    }
                }
                sum = sum + total;
                sumString = BigDecimal.valueOf(sum).toPlainString();
                System.out.println("VCMB:viewCost:expenseList: "+expenseList.get(i));
                System.out.println("VCMB:viewCost:keyList: "+keyList);
                System.out.println("VCMB:viewCost:paymentDate: "+payDate);
            }
            if (total == 0.0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "There is no record found in Year " + costYear + " Quarter " + costQuarter + " ! ", ""));
            } else {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("typeMap", typeMap);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("payableMap", payableMap);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cateMap", cateMap);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateMap",dateMap);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("amountMap", amountMap);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("totalMap", totalMap);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("sourceMap", sourceMap);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("expenseList", expenseList);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("sum", sum);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("sumString", sumString);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("costYear", costYear);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("costQuarter", costQuarter);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("keyList", keyList);
                FacesContext.getCurrentInstance().getExternalContext().redirect("./viewCostTable.xhtml");
            }
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCostSource() {
        return costSource;
    }

    public void setCostSource(String costSource) {
        this.costSource = costSource;
    }

    public List<Long> getKeyList() {
        return keyList;
    }

    public void setKeyList(List<Long> keyList) {
        this.keyList = keyList;
    }

    public Map<Long, String> getCateMap() {
        return cateMap;
    }

    public void setCateMap(Map<Long, String> cateMap) {
        this.cateMap = cateMap;
    }

    public Map<Long, String> getTypeMap() {
        return typeMap;
    }

    public void setTypeMap(Map<Long, String> typeMap) {
        this.typeMap = typeMap;
    }

    public Map<Long, Double> getPayableMap() {
        return payableMap;
    }

    public void setPayableMap(Map<Long, Double> payableMap) {
        this.payableMap = payableMap;
    }

    public Map<Long, Date> getDateMap() {
        return dateMap;
    }

    public void setDateMap(Map<Long, Date> dateMap) {
        this.dateMap = dateMap;
    }

    public Map<Long, Double> getAmountMap() {
        return amountMap;
    }

    public void setAmountMap(Map<Long, Double> amountMap) {
        this.amountMap = amountMap;
    }

    public Map<Long, Double> getTotalMap() {
        return totalMap;
    }

    public void setTotalMap(Map<Long, Double> totalMap) {
        this.totalMap = totalMap;
    }

    public Map<Long, String> getSourceMap() {
        return sourceMap;
    }

    public void setSourceMap(Map<Long, String> sourceMap) {
        this.sourceMap = sourceMap;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
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

}
