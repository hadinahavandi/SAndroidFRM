package ir.sweetsoft.ges.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "herd")
public class HerdFile extends Model {
    @Column(name = "herd_fid", index = true)
    public String Herd;
    @Column(name = "name")
    public String Name;
    public HerdFile()
    {
        super();
    }
}
