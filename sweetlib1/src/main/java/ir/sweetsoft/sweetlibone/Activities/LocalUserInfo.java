package ir.sweetsoft.sweetlibone.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Will on 3/23/2018.
 */

public class LocalUserInfo {
    public static void LogOut(Activity TheActivity)
    {
        SharedPreferences sharedpreferences = TheActivity.getSharedPreferences(Constants.GENERAL_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove(Constants.MOBILE);
        editor.remove(Constants.USERNAME);
        editor.remove(Constants.PASSWORD);
        editor.remove(Constants.NAME);
        editor.remove(Constants.ROLE);
        editor.apply();
    }
    public static void SetUserInfo(Activity TheActivity, String UserName, String Password,String Mobile,String Name, int Role)
    {
        SharedPreferences sharedpreferences =TheActivity.getSharedPreferences(Constants.GENERAL_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Constants.USERNAME, UserName);
        editor.putString(Constants.PASSWORD, Password);
        editor.putString(Constants.NAME, Name);
        editor.putString(Constants.MOBILE, Mobile);
        editor.putInt(Constants.ROLE, Role);
        editor.apply();
    }
}
