package ir.sweetsoft.orderapp.ui.factor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import common.SweetFonts;
import ir.sweetsoft.orderapp.Model.Factor;
import ir.sweetsoft.orderapp.Model.FactorProduct;
import ir.sweetsoft.orderapp.R;

public class FactorProductRecyclerViewAdapter extends RecyclerView.Adapter<FactorProductRecyclerViewAdapter.ViewHolder> {

    public List<FactorProduct> mValues;
    public FactorManageActivity activity;
    public FactorProductRecyclerViewAdapter(List<FactorProduct> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_factorproduct, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText((position+1)+"");
        holder.mNameView.setText(mValues.get(position).product.Name);
        holder.mCountView.setText(mValues.get(position).Count+"");
        if(mValues.get(position).Description!=null)
            holder.mDescriptionView.setText(mValues.get(position).Description+"");
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
                                activity.loadData();
                            }
                        })
                        .setNegativeButton("خیر", null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return true;
            }
        };
        holder.mView.setOnLongClickListener(longClickListener);
        TextWatcher changeWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String count=holder.mCountView.getText().toString().trim();
                String description=holder.mDescriptionView.getText().toString().trim();

                holder.mItem.Description=description;
                if(count.length()>0)
                {
                    try
                    {
                        holder.mItem.Count=Integer.valueOf(count);
                    }
                    catch (Exception ex){}
                }
                holder.mItem.save();
            }
        };
        holder.mCountView.addTextChangedListener(changeWatcher);
        holder.mDescriptionView.addTextChangedListener(changeWatcher);
    }
    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mNameView;
        public final EditText mCountView;
        public final EditText mDescriptionView;
        public FactorProduct mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            Typeface font= SweetFonts.getFont(activity,SweetFonts.IranSans);
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mIdView.setTypeface(font);
            mNameView = (TextView) view.findViewById(R.id.item_name);
            mNameView.setTypeface(font);
            mCountView = (EditText) view.findViewById(R.id.item_count);
            mCountView.setTypeface(font);
            mDescriptionView = (EditText) view.findViewById(R.id.item_description);
            mDescriptionView.setTypeface(font);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'"+ " '" + mCountView.getText() + "'";
        }
    }
}
