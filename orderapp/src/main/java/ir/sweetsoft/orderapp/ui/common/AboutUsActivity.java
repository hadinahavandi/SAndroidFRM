package ir.sweetsoft.orderapp.ui.common;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import common.SweetDisplayScaler;
import ir.sweetsoft.orderapp.R;

public class AboutUsActivity extends AppCompatActivity {
    private TextView lbl_title,lbl_address,lbl_tel,lbl_email;

    private ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        lbl_title=(TextView)findViewById(ir.sweetsoft.sweetlibone.R.id.lbl_title);
        lbl_address=(TextView)findViewById(ir.sweetsoft.sweetlibone.R.id.lbl_address);
        lbl_tel=(TextView)findViewById(ir.sweetsoft.sweetlibone.R.id.lbl_tel);
        lbl_email=(TextView)findViewById(ir.sweetsoft.sweetlibone.R.id.lbl_email);

        logo=findViewById(R.id.logo);
        SweetDisplayScaler scaler=new SweetDisplayScaler(this);
        logo.getLayoutParams().width=scaler.WidthPercentToPixel(70);
        logo.getLayoutParams().height=scaler.WidthPercentToPixel(50);

        Typeface face= Typeface.createFromAsset(getAssets(),"fonts/IRANSansMobile.ttf");
        lbl_title.setTypeface(face);
        lbl_address.setTypeface(face);
        lbl_tel.setTypeface(face);
        lbl_email.setTypeface(face);
    }
}
