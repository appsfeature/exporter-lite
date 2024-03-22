//package com.exporter.excel;
//
//import android.app.Activity;
//import android.content.Context;
//import android.util.Log;
//import android.widget.Toast;
//
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.poifs.filesystem.POIFSFileSystem;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Iterator;
//
///**
// * @author Created by Abhijit Rao on 8/10/2017.
// */
//
//public class EXELMaker {
//
//    private Activity mContext;
//
//    public static EXELMaker newInstance(Activity mContext) {
//        EXELMaker exelMaker = new EXELMaker();
//        exelMaker.mContext = mContext;
//        return exelMaker;
//    }
//
//
//    public File exportCustomerInExcel() {
//
//        String fileName = "Customer_List_" + DateTimeUtility.getTimeStamp() + ".xls";
//        CustomerModel customerModel = dbManager.getCustomerList(new CustomerModel(), AppUser.getUserId(mContext));
//
//        if (!customerModel.getOutputDB().equals("success")) {
//            return null;
//        }
//        //New Workbook
//        Workbook wb = new HSSFWorkbook();
//        Cell cell = null;
//        //Cell style for header row
//        CellStyle cs = null;
//        //New Sheet
//        Sheet sheet1 = null;
//        sheet1 = wb.createSheet("Customers");
//
//        // Generate column headings
//        Row row = sheet1.createRow(0);
//
//        createHeaderRowColumn(sheet1, cell, row, cs, 100, 0, "S.No.");
//        createHeaderRowColumn(sheet1, cell, row, cs, 140, 1, "personName");
//        createHeaderRowColumn(sheet1, cell, row, cs, 500, 2, "companyName");
//        createHeaderRowColumn(sheet1, cell, row, cs, 160, 3, "contactEmail");
//        createHeaderRowColumn(sheet1, cell, row, cs, 250, 4, "mobile");
//        createHeaderRowColumn(sheet1, cell, row, cs, 130, 5, "website");
//
//        Row multiRow;
//        ArrayList<CustomerModel> listItem = customerModel.getList();
//        for (int rowPos = 1; rowPos <= listItem.size(); rowPos++) {
//            int listPos = rowPos - 1;
//            // Generate column headings
//            multiRow = sheet1.createRow(rowPos);
//            setRowEntry(cell, multiRow, 0, String.valueOf(rowPos));
//            setRowEntry(cell, multiRow, 1, listItem.get(listPos).getCustomerId());
//            setRowEntry(cell, multiRow, 2, listItem.get(listPos).getCustomerName());
//            setRowEntry(cell, multiRow, 3, listItem.get(listPos).getCodeHSN());
//            setRowEntry(cell, multiRow, 4, listItem.get(listPos).getBarcode());
//            setRowEntry(cell, multiRow, 5, listItem.get(listPos).getSalePrice());
//            setRowEntry(cell, multiRow, 6, listItem.get(listPos).getTaxRate());
//        }
//
//        File myExternalFile = new File(mContext.getExternalFilesDir(AppConfig.FOLDER_XLS), fileName);
//        FileOutputStream fileOut = null;
//        try {
//            fileOut = new FileOutputStream(myExternalFile);
//            wb.write(fileOut);
//            fileOut.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//        System.out.print("file created");
//
//        return myExternalFile;
//    }
//
//
//    private Cell createHeaderRowColumn(Sheet sheet1, Cell cell, Row row, CellStyle cs, int width, int columnPosition, String columnName) {
//        cell = row.createCell(columnPosition);
//        cell.setCellValue(columnName);
////        cell.setCellStyle(cs);
//        sheet1.setColumnWidth(columnPosition, (15 * width));
//        return cell;
//    }
//
//    private Cell setRowEntry(Cell cell, Row multiRow, int listPos, String listValue) {
//        cell = multiRow.createCell(listPos);
//        cell.setCellValue(listValue);
//        return cell;
//    }
//
//    private Cell setRowEntry(Cell cell, Row multiRow, int listPos, String label, String[] data) {
//        cell = multiRow.createCell(listPos);
//        cell.setCellValue(label);
//        int columnIndex = 0;
//        for (String item : data) {
//            cell = multiRow.createCell(columnIndex++);
//            cell.setCellValue(item);
//        }
//        return cell;
//    }
//
//    private static void readExcelFile(Context context, String filename) {
//
//        try{
//            // Creating Input Stream
//            File file = new File(context.getExternalFilesDir(null), filename);
//            FileInputStream myInput = new FileInputStream(file);
//
//            // Create a POIFSFileSystem object
//            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
//
//            // Create a workbook using the File System
//            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
//
//            // Get the first sheet from workbook
//            HSSFSheet mySheet = myWorkBook.getSheetAt(0);
//
//            /** We now need something to iterate through the cells.**/
//            Iterator<Row> rowIter = mySheet.rowIterator();
//
//            while(rowIter.hasNext()){
//                HSSFRow myRow = (HSSFRow) rowIter.next();
//                Iterator<Cell> cellIter = myRow.cellIterator();
//                while(cellIter.hasNext()){
//                    HSSFCell myCell = (HSSFCell) cellIter.next();
//                    Log.w("FileUtils", "Cell Value: " +  myCell.toString());
//                    Toast.makeText(context, "cell Value: " + myCell.toString(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        }catch (Exception e){e.printStackTrace(); }
//
//        return;
//    }
//
//
//}
