package ir.sweetsoft.ges;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import common.Exceptions.InvalidInputException;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ItemListActivity}.
 */
public class ItemDetailActivity extends CowManActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DefaultLyout=R.layout.activity_item_detail;
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putLong(CowManActivity.ARG_ITEM_ID,
                    getIntent().getLongExtra(CowManActivity.ARG_ITEM_ID,-1));
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            LastFragment=fragment;
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try
        {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                Log.d("Returning","Start");
                if(LastFragment!=null)
                {
                    Log.d("Returning","Save");
                    LastFragment.Save(false);
                }

                navigateUpTo(new Intent(this, ItemListActivity.class));
                return true;
            }
        }
        catch (InvalidInputException ex)
        {
            showAlert("Error",ex.getMessage(),null,true);
        }
        return super.onOptionsItemSelected(item);

    }
}
