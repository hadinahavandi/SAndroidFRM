package common;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExcelWriter {
    private WritableWorkbook writableWorkbook;
    private void appendListSheetToWorkbook(List theList,String[] fieldNames){}
    private WritableWorkbook createWorkBook(String path) throws IOException {
        return Workbook.createWorkbook(new File(path));
    }
    public ExcelWriter(String path) throws IOException {
        writableWorkbook=createWorkBook(path);
    }
    public void appendListSheet(String sheetName,List<List<String>> theList) throws WriteException {
        WritableSheet theSheet = writableWorkbook.createSheet(sheetName, writableWorkbook.getNumberOfSheets());
        int rowNum=0;
        for(List<String> row:theList){
            for(int col=0;col<row.size();col++){
                Label cell=new Label(col,rowNum,row.get(col));
                theSheet.addCell(cell);
            }
            rowNum++;
        }
    }
    public void write() throws IOException, WriteException {
        writableWorkbook.write();
        writableWorkbook.close();
    }
    public void appendListSheet(String sheetName,List theList,String[] fieldNames,String nullValue) throws NoSuchFieldException, IllegalAccessException, WriteException {
        WritableSheet theSheet = writableWorkbook.createSheet(sheetName, writableWorkbook.getNumberOfSheets());
        int rowNum=0;
        for(Object obj:theList){
            Class objClass=obj.getClass();
            for(int col=0;col<fieldNames.length;col++){
                Field theField=objClass.getField(fieldNames[col]);
//                Log.d("Field",theField.get(obj).toString());
                Object property=theField.get(obj);
                Label cell=new Label(col,rowNum,property==null?nullValue:property.toString());
                theSheet.addCell(cell);
            }
            rowNum++;
        }
    }
    public void appendListSheet(String sheetName,List theList,String[] fieldNames) throws NoSuchFieldException, IllegalAccessException, WriteException {
        appendListSheet(sheetName,theList,fieldNames,"");
    }


    }
