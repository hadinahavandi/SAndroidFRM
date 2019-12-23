package ir.sweetsoft.orderapp.ui.product;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.print.PageRange;
import android.print.onPdfMakeComplatedListener;
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
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.List;
import java.util.Locale;

import common.SweetDate;
import common.SweetFonts;
import ir.sweetsoft.orderapp.HtmlAdapter;
import ir.sweetsoft.orderapp.Model.FactorProduct;
import ir.sweetsoft.orderapp.Model.Product;
import ir.sweetsoft.orderapp.R;
import ir.sweetsoft.orderapp.ui.factor.FactorManageActivity;
import ir.sweetsoft.orderapp.ui.menu.MenuActivity;
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
                new LovelyStandardDialog(ProductListActivity.this, LovelyStandardDialog.ButtonLayout.VERTICAL)
                        .setTitle("چاپ فهرست محصولات")
                        .setMessage("آیا می خواهید قیمت محصولات هم چاپ شود؟")
                        .setPositiveButton("بله", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ProductListActivity.this.openPrintPage(true);                            }
                        })

                        .setNegativeButton("خیر", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ProductListActivity.this.openPrintPage(false);
                            }
                        })
                        .show();
            }
        });

    }

    private void openPrintPage(Boolean showPrice)
    {
        Products=getProducts(txtSearch.getText().toString());
        final String HTML=getDataHTML(showPrice);
        HtmlAdapter adapter=new HtmlAdapter(this,HTML);
        adapter.makePDF(Environment.getExternalStorageDirectory().getAbsolutePath()+"/ata/"
                ,showPrice?"Product-Price-List.pdf":"Product-List.pdf",
                new onPdfMakeComplatedListener() {
                    @Override
                    public void onComplate(PageRange[] pr) {
                        Intent i = new Intent(ProductListActivity.this, PrintActivity.class);
                        i.putExtra("html_data", HTML);
                        startActivity(i);
                    }
                });
    }
    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        loadData("");
    }
    private List<Product> getProducts(String SearchText)
    {
        List<Product> Products=new Select().from(Product.class).orderBy("name").where("name like ? or code like ? or description like ?","%"+SearchText+"%","%"+SearchText+"%","%"+SearchText+"%").execute();
        return Products;
    }
    public void loadData(String SearchText)
    {
        List<Product> Products=getProducts(SearchText);
        ProductRecyclerViewAdapter theAdapter=(ProductRecyclerViewAdapter)recyclerView.getAdapter();
        theAdapter.mValues=Products;
        theAdapter.notifyDataSetChanged();
    }
    private String getDataHTML(Boolean showPrice) {
        String HTML = "<!DOCTYPE html>\n" +
                "<html><head><title>فهرست محصولات</title>\n"+
                "  <link rel=\"stylesheet\" href=\"css/style.css\">\n"+
                "  <link rel=\"stylesheet\" href=\"css/normalize.min.css\">\n" +
                "  <link rel=\"stylesheet\" href=\"css/paper.css\">\n" +
                "</head><body>";
                int pageSize=20;
        int pageNumber=0;
                for(int startRow=0;Products != null && startRow<Products.size();startRow+=pageSize)
                {
                    HTML+=ItemsToPage(pageNumber,pageSize,showPrice);
                    pageNumber++;

                }
        HTML+="</body></html>";
        return HTML;
    }
    private String ItemsToPage(int pageNumber,int pageSize,Boolean showPrice)
    {
        int startRow=pageNumber*pageSize;
        int endRow=startRow+pageSize;

        String HTML="";
        HTML+="<div class=\"A5\">\n" +
                "<section class=\"sheet padding-2mm\">\n" +
                "    <article>";
        String Header="<div class='headbar'>\n" +
                "\t\t<div class='headbarleft'>\n" +
                "\t\t<p>تاریخ:"+ SweetDate.Time2DateString(SweetDate.getTimeInMiliseconds(),"/")+"</p>\n" +
                "\t\t</div>\n" +
                "\t\t<div class='logocontainer'><img src='img/logoblack.png' class='logo' />" +
                "\n" +
                "\t<div class='logoname'>فهرست کالاها</div>" +
                "</div>\n" +
                "\t</div>\n" +
                "\t<div class='rightsweet'>گروه نرم افزاری سوئیت - sweetsoft.ir</div>\n";
        HTML+=Header;
        String Title = "<table><tr class='head'>";
        Title += "<td>ردیف</td>";
        Title += "<td>کد</td>";
        Title += "<td>نام کالا</td>";
        if(showPrice)
            Title += "<td>قیمت به ریال</td>";
        Title += "</tr>";
        HTML += Title;
        try {
            if (Products != null) {
                for (int RowNum=startRow;RowNum<Products.size() && RowNum<endRow;RowNum++) {
                    Product p=Products.get(RowNum);
                    String Row = "<tr class='content'>";
                    Row += "<td>" + (RowNum+1) + "</td>";
                    Row += "<td>" + p.Code + "</td>";
                    Row += "<td class='productname'>" + p.Name + "</td>";
                    if(showPrice)
                        Row += "<td>" + String.format(Locale.ENGLISH,"%,d",Integer.valueOf(p.Price)) + "</td>";
                    Row += "</tr>";
                    HTML += Row;
                }
            }
        } catch (Exception ex) {
        }
        HTML += "</table>";

        HTML+="</article></section>";
        HTML += "<div class='pagenumber'>"+(pageNumber+1)+"</div>";
        HTML+="</div>";
        return HTML;
    }
}
