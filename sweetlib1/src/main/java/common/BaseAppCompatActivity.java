package common;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
    protected boolean shouldAskPermission() {
        return (Build.VERSION.SDK_INT > 22);
    }
    protected void requestFileAccessPermission(int RequestCode)
    {
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},RequestCode);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        if (requestCode==1147) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                AlertDialog.Builder ab=new AlertDialog.Builder(this);
                ab.setMessage("دسترسی لازم برای ذخیره فایل اعطا نشده است.لطفا اجازه ذخیره و مشاهده فایل را فراهم نمایید");
                ab.setPositiveButton("OK",null);
                ab.show();
            }
            return;
        }
    }

}
