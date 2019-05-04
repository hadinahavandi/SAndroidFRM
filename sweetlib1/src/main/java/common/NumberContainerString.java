package common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberContainerString {
    private String theStr;
    public NumberContainerString(String theString)
    {
        theStr=theString;
    }
    public Long getNumberLong()
    {
        if(getNumberPart().isEmpty())
            return 0l;
        return Long.parseLong(getNumberPart());
    }
    public Integer getNumberInt()
    {
        if(getNumberPart().isEmpty())
            return 0;
        return Integer.parseInt(getNumberPart());
    }
    private String getNumberPart()
    {
        StringBuffer num = new StringBuffer();
        for (int i=0; i<theStr.length(); i++)
            if (Character.isDigit(theStr.charAt(i)))
                num.append(theStr.charAt(i));
        return num.toString();
    }
}
