package ir.sweetsoft.orderapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
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
import common.SweetFonts;
import ir.sweetsoft.orderapp.Model.Parameter;
import ir.sweetsoft.orderapp.Model.Product;
import ir.sweetsoft.orderapp.ui.menu.MenuActivity;

public class PasswordActivity extends BaseAppCompatActivity {
    private EditText txtPassword;
    private ImageView pageLogo;
    private Button btnCheckPass;
    private String MasterPassword = "1";

    //    private String MasterPassword="55487810";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        Typeface font = SweetFonts.getFont(this, SweetFonts.IranSans);
        SweetDisplayScaler scaler=new SweetDisplayScaler(this);

        pageLogo = findViewById(R.id.pagelogo);
        pageLogo.getLayoutParams().width=scaler.WidthPercentToPixel(15);
        pageLogo.getLayoutParams().height=scaler.WidthPercentToPixel(15);
        txtPassword = findViewById(R.id.txt_password);
        txtPassword.setTypeface(font);
        btnCheckPass = findViewById(R.id.btn_checkpass);
        btnCheckPass.setTypeface(font);
        btnCheckPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String thePassInDB=Parameter.getValueByName("pass","").trim().toLowerCase();
                String enteredPass = txtPassword.getText().toString().trim().toLowerCase();
                if(thePassInDB.equals(enteredPass))
                    checkAndGoIn();
                else
                    showInvalidMasterPassError();
            }
        });

    }

    private void showInvalidMasterPassError() {
        String Message = "رمز وارد شده صحیح نیست.";
        new AlertDialog.Builder(PasswordActivity.this)
                .setTitle("خطا")
                .setMessage(Message)
                .setPositiveButton("بستن", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void checkAndGoIn() {
        List<Product> products = new Select().from(Product.class).execute();
        goToMenuPage();
    }

    private void goToMenuPage() {
        Intent i = new Intent(PasswordActivity.this, MenuActivity.class);
        startActivity(i);
        finish();
    }

}