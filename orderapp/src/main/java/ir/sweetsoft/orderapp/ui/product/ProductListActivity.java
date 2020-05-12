package ir.sweetsoft.orderapp.ui.product;

import android.app.Activity;
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
import com.google.gson.Gson;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import common.SweetDate;
import common.SweetFonts;
import ir.sweetsoft.orderapp.HtmlAdapter;
import ir.sweetsoft.orderapp.Model.FactorProduct;
import ir.sweetsoft.orderapp.Model.Product;
import ir.sweetsoft.orderapp.OrderSelectedProduct;
import ir.sweetsoft.orderapp.R;
import ir.sweetsoft.orderapp.ui.factor.FactorManageActivity;
import ir.sweetsoft.orderapp.ui.menu.MenuActivity;
import ir.sweetsoft.orderapp.ui.print.PrintActivity;

public class ProductListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText txtSearch;
    public static int VIEWTYPE_EDIT=1;
    public static int VIEWTYPE_SELECT=2;
    String lastSearchText="";
    List<Product> Products=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        int viewType=getIntent().getIntExtra("view_type",VIEWTYPE_EDIT);
        FloatingActionButton fab = findViewById(R.id.fab);
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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Products=new Select().from(Product.class).orderBy("name").execute();
        ProductRecyclerViewAdapter adapter;
        if(viewType==VIEWTYPE_EDIT){
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(ProductListActivity.this,ProductManageActivity.class);
                    startActivity(i);
                }
            });
            adapter=new ProductManageRecyclerViewAdapter(Products);
        }
        else{
            adapter=new ProductSelectRecyclerViewAdapter(Products);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<OrderSelectedProduct> selected=adapter.getSelectedItems();
                    if(selected!=null && selected.size()>0) {
                        Intent intent = new Intent();
                        String[] selectedArray = new String[selected.size()];
                        for (int i = 0; i < selected.size(); i++) {
                            selectedArray[i] = selected.get(i).getJSON();
                        }
                        intent.putExtra("item_infos", selectedArray);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }
                }
            });
        }
        adapter.activity=this;
        recyclerView.setAdapter(adapter);


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
                                ProductListActivity.this.showPrintDialog(true);                            }
                        })

                        .setNegativeButton("خیر", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ProductListActivity.this.showPrintDialog(false);
                            }
                        })
                        .show();
            }
        });

    }

    private void showPrintDialog(boolean showPrice){
        new LovelyTextInputDialog(ProductListActivity.this, R.style.TintTheme)
                .setTitle("چاپ فهرست محصولات")
                .setMessage("لطفا متن بالای صفحات را وارد کنید")
                .setConfirmButton("چاپ", new  LovelyTextInputDialog.OnTextInputConfirmListener() {
                    @Override
                    public void onTextInputConfirmed(String text) {
                        ProductListActivity.this.openPrintPage(text,showPrice);                            }
                })
                .show();
    }
    private void openPrintPage(String PageTopString,Boolean showPrice)
    {
        Products=getProducts(txtSearch.getText().toString());
        final String HTML=getDataHTML(PageTopString,showPrice);
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
        loadData(null);
    }
    private List<Product> getProducts(String SearchText)
    {
        List<Product> Products=new Select().from(Product.class).orderBy("name").where("name like ? or code like ? or description like ?","%"+SearchText+"%","%"+SearchText+"%","%"+SearchText+"%").execute();
        return Products;
    }
    public void loadData(String SearchText)
    {
        if(SearchText==null)
            SearchText=lastSearchText;
        else
            lastSearchText=SearchText;
        List<Product> Products=getProducts(SearchText);
        ProductRecyclerViewAdapter theAdapter=(ProductRecyclerViewAdapter)recyclerView.getAdapter();
        theAdapter.mValues=Products;
        theAdapter.notifyDataSetChanged();
    }
    private String getDataHTML(String PageTopString,Boolean showPrice) {
        String HTML = "<!DOCTYPE html>\n" +
                "<html><head><title>فهرست محصولات</title>\n"+
                "  <link rel=\"stylesheet\" href=\"css/style.css\">\n"+
                "  <link rel=\"stylesheet\" href=\"css/normalize.min.css\">\n" +
                "  <link rel=\"stylesheet\" href=\"css/paper.css\">\n" +
                "</head><body>";
                int pageSize=25;
        int pageNumber=0;
                for(int startRow=0;Products != null && startRow<Products.size();startRow+=pageSize)
                {
                    HTML+=ItemsToPage(pageNumber,pageSize,PageTopString,showPrice);
                    pageNumber++;

                }
        HTML+="</body></html>";
        return HTML;
    }
    private String ItemsToPage(int pageNumber,int pageSize,String PageTopString,Boolean showPrice)
    {
        int startRow=pageNumber*pageSize;
        int endRow=startRow+pageSize;

        StringBuilder HTML=new StringBuilder();
        HTML.append("");
//        String HTML="";
        HTML.append("<div class=\"A5\">\n" +
                "<section class=\"sheet padding-2mm\">\n" +
                "    <article>");
        String Header="<div class='headbar'>\n" +
                "\t\t<div class='headbarleft'>\n" +
                "\t\t<p>تاریخ:"+ SweetDate.Time2DateString(SweetDate.getTimeInMiliseconds(),"/")+"</p>\n" +
                "\t\t</div>\n" +
                "\t\t<div class='logocontainer'><img src='img/logoblack.png' class='logo' />" +
                "\n" +
                "\t<div class='logoname'>فهرست کالاها</div>" +
                "\t<div class='logodesc'>"+PageTopString+"</div>" +
                "</div>\n" +
                "\t</div>\n" +
                "\t<div class='rightsweet'>گروه نرم افزاری سوئیت - sweetsoft.ir</div>\n";
        HTML.append(Header);
        String Title = "<table><tr class='head'>";
        Title += "<td>ردیف</td>";
        Title += "<td>کد</td>";
        Title += "<td>نام کالا</td>";
        if(showPrice)
            Title += "<td>قیمت به ریال</td>";
        Title += "</tr>";
        HTML.append(Title);
        try {
            if (Products != null) {
                for (int RowNum=startRow;RowNum<Products.size() && RowNum<endRow;RowNum++) {
                    Product p=Products.get(RowNum);
                    String Row = "<tr class='content'>";
                    Row += "<td>" + (RowNum+1) + "</td>";
                    Row += "<td>" + p.Code + "</td>";
                    ProductStatus ps=new ProductStatus(p);

                    Row += "<td class='productname "+ps.getStatusClass()+"'>" + p.Name + getHTMLFromStatusChar(ps.getStatusChar()) +"</td>";
//                    if(p.getIsActive()){
//                        if(p.Status==0)
//                            Row += "<td class='productname'>" + p.Name + "</td>";
//                        else if(p.Status==1)
//                            Row += "<td class='status-one-productname'>" + p.Name + getHTMLFromStatusChar("--") +" </td>";
//                        else if(p.Status==2)
//                            Row += "<td class='status-two-productname'>" + p.Name + getHTMLFromStatusChar("++") +"  </td>";
//                        else if(p.Status==3)
//                            Row += "<td class='status-three-productname'>" + p.Name + getHTMLFromStatusChar("##") +"  </td>";
//                    }
//                    else
//                        Row += "<td class='inactiveproductname'> ** " + p.Name + " **</td>";
                    if(showPrice)
                        Row += "<td>" + String.format(Locale.ENGLISH,"%,d",Integer.valueOf(p.Price)) + "</td>";
                    Row += "</tr>";
                    HTML.append(Row);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        HTML.append("</table>");

        HTML.append("</article></section>");
        String pn="<div class='pagenumber'>"+(pageNumber+1)+"</div>";
        HTML.append(pn);
        HTML.append("</div>");
        return HTML.toString();
    }
    private String getHTMLFromStatusChar(String theChar){
        return "<strong class='status-char'>"+theChar+"</strong>";
    }
}
