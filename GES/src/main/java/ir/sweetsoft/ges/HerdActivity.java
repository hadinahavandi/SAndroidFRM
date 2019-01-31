package ir.sweetsoft.ges;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.activeandroid.query.Select;

import java.util.List;

import ir.sweetsoft.ges.Model.Herd;

public class HerdActivity extends BaseBtnManageActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ReloadItems();
        setPageTitle("Herds List: ");
    }
    @Override
    protected void DeleteItem(Long ItemID)
    {
        Herd hd=Herd.load(Herd.class,ItemID);
        hd.delete();
        ReloadItems();
    }
    @Override
    protected void AddItem()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please Insert Herd ID:");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String HerdID = input.getText().toString().toUpperCase().trim();
                Herd hd=new Herd();
                hd.Code=HerdID;
                hd.save();
                ReloadItems();
            }
        });
        builder.show();
    }
    @Override
    protected void ReloadItems()
    {
        List<Herd> Herds=new Select().from(Herd.class).orderBy("code").execute();
        List.removeAllViews();
        if(Herds!=null && Herds.size()>0) {
            lbl_noItemExists.setVisibility(View.GONE);
            Intent i = new Intent(this, HerdFileActivity.class);
            for (final Herd theHerd : Herds) {
                Bundle bdl = new Bundle();
                bdl.putLong("herd_id", theHerd.getId());
                AddItem(theHerd.getId(),theHerd.Code, i, bdl);
            }
        }
        else
            lbl_noItemExists.setVisibility(View.VISIBLE);
    }

}
