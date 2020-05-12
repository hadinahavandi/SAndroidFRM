package ir.sweetsoft.orderapp.ui.product;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.google.gson.Gson;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import common.SweetFonts;
import ir.sweetsoft.orderapp.Model.FactorProduct;
import ir.sweetsoft.orderapp.Model.Product;
import ir.sweetsoft.orderapp.R;
import ir.sweetsoft.orderapp.OrderSelectedProduct;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public abstract class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ViewHolder> {

    protected List<Product> mValues;
    protected ProductListActivity activity;

    public List<OrderSelectedProduct> getSelectedItems() {
        return selectedItems;
    }

    public List<OrderSelectedProduct> selectedItems;
    public ProductRecyclerViewAdapter(List<Product> items) {
        mValues = items;
        selectedItems=new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        int adapterPosition=holder.getAdapterPosition();
        holder.LongClickListener.position=adapterPosition;
        holder.mItem = mValues.get(holder.getAdapterPosition());
        holder.mIdView.setText((adapterPosition+1)+"");
        holder.mContentView.setText(mValues.get(adapterPosition).Name);
        Product item=mValues.get(adapterPosition);
        int color=item.getTextColor();
        holder.mContentView.setTextColor(ContextCompat.getColor(activity,color));
        holder.mPrice.setText(String.format(Locale.ENGLISH,"%,d",Integer.valueOf(mValues.get(adapterPosition).Price))+" ریال");
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

        public ItemOnLongClickListener LongClickListener;
        public ViewHolder(View view,ItemOnLongClickListener LongClickListener) {
            super(view);
            mView = view;
            Typeface font= SweetFonts.getFont(activity,SweetFonts.IranSans);

            this.LongClickListener=LongClickListener;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mPrice = (TextView) view.findViewById(R.id.item_price);
            mContentView = (TextView) view.findViewById(R.id.content);
            mIdView.setTypeface(font);
            mPrice.setTypeface(font);
            mContentView.setTypeface(font);
            mView.setOnLongClickListener(LongClickListener);
        }
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
    public abstract class ItemOnLongClickListener implements View.OnLongClickListener
    {
        int position;
        @Override
        public abstract boolean onLongClick(View v);
    }
}
class ItemActivity{
    public int id;
    onItemActivitySelect onSelectListener;
    public ItemActivity(int id, String title,onItemActivitySelect selectListener) {
        this.id = id;
        this.title = title;
        this.onSelectListener=selectListener;
    }

    @NonNull
    @Override
    public String toString() {
        return title;
    }

    public String title;
}
abstract class onItemActivitySelect{
    public abstract void onSelect(Product theProduct);
}



