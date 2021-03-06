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

    public static Long getTimeInMiliseconds()
	{
		Calendar cal = Calendar.getInstance();
		return cal.getTimeInMillis();
	}
	public static String Time2DateString(Long Time,String DateSeparatorString)
	{
		return Time2String(Time,DateSeparatorString,"",true,true,true,false,false);
	}
	public static String Time2String(Long Time,String DateSeparatorString,String TimeSeparatorString,boolean hasYear,boolean hasMonth,boolean hasDay,boolean hasHour,boolean hasMinute)
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
		String ResultDateString="";
		if(hasYear)
			ResultDateString=String.valueOf(PDate.getYear());
		if(hasMonth)
		{
			if(ResultDateString.length()>0)
				ResultDateString+=DateSeparatorString;
			ResultDateString+=String.valueOf(PDate.getMonth());
		}
		if(hasDay)
		{
			if(ResultDateString.length()>0)
				ResultDateString+=DateSeparatorString;
			ResultDateString+=String.valueOf(PDate.getDayOfMonth());
		}
		if(hasHour)
		{
			if(ResultDateString.length()>0)
				ResultDateString+=" ";
			ResultDateString+=Hour;
		}
		if(hasMinute)
		{
			if(ResultDateString.length()>0)
			{
				if(hasHour)
					ResultDateString+=TimeSeparatorString;
				else
					ResultDateString+=" ";
			}
			ResultDateString+=Minute;
		}
		return ResultDateString;
	}
    public static String Time2String(Long Time,String DateSeparatorString,String TimeSeparatorString)
	{
		return Time2String(Time,DateSeparatorString,TimeSeparatorString,true,true,true,true,true);
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
