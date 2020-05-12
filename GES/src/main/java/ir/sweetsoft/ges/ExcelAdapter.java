package ir.sweetsoft.ges;

import android.app.Activity;
import android.database.sqlite.SQLiteConstraintException;
import android.media.MediaScannerConnection;
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
import ir.sweetsoft.ges.Model.HerdFile;
import jxl.Cell;
import jxl.Sheet;
import jxl.SheetSettings;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
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
  private WritableWorkbook createExcelSheet(WritableWorkbook workbook,HerdFile theHerdFile,Boolean isFilled,Boolean isHeifer,int SheetNumber) throws Exception
  {
    List<Cow> Cows=new Select().from(Cow.class).orderBy("codeint").where("herdfile_fid= ? AND is_filled= ? AND isheifer= ?",theHerdFile.getId(),isFilled,isHeifer).execute();
    int StartRow=0;
    String SheetName="Herd "+theHerdFile.Herd.Code;
    if(isFilled)
      SheetName=SheetName+" ";
    else
      SheetName=SheetName+" P-Mate";

    if(isHeifer)
      SheetName+=" Heifers";
    else
      SheetName+=" Cows";
    WritableSheet theSheet = workbook.createSheet(SheetName, SheetNumber);
//        theSheet.mergeCells(2,StartRow,3,StartRow);
    Label HerdCode=new Label(0,StartRow,"Herd ID:");
    HerdCode.setCellFormat(getTitleFormat());
    theSheet.addCell(HerdCode);

//        theSheet.mergeCells(4,StartRow,6,StartRow);
    Label HerdCodeContent=new Label(1,StartRow,theHerdFile.Herd.Code);
    HerdCodeContent.setCellFormat(getNormalFormat());

    theSheet.addCell(HerdCodeContent);
    StartRow++;
    StartRow+=AddTitles(theSheet,StartRow,isHeifer);
    int AddedRows=0;
    for(int CowIndex=0;CowIndex<Cows.size();CowIndex++) {
      AddedRows++;
      Cow theCow=Cows.get(CowIndex);
      String[] CowItems={(CowIndex+1)+"",theCow.CowCode+"",theCow.sire,theCow.mgs,theCow.mmgs,theCow.ls+""
              ,theCow.st+"",theCow.sr+"",theCow.bd+"",theCow.df+"",theCow.ra+"",theCow.rw+"",theCow.sv+""
              ,theCow.rv+"",theCow.fa+"",theCow.fu+"",theCow.uh+"",theCow.uw+"",theCow.uc+""
              ,theCow.ud+"",theCow.tp+"",theCow.tl+"",theCow.rtp+"",theCow.Description+""};
      String[] HeiferItems={(CowIndex+1)+"",theCow.CowCode+"",theCow.sire,theCow.mgs,theCow.mmgs
              ,theCow.st+"",theCow.sr+"",theCow.bd+"",theCow.df+"",theCow.ra+"",theCow.rw+"",theCow.sv+""
              ,theCow.rv+"",theCow.fa+"",theCow.Description+""};
//            String[] Items=isHeifer?HeiferItems:CowItems;
      String[] Items=CowItems;
      //Label[] ItemLables=new Label[Items.length];

      theSheet.mergeCells(Items.length-1,StartRow+CowIndex,Items.length+5,StartRow+CowIndex);//Comment Part
      for(int i=0;i<Items.length;i++)
      {
        if(Items[i].equals("-1"))
          Items[i]="-99";
        if(Items[i].equals("-10"))// Just for LS
          Items[i]="";
//                NumberFormat decimalNo = new NumberFormat("#.0");
//                WritableCellFormat numberFormat = new WritableCellFormat(decimalNo);
        try
        {
          String Value=Items[i].trim().toUpperCase();
          int ValueInt=Integer.valueOf(Value);
          if(!Value.equals(String.valueOf(ValueInt)))
            throw new NumberFormatException();
          Number numberCell = new Number(i,StartRow+CowIndex,ValueInt);
          numberCell.setCellFormat(getNormalFormat());
          theSheet.addCell(numberCell);

        }
        catch(NumberFormatException nfe)
        {
          Label LabelItem=new Label(i,StartRow+CowIndex,Items[i]);
          LabelItem.setCellFormat(getNormalFormat());
          theSheet.addCell(LabelItem);
        }
      }
    }

    SheetSettings settings = theSheet.getSettings();
    settings.setVerticalFreeze(2);
    return workbook;

  }
  public int makeExcelFromHerd(HerdFile theHerdFile,String ExportPath) throws Exception
  {
    WritableWorkbook workbook = Workbook.createWorkbook(new File(ExportPath));
    int AddedRows=0;
    workbook=createExcelSheet(workbook,theHerdFile,true,false,0);
    workbook=createExcelSheet(workbook,theHerdFile,true,true,1);
    workbook=createExcelSheet(workbook,theHerdFile,false,false,2);
    workbook=createExcelSheet(workbook,theHerdFile,false,true,3);
    workbook.write();
    workbook.close();
    return AddedRows;
  }
  private int AddTitles(WritableSheet theXLSheet,int StartRow,Boolean isHeifer) throws Exception
  {
    String[] CowTitles={"#","Cow ID","Sire","M.G.S","M.M.G.S","LS","ST","SR","BD","DF","RA","RW","SV","RV","FA","FU","UH","UW","UC","UD","FTP","TL","RTP","Comments"};
//        String[] HeiferTitles={"#","Heifer ID","Sire","M.G.S","M.M.G.S","ST","SR","BD","DF","RA","RW","SV","RV","FA","Comments"};
    String[] HeiferTitles={"#","Heifer ID","Sire","M.G.S","M.M.G.S","LS","ST","SR","BD","DF","RA","RW","SV","RV","FA","FU","UH","UW","UC","UD","FTP","TL","RTP","Comments"};
    String[] Titles=isHeifer?HeiferTitles:CowTitles;
    Label[] TitleLables=new Label[Titles.length];
    theXLSheet.mergeCells(Titles.length-1,StartRow,Titles.length+5,StartRow);//Comment Part

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
  public int importDataExcel(HerdFile theHerdFile,Boolean isHeifer, String ImportingFilePath) throws SQLiteConstraintException,IOException,BiffException,noSheetException,ItemExistsException {

    ActiveAndroid.beginTransaction();
    File inputWorkbook = new File(ImportingFilePath);
    int AddedRows=0;
    if (inputWorkbook.exists())
    {
      Workbook w;
      w = Workbook.getWorkbook(inputWorkbook);
      new Delete().from(Cow.class).where("herdfile_fid = ? AND isheifer = ?",theHerdFile.getId(),isHeifer).execute();
      /***************Read UserList From Sheet0****************/
      Sheet CowListSheet = w.getSheet(0);
      if (CowListSheet.getRows() > 1) {
        for(int row=1;row<CowListSheet.getRows();row++) {//Row 0 is Title
          CowListSheet.getColumns();
          Cell[] RowsCols=CowListSheet.getRow(row);
          String CowCode=RowsCols[1].getContents().trim().toUpperCase();
          List<Cow> cows= new Select().from(Cow.class).where("code = ? AND isheifer = ? AND herdfile_fid = ?",CowCode,isHeifer,theHerdFile.getId()).execute();
          if(cows!=null && cows.size()>0)
          {
            ActiveAndroid.endTransaction();
            throw new ItemExistsException();
          }

          if(!CowCode.equals(""))
          {
            Cow cow=new Cow();
            cow.HerdFile=theHerdFile;
            cow.CowCode=CowCode;
            if(RowsCols.length>2)
              cow.sire=RowsCols[2].getContents().trim().toUpperCase();
            if(RowsCols.length>3)
              cow.mgs=RowsCols[3].getContents().trim().toUpperCase();
            if(RowsCols.length>4)
              cow.mmgs=RowsCols[4].getContents().trim().toUpperCase();
            try
            {

              if(RowsCols.length>5)
                cow.ls=Integer.parseInt(RowsCols[5].getContents().trim().toUpperCase());
            }
            catch (Exception ex){ex.printStackTrace();};
            cow.IsHeifer=isHeifer;
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
