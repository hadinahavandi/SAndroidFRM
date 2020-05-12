package ir.sweetsoft.orderapp.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import ir.sweetsoft.orderapp.R;

@Table(name = "product")
public class Product extends Model {
    @Column(name = "name")
    public String Name;
    @Column(name = "code")
    public String Code;
    @Column(name = "description")
    public String Description;
    @Column(name = "price")
    public Integer Price;
    @Column(name = "isactive")
    public Boolean IsActive;
    @Column(name = "status")
    public Integer Status;
    public Product()
    {
        super();
    }
    public int getTextColor(){
        int color= R.color.colorActive;
        if(!getIsActive())
            color=R.color.colorInactive;
        else{
            if(Status!=null) {
                if (Status == 1)
                    color = R.color.colorActive1;
                else if (Status == 2)
                    color = R.color.colorActive2;
                else if (Status == 3)
                    color = R.color.colorActive3;
            }
        }
        return color;
    }
    public Boolean getIsActive(){
        return (this.IsActive==null || this.IsActive);
    }
}
