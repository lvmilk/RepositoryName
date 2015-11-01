/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AFOS;

import Entity.AFOS.Maintenance;
import Entity.AFOS.MaintenanceLog;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.FileOutputStream;
import java.io.IOException;
 
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//

/**
 *
 * @author Xu
 */
@Stateless
public class MaintenanceSchedulingBean implements MaintenanceSchedulingBeanLocal {

    @PersistenceContext
    EntityManager em;

    private MaintenanceLog log;

    @Override
    public void addMaintenanceLog(Maintenance mt, String aircraft, String acType, String objective, Date startTime, Date endTime, Integer manhour, String activity, String remark, String mtCrew) {
//        em.refresh(mt);
//        if(mt.getLog()!=null) {}
        log = new MaintenanceLog();
        log.create(mt, aircraft, acType, objective, startTime, endTime, manhour, activity, remark, mtCrew);
        em.persist(log);
        mt.setStatus("Completed");
        mt.setLog(log);
        em.merge(mt);
        em.flush();
        System.out.println("msb.addMaintenanceLog(): Maintenance Log for " + aircraft + " " + objective + "  " + startTime + " - " + endTime + " added!");
    }

    @Override
    public void editMaintenanceLog(Maintenance mt, String aircraft, String acType, String objective, Date startTime, Date endTime, Integer manhour, String activity, String remark, String mtCrew) {
        log = em.find(MaintenanceLog.class, mt.getLog().getId());
        log.setManhour(manhour);
        log.setMtCrew(mtCrew);
        log.setRemark(remark);
        log.setActivity(activity);
        em.merge(log);
        em.flush();
        System.out.println("msb.editMaintenanceLog(): Maintenance Log for " + aircraft + " " + objective + "  " + startTime + " - " + endTime + " edited!");
    }

//    public void exportMtLogExcel(ArrayList<Maintenance>, String excelFilePath) throws IOException {
//        Workbook workbook = new HSSFWorkbook();
//        Sheet sheet = workbook.createSheet();
//
//        int rowCount = 0;
//        
//        for (Maintenance aBook : listBook) {
//            Row row = sheet.createRow(++rowCount);
//            writeBook(aBook, row);
//        }
//
//        try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
//            workbook.write(outputStream);
//        }
//    }
//
//    private void writeBook(Book aBook, Row row) {
//        Cell cell = row.createCell(1);
//        cell.setCellValue(aBook.getTitle());
//
//        cell = row.createCell(2);
//        cell.setCellValue(aBook.getAuthor());
//
//        cell = row.createCell(3);
//        cell.setCellValue(aBook.getPrice());
//    }
//
//    private List<Book> getListBook() {
//        Book book1 = new Book("Head First Java", "Kathy Serria", 79);
//        Book book2 = new Book("Effective Java", "Joshua Bloch", 36);
//        Book book3 = new Book("Clean Code", "Robert Martin", 42);
//        Book book4 = new Book("Thinking in Java", "Bruce Eckel", 35);
//
//        List<Book> listBook = Arrays.asList(book1, book2, book3, book4);
//
//        return listBook;
//    }
//
//    NiceExcelWriterExample excelWriter = new NiceExcelWriterExample();
//
//    List<Book> listBook = excelWriter.getListBook();
//    String excelFilePath = "NiceJavaBooks.xls";
//
//    excelWriter.writeExcel (listBook, excelFilePath);
    
    
}
