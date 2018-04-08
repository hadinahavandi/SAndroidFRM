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

import fileshop.FileFragment;
import ir.sweetsoft.sweetlibone.Activities.Constants;
import ir.sweetsoft.sweetlibone.Activities.MainActivity;
import ir.sweetsoft.sweetlibone.R;
import ocms.SpecialityFragment;

public class DoctorMenuFragment extends Fragment {

    private Button btn_getreserves,btn_manageplans,btn_getbalance;

    private OnFragmentInteractionListener mListener;

    public DoctorMenuFragment() {
    }
    public static DoctorMenuFragment newInstance(String param1, String param2) {
        DoctorMenuFragment fragment = new DoctorMenuFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_doctormenu, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_getreserves=(Button)getActivity().findViewById(R.id.btn_getreserves);
        btn_manageplans=(Button)getActivity().findViewById(R.id.btn_manageplans);
        btn_getbalance=(Button)getActivity().findViewById(R.id.btn_getbalance);


        Typeface face= Typeface.createFromAsset(getActivity().getAssets(),"fonts/IRANSansMobile.ttf");
        btn_getreserves.setTypeface(face);
        btn_getbalance.setTypeface(face);
        btn_manageplans.setTypeface(face);


        btn_getreserves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity theAct=(MainActivity)getActivity();
                String UserName2= theAct.getSharedPreferences(Constants.GENERAL_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.USERNAME,"0");
                String Password2= theAct.getSharedPreferences(Constants.GENERAL_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.PASSWORD,"0");
                String URL2=Constants.DOCTORRESERVELIST+"?username="+UserName2+"&password="+Password2;
                theAct.mainWebView.loadUrl(URL2);
                Log.d("URL",URL2);
                theAct.mainWebView.setVisibility(View.VISIBLE);
            }
        });
        btn_getbalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity theAct=(MainActivity)getActivity();
                theAct.showFragment(PurchaseFragment.class);
            }
        });
        btn_manageplans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity theAct=(MainActivity)getActivity();
                String UserName2= theAct.getSharedPreferences(Constants.GENERAL_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.USERNAME,"0");
                String Password2= theAct.getSharedPreferences(Constants.GENERAL_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.PASSWORD,"0");
                String URL2=Constants.MANAGEUSERPLANLIST+"?username="+UserName2+"&password="+Password2;
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
