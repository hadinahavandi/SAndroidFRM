package ir.sweetsoft.orderapp.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "factorproduct")
public class FactorProduct extends Model {
    @Column(name = "factor_fid", index = true,onDelete = Column.ForeignKeyAction.CASCADE)
    public Factor factor;
    @Column(name = "product_fid", index = true)
    public Product product;
    @Column(name = "count")
    public Integer Count;
    @Column(name = "price")
    public Integer Price;
    @Column(name = "description")
    public String Description;
    public FactorProduct()
    {
        super();
    }
}
