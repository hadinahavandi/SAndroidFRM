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

import ir.sweetsoft.sweetlibone.R;

public class SelectSpecialityGroupFragment extends Fragment {
    private Button btn_speciality_medical,btn_speciality_dentist,btn_speciality_psychology;

    private OnFragmentInteractionListener mListener;

    public SelectSpecialityGroupFragment() {
    }
    public static SelectSpecialityGroupFragment newInstance(String param1, String param2) {
        SelectSpecialityGroupFragment fragment = new SelectSpecialityGroupFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_selectspecialitygroup, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_speciality_medical=(Button)getActivity().findViewById(R.id.btn_speciality_medical);
        btn_speciality_dentist=(Button)getActivity().findViewById(R.id.btn_speciality_dentist);
        btn_speciality_psychology=(Button)getActivity().findViewById(R.id.btn_speciality_psychology);


        Typeface face= Typeface.createFromAsset(getActivity().getAssets(),"fonts/IRANSansMobile.ttf");
        btn_speciality_medical.setTypeface(face);
        btn_speciality_dentist.setTypeface(face);
        btn_speciality_psychology.setTypeface(face);


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
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
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
