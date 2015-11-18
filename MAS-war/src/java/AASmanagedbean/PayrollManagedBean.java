/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AASmanagedbean;

import Entity.AAS.Payroll;
import SessionBean.AAS.ResourceTrackingBeanLocal;
import SessionBean.AFOS.CrewSchedulingBeanLocal;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
@Named(value = "PMB")
@ViewScoped
public class PayrollManagedBean implements Serializable {

    /**
     * Creates a new instance of PayrollManagedBean
     */
    @EJB
    private ResourceTrackingBeanLocal rtb;
    @EJB
    private CrewSchedulingBeanLocal csb;

    private long year;
    private int month;
    private List<Long> yearList = new ArrayList<>();
    long currentYear = Calendar.getInstance().get(Calendar.YEAR);
    private List<Payroll> payrollList = new ArrayList<>();

    private Long key;
    private String name;
    private Double salary;
    private Double bonus;
    private Integer leave;
    private Double total;
    private Double sum;
    private String sumString;
    private List<Long> keyList = new ArrayList<>();
    private Map<Long, String> nameMap;
    private Map<Long, Double> salaryMap;
    private Map<Long, Double> bonusMap;
    private Map<Long, Double> totalMap;
    private Map<Long, Integer> leaveMap;

    public PayrollManagedBean() {
    }

    @PostConstruct
    public void init() {
        for (int i = 0; i < 10; i++) {
            yearList.add(currentYear);
            currentYear--;
        }
        payrollList = rtb.getAllPayroll();
        nameMap = (Map<Long, String>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nameMap");
        salaryMap = (Map<Long, Double>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("salaryMap");
        bonusMap = (Map<Long, Double>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("bonusMap");
        leaveMap = (Map<Long, Integer>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("leaveMap");
        totalMap = (Map<Long, Double>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("totalMap");
        keyList = (List<Long>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("keyList");
        sum = (Double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sum");
        sumString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sumString");

        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("year") != null && FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("month") != null) {
            year = (long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("year");
            month = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("month");
        } else {
            year = 0;
            month = 0;
        }
    }

    public void viewPayroll() throws IOException {
        if (year == 0 || month == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Input", "Please select year and month ! "));
        } else {
            sum = 0.0;
            keyList = new ArrayList<>();
            nameMap = new HashMap<Long, String>();
            salaryMap = new HashMap<Long, Double>();
            bonusMap = new HashMap<Long, Double>();
            leaveMap = new HashMap<Long, Integer>();
            totalMap = new HashMap<Long, Double>();
            Double totalBonus = 0.0;
            Calendar cal = new GregorianCalendar((int) year, month-1, 1);
            int dayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            for (int i = 0; i < payrollList.size(); i++) {
                key = payrollList.get(i).getId();
                keyList.add(key);
                name = payrollList.get(i).getName();
                salary = payrollList.get(i).getSalary();
                bonus = payrollList.get(i).getBonus();
                leave = rtb.getLeaveAmount(name, year, month);
                totalBonus = rtb.getTotalBonus(name, year, month);
                total = (1 - ((double) leave / (double) dayOfMonth)) * salary + totalBonus;
                sum = sum + total;
                NumberFormat formatter = new DecimalFormat("#0.00");
                sumString = formatter.format(sum);
                nameMap.put(key, name);
                salaryMap.put(key, salary);
                bonusMap.put(key, bonus);
                leaveMap.put(key, leave);
                totalMap.put(key, total);
            }
            Date selectedDate =cal.getTime();
            if (selectedDate.after(new Date())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "There is no record existing in Year " + year + " Month " + month + " ! ", ""));
            } else {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("nameMap", nameMap);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("salaryMap", salaryMap);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("bonusMap", bonusMap);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("leaveMap", leaveMap);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("totalMap", totalMap);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("sum", sum);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("sumString", sumString);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("keyList", keyList);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("year", year);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("month", month);
                FacesContext.getCurrentInstance().getExternalContext().redirect("./displayPayroll.xhtml");
            }
        }
    }

    public void back() throws Exception {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewPayroll.xhtml");
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

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
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

    public List<Payroll> getPayrollList() {
        return payrollList;
    }

    public void setPayrollList(List<Payroll> payrollList) {
        this.payrollList = payrollList;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Double getBonus() {
        return bonus;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }

    public Integer getLeave() {
        return leave;
    }

    public void setLeave(Integer leave) {
        this.leave = leave;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getSumString() {
        return sumString;
    }

    public void setSumString(String sumString) {
        this.sumString = sumString;
    }

    public List<Long> getKeyList() {
        return keyList;
    }

    public void setKeyList(List<Long> keyList) {
        this.keyList = keyList;
    }

    public Map<Long, String> getNameMap() {
        return nameMap;
    }

    public void setNameMap(Map<Long, String> nameMap) {
        this.nameMap = nameMap;
    }

    public Map<Long, Double> getSalaryMap() {
        return salaryMap;
    }

    public void setSalaryMap(Map<Long, Double> salaryMap) {
        this.salaryMap = salaryMap;
    }

    public Map<Long, Double> getBonusMap() {
        return bonusMap;
    }

    public void setBonusMap(Map<Long, Double> bonusMap) {
        this.bonusMap = bonusMap;
    }

    public Map<Long, Double> getTotalMap() {
        return totalMap;
    }

    public void setTotalMap(Map<Long, Double> totalMap) {
        this.totalMap = totalMap;
    }

    public Map<Long, Integer> getLeaveMap() {
        return leaveMap;
    }

    public void setLeaveMap(Map<Long, Integer> leaveMap) {
        this.leaveMap = leaveMap;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

}
