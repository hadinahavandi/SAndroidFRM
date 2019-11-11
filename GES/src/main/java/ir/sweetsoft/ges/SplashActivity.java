package ir.sweetsoft.ges;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.activeandroid.query.Select;

import java.util.List;

import common.BaseAppCompatActivity;
import common.SweetDisplayScaler;
import ir.sweetsoft.ges.Model.Cow;

public class SplashActivity extends BaseAppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    private ImageView imgLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imgLogo=findViewById(R.id.imgLogo);
        SweetDisplayScaler scaler=new SweetDisplayScaler(this);
        imgLogo.getLayoutParams().width=scaler.WidthPercentToPixel(70);
        List<Cow> Cows=new Select().from(Cow.class).execute();
//        if(Cows!=null && Cows.size()>200)
//        {
//            showAlert("Expired", "Your Demo Version Is Expired,Please Contact the Developer.", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    SplashActivity.this.finish();
//                }
//            },false);
//        }
//        imgLogo.getLayoutParams().width=scaler.WidthPercentToPixel(50);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logobig / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashActivity.this, HerdActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}