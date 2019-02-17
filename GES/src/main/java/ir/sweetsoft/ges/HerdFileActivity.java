package ir.sweetsoft.ges;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.activeandroid.query.Select;

import java.util.List;

import ir.sweetsoft.ges.Model.Herd;
import ir.sweetsoft.ges.Model.HerdFile;

public class HerdFileActivity extends BaseBtnManageActivity {

    private Herd theHerd=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long herdID=getIntent().getLongExtra("herd_id",-1);
        theHerd=Herd.load(Herd.class,herdID);
        Log.d("HerdID",herdID+"");
        createAndSendToMainActivity();
//        ReloadItems();
        setPageTitle("Files of Herd "+theHerd.Code+" :");
    }
    private void createAndSendToMainActivity()
    {
        List<HerdFile> HerdFiles=new Select().from(HerdFile.class).where("herd_fid = ?",theHerd.getId()).execute();
        HerdFile theHerdFile;
        if(HerdFiles==null || HerdFiles.isEmpty())
        {
            theHerdFile=AddHerdFile("defaultFile");
        }
        else
            theHerdFile=HerdFiles.get(0);
        RedirectToNextActivity(theHerdFile);

    }
    private void RedirectToNextActivity(HerdFile theHerdFile)
    {
        Bundle theBundle=new Bundle();
        theBundle.putLong("herdfile_id",theHerdFile.getId());
        Intent thePathIntent=new Intent(this,ItemListActivity.class);
        thePathIntent.putExtras(theBundle);
        startActivity(thePathIntent);
    }
    @Override
    protected void AddItem()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please Insert Herd File Name:");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AddHerdFile(input.getText().toString());
                ReloadItems();
            }
        });
        builder.show();
    }
    @Override
    protected void DeleteItem(Long ItemID)
    {
        HerdFile hdf=HerdFile.load(HerdFile.class,ItemID);
        hdf.delete();
        ReloadItems();
    }
    private HerdFile AddHerdFile(String Name)
    {
        String HerdFileName = Name.toUpperCase().trim();
        HerdFile hdf=new HerdFile();
        hdf.Name=HerdFileName;
        hdf.Herd=theHerd;
        hdf.save();
        return hdf;
    }
    @Override
    protected void ReloadItems()
    {
        List<HerdFile> HerdFiles=new Select().from(HerdFile.class).where("herd_fid = ?",theHerd.getId()).execute();
        List.removeAllViews();
        if(HerdFiles!=null && HerdFiles.size()>0)
        {
            lbl_noItemExists.setVisibility(View.GONE);
            Intent i=new Intent(this,ItemListActivity.class);
            for(final HerdFile theHerdFile:HerdFiles)
            {
                Bundle bdl=new Bundle();
                bdl.putLong("herdfile_id",theHerdFile.getId());
                AddItem(theHerdFile.getId(),theHerdFile.Name+" ",i,bdl);
            }
        }
        else
            lbl_noItemExists.setVisibility(View.VISIBLE);


    }

}
