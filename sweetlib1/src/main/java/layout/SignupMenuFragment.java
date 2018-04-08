package layout;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import common.SweetDeviceManager;
import ir.sweetsoft.sweetlibone.Activities.Constants;
import ir.sweetsoft.sweetlibone.Activities.MainActivity;
import ir.sweetsoft.sweetlibone.R;
public class SignupMenuFragment extends Fragment {
    private Button btnSignup;
    private Button btnLogin;
    private ImageView PageBG;

    private OnFragmentInteractionListener mListener;

    public SignupMenuFragment() {
    }
    public static SignupMenuFragment newInstance(String param1, String param2) {
        SignupMenuFragment fragment = new SignupMenuFragment();
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
        return inflater.inflate(R.layout.fragment_signupmenu, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSignup=(Button)getView().findViewById(R.id.btnsignup);
        btnLogin=(Button)getView().findViewById(R.id.btnlogin);
        PageBG=(ImageView) getView().findViewById(R.id.pagebg);
        Bitmap bitmap= BitmapFactory.decodeResource(getActivity().getResources(),
                R.drawable.bg3);
        int nh = (int) ( bitmap.getHeight() * (512.0 / bitmap.getWidth()) );
        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
        PageBG.setImageBitmap(scaled);
        Typeface face= Typeface.createFromAsset(getActivity().getAssets(),"fonts/IRANSansMobile.ttf");
        btnSignup.setTypeface(face);
        btnLogin.setTypeface(face);
        final MainActivity theAct=(MainActivity)getActivity();
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                theAct.showFragment(SignupFragment.class,false);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                theAct.showFragment(LoginFragment.class,false);
            }
        });
        MainActivity mainActivity=(MainActivity) getActivity();
        mainActivity.setNavigationDrawerLockState(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }
    public void CheckLogin(String Mobile)
    {
        URL httpbinEndpoint = null;
        try {
            String DeviceID= SweetDeviceManager.getDeviceID(this.getActivity().getApplicationContext());
            String URL=Constants.SITEURL+"json/fa/onlineclass/userlist.jsp?"+"service=getuserstatus&mobile="+Mobile+"&deviceid="+DeviceID;
            Log.d("URL",URL);
            httpbinEndpoint = new URL(URL);
            HttpURLConnection myConnection = null;
            myConnection = (HttpURLConnection) httpbinEndpoint.openConnection();
            myConnection.setRequestMethod("GET");
            myConnection.setDoOutput(true);
//            Log.d("Message",myConnection.getResponseMessage());
            InputStream responseBody = myConnection.getInputStream();
            InputStreamReader responseBodyReader =
                    new InputStreamReader(responseBody, "UTF-8");
            BufferedReader r = new BufferedReader(new InputStreamReader(responseBody));
            JsonReader reader=new JsonReader(r);
            int Status=0;
            String userid="0";
            if(reader.hasNext()) {
                reader.beginObject(); // Start processing the JSON object
                while (reader.hasNext()) { // Loop through all keys
                    String key = reader.nextName(); // Fetch the next key
                    Log.d("***** key ", key);
                    if (key.equals("status")) {Status=reader.nextInt();}
                    else
                    {userid=reader.nextString();}
                }
                reader.endObject();
            }
            if(Status==3)//Invalid
            {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Builder builder = new Builder(SignupMenuFragment.this.getActivity());
                        builder.setTitle("خطا");
                        builder.setMessage("گوشی دیگری برای این شماره موبایل فعال می باشد.");
                        builder.setPositiveButton("OK", null);
                        builder.show();
                    }
                });

            }
            else
            {
                SharedPreferences sharedpreferences = getActivity().getSharedPreferences(Constants.GENERAL_PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(Constants.MOBILE, Mobile);
                editor.apply();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.d("S","Showing A Page");
                            Class fragmentClass = null;
                            fragmentClass = MenuFragment.class;
                            Fragment fragment = (Fragment) fragmentClass.newInstance();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.MainContent, fragment).commit();

                        } catch (java.lang.InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
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
