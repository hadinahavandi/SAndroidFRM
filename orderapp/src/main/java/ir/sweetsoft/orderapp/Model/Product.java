package ir.sweetsoft.orderapp.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "product")
public class Product extends Model {
    @Column(name = "name")
    public String Name;
    @Column(name = "description")
    public String Description;
    @Column(name = "price")
    public Integer Price;
    public Product()
    {
        super();
    }
}
