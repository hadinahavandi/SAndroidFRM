package layout;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ir.sweetsoft.sweetlibone.Activities.LocalUserInfo;
import ir.sweetsoft.sweetlibone.Activities.Constants;
import ir.sweetsoft.sweetlibone.Activities.MainActivity;
import ir.sweetsoft.sweetlibone.R;

public class AdminMenuFragment extends Fragment {

    private Button btn_manage_doctors,btn_manage_voices,btn_manage_speciality,btn_manage_transactions;

    private OnFragmentInteractionListener mListener;

    public AdminMenuFragment() {
    }
    public static AdminMenuFragment newInstance(String param1, String param2) {
        AdminMenuFragment fragment = new AdminMenuFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_adminmenu, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_manage_doctors=(Button)getActivity().findViewById(R.id.btn_manage_doctors);
        btn_manage_voices=(Button)getActivity().findViewById(R.id.btn_manage_voices);
        btn_manage_speciality=(Button)getActivity().findViewById(R.id.btn_manage_speciality);
        btn_manage_transactions=(Button)getActivity().findViewById(R.id.btn_manage_transactions);


        Typeface face= Typeface.createFromAsset(getActivity().getAssets(),"fonts/IRANSansMobile.ttf");
        btn_manage_doctors.setTypeface(face);
        btn_manage_voices.setTypeface(face);
        btn_manage_speciality.setTypeface(face);
        btn_manage_transactions.setTypeface(face);


        btn_manage_doctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity theAct=(MainActivity)getActivity();
                String UserName2= theAct.getSharedPreferences(Constants.GENERAL_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.USERNAME,"0");
                String Password2= theAct.getSharedPreferences(Constants.GENERAL_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.PASSWORD,"0");
                String URL2=Constants.MANAGEDOCTORS+"?username="+UserName2+"&password="+Password2;
                theAct.mainWebView.loadUrl(URL2);
                Log.d("URL",URL2);
                theAct.mainWebView.setVisibility(View.VISIBLE);
            }
        });
        btn_manage_voices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity theAct=(MainActivity)getActivity();
                String UserName2= theAct.getSharedPreferences(Constants.GENERAL_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.USERNAME,"0");
                String Password2= theAct.getSharedPreferences(Constants.GENERAL_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.PASSWORD,"0");
                String URL2=Constants.MANAGEVOICES+"?username="+UserName2+"&password="+Password2;
                theAct.mainWebView.loadUrl(URL2);
                Log.d("URL",URL2);
                theAct.mainWebView.setVisibility(View.VISIBLE);
            }
        });
        btn_manage_speciality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity theAct=(MainActivity)getActivity();
                String UserName2= theAct.getSharedPreferences(Constants.GENERAL_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.USERNAME,"0");
                String Password2= theAct.getSharedPreferences(Constants.GENERAL_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.PASSWORD,"0");
                String URL2=Constants.MANAGESPECIALITIES+"?username="+UserName2+"&password="+Password2;
                theAct.mainWebView.loadUrl(URL2);
                Log.d("URL",URL2);
                theAct.mainWebView.setVisibility(View.VISIBLE);
            }
        });
        btn_manage_transactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity theAct=(MainActivity)getActivity();
                String UserName2= theAct.getSharedPreferences(Constants.GENERAL_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.USERNAME,"0");
                String Password2= theAct.getSharedPreferences(Constants.GENERAL_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.PASSWORD,"0");
                String URL2=Constants.MANAGETRANSACTIONS+"?username="+UserName2+"&password="+Password2;
                theAct.mainWebView.loadUrl(URL2);
                Log.d("URL",URL2);
                theAct.mainWebView.setVisibility(View.VISIBLE);
            }
        });
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
        void onFragmentInteraction(Uri uri);
    }
}
