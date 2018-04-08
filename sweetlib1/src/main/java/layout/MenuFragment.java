package layout;

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
import android.widget.ImageView;
import android.widget.TextView;

import common.SweetDisplayScaler;
import fileshop.FileFragment;
import ir.sweetsoft.sweetlibone.Activities.LocalUserInfo;
import ir.sweetsoft.sweetlibone.Activities.Constants;
import ir.sweetsoft.sweetlibone.Activities.MainActivity;
import ir.sweetsoft.sweetlibone.R;

import ocms.SpecialityFragment;

public class MenuFragment extends Fragment {

    private ImageView btn_reserve_doctor,btn_reserve_beautyshop,btn_reserve_voicestory,btn_reserve_psychology,btn_reserve_dentist;

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
        btn_reserve_doctor=getActivity().findViewById(R.id.btn_reserve_doctor);
        btn_reserve_dentist=getActivity().findViewById(R.id.btn_reserve_dentist);
        btn_reserve_psychology=getActivity().findViewById(R.id.btn_reserve_psychology);
        btn_reserve_beautyshop=getActivity().findViewById(R.id.btn_reserve_beautyshop);
        btn_reserve_voicestory=getActivity().findViewById(R.id.btn_reserve_voicestory);

        SweetDisplayScaler scaler=new SweetDisplayScaler(this.getActivity());
        int height=(int)scaler.getScreenWidth()/2-5;

        btn_reserve_doctor.getLayoutParams().height=height;
        btn_reserve_dentist.getLayoutParams().height=height;
        btn_reserve_psychology.getLayoutParams().height=height;
        btn_reserve_beautyshop.getLayoutParams().height=height;
        btn_reserve_voicestory.getLayoutParams().height=height;
//        Typeface face= Typeface.createFromAsset(getActivity().getAssets(),"fonts/IRANSansMobile.ttf");
//        btn_reserve_doctor.setTypeface(face);
//        btn_reserve_beautyshop.setTypeface(face);
//        btn_reserve_voicestory.setTypeface(face);
//        btn_reserve_dentist.setTypeface(face);
//        btn_reserve_psychology.setTypeface(face);


        btn_reserve_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity theAct=(MainActivity)getActivity();
                theAct.SharedConf_specialityGroup= Constants.SPECIALITYGROUP_DOCTOR;
                theAct.showFragment(SelectPresenceTypeFragment.class);
            }
        });
        btn_reserve_beautyshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity theAct=(MainActivity)getActivity();
                theAct.SharedConf_specialityGroup= Constants.SPECIALITYGROUP_BEAUTYSHOP;
                theAct.SharedConf_presencetype= Constants.PRESENCETYPE_INOFFICE;
                theAct.showFragment(SpecialityFragment.class);
            }
        });
        btn_reserve_dentist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity theAct=(MainActivity)getActivity();
                theAct.SharedConf_specialityGroup= Constants.SPECIALITYGROUP_DENTIST;
                theAct.showFragment(SelectPresenceTypeFragment.class);
            }
        });
        btn_reserve_psychology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity theAct=(MainActivity)getActivity();
                theAct.SharedConf_specialityGroup= Constants.SPECIALITYGROUP_PSYCHOLOGY;
                theAct.showFragment(SelectPresenceTypeFragment.class);
            }
        });
        btn_reserve_voicestory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity theAct=(MainActivity)getActivity();
                theAct.showFragment(FileFragment.class);
            }
        });
        btn_reserve_voicestory.setVisibility(View.GONE);
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
