package ir.sweetsoft.ges.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "herdfile")
public class HerdFile extends Model {
    @Column(name = "herd_fid", index = true,onDelete = Column.ForeignKeyAction.CASCADE)
    public Herd Herd;
    @Column(name = "name")
    public String Name;
    @Column(name = "date")
    public int Date;
    public HerdFile()
    {
        super();
    }
}
