package ir.sweetsoft.sweettts;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

public class MainActivity extends ir.sweetsoft.sweetlibone.Activities.MainActivity
        implements NavigationView.OnNavigationItemSelectedListener  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.setSharedConf_hasWebView(false);
        this.setSharedConf_hasUserManagement(false);
        this.setLayoutID(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        showFragment(TTSFragment.class,true);

    }

    public void routeToIndex()
    {
        showFragment(TTSFragment.class);
        setNavigationDrawerLockState(DrawerLayout.LOCK_MODE_UNLOCKED);
    }
}
