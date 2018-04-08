package layout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.bridge.Bridge;
import com.afollestad.bridge.BridgeException;
import com.afollestad.bridge.Request;
import com.afollestad.bridge.Response;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import common.SweetDeviceManager;
import ir.sweetsoft.sweetlibone.Activities.LocalUserInfo;
import ir.sweetsoft.sweetlibone.Activities.Constants;
import ir.sweetsoft.sweetlibone.Activities.MainActivity;
import ir.sweetsoft.sweetlibone.R;

public class LoginFragment extends Fragment {
    private EditText txtusername,txtpassword;
    private TextView lblusername,lblpassword;
    private Button btnLogin;
    private ImageView PageBG;
    private ProgressBar WaitBar;


    private OnFragmentInteractionListener mListener;

    public LoginFragment() {

    }
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnLogin=(Button)getView().findViewById(R.id.btnlogin);
        txtusername=(EditText) getView().findViewById(R.id.txtusername);
        txtpassword=(EditText) getView().findViewById(R.id.txtpassword);
        lblusername=(TextView) getView().findViewById(R.id.lblusername);
        lblpassword=(TextView) getView().findViewById(R.id.lblpassword);
        PageBG=(ImageView) getView().findViewById(R.id.pagebg);
        WaitBar=(ProgressBar)getView().findViewById(R.id.progressbar);
        WaitBar.setVisibility(View.GONE);
        Bitmap bitmap= BitmapFactory.decodeResource(getActivity().getResources(),
                R.drawable.bg3);
        int nh = (int) ( bitmap.getHeight() * (512.0 / bitmap.getWidth()) );
        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
        PageBG.setImageBitmap(scaled);
        Typeface face= Typeface.createFromAsset(getActivity().getAssets(),"fonts/IRANSansMobile.ttf");
        btnLogin.setTypeface(face);
        txtusername.setTypeface(face);
        txtpassword.setTypeface(face);
        lblusername.setTypeface(face);
        lblpassword.setTypeface(face);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WaitBar.setVisibility(View.VISIBLE);
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        CheckLogin(txtusername.getText().toString(),txtpassword.getText().toString());
                    }
                });
            }
        });
        MainActivity mainActivity=(MainActivity) getActivity();
        mainActivity.setNavigationDrawerLockState(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }
    public void CheckLogin(String UserName,String Password)
    {
        try {
            String DeviceID= SweetDeviceManager.getDeviceID(this.getActivity().getApplicationContext());
            String URL=Constants.SIGNINURL+"&username="+ URLEncoder.encode(UserName,"utf-8")+"&password="+URLEncoder.encode(Password,"utf-8")+"&deviceid="+URLEncoder.encode(DeviceID,"utf-8");
            Log.d("URL",URL);
            Request request = Bridge.get(URL).throwIfNotSuccess().retries(5, 6000).request();
            Response response = request.response();

            int Status=-1;
            if (response.isSuccess()) {
                Status=Integer.parseInt(response.asAsonObject().get("status").toString());
            }
            Log.d("DdD","SSSSSSSSSSSSSSSSSSSSSSSS");
            if(Status==404)//NotFound
            {
                Log.d("DD","SSSSSSSSSSSSSSSSSSSSSSSS");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new LovelyStandardDialog(getActivity())
                                .setButtonsColor(Color.parseColor("#FFC01059"))
                                .setTitle("خطا")
                                .setMessage("کاربری با مشخصات وارد شده پیدا نشد.")
                                .setPositiveButton(android.R.string.ok,null)
                                .show();

                        WaitBar.setVisibility(View.GONE);
                    }
                });

            }
            else if(Status>0)
            {
                int Role=Integer.parseInt(response.asAsonObject().get("role").toString());
                LocalUserInfo.SetUserInfo(getActivity(),UserName,Password,"","",Role);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((MainActivity)getActivity()).routeToIndex();
                        WaitBar.setVisibility(View.GONE);
                    }
                });
            }
            else
            {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new LovelyStandardDialog(getActivity())
                                .setButtonsColor(Color.parseColor("#FFC01059"))
                                .setTitle("خطا")
                                .setMessage("خطایی در ارتباط با سرور به وجود آمد.")
                                .setPositiveButton(android.R.string.ok,null)
                                .show();
                        WaitBar.setVisibility(View.GONE);
                    }
                });
            }
        }
        catch (BridgeException ex){
            ex.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
