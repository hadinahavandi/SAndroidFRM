package ir.sweetsoft.orderapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.List;

import common.BaseAppCompatActivity;
import common.SweetDisplayScaler;
import common.SweetFonts;
import ir.sweetsoft.orderapp.Model.FactorProduct;
import ir.sweetsoft.orderapp.Model.Parameter;
import ir.sweetsoft.orderapp.Model.Product;
import ir.sweetsoft.orderapp.ui.factor.FactorListActivity;
import ir.sweetsoft.orderapp.ui.menu.MenuActivity;
import ir.sweetsoft.orderapp.ui.product.ProductListActivity;

public class SplashActivity extends BaseAppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    private ImageView imgLogo;
    private EditText txtPassword;
    private RelativeLayout masterpassbox;
    private Button btnCheckPass;
    private String MasterPassword="156arioazarbaijan1148";
    private String MASTER_PASSWORD_PARAMETER_TITLE="master_pass";
    private String USAGE_COUNT="usage_count_phase_two";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if(shouldAskPermission())
            requestFileAccessPermission(1147);
        Typeface font = SweetFonts.getFont(this, SweetFonts.IranSans);
        Product item = new Product();
        item.Name="test";
        item.Code="0";
        item.Price=0;
        item.Description="";
        item.IsActive=false;
        item.save();
        item.delete();
        imgLogo=findViewById(R.id.imgLogo);
        txtPassword=findViewById(R.id.txt_password);
        txtPassword.setTypeface(font);
        masterpassbox=findViewById(R.id.masterpassbox);
        btnCheckPass=findViewById(R.id.btn_checkpass);
        btnCheckPass.setTypeface(font);
        SweetDisplayScaler scaler=new SweetDisplayScaler(this);
        imgLogo.getLayoutParams().width=scaler.WidthPercentToPixel(70);
        String usageCount= Parameter.getValueByName(USAGE_COUNT,"0");
        int usageCountNum=Integer.parseInt(usageCount);
        Parameter.setParamValue(USAGE_COUNT,(usageCountNum+1)+"");
        String master_pass= Parameter.getValueByName(MASTER_PASSWORD_PARAMETER_TITLE,"");
        if(!master_pass.equals(MasterPassword))
        {
            masterpassbox.setVisibility(View.VISIBLE);
            btnCheckPass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String enteredPass=txtPassword.getText().toString().trim().toLowerCase();
                    if(enteredPass.equals(MasterPassword))
                    {
                        Parameter.setParamValue(MASTER_PASSWORD_PARAMETER_TITLE,enteredPass);
                        checkAndGoIn(1);
                    }
                    else
                        showInvalidMasterPassError();

                }
            });
        }
        /*else if(usageCountNum>250){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 10000);
            /*new LovelyStandardDialog(this, LovelyStandardDialog.ButtonLayout.VERTICAL)
                    .setTitle("اتمام زمان تست")
                    .setIcon(R.drawable.warning)
                    .setTopColorRes(R.color.Red)
                    .setMessage("مهلت استفاده از نسخه دمو به پایان رسیده است.")
                    .setPositiveButton("بستن",null)
                    .show();*/

        //}
        /*else if(usageCountNum>100){
            if(usageCountNum%3==0){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 10000);
            }
            else {

                checkAndGoIn(SPLASH_TIME_OUT);
            }
            /*new LovelyStandardDialog(this, LovelyStandardDialog.ButtonLayout.VERTICAL)
                    .setTitle("اتمام زمان تست")
                    .setIcon(R.drawable.warning)
                    .setTopColorRes(R.color.Red)
                    .setMessage("مهلت استفاده از نسخه دمو به پایان رسیده است.")
                    .setPositiveButton("ورود به نرم افزار", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            checkAndGoIn(SPLASH_TIME_OUT);
                        }
                    })
                    .show();*/
        //}
        else
            checkAndGoIn(SPLASH_TIME_OUT);

    }
    private void showInvalidMasterPassError()
    {
        String Message="رمز وارد شده با رمز اختصاص داده شده برای این نرم افزار توسط گروه نرم افزاری Sweet مطابقت ندارد.";
        new AlertDialog.Builder(SplashActivity.this)
                .setTitle("خطا")
                .setMessage(Message)
                .setPositiveButton("بستن", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    private void checkAndGoIn(int TimeOut)
    {
//        List<Product> products= new Select().from(Product.class).execute();
//        if(products!=null && products.size()>30)
//            showMaxUsageInvalidationMessageAndExit();
//        else {
            goToMenuPage(TimeOut);
//        }
    }
    private void goToMenuPage(int TimeOut)
    {

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, PasswordActivity.class);
                startActivity(i);
                finish();
            }
        }, TimeOut);
    }
    private void showMaxUsageInvalidationMessageAndExit()
    {
        Toast.makeText(getApplicationContext(),"مدت استفاده آزمایشی از نرم افزار به پایان رسیده است",Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}