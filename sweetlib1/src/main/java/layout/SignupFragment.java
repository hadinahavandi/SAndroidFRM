package layout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import common.Message;
import common.SweetDeviceManager;
import ir.sweetsoft.sweetlibone.Activities.LocalUserInfo;
import ir.sweetsoft.sweetlibone.Activities.Constants;
import ir.sweetsoft.sweetlibone.Activities.MainActivity;
import ir.sweetsoft.sweetlibone.R;

public class SignupFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    private EditText txtmobile, txtusername, txtpassword, txtpassword2,txtName;
    private TextView lblmobile, lblusername, lblpassword, lblpassword2,lblName;
    private Button btnSignUp;
    private ImageView PageBG;
    private ProgressBar WaitBar;

    public SignupFragment() {
    }

    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity mainActivity=(MainActivity) getActivity();
        mainActivity.setNavigationDrawerLockState(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSignUp = (Button) getView().findViewById(R.id.btnsignup);
        txtmobile = (EditText) getView().findViewById(R.id.txtmobile);
        txtusername = (EditText) getView().findViewById(R.id.txtusername);
        txtpassword = (EditText) getView().findViewById(R.id.txtpassword);
        txtName = (EditText) getView().findViewById(R.id.txtname);
        txtpassword2 = (EditText) getView().findViewById(R.id.txtpassword2);
        lblmobile = (TextView) getView().findViewById(R.id.lblmobile);
        lblName = (TextView) getView().findViewById(R.id.lblname);
        lblusername = (TextView) getView().findViewById(R.id.lblusername);
        lblpassword = (TextView) getView().findViewById(R.id.lblpassword);
        lblpassword2 = (TextView) getView().findViewById(R.id.lblpassword2);
        PageBG = (ImageView) getView().findViewById(R.id.pagebg);
        WaitBar = (ProgressBar) getView().findViewById(R.id.progressbar);
        WaitBar.setVisibility(View.GONE);
        Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(),
                R.drawable.bg3);
        int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
        PageBG.setImageBitmap(scaled);
        Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANSansMobile.ttf");
        btnSignUp.setTypeface(face);
        txtmobile.setTypeface(face);
        txtusername.setTypeface(face);
        txtpassword.setTypeface(face);
        txtpassword2.setTypeface(face);
        lblmobile.setTypeface(face);
        lblusername.setTypeface(face);
        lblpassword.setTypeface(face);
        lblName.setTypeface(face);
        txtName.setTypeface(face);
        lblpassword2.setTypeface(face);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String ErrorMessage = null;
                    if (txtName.getText().toString().length() < 4)
                        ErrorMessage = "لطفا نام و نام خانوادگی خود را به طور کامل وارد نمایید";
                    else if (txtmobile.getText().toString().length() < 10)
                        ErrorMessage = "لطفا شماره موبایل صحیح را وارد نمایید";
                    else if (txtusername.getText().toString().length() < 8)
                        ErrorMessage = "طول نام کاربری باید بیشتر از 8 حرف باشد";
                    else if (txtpassword.getText().toString().length() < 8)
                        ErrorMessage = "طول کلمه عبور باید بیشتر از 8 حرف باشد";
                    else if (!txtpassword.getText().toString().equals(txtpassword2.getText().toString()))
                        ErrorMessage = "کلمه عبور وارد شده با تکرار آن یکسان نیست";
                    if (ErrorMessage != null) {
                        new LovelyStandardDialog(getActivity())
                                .setButtonsColor(Color.parseColor("#FFC01059"))
                                .setTitle("خطا")
                                .setMessage(ErrorMessage)
                                .setPositiveButton(android.R.string.ok, null)
                                .show();
                    } else {

                        WaitBar.setVisibility(View.VISIBLE);
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                final Message message = Signup();
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        WaitBar.setVisibility(View.GONE);
                                        if (message.getMessageType() != 3) {
                                            new LovelyStandardDialog(getActivity())
                                                    .setButtonsColor(Color.parseColor("#FFC01059"))
                                                    .setTitle("خطا")
                                                    .setMessage(message.getMessageText())
                                                    .setPositiveButton(android.R.string.ok, null)
                                                    .show();
                                        } else {
                                            LocalUserInfo.SetUserInfo(getActivity(),txtusername.getText().toString(),txtpassword.getText().toString(),txtmobile.getText().toString(),txtName.getText().toString(),0);
                                            SharedPreferences sharedpreferences = getActivity().getSharedPreferences(Constants.GENERAL_PREFERENCES, Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedpreferences.edit();
                                            editor.putString(Constants.MOBILE, txtmobile.getText().toString());
                                            editor.putString(Constants.USERNAME, txtusername.getText().toString());
                                            editor.putString(Constants.PASSWORD, txtpassword.getText().toString());
                                            editor.putString(Constants.NAME, txtName.getText().toString());
                                            editor.apply();
                                            new LovelyStandardDialog(getActivity())
                                                    .setButtonsColor(Color.parseColor("#FFC01059"))
                                                    .setTitle("ثبت نام موفق")
                                                    .setMessage(message.getMessageText())
                                                    .setPositiveButton(android.R.string.ok, new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            MainActivity mact = (MainActivity) getActivity();
                                                            mact.SharedConf_showAllCourses = false;
                                                            mact.routeToIndex();
                                                        }
                                                    })
                                                    .show();
                                        }
                                    }
                                });
                            }
                        });
                    }

                } catch (Exception ex) {
                    WaitBar.setVisibility(View.GONE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupFragment.this.getActivity());
                    builder.setMessage("خطایی در برقراری ارتباط با سرور به وجود آمد ، لطفا از اتصال گوشی به اینترنت اطمینان حاصل کنید.");
                    builder.setTitle("خطا");
                    builder.setPositiveButton("OK", null);
                    builder.show();
                }
            }


        });
        MainActivity mainActivity=(MainActivity) getActivity();
        mainActivity.setNavigationDrawerLockState(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public Message Signup() {
        try {
            String DeviceID = SweetDeviceManager.getDeviceID(this.getActivity().getApplicationContext());
            String PageURL = Constants.SIGNUPURL;
            String Data = "action=btnSave_Click";
            Data += "&username=" + String.valueOf(txtusername.getText());
            Data += "&password=" + String.valueOf(txtpassword.getText());
            Data += "&fullname=" + String.valueOf(txtName.getText());
            Data += "&mobile=" + String.valueOf(txtmobile.getText());
            Data += "&devicecode=" + String.valueOf(DeviceID);
            Log.d("Data",Data);
            JsonReader reader = getReader(PageURL, true, Data);
            Log.d("dd",reader.toString());
            reader.beginObject();
            Message theMessage = new Message();
            while (reader.hasNext()) {
                String key = reader.nextName();
                if (key.equals("message")) {
                    theMessage.setMessageText(reader.nextString());
                } else if (key.equals("messagetype")) {
                    theMessage.setMessageType(reader.nextInt());
                }
            }
            reader.endObject();
            return theMessage;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JsonReader getReader(String PageURL, boolean isPostMethod, String Data) throws IOException {
        URL httpbinEndpoint = null;
        try {
            httpbinEndpoint = new URL(PageURL);
            HttpURLConnection myConnection = null;
            myConnection = (HttpURLConnection) httpbinEndpoint.openConnection();
            if (!isPostMethod)
                myConnection.setRequestMethod("GET");
            else
                myConnection.setRequestMethod("POST");

            myConnection.setDoOutput(true);
            if (Data != null)
                myConnection.getOutputStream().write(Data.getBytes());
            InputStream responseBody = myConnection.getInputStream();
            BufferedReader r = new BufferedReader(new InputStreamReader(responseBody));
//            StringBuilder total = new StringBuilder();
//            String line;
//            while ((line = r.readLine()) != null) {
//                total.append(line).append('\n');
//            }
//            Log.d("RRR",total.toString());
            JsonReader reader = new JsonReader(r);
            return reader;
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
