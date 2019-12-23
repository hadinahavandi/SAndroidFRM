package ir.sweetsoft.orderapp.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by Hadi on 10/03/2017.
 */

@Table(name = "parameter")
public class Parameter extends Model {
    @Column(name = "name")
    public String name;
    @Column(name = "value")
    public String value;
    public Parameter()
    {
        super();
    }
    public static Parameter getByName(String theName)
    {
        theName=theName.trim().toLowerCase();
        List records=new Select().from(Parameter.class).where("name = ?",theName).execute();
        if(records==null || records.size()==0)
            return null;
        return (Parameter) records.get(0);
    }
    public static String getValueByName(String theName,String defaultValue)
    {
        Parameter p=getByName(theName);
        if(p==null)
            return defaultValue;
        return p.value;
    }
    public static void setParamValue(String theName,String value)
    {
        theName=theName.trim().toLowerCase();
        Parameter p=getByName(theName);
        if(p==null) {
            p = new Parameter();
            p.name = theName;
        }
        p.value=value;
        p.save();
    }
}
