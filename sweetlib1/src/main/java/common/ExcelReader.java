package common;

import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.read.biff.BiffException;
import jxl.read.biff.CellValue;
import jxl.write.WritableSheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import java.io.File;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExcelReader {
    Workbook readedWorkbook;
    public ExcelReader(String path) throws IOException, BiffException {
        File inputWorkbook = new File(path);
        readedWorkbook = Workbook.getWorkbook(inputWorkbook);
    }
    public List<List<String>> getSheet(int sheetNum,String nullValue){
        List<List<String>> sheetList=new ArrayList<List<String>>();
        Sheet theSheet = readedWorkbook.getSheet(sheetNum);
        int Rows=theSheet.getRows();
        if(Rows>0){
            for(int row=0;row<Rows;row++){
                List<String> rowList=new ArrayList<String>();
                Cell[] RowsCols=theSheet.getRow(row);
                int Cols=RowsCols.length;

                for(int col=0;col<Cols;col++){
                    String cellValue=RowsCols[col].getContents();
                    if(cellValue.equals(nullValue))
                        rowList.add(null);
                    else
                        rowList.add(cellValue);
                }
                sheetList.add(rowList);
            }
        }
        return sheetList;
    }
}
