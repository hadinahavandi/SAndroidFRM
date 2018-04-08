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
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import common.SweetDisplayScaler;
import ir.sweetsoft.sweetlibone.Activities.Constants;
import ir.sweetsoft.sweetlibone.Activities.MainActivity;
import ir.sweetsoft.sweetlibone.R;
import ocms.SpecialityFragment;

public class SelectPresenceTypeFragment extends Fragment {

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView btn_presencetype_bytel,btn_presencetype_inhome,btn_presencetype_inoffice;

    private OnFragmentInteractionListener mListener;

    public SelectPresenceTypeFragment() {
    }
    public static SelectPresenceTypeFragment newInstance(String param1, String param2) {
        SelectPresenceTypeFragment fragment = new SelectPresenceTypeFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_selectpresencetype, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final MainActivity theAct=(MainActivity)getActivity();
        if(theAct.SharedConf_specialityGroup==Constants.SPECIALITYGROUP_BEAUTYSHOP)
        {
            theAct.SharedConf_presencetype= Constants.PRESENCETYPE_INOFFICE;
            theAct.showFragment(SpecialityFragment.class);
        }
        btn_presencetype_bytel=getActivity().findViewById(R.id.btn_presencetype_bytel);
        btn_presencetype_inhome=getActivity().findViewById(R.id.btn_presencetype_inhome);
        btn_presencetype_inoffice=getActivity().findViewById(R.id.btn_presencetype_inoffice);

        SweetDisplayScaler scaler=new SweetDisplayScaler(theAct);
        int Height=scaler.WidthPercentToPixel(40);

        btn_presencetype_inhome.getLayoutParams().width=Height;
        btn_presencetype_inhome.getLayoutParams().height=Height;

        btn_presencetype_bytel.getLayoutParams().width=Height;
        btn_presencetype_bytel.getLayoutParams().height=Height;

        btn_presencetype_inoffice.getLayoutParams().width=Height;
        btn_presencetype_inoffice.getLayoutParams().height=Height;
//        Typeface face= Typeface.createFromAsset(getActivity().getAssets(),"fonts/IRANSansMobile.ttf");
//        btn_presencetype_bytel.setTypeface(face);
//        btn_presencetype_inhome.setTypeface(face);
//        btn_presencetype_inoffice.setTypeface(face);
        final View vv=view;
        final WebView fwebview=theAct.mainWebView;
        btn_presencetype_bytel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity theAct=(MainActivity)getActivity();
                theAct.SharedConf_presencetype= Constants.PRESENCETYPE_BYTEL;
                theAct.showFragment(SpecialityFragment.class);
//                MainActivity theActivity=(MainActivity) getActivity();
//                String URL=Constants.SPECIALITYLIST_URL+"?presencetypeid="+theActivity.SharedConf_presencetype+"&motherspecialityid="+theActivity.SharedConf_specialityGroup;
//                fwebview.loadUrl(URL);
//                Log.d("URL",URL);
//                theActivity.hideLastFragment();
//                ScrollView pt=(ScrollView)theActivity.findViewById(R.id.ptscroll);
//                pt.setVisibility(View.GONE);
//                fwebview.setVisibility(View.VISIBLE);

            }
        });
        btn_presencetype_inhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity theAct=(MainActivity)getActivity();
                theAct.SharedConf_presencetype= Constants.PRESENCETYPE_INHOME;
                theAct.showFragment(SpecialityFragment.class);
            }
        });
        btn_presencetype_inoffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity theAct=(MainActivity)getActivity();
                theAct.SharedConf_presencetype= Constants.PRESENCETYPE_INOFFICE;
                theAct.showFragment(SpecialityFragment.class);
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
}
