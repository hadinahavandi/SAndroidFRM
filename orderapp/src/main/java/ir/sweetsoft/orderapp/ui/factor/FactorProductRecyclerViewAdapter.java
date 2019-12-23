package ir.sweetsoft.orderapp.ui.factor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
        ViewHolder vh = new ViewHolder(view, new MyCustomCountEditTextListener(),new MyCustomDescriptionEditTextListener(),new MyCustomLongClickListener());

        return vh;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        int adapterPosition=holder.getAdapterPosition();

        holder.CountChangeListener.position=adapterPosition;
        holder.DescriptionChangeListener.position=adapterPosition;
        holder.LongClickListener.position=adapterPosition;

        FactorProduct itemObject=mValues.get(adapterPosition);
        holder.mItem = itemObject;
        holder.mIdView.setText(String.valueOf(adapterPosition+1));
        holder.mNameView.setText(itemObject.product.Name);
        holder.mCountView.setText(String.valueOf(itemObject.Count));
        if(holder.mItem.Description!=null)
            holder.mDescriptionView.setText(holder.mItem.Description+"");

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
        public MyCustomCountEditTextListener CountChangeListener;
        public MyCustomDescriptionEditTextListener DescriptionChangeListener;
        public FactorProduct mItem;
        public MyCustomLongClickListener LongClickListener;
        public ViewHolder(View view,MyCustomCountEditTextListener CountChangeListener,MyCustomDescriptionEditTextListener DescriptionChangeListener,MyCustomLongClickListener LongClickListener) {
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
            this.LongClickListener=LongClickListener;
            this.CountChangeListener = CountChangeListener;
            this.DescriptionChangeListener = DescriptionChangeListener;
            this.mDescriptionView.addTextChangedListener(DescriptionChangeListener);
            this.mCountView.addTextChangedListener(CountChangeListener);
            mView.setOnLongClickListener(LongClickListener);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'"+ " '" + mCountView.getText() + "'";
        }
    }

    class MyCustomDescriptionEditTextListener implements TextWatcher {
        private int position;
        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            FactorProduct fp=mValues.get(position);
            fp.Description = charSequence.toString();
//            Log.d("SAVEE","SS");

            Log.d("SAVEE","Pos:"+position);
            Log.d("SAVEE","Description:"+charSequence.toString());
            fp.save();

        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }
    class MyCustomLongClickListener implements View.OnLongClickListener
    {

        int position;
        @Override
        public boolean onLongClick(View v) {
            String Message="آیا می خواهید این مورد را حذف کنید؟";
            new AlertDialog.Builder(activity)
                    .setTitle("حذف مورد")
                    .setMessage(Message)
                    .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            mValues.get(position).delete();
                            activity.loadData();
                        }
                    })
                    .setNegativeButton("خیر", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            return true;        }
    }
    class MyCustomCountEditTextListener implements TextWatcher {
        private int position;
        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            FactorProduct fp=mValues.get(position);

                if(charSequence.toString().trim().length()>0)
                {
                    try
                    {
                        Log.d("SAVEE","Pos:"+position);
                        Log.d("SAVEE","OldCount:"+fp.Count);
                        Log.d("SAVEE","Count:"+Integer.valueOf(charSequence.toString().trim()));

                        fp.Count=Integer.valueOf(charSequence.toString().trim());
                        fp.save();
//                        Log.d("SAVEE","123");

                    }
                    catch (Exception ex){}
                }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }

}
