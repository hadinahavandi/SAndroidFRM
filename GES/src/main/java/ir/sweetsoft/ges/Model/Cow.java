package ir.sweetsoft.ges.Model;

import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import common.NumberContainerString;

@Table(name = "cow")
public class Cow extends Model {
    @Column(name = "herdfile_fid", index = true,onDelete = Column.ForeignKeyAction.CASCADE)
    public HerdFile HerdFile;
    @Column(name = "codeint", index = true,notNull = true)
    public Long CowCodeInt;
    @Column(name = "code",notNull = true)
    public String CowCode;
    @Column(name = "sire")
    public String sire="";
    @Column(name = "mgs")
    public String mgs="";
    @Column(name = "mmgs")
    public String mmgs="";
    @Column(name = "ls")
    public Integer ls=-10;
    @Column(name = "st")
    public Integer st=-1;
    @Column(name = "sr")
    public Integer sr=-1;
    @Column(name = "bd")
    public Integer bd=-1;
    @Column(name = "df")
    public Integer df=-1;
    @Column(name = "ra")
    public Integer ra=-1;
    @Column(name = "rw")
    public Integer rw=-1;
    @Column(name = "sv")
    public Integer sv=-1;
    @Column(name = "rv")
    public Integer rv=-1;
    @Column(name = "fa")
    public Integer fa=-1;
    @Column(name = "fu")
    public Integer fu=-1;
    @Column(name = "uh")
    public Integer uh=-1;
    @Column(name = "uw")
    public Integer uw=-1;
    @Column(name = "uc")
    public Integer uc=-1;
    @Column(name = "ud")
    public Integer ud=-1;
    @Column(name = "tp")
    public Integer tp=-1;
    @Column(name = "tl")
    public Integer tl=-1;
    @Column(name = "rtp")
    public Integer rtp=-1;
    @Column(name = "description")
    public String Description="";
    @Column(name = "isheifer")
    public Boolean IsHeifer=false;
    @Column(name = "is_filled")
    public Boolean isFilled=false;

    public Cow() {
        super();
    }
    public Long SaveData() {
        this.isFilled=getIsFilled();
        NumberContainerString Str=new NumberContainerString(this.CowCode);
        if(ls==-1)
            ls=-10;
        this.CowCodeInt=Str.getNumberLong();
        Log.d("SavingData",this.CowCodeInt+"");
        Log.d("SavingData","OK");
        return save();
    }


    public boolean getIsFilled() {
        int FilledItems = 0;
        if (st > 0)
            FilledItems++;
        if (sr > 0)
            FilledItems++;
        if (bd > 0)
            FilledItems++;
        if (df > 0)
            FilledItems++;
        if (ra > 0)
            FilledItems++;
        if (rw > 0)
            FilledItems++;
        if (sv > 0)
            FilledItems++;
        if (rv > 0)
            FilledItems++;
        if (fa > 0)
            FilledItems++;
        if (fu > 0)
            FilledItems++;
        if (uh > 0)
            FilledItems++;
        if (uw > 0)
            FilledItems++;
        if (uc > 0)
            FilledItems++;
        if (ud > 0)
            FilledItems++;
        if (tp > 0)
            FilledItems++;
        if (tl > 0)
            FilledItems++;
        if (rtp > 0)
            FilledItems++;
        return FilledItems > 0;
    }
    public void clear()
    {
        st=-1;
        sr=-1;
        bd=-1;
        df=-1;
        ra=-1;
        rw=-1;
        sv=-1;
        rv=-1;
        fa=-1;
        fu=-1;
        uh=-1;
        uw=-1;
        uc=-1;
        ud=-1;
        tp=-1;
        tl=-1;
        rtp=-1;
        SaveData();
    }
}
