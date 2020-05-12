package ir.sweetsoft.orderapp.ui.product;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;

import java.util.List;
import ir.sweetsoft.orderapp.Model.Product;
import ir.sweetsoft.orderapp.OrderSelectedProduct;
import ir.sweetsoft.orderapp.R;
import ir.sweetsoft.orderapp.ui.factor.FactorManageActivity;

public class ProductSelectRecyclerViewAdapter extends ProductRecyclerViewAdapter {

    public ProductSelectRecyclerViewAdapter(List<Product> items) {
        super(items);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_product, parent, false);
        return new ViewHolder(view, new ItemOnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Product theProduct=mValues.get(position);
                changeProductSelection(theProduct);
                return true;
            }
        });
    }
    private void changeProductSelection(Product theProduct){
//                Log.d("added","item"+theProduct.Name);
        if(productIsSelected(theProduct)){
            selectedItems.removeIf(osp->(osp.getItemID().equals(theProduct.getId())));
            activity.loadData(null);
        }
        else{
            new LovelyTextInputDialog(activity, R.style.TintTheme)
                    .setTitle("ثبت تعداد سفارش")
                    .setMessage("لطفا تعداد مورد سفارش را وارد کنید(1)")
                    .setConfirmButton("ثبت", new  LovelyTextInputDialog.OnTextInputConfirmListener() {
                        @Override
                        public void onTextInputConfirmed(String text) {
                            int count=1;
                            try{
                                int cnt=Integer.parseInt(text.toString());
                                if(cnt!=0)
                                    count=cnt;
                            }
                            catch (Exception ex){}
                            selectedItems.add(new OrderSelectedProduct(theProduct.getId(),count));
                            activity.loadData(null);
                        }
                    })
                    .show();
        }
    }
    private boolean productIsSelected(Product theProduct){
//        return selectedItems.stream().filter(osp->(osp.itemID.equals(theProduct.getId()))).count()>0;
        return selectedItems.stream().anyMatch(osp->(osp.getItemID().equals(theProduct.getId())));
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        super.onBindViewHolder(holder,position);
        int adapterPosition=holder.getAdapterPosition();
        Product item=mValues.get(adapterPosition);
        if(productIsSelected(item)){
            Log.d("found","item");
            holder.mIdView.setTextColor(ContextCompat.getColor(activity,R.color.colorSelected));
            holder.mContentView.setTextColor(ContextCompat.getColor(activity,R.color.colorSelected));
            holder.mPrice.setTextColor(ContextCompat.getColor(activity,R.color.colorSelected));
            holder.mView.setBackgroundResource(R.color.colorSelectedBG);
        }
        else{
            holder.mIdView.setTextColor(Color.BLACK);
            holder.mPrice.setTextColor(Color.BLACK);
            holder.mView.setBackgroundColor(Color.WHITE);

        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedItems!=null && selectedItems.size()<1) {//if is not in multiple selection mode
                    Intent i;
                    i = new Intent();
                    new LovelyTextInputDialog(activity, R.style.TintTheme)
                            .setTitle("ثبت تعداد سفارش")
                            .setMessage("لطفا تعداد مورد سفارش را وارد کنید(1)")
                            .setConfirmButton("ثبت", new  LovelyTextInputDialog.OnTextInputConfirmListener() {
                                @Override
                                public void onTextInputConfirmed(String text) {
                                    int count=1;
                                    try{
                                        int cnt=Integer.parseInt(text.toString());
                                        if(cnt!=0)
                                            count=cnt;
                                    }
                                    catch (Exception ex){}

                                    i.putExtra("item_info", new Gson().toJson(new OrderSelectedProduct(mValues.get(adapterPosition).getId(),count)));
                                    activity.setResult(Activity.RESULT_OK, i);
                                    activity.finish();
                                }
                            })
                            .show();
                }
                else {
                    changeProductSelection(mValues.get(adapterPosition));
                }
            }
        });

    }

}