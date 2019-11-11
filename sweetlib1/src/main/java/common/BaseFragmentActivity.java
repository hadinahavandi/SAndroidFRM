package common;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Stack;

import ir.sweetsoft.sweetlibone.Activities.Constants;
import ir.sweetsoft.sweetlibone.Activities.LocalUserInfo;
import ir.sweetsoft.sweetlibone.Activities.PlayerActivity;
import ir.sweetsoft.sweetlibone.R;
import layout.AboutDeveloperFragment;
import layout.AboutUsFragment;
import layout.AdminMenuFragment;
import layout.DoctorMenuFragment;
import layout.LoginFragment;
import layout.MenuFragment;
import layout.PurchaseFragment;
import layout.SignupFragment;
import layout.SignupMenuFragment;

public abstract class BaseFragmentActivity extends BaseAppCompatActivity {

    //Shared Configurations
    public long SharedConf_ItemID =-1;
    //EOF Shared Configurations

    private Fragment lastFragment=null;
    public Stack<Class> FragmentCallList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentCallList = new Stack<>();
    }
    public void hideLastFragment()
    {
            FragmentManager fragmentManager = getSupportFragmentManager();
            if(lastFragment!=null)
                fragmentManager.beginTransaction().remove(lastFragment).commit();
        lastFragment=null;

    }
    public Fragment showFragment(Class FragmentClass)
    {
       return showFragment(FragmentClass,true);
    }
    public Fragment showFragment(Class FragmentClass,boolean addToHistory)
    {
        try {
            Log.d("Displaying Fragment",FragmentClass.toString());
            Fragment fragment = (Fragment) FragmentClass.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();

            if(lastFragment!=null)
            {
                fragmentManager.beginTransaction().remove(lastFragment).commit();
                if(addToHistory)
                    FragmentCallList.push(lastFragment.getClass());
            }
            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .show(fragment)
                    .commit();
            //fragmentManager.beginTransaction().replace(getMainContentID(), fragment).commit();
            lastFragment=fragment;
            return  fragment;

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public abstract int getMainContentID();
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
