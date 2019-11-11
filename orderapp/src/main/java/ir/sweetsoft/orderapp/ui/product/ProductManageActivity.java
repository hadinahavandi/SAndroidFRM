package ir.sweetsoft.orderapp.ui.product;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

import common.SweetFonts;
import ir.sweetsoft.orderapp.Model.Product;
import ir.sweetsoft.orderapp.R;

public class ProductManageActivity extends AppCompatActivity {

    Product p;
    EditText textName;
    EditText textPrice;
    EditText textDescription;
    Button btnSave;
    TextView lblName;
    TextView lblPrice;
    TextView lblDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_manage);
        Typeface font= SweetFonts.getFont(this,SweetFonts.IranSans);

        long id=getIntent().getLongExtra("item_id",-1);
        if(id>0)
            p=Product.load(Product.class,id);
        else
            p=new Product();
        textName=findViewById(R.id.txtname);

        lblName=findViewById(R.id.lblname);
        lblName.setTypeface(font);
        lblDescription=findViewById(R.id.lbldescription);
        lblDescription.setTypeface(font);
        textDescription=findViewById(R.id.txtdescription);
        textDescription.setTypeface(font);
        if(p.Name!=null)
            textName.setText(p.Name);
        if(p.Description!=null)
            textDescription.setText(p.Description);
        textName.setTypeface(font);
        textPrice=findViewById(R.id.txtprice);
        lblPrice=findViewById(R.id.lblprice);
        lblPrice.setTypeface(font);
        if(p.Price!=null)
            textPrice.setText(String.format(Locale.ENGLISH, "%,d", Integer.valueOf(p.Price)));
        textPrice.setTypeface(font);
        btnSave=findViewById(R.id.btnsave);
        btnSave.setTypeface(font);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(textName.getText().toString(),textDescription.getText().toString(),textPrice.getText().toString());
                ProductManageActivity.this.finish();
            }
        });
        textName.addTextChangedListener(changeWatcher);
        textDescription.addTextChangedListener(changeWatcher);
        textPrice.addTextChangedListener(changeWatcher);
//        textPrice.addTextChangedListener(priceChangeWatcher);
    }
//    private TextWatcher priceChangeWatcher=new TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//            try {
//                textPrice.removeTextChangedListener(this);
//                String value = textPrice.getText().toString();
//                String newValue=String.format(Locale.ENGLISH, "%,d", Integer.valueOf(value));
//                textPrice.setText(newValue);
//
//                textPrice.addTextChangedListener(this);
//            }
//            catch (Exception ex){
//                textPrice.removeTextChangedListener(this);
//                textPrice.addTextChangedListener(this);
//            }
//        }
//    };
    private TextWatcher changeWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            save(textName.getText().toString(),textDescription.getText().toString(),textPrice.getText().toString());
        }
    };
    private void save(String Name,String Description,String Price)
    {

        p.Name = (Name==null||Name=="")?"بدون نام":Name;
        p.Description = Description==null?"":Description;
        try {
            p.Price = Integer.parseInt(Price.replaceAll("[\\D]", ""));
        }
        catch (Exception ex)
        {
            p.Price=0;
        }
        p.save();
    }
}
