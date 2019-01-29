package ir.sweetsoft.ges;

import android.app.Activity;
import android.database.sqlite.SQLiteConstraintException;
import android.text.format.DateFormat;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import calendar.CivilDate;
import calendar.DateConverter;
import calendar.PersianDate;
import common.Exceptions.ItemExistsException;
import ir.sweetsoft.ges.Exceptions.noSheetException;
import ir.sweetsoft.ges.Model.Cow;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * Created by Nahavandi on 8/19/2016.
 */
public class ExcelAdapter {
    private Activity theActivity;
    public static final WritableFont.FontName BHOMA = WritableFont.createFont("B Homa");
    public static final WritableFont.FontName BMITRA = WritableFont.createFont("B Mitra");
    public ExcelAdapter(Activity Context)
    {
        theActivity=Context;
    }
    public WritableWorkbook createExcelSheet(WritableWorkbook workbook,String HerdID,Boolean isFilled,Boolean isHeifer,int SheetNumber) throws Exception
    {
        List<Cow> Cows=new Select().from(Cow.class).orderBy("code").where("herd_fid= ? AND is_filled= ? AND isheifer= ?",HerdID,isFilled,isHeifer).execute();
        int StartRow=0;
        String SheetName="Herd "+HerdID;
        if(isFilled)
            SheetName=SheetName+" ";
        else
            SheetName=SheetName+" P-Mate";

        if(isHeifer)
            SheetName+=" Heifers";
        else
            SheetName+=" Cows";
        WritableSheet theSheet = workbook.createSheet(SheetName, SheetNumber);
        Label HerdCode=new Label(0,StartRow,"Herd ID:");
        HerdCode.setCellFormat(getTitleFormat());
        theSheet.addCell(HerdCode);
        Label HerdCodeContent=new Label(1,StartRow,HerdID);
        HerdCodeContent.setCellFormat(getNormalFormat());
        theSheet.addCell(HerdCodeContent);
        StartRow++;
        StartRow+=AddTitles(theSheet,StartRow);
        int AddedRows=0;
        for(int CowIndex=0;CowIndex<Cows.size();CowIndex++) {
            AddedRows++;
            Cow theCow=Cows.get(CowIndex);
            String[] Items={(CowIndex+1)+"",theCow.CowCode+"",theCow.sire,theCow.mgs,theCow.mmgs,theCow.ls+""
                    ,theCow.st+"",theCow.sr+"",theCow.bd+"",theCow.df+"",theCow.ra+"",theCow.rw+"",theCow.sv+""
                    ,theCow.rv+"",theCow.fa+"",theCow.fu+"",theCow.uh+"",theCow.uw+"",theCow.uc+""
                    ,theCow.ud+"",theCow.tp+"",theCow.tl+"",theCow.rtp+""};
            Label[] ItemLables=new Label[Items.length];
            for(int i=0;i<ItemLables.length;i++)
            {
                if(Items[i].equals("-1"))
                    Items[i]="";
                ItemLables[i]=new Label(i,StartRow+CowIndex,Items[i]);
                ItemLables[i].setCellFormat(getNormalFormat());
                theSheet.addCell(ItemLables[i]);
            }
        }
        return workbook;

    }
    public int makeExcelFromHerd(String HerdID,String ExportPath) throws Exception
    {
        WritableWorkbook workbook = Workbook.createWorkbook(new File(ExportPath));
        int AddedRows=0;
        workbook=createExcelSheet(workbook,HerdID,false,false,0);
        workbook=createExcelSheet(workbook,HerdID,false,true,1);
        workbook=createExcelSheet(workbook,HerdID,true,false,2);
        workbook=createExcelSheet(workbook,HerdID,true,true,3);
        workbook.write();
        workbook.close();
        return AddedRows;
    }
    private int AddTitles(WritableSheet theXLSheet,int StartRow) throws Exception
    {
        String[] Titles={"#","cow-lD","Sire","M.G.S","M.M.G.S","LS","ST","SR","BD","DF","RA","RW","SV","RV","FA","FU","UH","UW","UC","UD","FTP","TL","RTP"};
        Label[] TitleLables=new Label[Titles.length];
        for(int i=0;i<Titles.length;i++)
        {
            TitleLables[i]=new Label(i,StartRow,Titles[i]);
            TitleLables[i].setCellFormat(getTitleFormat());
            theXLSheet.addCell(TitleLables[i]);
        }
        return 1;//rows added
    }

