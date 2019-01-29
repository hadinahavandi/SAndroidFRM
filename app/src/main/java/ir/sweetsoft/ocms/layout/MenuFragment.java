package ir.sweetsoft.ocms.layout;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ir.sweetsoft.ocms.R;
import ir.sweetsoft.sweetlibone.Activities.Constants;
import ir.sweetsoft.sweetlibone.Activities.MainActivity;
import ocms.DoctorFragment;

public class MenuFragment extends Fragment {

    private Button btn_female,btn_male;
    private OnFragmentInteractionListener mListener;

    public MenuFragment() {
    }
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_female=getActivity().findViewById(R.id.btn_female);
        btn_male=getActivity().findViewById(R.id.btn_male);

        Typeface face= Typeface.createFromAsset(getActivity().getAssets(),"fonts/IRANSansMobile.ttf");
        btn_female.setTypeface(face);
        btn_male.setTypeface(face);
        btn_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity theAct=(MainActivity)getActivity();
                theAct.SharedConf_speciality= Constants.SPECIALITYGROUP_BEAUTYSHOP_WOMAN;
                theAct.SharedConf_ItemID= Constants.SPECIALITYGROUP_BEAUTYSHOP_WOMAN;
                theAct.SharedConf_presencetype= Constants.PRESENCETYPE_INOFFICE;
                theAct.showFragment(DoctorFragment.class);
            }
        });
        btn_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity theAct=(MainActivity)getActivity();
                theAct.SharedConf_speciality= Constants.SPECIALITYGROUP_BEAUTYSHOP_MAN;
                theAct.SharedConf_ItemID= Constants.SPECIALITYGROUP_BEAUTYSHOP_MAN;
                theAct.SharedConf_presencetype= Constants.PRESENCETYPE_INOFFICE;
                theAct.showFragment(DoctorFragment.class);
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
