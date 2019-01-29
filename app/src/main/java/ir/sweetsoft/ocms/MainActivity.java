package ir.sweetsoft.ocms;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;

import ir.sweetsoft.ocms.layout.MenuFragment;
import ocms.DoctorreserveFragment;

public class MainActivity extends ir.sweetsoft.sweetlibone.Activities.MainActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("نرم افزار رزرو آنلاین");
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        super.onNavigationItemSelected(item);
        int id = item.getItemId();
        if (id == ir.sweetsoft.sweetlibone.R.id.nav_orderlist) {
            showFragment(DoctorreserveFragment.class);
        }
        return true;
    }

    public void routeToIndex()
    {
        Log.d("Route","Routing to Index From APP");

        showFragment(MenuFragment.class);
        setNavigationDrawerLockState(DrawerLayout.LOCK_MODE_UNLOCKED);
    }
}
