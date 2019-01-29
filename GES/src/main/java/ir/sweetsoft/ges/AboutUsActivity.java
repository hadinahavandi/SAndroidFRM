package ir.sweetsoft.ges;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import common.SweetDisplayScaler;

public class AboutUsActivity extends AppCompatActivity {

    Float LogoSizePercent=17f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        SweetDisplayScaler scaler=new SweetDisplayScaler(this);
        ImageView logo1=findViewById(R.id.logo1);
        ImageView logo2=findViewById(R.id.logo2);
        logo1.getLayoutParams().width=scaler.WidthPercentToPixel(LogoSizePercent);
        logo2.getLayoutParams().width=scaler.WidthPercentToPixel(LogoSizePercent);
        logo1.getLayoutParams().height=scaler.WidthPercentToPixel(LogoSizePercent);
        logo2.getLayoutParams().height=scaler.WidthPercentToPixel(LogoSizePercent);
    }
}
