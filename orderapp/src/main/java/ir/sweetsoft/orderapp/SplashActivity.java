package ir.sweetsoft.orderapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.util.List;

import common.BaseAppCompatActivity;
import common.SweetDisplayScaler;
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
    private Button btnCheckPass;
    private String MasterPassword="156arioazarbaijan1148";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imgLogo=findViewById(R.id.imgLogo);
        txtPassword=findViewById(R.id.txt_password);
        btnCheckPass=findViewById(R.id.btn_checkpass);
        SweetDisplayScaler scaler=new SweetDisplayScaler(this);
        imgLogo.getLayoutParams().width=scaler.WidthPercentToPixel(70);
        List<Parameter> passParams=new Select().from(Parameter.class).where("name=?","master_pass").execute();
        if(passParams==null || passParams.size()==0 || !passParams.get(0).value.equals(MasterPassword))
        {

            txtPassword.setVisibility(View.VISIBLE);
//            txtPassword.setText(passParams.get(0).value);
            btnCheckPass.setVisibility(View.VISIBLE);
            btnCheckPass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String enteredPass=txtPassword.getText().toString().trim().toLowerCase();
                    if(enteredPass.equals(MasterPassword))
                    {
                        Parameter p=new Parameter();
                        p.name="master_pass";
                        p.value=enteredPass;
                        p.save();
                        checkAndGoIn(1);
                    }
                    else
                        showInvalidMasterPassError();

                }
            });
        }
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
        List<Product> products= new Select().from(Product.class).execute();
        if(products!=null && products.size()>30)
            showMaxUsageInvalidationMessageAndExit();
        else {
            goToMenuPage(TimeOut);
        }
    }
    private void goToMenuPage(int TimeOut)
    {

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MenuActivity.class);
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