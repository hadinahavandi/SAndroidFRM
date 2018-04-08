package ir.sweetsoft.ocms;
import android.os.Bundle;
import android.view.MenuItem;
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
}
