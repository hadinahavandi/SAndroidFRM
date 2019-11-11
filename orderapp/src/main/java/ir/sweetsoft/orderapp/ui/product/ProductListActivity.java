package ir.sweetsoft.orderapp.ui.product;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.activeandroid.query.Select;

import java.util.List;
import java.util.Locale;

import common.SweetDate;
import common.SweetFonts;
import ir.sweetsoft.orderapp.Model.FactorProduct;
import ir.sweetsoft.orderapp.Model.Product;
import ir.sweetsoft.orderapp.R;
import ir.sweetsoft.orderapp.ui.factor.FactorManageActivity;
import ir.sweetsoft.orderapp.ui.print.PrintActivity;

public class ProductListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText txtSearch;
    public static int VIEWTYPE_EDIT=1;
    public static int VIEWTYPE_SELECT=2;
    List<Product> Products=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        Typeface font= SweetFonts.getFont(this,SweetFonts.IranSans);

        recyclerView =findViewById(R.id.list);
        txtSearch=findViewById(R.id.txtsearch);
        txtSearch.setTypeface(font);
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("Search",s.toString());
                ProductListActivity.this.loadData(s.toString());
            }
        });
        int viewType=getIntent().getIntExtra("view_type",VIEWTYPE_EDIT);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Products=new Select().from(Product.class).orderBy("name").execute();
        ProductRecyclerViewAdapter adapter=new ProductRecyclerViewAdapter(Products);
        adapter.activity=this;
        adapter.setViewType(viewType);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ProductListActivity.this,ProductManageActivity.class);
                startActivity(i);
            }
        });
        FloatingActionButton printfab = findViewById(R.id.printfab);
        printfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductListActivity.this.openPrintPage();
            }
        });
    }

    private void openPrintPage()
    {
        Intent i = new Intent(ProductListActivity.this, PrintActivity.class);
        Products=getProducts(txtSearch.getText().toString());
        i.putExtra("html_data", getDataHTML());
        startActivity(i);
    }
    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        loadData("");
    }
    private List<Product> getProducts(String SearchText)
    {
        List<Product> Products=new Select().from(Product.class).orderBy("name").where("name like ?","%"+SearchText+"%").execute();
        return Products;
    }
    public void loadData(String SearchText)
    {
        List<Product> Products=getProducts(SearchText);
        ProductRecyclerViewAdapter theAdapter=(ProductRecyclerViewAdapter)recyclerView.getAdapter();
        theAdapter.mValues=Products;
        theAdapter.notifyDataSetChanged();
    }
    private String getDataHTML() {
        String HTML = "<!DOCTYPE html>\n" +
                "<html><head><title>فهرست محصولات</title>\n"+
                "  <link rel=\"stylesheet\" href=\"css/style.css\">\n"+
                "  <link rel=\"stylesheet\" href=\"css/normalize.min.css\">\n" +
                "  <link rel=\"stylesheet\" href=\"css/paper.css\">\n" +
                "</head><body class=\"A4\">\n" +
                "<section class=\"sheet padding-10mm\">\n" +
                "    <article>";
        String Header="<div class='headbar'>\n" +
                "\t\t<div class='headbarleft'>\n" +
                "\t\t<p>تاریخ:"+ SweetDate.Time2DateString(SweetDate.getTimeInMiliseconds(),"/")+"</p>\n" +
//                "\t\t<p>شماره:</p>\n" +
                "\t\t</div>\n" +
                "\t\t<div class='logocontainer'><img src='img/logoblack.png' class='logo' />" +
                "\n" +
                "\t<div class='logoname'>فهرست کالاها</div>" +
                "</div>\n" +
                "\t</div>\n" +
//                "\t<div class='customername'>آقای نهاوندی</div>\n" +
                "\t<div class='rightsweet'>گروه نرم افزاری سوئیت - sweetsoft.ir</div>\n";
        HTML+=Header;
        String Title = "<table><tr class='head'>";
        Title += "<td>ردیف</td>";
        Title += "<td>نام کالا</td>";
        Title += "<td>قیمت به ریال</td>";
//        Title += "<td>توضیحات</td>";
        Title += "</tr>";
        HTML += Title;
        try {
            if (Products != null) {
                int RowNum = 0;
                for (Product p : Products) {
                    String Row = "<tr class='content'>";
                    Row += "<td>" + (++RowNum) + "</td>";
                    Row += "<td class='productname'>" + p.Name + "</td>";
                    Row += "<td>" + String.format(Locale.ENGLISH,"%,d",Integer.valueOf(p.Price)) + "</td>";
                    Row += "</tr>";
                    HTML += Row;
                }
//                String EndRow = "<tr class='content'>";
//                EndRow += "<td class='productname'>********************</td><td>****</td><td class='productdescription'>********************</td>";
//                EndRow += "</tr>";
//                HTML += EndRow;
            }
        } catch (Exception ex) {
        }
        HTML += "</table>";
/*
        String Name ="";
        if(factor.Description!=null && factor.Description.length()>0)
//            Name+= "<div class='description'><p>توضیحات: "+factor.Description+"</p></div>\n";
            Name+= "<div class='signatureright'><p>ویزیتور: "+factor.Name+"</p></div>\n" +
//                "<div class='signatureleft'><p>فروشنده: بازرگانی آریو تجارت آذربایجان"+"</p></div>\n" +
                    "<div class='footbar'>\n" +
                    "\t\t<div class='footbarright'>\n" +
                    "\t\t<p>تلفن: \n" +
                    "  09144560455 - 09016543040</p>\n" +
                    "\t\t</div>\n" +
                    "\t</div>";
        HTML += Name;
        */

        HTML+="</article></section></body></html>";
        return HTML;
    }
}
