package ir.sweetsoft.voicestoryshop.voicestoryshop;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fileshop.FileFragment;
import layout.AdminMenuFragment;
import layout.DoctorMenuFragment;
import layout.MenuFragment;

public class MainActivity extends ir.sweetsoft.sweetlibone.Activities.MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("داستان های آموزنده");
    }
    public void routeToIndex()
    {
        String UserName= getSharedPreferences(ir.sweetsoft.sweetlibone.Activities.Constants.GENERAL_PREFERENCES, Context.MODE_PRIVATE).getString(ir.sweetsoft.sweetlibone.Activities.Constants.USERNAME,"0");
        String Password= getSharedPreferences(ir.sweetsoft.sweetlibone.Activities.Constants.GENERAL_PREFERENCES, Context.MODE_PRIVATE).getString(ir.sweetsoft.sweetlibone.Activities.Constants.PASSWORD,"0");
        int ROLE= getSharedPreferences(ir.sweetsoft.sweetlibone.Activities.Constants.GENERAL_PREFERENCES, Context.MODE_PRIVATE).getInt(ir.sweetsoft.sweetlibone.Activities.Constants.ROLE,0);

            showFragment(FileFragment.class);
    }
}
