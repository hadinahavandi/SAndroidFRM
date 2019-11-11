package ir.sweetsoft.orderapp.ui.product;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.activeandroid.query.Select;

import common.SweetFonts;
import ir.sweetsoft.orderapp.Model.Product;
import ir.sweetsoft.orderapp.R;

import java.util.List;
import java.util.Locale;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ViewHolder> {

    public List<Product> mValues;
    public ProductListActivity activity;

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    private int viewType=ProductListActivity.VIEWTYPE_EDIT;
    public ProductRecyclerViewAdapter(List<Product> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_product, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText((position+1)+"");
        holder.mContentView.setText(mValues.get(position).Name);
        holder.mPrice.setText(String.format(Locale.ENGLISH,"%,d",Integer.valueOf(mValues.get(position).Price))+" ریال");
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                if(viewType==ProductListActivity.VIEWTYPE_EDIT)
                {
                    i=new Intent(activity,ProductManageActivity.class);
                    i.putExtra("item_id",mValues.get(position).getId());
                    activity.startActivity(i);
                }
                else {
                    i=new Intent();
                    i.putExtra("item_id",mValues.get(position).getId());
                    activity.setResult(Activity.RESULT_OK,i);
                    activity.finish();
                }
            }
        });
        View.OnLongClickListener longClickListener=new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String Message="آیا می خواهید این مورد را حذف کنید؟";
                new AlertDialog.Builder(activity)
                        .setTitle("حذف مورد")
                        .setMessage(Message)
                        .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                holder.mItem.delete();
                                activity.loadData("");
                            }
                        })
                        .setNegativeButton("خیر", null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return true;
            }
        };
        holder.mView.setOnLongClickListener(longClickListener);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mPrice;
        public Product mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            Typeface font= SweetFonts.getFont(activity,SweetFonts.IranSans);

            mIdView = (TextView) view.findViewById(R.id.item_number);
            mPrice = (TextView) view.findViewById(R.id.item_price);
            mContentView = (TextView) view.findViewById(R.id.content);
            mIdView.setTypeface(font);
            mPrice.setTypeface(font);
            mContentView.setTypeface(font);
        }
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
