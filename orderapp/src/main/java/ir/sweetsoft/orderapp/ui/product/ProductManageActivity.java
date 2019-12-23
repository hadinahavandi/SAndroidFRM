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
    EditText textCode;
    EditText textName;
    EditText textPrice;
    EditText textDescription;
    Button btnSave;
    TextView lblCode;
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
        textCode=findViewById(R.id.txtcode);
        textName=findViewById(R.id.txtname);

        lblCode=findViewById(R.id.lblcode);
        lblCode.setTypeface(font);
        lblName=findViewById(R.id.lblname);
        lblName.setTypeface(font);
        lblDescription=findViewById(R.id.lbldescription);
        lblDescription.setTypeface(font);
        textDescription=findViewById(R.id.txtdescription);
        textDescription.setTypeface(font);
        if(p.Name!=null)
            textName.setText(p.Name);
        if(p.Code!=null)
            textCode.setText(p.Code);
        if(p.Description!=null)
            textDescription.setText(p.Description);
        textCode.setTypeface(font);
        textName.setTypeface(font);
        textPrice=findViewById(R.id.txtprice);
        lblPrice=findViewById(R.id.lblprice);
        lblPrice.setTypeface(font);
        if(p.Price!=null)
            textPrice.setText(String.valueOf(Integer.valueOf(p.Price)));
        textPrice.setTypeface(font);
        btnSave=findViewById(R.id.btnsave);
        btnSave.setTypeface(font);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(textCode.getText().toString(),textName.getText().toString(),textDescription.getText().toString(),textPrice.getText().toString());
                ProductManageActivity.this.finish();
            }
        });
        textCode.addTextChangedListener(changeWatcher);
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
            save(textCode.getText().toString(),textName.getText().toString(),textDescription.getText().toString(),textPrice.getText().toString());
        }
    };
    private void save(String Code,String Name,String Description,String Price)
    {

        p.Name = (Name==null||Name=="")?"بدون نام":Name;
        p.Code = (Code==null||Code=="")?"0":Code;
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
