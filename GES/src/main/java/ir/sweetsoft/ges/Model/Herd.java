package ir.sweetsoft.ges.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "herd")
public class Herd extends Model {
    @Column(name = "code")
    public String Code;
    public Herd()
    {
        super();
    }
}
