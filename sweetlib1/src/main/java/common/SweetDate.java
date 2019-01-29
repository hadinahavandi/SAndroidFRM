package common;

import android.text.format.DateFormat;

import java.util.Calendar;

import calendar.CivilDate;
import calendar.DateConverter;
import calendar.PersianDate;

public class SweetDate {

	public static String getDayName(int CivilDayOfWeek)
	{
		String DayName="";
		switch (CivilDayOfWeek) {
		case 7:
			DayName="شنبه";
			break;
		case 1:
			DayName="یکشنبه";
			break;
		case 2:
			DayName="دوشنبه";
			break;
		case 3:
			DayName="سه شنبه";
			break;
		case 4:
			DayName="چهارشنبه";
			break;
		case 5:
			DayName="پنجشنبه";
			break;
		case 6:
			DayName="جمعه";
			break;
		default:
			break;
		}
		return DayName;
	}
	public static String Date2String(CivilDate cd,String DateSeparatorString)
	{
		PersianDate PDate= DateConverter.civilToPersian(cd);
        return String.valueOf(PDate.getYear())+DateSeparatorString+String.valueOf(PDate.getMonth())+DateSeparatorString+String.valueOf(PDate.getDayOfMonth());
	}
    public static String Date2String(String DateSeparatorString)
    {
        return Date2String(new CivilDate(),DateSeparatorString);
    }

    public String Time2String(Long Time,String DateSeparatorString,String TimeSeparatorString)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(Time);
		CivilDate cd=new CivilDate();
		String year = DateFormat.format("yyyy", cal).toString();
		String month = DateFormat.format("MM", cal).toString();
		String day = DateFormat.format("dd", cal).toString();
		String Hour=DateFormat.format("k", cal).toString();
		String Minute=DateFormat.format("mm", cal).toString();
		cd.setDate(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
		PersianDate PDate=DateConverter.civilToPersian(cd);
        return String.valueOf(PDate.getYear())+DateSeparatorString+String.valueOf(PDate.getMonth())+DateSeparatorString+String.valueOf(PDate.getDayOfMonth() + " "+Hour+TimeSeparatorString+Minute);
	}
	private static CivilDate GetDateFromTime(Long Time)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(Time);
		CivilDate cd=new CivilDate();
		String year = DateFormat.format("yyyy", cal).toString();
		String month = DateFormat.format("MM", cal).toString();
		String day = DateFormat.format("dd", cal).toString();
		cd.setDate(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
		return cd;
	}
}
