package layout;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import common.SweetDisplayScaler;
import ir.sweetsoft.sweetlibone.Activities.Constants;
import ir.sweetsoft.sweetlibone.Activities.MainActivity;
import ir.sweetsoft.sweetlibone.R;
import ocms.Doctor;
import ocms.Finance;

public class PurchaseFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    EditText txtprice;
    TextView lblprice;
    TextView lblpricecurrency;
    TextView lblAccountBalance;
    ImageView imgMoneyIcon;

    public void setThePurchaseCompleteListener(onPurchaseCompleteListener thePurchaseCompleteListener) {
        this.thePurchaseCompleteListener = thePurchaseCompleteListener;
    }

    private onPurchaseCompleteListener thePurchaseCompleteListener;
    private int PayAmount=-1;
    Button btnPay;
    WebView myWebView;
    public PurchaseFragment() {
    }
    public static PurchaseFragment newInstance(String param1, String param2) {
        PurchaseFragment fragment = new PurchaseFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_purchase, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myWebView = (WebView) getActivity().findViewById(R.id.webview);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            myWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            myWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        txtprice=(EditText)getActivity().findViewById(R.id.txtprice);
        lblprice=(TextView)getActivity().findViewById(R.id.lblprice);
        lblpricecurrency=(TextView)getActivity().findViewById(R.id.lblpricecurrency);
        lblAccountBalance=(TextView)getActivity().findViewById(R.id.lblaccountbalance);
        imgMoneyIcon=getActivity().findViewById(R.id.moneyicon);
        SweetDisplayScaler scaler=new SweetDisplayScaler(getActivity());
        int height=scaler.HeightPercentToPixel(30);
        imgMoneyIcon.getLayoutParams().height=height;
        imgMoneyIcon.getLayoutParams().width=height;
        btnPay=(Button)getActivity().findViewById(R.id.btnPay);

        final Typeface face= Typeface.createFromAsset(getActivity().getAssets(),"fonts/IRANSansMobile.ttf");
        txtprice.setTypeface(face);
        lblprice.setTypeface(face);
        lblpricecurrency.setTypeface(face);
        lblAccountBalance.setTypeface(face);
        btnPay.setTypeface(face);
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {


                String TheURL = view.getUrl();
                if (TheURL.contains("finance/epayment.jsp") && !TheURL.contains("?id"))
                {
                    myWebView.setVisibility(View.GONE);
                    thePurchaseCompleteListener.OnPurchaseComplete();
//                    code = url.substring(url.lastIndexOf("value=") + 6);
//                    dialog.dismiss(); //action
                }
                Log.d("webViewURL",TheURL);
                return false;
            }
        });
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int Price=Integer.parseInt(txtprice.getText().toString());
                MainActivity theActivity=(MainActivity) getActivity();
               Pay(Price,theActivity);
            }
        });
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try
                {

                    String UserName= getActivity().getSharedPreferences(Constants.GENERAL_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.USERNAME,"0");
                    String Password= getActivity().getSharedPreferences(Constants.GENERAL_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.PASSWORD,"0");

                    final long balance = new Finance(getActivity()).getUserBalance(UserName,Password);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lblAccountBalance.setText("موجودی حساب : "+ String.valueOf(balance) + " ریال");
                        }
                    });
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        if(PayAmount>0)
            Pay(PayAmount,(MainActivity) getActivity());

    }
    private void Pay(int Price,  MainActivity theActivity)
    {
        String Mobile= theActivity.getSharedPreferences(Constants.GENERAL_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.MOBILE,"0");
        String UserName= theActivity.getSharedPreferences(Constants.GENERAL_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.USERNAME,"0");
        String Password= theActivity.getSharedPreferences(Constants.GENERAL_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.PASSWORD,"0");
        String postData="txtName&action=txtPay_Click&txtAmount="+(Price/10)+"&txtFamily&txtTel="+Mobile+"&txtDescription=افزایش اعتبار&redirecturl=commit";
        String URL=Constants.PURCHASEURL+"?username="+UserName+"&password="+Password;
        Log.d("URL",URL);
        myWebView.postUrl(URL,postData.getBytes());
        Log.d("URL",URL);
        btnPay.setVisibility(View.GONE);
        myWebView.setVisibility(View.VISIBLE);
        PayAmount=-1;
    }
    public void Pay(int Price)
    {
        PayAmount=Price;
    }
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public interface onPurchaseCompleteListener {
        void OnPurchaseComplete();
    }
}
