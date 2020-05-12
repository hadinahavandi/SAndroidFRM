package ir.sweetsoft.orderapp.ui.product;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.activeandroid.query.Select;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;
import java.util.List;
import ir.sweetsoft.orderapp.Model.FactorProduct;
import ir.sweetsoft.orderapp.Model.Product;
import ir.sweetsoft.orderapp.R;

public class ProductManageRecyclerViewAdapter extends ProductRecyclerViewAdapter {

    public ProductManageRecyclerViewAdapter(List<Product> items) {
        super(items);
    }

    @Override
    public ProductRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_product, parent, false);
        return new ProductRecyclerViewAdapter.ViewHolder(view, new ProductRecyclerViewAdapter.ItemOnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String Message="لطفا وضعیت جدید این محصول را مشخص کنید؟";
                Product theProduct=mValues.get(position);
                ItemActivity[] itemActs=new ItemActivity[6];
                itemActs[0]=new ItemActivity(0,"عادی", new onItemActivitySelect() {
                    @Override
                    public void onSelect(Product p) {
                        p.IsActive=true;
                        p.Status=0;
                        p.save();
                        p=null;
                        activity.loadData(null);
                    }
                });

                itemActs[1]=new ItemActivity(1,"سبز-افزایش", new onItemActivitySelect() {
                    @Override
                    public void onSelect(Product p) {
                        p.IsActive=true;
                        p.Status=2;
                        p.save();
                        p=null;
                        activity.loadData(null);
                    }
                });
                itemActs[2]=new ItemActivity(2,"آبی-کاهش", new onItemActivitySelect() {
                    @Override
                    public void onSelect(Product p) {
                        p.IsActive=true;
                        p.Status=1;
                        p.save();
                        p=null;
                        activity.loadData(null);
                    }
                });

                itemActs[3]=new ItemActivity(5,"زرد-نامشخص", new onItemActivitySelect() {
                    @Override
                    public void onSelect(Product p) {
                        p.IsActive=true;
                        p.Status=3;
                        p.save();
                        p=null;
                        activity.loadData(null);
                    }
                });
                itemActs[4]=new ItemActivity(3,"غیرفعال", new onItemActivitySelect() {
                    @Override
                    public void onSelect(Product p) {
                        p.IsActive=false;
                        p.Status=0;
                        p.save();
                        p=null;
                        activity.loadData(null);
                    }
                });
                itemActs[5]=new ItemActivity(4,"حذف کامل", new onItemActivitySelect() {
                    @Override
                    public void onSelect(Product p) {
                        Boolean existsInFactorProducts=new Select().from(FactorProduct.class).where("product_fid = ?",p.getId()).exists();
                        if(!existsInFactorProducts)
                        {
                            p.delete();
                            activity.loadData(null);
                        }
                        else{
                            new LovelyStandardDialog(activity, LovelyStandardDialog.ButtonLayout.VERTICAL)
                                    .setTitle("محصول حذف نشد")
                                    .setTopColorRes(R.color.Red)
                                    .setIcon(R.drawable.warning)
                                    .setMessage("این محصول در برخی از فاکتور ها وجود دارد، به همین دلیل امکان حذف آن وجود ندارد.")
                                    .setNeutralButton("بستن",null)
                                    .show();
                        }
                    }
                });


                ArrayAdapter<ItemActivity> adapter = new ArrayAdapter<ItemActivity>(activity,android.R.layout.simple_list_item_1,android.R.id.text1,itemActs);
                new LovelyChoiceDialog(activity)
                        .setTopColorRes(R.color.colorPrimary)
                        .setIcon(R.drawable.status)
                        .setTitle("تغییر وضعیت محصول")
//                    .setIcon(R.drawable.ic_local_atm_white_36dp)
                        .setMessage(Message)
                        .setItems(adapter, new LovelyChoiceDialog.OnItemSelectedListener<ItemActivity>() {
                            @Override
                            public void onItemSelected(int position, ItemActivity item) {
                                item.onSelectListener.onSelect(theProduct);
                            }
                        })
                        .show();
                return true;
            }
        });
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        super.onBindViewHolder(holder,position);
        int adapterPosition=holder.getAdapterPosition();
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                    i=new Intent(activity,ProductManageActivity.class);
                    i.putExtra("item_id",mValues.get(adapterPosition).getId());
                    activity.startActivity(i);
            }
        });

    }
}

