package ir.sweetsoft.orderapp.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import common.SweetDate;

@Table(name = "customer")
public class Customer extends Model {
    @Column(name = "name")
    public String Name;
    @Column(name = "tel")
    public String Tel;
    @Column(name = "mobile")
    public String Mobile;
    @Column(name = "address")
    public String Address;
    @Column(name = "description")
    public String Description;
}
