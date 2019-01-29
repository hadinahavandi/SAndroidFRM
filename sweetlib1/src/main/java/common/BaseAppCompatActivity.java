package common;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

public class BaseAppCompatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void showAlert(final String Title,final String Message, final AlertDialog.OnClickListener OnClose, final boolean isCancelable)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder AB = new AlertDialog.Builder(BaseAppCompatActivity.this);
                AB.setMessage(Message);
                AB.setPositiveButton("OK", OnClose);
                AB.setCancelable(isCancelable);
                AB.setTitle(Title);
                AB.show();
            }
        });

    }

}
