package ir.sweetsoft.orderapp.ui.print;

import android.content.Context;
import android.graphics.Typeface;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import common.SweetFonts;
import ir.sweetsoft.orderapp.R;

public class PrintActivity extends AppCompatActivity {

    Button btnPrint;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);
        String WebViewContent=getIntent().getStringExtra("html_data");
        Typeface font= SweetFonts.getFont(this,SweetFonts.IranSans);

        btnPrint=(Button)findViewById(R.id.btnprint);
        btnPrint.setTypeface(font);
        webView=(WebView)findViewById(R.id.webview);
        String mime = "text/html";
        String encoding = "utf-8";
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadDataWithBaseURL("file:///android_asset/", WebViewContent, mime, encoding, null);
        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {createWebPrintJob(webView);}
        });
    }
    private void createWebPrintJob(WebView webView) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            PrintManager printManager = (PrintManager) this
                    .getSystemService(Context.PRINT_SERVICE);

            PrintDocumentAdapter printAdapter =
                    null;
            printAdapter = webView.createPrintDocumentAdapter("MyDocument");
            String jobName = getString(R.string.app_name) + " Print Test";

            printManager.print(jobName, printAdapter,
                    new PrintAttributes.Builder().build());
        }
        else{
            Toast.makeText(PrintActivity.this, "Print job has been canceled! ", Toast.LENGTH_SHORT).show();
        }

    }
}
