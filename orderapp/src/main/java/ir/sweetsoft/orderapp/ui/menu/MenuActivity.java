package ir.sweetsoft.orderapp.ui.menu;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import common.SweetFonts;
import ir.sweetsoft.orderapp.Model.Product;
import ir.sweetsoft.orderapp.R;
import ir.sweetsoft.orderapp.ui.common.AboutDeveloperActivity;
import ir.sweetsoft.orderapp.ui.common.AboutUsActivity;
import ir.sweetsoft.orderapp.ui.customer.CustomerListActivity;
import ir.sweetsoft.orderapp.ui.factor.FactorListActivity;
import ir.sweetsoft.orderapp.ui.product.ProductListActivity;

public class MenuActivity extends AppCompatActivity {

    Button btnProducts;
    Button btnCustomers;
    Button btnFactors;
    Button btnArchivedFactors;
    Button btnAboutUs;
    Button btnAboutDeveloper;
    Button btnExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Typeface font=SweetFonts.getFont(this,SweetFonts.IranSans);
        btnProducts=findViewById(R.id.btnproducts);
        btnProducts.setTypeface(font);
        btnCustomers=findViewById(R.id.btncustomers);
        btnCustomers.setTypeface(font);
        btnFactors=findViewById(R.id.btnfactors);
        btnFactors.setTypeface(font);
        btnArchivedFactors=findViewById(R.id.btnarchivedfactors);
        btnArchivedFactors.setTypeface(font);
        btnAboutUs=findViewById(R.id.btnaboutus);
        btnAboutUs.setTypeface(font);
        btnAboutDeveloper=findViewById(R.id.btnaboutdeveloper);
        btnAboutDeveloper.setTypeface(font);
        btnExit=findViewById(R.id.btnexit);
        btnExit.setTypeface(font);

        btnProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MenuActivity.this, ProductListActivity.class);
                startActivity(i);
            }
        });

        btnCustomers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MenuActivity.this, CustomerListActivity.class);
                startActivity(i);
            }
        });

        btnFactors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MenuActivity.this, FactorListActivity.class);
                i.putExtra("is_archived",false);
                startActivity(i);
            }
        });
        btnArchivedFactors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MenuActivity.this, FactorListActivity.class);
                i.putExtra("is_archived",true);
                startActivity(i);
            }
        });
        btnAboutDeveloper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MenuActivity.this, AboutDeveloperActivity.class);
                startActivity(i);
            }
        });

        btnAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MenuActivity.this, AboutUsActivity.class);
                startActivity(i);
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuActivity.this.finish();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuActivity.this.finish();
            }
        });
    }
}
