package ir.sweetsoft.orderapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.activeandroid.query.Select;

import java.util.List;

import common.BaseAppCompatActivity;
import common.SweetDisplayScaler;
import common.SweetFonts;
import ir.sweetsoft.orderapp.Model.Parameter;
import ir.sweetsoft.orderapp.Model.Product;
import ir.sweetsoft.orderapp.ui.menu.MenuActivity;

public class PasswordChangeActivity extends BaseAppCompatActivity {
    private EditText txtPassword;
    private EditText txtOldPassword;
    private EditText txtPasswordCopy;
    private ImageView pageLogo;
    private Button btnChangePass;
    private String MasterPassword = "1";
    private String PASS_PARAMETER_TITLE="pass";

    //    private String MasterPassword="55487810";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);
        Typeface font = SweetFonts.getFont(this, SweetFonts.IranSans);
        SweetDisplayScaler scaler=new SweetDisplayScaler(this);
        pageLogo = findViewById(R.id.pagelogo);
        pageLogo.getLayoutParams().width=scaler.WidthPercentToPixel(15);
        pageLogo.getLayoutParams().height=scaler.WidthPercentToPixel(15);
        txtOldPassword = findViewById(R.id.txt_old_password);
        txtOldPassword.setTypeface(font);
        txtPassword = findViewById(R.id.txt_password);
        txtPassword.setTypeface(font);
        txtPasswordCopy = findViewById(R.id.txt_password_copy);
        txtPasswordCopy.setTypeface(font);
        btnChangePass = findViewById(R.id.btn_checkpass);
        btnChangePass.setTypeface(font);
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String thePassInDB=Parameter.getValueByName(PASS_PARAMETER_TITLE,"");
                String enteredOldPass = txtOldPassword.getText().toString().trim().toLowerCase();
                String enteredPass = txtPassword.getText().toString().trim().toLowerCase();
                String enteredPass2 = txtPasswordCopy.getText().toString().trim().toLowerCase();
                if(thePassInDB.equals(enteredOldPass)) {
                    if(enteredPass.length()==0)
                    {
                        showSmallPassError();
                    }
                    else {
                        if (enteredPass.equals(enteredPass2))
                            checkAndChangePassword(enteredPass);
                        else
                            showInvalidPassError();
                    }
                }
                else
                    showInvalidMasterPassError();
            }
        });

    }

    private void showInvalidMasterPassError() {
        String Message = "رمز فعلی وارد شده صحیح نیست.";
        new AlertDialog.Builder(PasswordChangeActivity.this)
                .setTitle("خطا")
                .setMessage(Message)
                .setPositiveButton("بستن", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    private void showSmallPassError() {
        String Message = "رمز وارد شده کوتاه تر از حد مجاز است.";
        new AlertDialog.Builder(PasswordChangeActivity.this)
                .setTitle("خطا")
                .setMessage(Message)
                .setPositiveButton("بستن", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    private void showInvalidPassError() {
        String Message = "رمز وارد شده با تکرار آن یکسان نیست.";
        new AlertDialog.Builder(PasswordChangeActivity.this)
                .setTitle("خطا")
                .setMessage(Message)
                .setPositiveButton("بستن", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    private void showPasswordChangedMessage(String Password) {
        String Message = "رمز با موفقیت به <"+Password+"> تغییر یافت." ;
        new AlertDialog.Builder(PasswordChangeActivity.this)
                .setTitle("تغییر رمز")
                .setMessage(Message)
                .setNegativeButton("بازگشت به منوی اصلی", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goToMenuPage();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    private void checkAndChangePassword(String newPass) {
        Parameter.setParamValue(PASS_PARAMETER_TITLE,newPass);
        showPasswordChangedMessage(newPass);
    }

    private void goToMenuPage() {
        Intent i = new Intent(PasswordChangeActivity.this, MenuActivity.class);
        startActivity(i);
        finish();
    }

}