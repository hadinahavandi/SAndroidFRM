package ir.sweetsoft.orderapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Environment;
import android.print.PageRange;
import android.print.PdfPrint;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.print.onPdfMakeComplatedListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import ir.sweetsoft.orderapp.ui.print.PrintActivity;

import static android.content.Context.PRINT_SERVICE;

public class HtmlAdapter {
    WebView webView;
    Activity activity;
    String HTMLContent;
    public HtmlAdapter(Activity theActivity,String HTMLContent)
    {
        activity=theActivity;
        webView=new WebView(activity);
        webView.getSettings().setJavaScriptEnabled(true);
        this.HTMLContent=HTMLContent;
    }
    private void _makePDF(String ExportPath, String ExportFileName,onPdfMakeComplatedListener onComplateListener)
    {
        File path = new File(ExportPath);
        PrintDocumentAdapter printAdapter;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            printAdapter = webView.createPrintDocumentAdapter(ExportFileName);
        } else {
            printAdapter = webView.createPrintDocumentAdapter();
        }
        PrintAttributes attributes = new PrintAttributes.Builder()
                .setMediaSize(PrintAttributes.MediaSize.ISO_A5)
                .setResolution(new PrintAttributes.Resolution("id", PRINT_SERVICE, 300, 300))
                .setColorMode(PrintAttributes.COLOR_MODE_MONOCHROME)
                .setMinMargins(new PrintAttributes.Margins(0, 0, 0, 0))
                .build();
        PdfPrint p=new PdfPrint(attributes);
        p.print(printAdapter, path, ExportFileName,onComplateListener );
    }
    public void makePDF(String ExportPath, String ExportFileName,onPdfMakeComplatedListener onComplateListener)
    {
        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                _makePDF(ExportPath,ExportFileName,onComplateListener);
            }
        });
        String mime = "text/html";
        String encoding = "utf-8";
        webView.loadDataWithBaseURL("file:///android_asset/", HTMLContent, mime, encoding, null);

    }
    private void webViewToJPG()
    {
        webView.measure(View.MeasureSpec.makeMeasureSpec(
                View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        webView.layout(0, 0, webView.getMeasuredWidth(),
                webView.getMeasuredHeight());
        webView.setDrawingCacheEnabled(true);
        webView.buildDrawingCache();
        Bitmap bm = Bitmap.createBitmap(webView.getMeasuredWidth(),
                webView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas bigcanvas = new Canvas(bm);
        Paint paint = new Paint();
        int iHeight = bm.getHeight();
        bigcanvas.drawBitmap(bm, 0, iHeight, paint);
        webView.draw(bigcanvas);
        System.out.println("1111111111111111111111="
                + bigcanvas.getWidth());
        System.out.println("22222222222222222222222="
                + bigcanvas.getHeight());

        if (bm != null) {
            try {
                String path = Environment.getExternalStorageDirectory()
                        .toString();
                OutputStream fOut = null;
                File file = new File(path, "/aaaa.png");
                fOut = new FileOutputStream(file);

                bm.compress(Bitmap.CompressFormat.PNG, 50, fOut);
                fOut.flush();
                fOut.close();
                bm.recycle();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void createWebPrintJob(WebView webView) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            PrintManager printManager = (PrintManager) activity
                    .getSystemService(PRINT_SERVICE);

            PrintDocumentAdapter printAdapter =
                    null;
            printAdapter = webView.createPrintDocumentAdapter("MyDocument");
            String jobName = activity.getString(R.string.app_name) + " Print Test";

            printManager.print(jobName, printAdapter,
                    new PrintAttributes.Builder().build());
        }
        else{
            Toast.makeText(activity, "Print job has been canceled! ", Toast.LENGTH_SHORT).show();
        }

    }
}
