package ir.sweetsoft.orderapp.ui.common;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import common.SweetDisplayScaler;
import ir.sweetsoft.orderapp.R;

public class AboutDeveloperActivity extends AppCompatActivity {
    private TextView lbl_title,lbl_address,lbl_tel,lbl_email,lbl_mob;
    private ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_developer);
        lbl_title=(TextView)findViewById(R.id.lbl_title);
        lbl_address=(TextView)findViewById(R.id.lbl_address);
        lbl_tel=(TextView)findViewById(R.id.lbl_tel);
        lbl_mob=(TextView)findViewById(R.id.lbl_mob);
        lbl_email=(TextView)findViewById(R.id.lbl_email);
        logo=findViewById(R.id.logo);
        SweetDisplayScaler scaler=new SweetDisplayScaler(this);
        logo.getLayoutParams().width=scaler.WidthPercentToPixel(50);
        logo.getLayoutParams().height=scaler.WidthPercentToPixel(50);
        Typeface face= Typeface.createFromAsset(getAssets(),"fonts/IRANSansMobile.ttf");
        lbl_title.setTypeface(face);
        lbl_address.setTypeface(face);
        lbl_tel.setTypeface(face);
        lbl_email.setTypeface(face);
        lbl_mob.setTypeface(face);
    }
}
