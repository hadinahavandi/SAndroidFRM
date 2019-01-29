package ir.sweetsoft.sweettts;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.speech.tts.Voice;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ir.sweetsoft.sweetlibone.Activities.LocalUserInfo;
import layout.AboutDeveloperFragment;
import layout.AboutUsFragment;
import layout.PurchaseFragment;
import layout.SignupMenuFragment;
import tts.Context;
import tts.ContextFragment;

public class MainActivity extends ir.sweetsoft.sweetlibone.Activities.MainActivity
        implements NavigationView.OnNavigationItemSelectedListener  {

    private List<Voice> VoiceList;

    public Context SharedContext;
    protected void setVoiceList(List<Voice> voiceList) {
        VoiceList = voiceList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.setSharedConf_hasWebView(false);
        this.setSharedConf_hasUserManagement(false);
        this.setSharedConf_hasCustomActionBar(false);
        this.setLayoutID(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        VoiceList=new ArrayList<>();
        showFragment(TTSFragment.class,true);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == ir.sweetsoft.sweetlibone.R.id.nav_mainmenu) {
            routeToIndex();
        }
        else if (id == R.id.nav_settings) {
            Intent intent=new Intent(getApplicationContext(),SettingsActivity.class);
            String[] VoiceListArray=new String[VoiceList.size()];
            String[] VoiceTitleListArray=new String[VoiceList.size()];
            for(int i=0;i<VoiceList.size();i++)
            {
                VoiceListArray[i]=VoiceList.get(i).getName();
                VoiceTitleListArray[i]=VoiceList.get(i).isNetworkConnectionRequired()+":"+VoiceList.get(i).getLocale().getISO3Language()+"-"+VoiceList.get(i).getLocale().getISO3Country();
            }
//            Log.d("MainVoice",VoiceTitleListArray.length+"");
//            Log.d("MainVoiceName",VoiceTitleListArray[0]);
            intent.putExtra("voicevalues", VoiceListArray);
            intent.putExtra("voicetitles", VoiceTitleListArray);
            startActivity(intent);
        }
        else if (id == ir.sweetsoft.sweettts.R.id.nav_contextlist) {
            showFragment(ContextFragment.class);
        }
        else if (id == ir.sweetsoft.sweetlibone.R.id.nav_aboutdeveloper) {
            showFragment(AboutDeveloperFragment.class);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(ir.sweetsoft.sweetlibone.R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void routeToIndex()
    {
        showFragment(TTSFragment.class);
        setNavigationDrawerLockState(DrawerLayout.LOCK_MODE_UNLOCKED);
    }
}
