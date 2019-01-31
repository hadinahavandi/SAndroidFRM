package ir.sweetsoft.ges;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import common.SweetDisplayScaler;

public abstract class BaseBtnManageActivity extends AppCompatActivity {


    LinearLayout List;
    ImageView BtnAdd;
    TextView lbl_noItemExists;
    TextView lbl_pagetitle;
    SweetDisplayScaler scaler;
    int FontSize=0;
    int MarginBetweenButtons=0;
    int ButtonsHorizontalMargin=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_herd);
        List=findViewById(R.id.list);
        BtnAdd=findViewById(R.id.btn_add);
        lbl_noItemExists=findViewById(R.id.lbl_noItemExists);
        lbl_pagetitle=findViewById(R.id.lbl_pagetitle);
        scaler=new SweetDisplayScaler(this);
        int ButtonSize=Math.min(scaler.HeightPercentToPixel(15),scaler.WidthPercentToPixel(15));
        FontSize=scaler.WidthPercentToPixel(3.5f);
        MarginBetweenButtons=scaler.HeightPercentToPixel(1f);
        lbl_noItemExists.setTextSize(TypedValue.COMPLEX_UNIT_PX,scaler.WidthPercentToPixel(3.5f));
        lbl_pagetitle.setTextSize(TypedValue.COMPLEX_UNIT_PX,scaler.WidthPercentToPixel(3.5f));
        ButtonsHorizontalMargin=scaler.WidthPercentToPixel(2f);
        BtnAdd.getLayoutParams().width=ButtonSize;
        BtnAdd.getLayoutParams().height=ButtonSize;
        BtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddItem();
            }
        });
    }
    protected void setPageTitle(String Title)
    {
        lbl_pagetitle.setText(Title);
    }
    protected abstract void DeleteItem(Long ItemID);
    protected abstract void AddItem();
    protected abstract void ReloadItems();
    protected void AddItem(final long ItemID,String Title, final Intent thePathIntent, final Bundle theBundle )
    {
        LayoutInflater lf=LayoutInflater.from(this);
        RelativeLayout tmpBtnRow=(RelativeLayout) lf.inflate(R.layout.item_btn_item,List,false);
        Button tmpBtn=(Button)tmpBtnRow.getChildAt(0);
        ImageView tmpDelete=(ImageView) tmpBtnRow.getChildAt(1);
        List.addView(tmpBtnRow);
        tmpBtn.setText(Title);
        tmpBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX,FontSize);
        ((LinearLayout.LayoutParams)tmpBtnRow.getLayoutParams()).topMargin=MarginBetweenButtons;
        ((LinearLayout.LayoutParams)tmpBtnRow.getLayoutParams()).leftMargin=ButtonsHorizontalMargin;
        ((LinearLayout.LayoutParams)tmpBtnRow.getLayoutParams()).rightMargin=ButtonsHorizontalMargin;
        ((RelativeLayout.LayoutParams)tmpBtn.getLayoutParams()).rightMargin=ButtonsHorizontalMargin;
        int ButtonSize=Math.min(scaler.HeightPercentToPixel(10),scaler.WidthPercentToPixel(10));
        tmpDelete.getLayoutParams().width=ButtonSize;
        tmpDelete.getLayoutParams().height=ButtonSize;
        tmpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thePathIntent.putExtras(theBundle);
                startActivity(thePathIntent);
            }
        });
        tmpDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteItem(ItemID);
            }
        });
    }

}
