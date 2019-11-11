package ir.sweetsoft.orderapp.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import common.SweetDate;

@Table(name = "factor")
public class Factor extends Model {
    @Column(name = "customer")
    public Customer Customer;
    @Column(name = "name")
    public String Name;
    @Column(name = "description")
    public String Description;
    @Column(name = "is_archived")
    public Boolean IsArchived;
    @Column(name = "date")
    public Long Date;
    public Factor()
    {
        super();
        Date= SweetDate.getTimeInMiliseconds();
    }
}
