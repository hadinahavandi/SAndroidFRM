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
import android.widget.TextView;

import ir.sweetsoft.sweetlibone.Activities.LocalUserInfo;
import ir.sweetsoft.sweetlibone.Activities.Constants;
import ir.sweetsoft.sweetlibone.Activities.MainActivity;
import ir.sweetsoft.sweetlibone.R;


public class AboutDeveloperFragment extends Fragment {
    private TextView lbl_title,lbl_address,lbl_tel,lbl_email;

    private OnFragmentInteractionListener mListener;

    public AboutDeveloperFragment() {
    }
    public static AboutDeveloperFragment newInstance(String param1, String param2) {
        AboutDeveloperFragment fragment = new AboutDeveloperFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about_developer, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lbl_title=(TextView)getActivity().findViewById(R.id.lbl_title);
        lbl_address=(TextView)getActivity().findViewById(R.id.lbl_address);
        lbl_tel=(TextView)getActivity().findViewById(R.id.lbl_tel);
        lbl_email=(TextView)getActivity().findViewById(R.id.lbl_email);


        Typeface face= Typeface.createFromAsset(getActivity().getAssets(),"fonts/IRANSansMobile.ttf");
        lbl_title.setTypeface(face);
        lbl_address.setTypeface(face);
        lbl_tel.setTypeface(face);
        lbl_email.setTypeface(face);
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
