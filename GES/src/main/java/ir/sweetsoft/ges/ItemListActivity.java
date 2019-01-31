package ir.sweetsoft.ges;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.FloatingActionButton;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import common.SweetDisplayScaler;
import ir.sweetsoft.ges.Model.Cow;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends CowManActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private ImageView btn_refresh;
    private ImageView btn_cow;
    private ImageView btn_heifer;
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DefaultLyout=R.layout.activity_item_list;
        super.onCreate(savedInstanceState);
        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        SweetDisplayScaler scaler=new SweetDisplayScaler(this);

        btn_refresh = (ImageView) findViewById(R.id.btn_refresh);
        btn_cow = (ImageView) findViewById(R.id.btn_cow);
        btn_heifer = (ImageView) findViewById(R.id.btn_heifer);
        ((RelativeLayout.LayoutParams)btn_refresh.getLayoutParams()).rightMargin=scaler.WidthPercentToPixel(TopIconsMarginPercent);
        ((RelativeLayout.LayoutParams)btn_heifer.getLayoutParams()).rightMargin=scaler.WidthPercentToPixel(TopIconsMarginPercent);
        ((RelativeLayout.LayoutParams)btn_cow.getLayoutParams()).rightMargin=scaler.WidthPercentToPixel(TopIconsMarginPercent);
        btn_refresh.getLayoutParams().height=ButtonSize;
        btn_cow.getLayoutParams().height=ButtonSize;
        btn_heifer.getLayoutParams().height=ButtonSize;
        btn_refresh.getLayoutParams().width=ButtonSize;
        btn_cow.getLayoutParams().width=ButtonSize;
        btn_heifer.getLayoutParams().width=ButtonSize;
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefreshData();
            }
        });
        View.OnClickListener IsHeiferListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean IsHeifer=!getHeifer();
                setIsHeifer(IsHeifer);
                btn_cow.setVisibility((IsHeifer)?View.GONE:View.VISIBLE);
                btn_heifer.setVisibility((IsHeifer)?View.VISIBLE:View.GONE);
            }
        };
        btn_heifer.setOnClickListener(IsHeiferListener);
        btn_cow.setOnClickListener(IsHeiferListener);
        LinearLayout ButtonBar2=(LinearLayout) findViewById(R.id.leftbar);
        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
//        recyclerView.getLayoutParams().width=scaler.WidthPercentToPixel(23);
        ButtonBar2.getLayoutParams().width=scaler.WidthPercentToPixel(23);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerViewAdapter=new SimpleItemRecyclerViewAdapter(this, getHerdCows(), mTwoPane);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ItemListActivity mParentActivity;
        private List<Cow> mValues;

        public void setDataset(List<Cow> dataset) {
            this.mValues = dataset;
        }

        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadItem(view,false);
            }
        };

        SimpleItemRecyclerViewAdapter(ItemListActivity parent,
                                      List<Cow> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            Cow Item=mValues.get(position);
            holder.mIdView.setText(Item.CowCode+"");
            if(Item.isFilled){

                holder.mIdContainer.setBackgroundResource(R.color.ListFilled);
                holder.mIdView.setTextColor(Color.parseColor("#ffffff"));
            }
            else
            {

                holder.mIdContainer.setBackgroundResource(R.color.ListNotFilled);
                holder.mIdView.setTextColor(Color.parseColor("#000000"));
            }


            holder.itemView.setTag(Item);
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final LinearLayout mIdContainer;

            ViewHolder(View view) {
                super(view);
                SweetDisplayScaler scaler=new SweetDisplayScaler((Activity) view.getContext());
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mIdContainer = (LinearLayout) view.findViewById(R.id.id_textcontainer);
                mIdView.setTextSize(TypedValue.COMPLEX_UNIT_PX,scaler.WidthPercentToPixel(3.5f));
            }
        }
    }
}