    private WritableCellFormat getTitleFormat() throws Exception
    {
        WritableFont TableFormat = new WritableFont(BHOMA, 11, WritableFont.BOLD,false, UnderlineStyle.NO_UNDERLINE, Colour.WHITE);
        WritableCellFormat tableFormatBackground = new WritableCellFormat(); //table cell format
        tableFormatBackground.setBackground(Colour.LIGHT_BLUE) ; //Table background
        tableFormatBackground.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.GRAY_25); //table border style
        tableFormatBackground.setFont(TableFormat); //set the font
        tableFormatBackground.setAlignment(Alignment.CENTRE);// set alignment left
        return tableFormatBackground;
    }
    private WritableCellFormat getNormalFormat() throws Exception
    {
        WritableFont TableFormat = new WritableFont(BMITRA, 10, WritableFont.NO_BOLD,false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
        WritableCellFormat tableFormatBackground = new WritableCellFormat(); //table cell format
        tableFormatBackground.setBackground(Colour.WHITE) ; //Table background
        tableFormatBackground.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.GRAY_25); //table border style
        tableFormatBackground.setFont(TableFormat); //set the font
        tableFormatBackground.setAlignment(Alignment.CENTRE);// set alignment left
        return tableFormatBackground;
    }
    public int importDataExcel(String HerdID,String ImportingFilePath) throws SQLiteConstraintException,IOException,BiffException,noSheetException,ItemExistsException {

        ActiveAndroid.beginTransaction();
        File inputWorkbook = new File(ImportingFilePath);
        int AddedRows=0;
        if (inputWorkbook.exists())
        {
            Workbook w;
            w = Workbook.getWorkbook(inputWorkbook);
            new Delete().from(Cow.class).where("herd_fid = ?",HerdID).execute();
            /***************Read UserList From Sheet0****************/
            Sheet CowListSheet = w.getSheet(0);
            if (CowListSheet.getRows() > 1) {
                for(int row=1;row<CowListSheet.getRows();row++) {//Row 0 is Title
                    CowListSheet.getColumns();
                    Cell[] RowsCols=CowListSheet.getRow(row);
                    String CowCode=RowsCols[1].getContents().trim().toUpperCase();
                    List<Cow> cows= new Select().from(Cow.class).where("code = ?",CowCode).and("herd_fid = ?",HerdID).execute();
                    if(cows!=null && cows.size()>0)
                    {
                        ActiveAndroid.endTransaction();
                        throw new ItemExistsException();
                    }

                    if(!CowCode.equals(""))
                    {
                        Cow cow=new Cow();
                        cow.Herd=HerdID;
                        cow.CowCode=Integer.parseInt(CowCode);
                        if(RowsCols.length>2)
                            cow.sire=RowsCols[2].getContents().trim().toUpperCase();
                        if(RowsCols.length>3)
                            cow.mgs=RowsCols[3].getContents().trim().toUpperCase();
                        if(RowsCols.length>4)
                            cow.mmgs=RowsCols[4].getContents().trim().toUpperCase();
                        cow.SaveData();
                        AddedRows++;
                    }
                }
            }

            ActiveAndroid.setTransactionSuccessful();
            ActiveAndroid.endTransaction();
            return AddedRows;
            /***************EOF Read UserList From Sheet0****************/


        }
        else
        {
            ActiveAndroid.endTransaction();
            throw new noSheetException();
        }

    }
}
