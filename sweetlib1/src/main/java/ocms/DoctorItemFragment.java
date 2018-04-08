package ocms;
import android.content.Intent;
import android.os.AsyncTask;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.mojtaba.materialdatetimepicker.utils.PersianCalendar;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import common.SweetDisplayScaler;
import ir.blue_saffron.persianmaterialdatetimepicker.date.DatePickerDialog;
import ir.sweetsoft.sweetlibone.Activities.Constants;
import ir.sweetsoft.sweetlibone.Activities.MainActivity;
import ir.sweetsoft.sweetlibone.R;

public class DoctorItemFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private Doctor theDoctor;
    private List<Doctorfile> theDoctorFiles;
    private TextView lbl_NameContent;
    private TextView lbl_FamilyContent;
    private TextView lbl_Nezam_codeContent;
    private TextView lbl_Nezam_codeCaption;
    private TextView lbl_PriceContent;
    private TextView lbl_PriceCaption;
    private TextView lbl_EmailContent;
    private TextView lbl_EmailCaption;
    private TextView lbl_TelContent;
    private TextView lbl_TelCaption;
    private TextView lbl_Speciality_fidCaption;
    private TextView lbl_Speciality_fidContent;
    private TextView lbl_EducationContent;
    private TextView lbl_EducationCaption;
    private TextView lbl_MatabtelContent;
    private TextView lbl_MatabtelCaption;
    private TextView lbl_MatabaddressContent;
    private TextView lbl_MatabaddressCaption;
    private ImageView lbl_Photo_fluContent;
    private Button btnReserve;

    public DoctorItemFragment() {
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANSansMobile.ttf");
        btnReserve=getActivity().findViewById(R.id.btn_reserve);
        lbl_NameContent =  getActivity().findViewById(R.id.lbl_name_content);
        lbl_FamilyContent =  getActivity().findViewById(R.id.lbl_family_content);
        lbl_Nezam_codeContent =  getActivity().findViewById(R.id.lbl_nezam_code_content);
        lbl_Nezam_codeCaption =  getActivity().findViewById(R.id.lbl_nezam_code_caption);
        lbl_PriceCaption =  getActivity().findViewById(R.id.lbl_price_caption);
        lbl_PriceContent =  getActivity().findViewById(R.id.lbl_price_content);
        lbl_EmailContent =  getActivity().findViewById(R.id.lbl_email_content);
        lbl_EmailCaption =  getActivity().findViewById(R.id.lbl_email_caption);
        lbl_TelContent =  getActivity().findViewById(R.id.lbl_tel_content);
        lbl_TelCaption =  getActivity().findViewById(R.id.lbl_tel_caption);
        lbl_Speciality_fidContent =  getActivity().findViewById(R.id.lbl_speciality_fid_content);
        lbl_EducationContent =  getActivity().findViewById(R.id.lbl_education_content);
        lbl_EducationCaption =  getActivity().findViewById(R.id.lbl_education_caption);
        lbl_MatabtelContent =  getActivity().findViewById(R.id.lbl_matabtel_content);
        lbl_MatabtelCaption =  getActivity().findViewById(R.id.lbl_matabtel_caption);
        lbl_MatabaddressContent =  getActivity().findViewById(R.id.lbl_matabaddress_content);
        lbl_MatabaddressCaption =  getActivity().findViewById(R.id.lbl_matabaddress_caption);
        lbl_Photo_fluContent = getActivity().findViewById(R.id.lbl_photo_flu_content);
        lbl_Speciality_fidCaption=getActivity().findViewById(R.id.lbl_speciality_fid_caption);
        lbl_Speciality_fidCaption.setTypeface(face);
        lbl_NameContent.setTypeface(face);
        lbl_FamilyContent.setTypeface(face);
        lbl_Nezam_codeContent.setTypeface(face);
        lbl_Nezam_codeCaption.setTypeface(face);
        lbl_PriceCaption.setTypeface(face);
        lbl_PriceContent.setTypeface(face);
        lbl_EmailContent.setTypeface(face);
        lbl_EmailCaption.setTypeface(face);
        lbl_TelContent.setTypeface(face);
        lbl_TelCaption.setTypeface(face);
        lbl_Speciality_fidContent.setTypeface(face);
        lbl_EducationContent.setTypeface(face);
        lbl_EducationCaption.setTypeface(face);
        lbl_MatabtelContent.setTypeface(face);
        lbl_MatabtelCaption.setTypeface(face);
        lbl_MatabaddressContent.setTypeface(face);
        lbl_MatabaddressCaption.setTypeface(face);
//        lbl_Photo_fluContent.setTypeface(face);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                theDoctor = new Doctor(getActivity()).getOne(((MainActivity) getActivity()).SharedConf_ItemID);
                theDoctorFiles=new ArrayList<Doctorfile>();
                new Doctorfile(getActivity()).getAll(theDoctorFiles,theDoctor.getId());
                Log.d("DoctorFileCount",String.valueOf(theDoctorFiles.size()));
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ReloadData();
                    }
                });
            }
        });
    }

    private void ReloadData() {
        SweetDisplayScaler scaler=new SweetDisplayScaler(getActivity());
        LinearLayout LastLL=null;
        for(int i=0;i<theDoctorFiles.size();i++)
        {
            Log.d("DoctorFileID",String.valueOf(theDoctorFiles.get(i).getId()));
            LinearLayout ll=new LinearLayout(getActivity());
            ll.setOrientation(LinearLayout.HORIZONTAL);
            ll.setGravity(Gravity.RIGHT);
            ll.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            ImageView imgFile=new ImageView(getActivity());
            imgFile.setImageResource(R.drawable.server);
            TextView tvTitle=new TextView(getActivity());
            tvTitle.setText(theDoctorFiles.get(i).getDescription());

            Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANSansMobile.ttf");
            tvTitle.setTypeface(face);
            ll.addView(imgFile);
            LinearLayout.LayoutParams imgLP= (LinearLayout.LayoutParams) imgFile.getLayoutParams();
            imgLP.width=scaler.WidthPercentToPixel(10);
            imgLP.height=scaler.WidthPercentToPixel(10);
            ll.addView(tvTitle);
            RelativeLayout MainLayout=getActivity().findViewById(R.id.mainlayout);
            MainLayout.addView(ll);
            RelativeLayout.LayoutParams llp= (RelativeLayout.LayoutParams) ll.getLayoutParams();
            if(LastLL==null)
                llp.addRule(RelativeLayout.BELOW,lbl_PriceContent.getId());
            else
                llp.addRule(RelativeLayout.BELOW,LastLL.getId());
            llp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,1);
            ll.setId(View.generateViewId());
            ((RelativeLayout.LayoutParams)(btnReserve.getLayoutParams())).addRule(RelativeLayout.BELOW,ll.getId());
            final String FileURL=theDoctorFiles.get(i).getFile_flu();
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try
                    {
                    String Format=FileURL.substring(FileURL.indexOf('.')).trim().toLowerCase();
                    if(Format.equals(".jpg") || Format.equals(".png") || Format.equals(".tiff") || Format.equals(".jpeg") || Format.equals(".gif")) {
                        final ImageView fileImage = getActivity().findViewById(R.id.fileimage);
                        Picasso.with(getActivity()).load(Constants.SITEURL + "/" + FileURL).into(fileImage);
                        fileImage.setVisibility(View.VISIBLE);
                        fileImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                fileImage.setVisibility(View.GONE);
                            }
                        });

                        VideoView vv=getActivity().findViewById(R.id.filevideo);
                        vv.setVisibility(View.GONE);
                    }
                    else if(Format.equals(".mkv") || Format.equals(".mp4") || Format.equals(".3gp") || Format.equals(".avi") || Format.equals(".mpeg") || Format.equals(".mpg")) {
                        VideoView vv=getActivity().findViewById(R.id.filevideo);
                        vv.setVideoPath(Constants.SITEURL + "/" + FileURL);
                        vv.start();
                        vv.setVisibility(View.VISIBLE);
                        final VideoView vvv=vv;
                        vv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                vvv.setVisibility(View.GONE);
                            }
                        });
                        final ImageView fileImage = getActivity().findViewById(R.id.fileimage);
                        fileImage.setVisibility(View.GONE);
                    }

                    }catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            });
            LastLL=ll;
        }
        lbl_NameContent.setText(theDoctor.getName());
        lbl_FamilyContent.setText(theDoctor.getFamily());
        lbl_Nezam_codeContent.setText(theDoctor.getNezam_code());
        lbl_EmailContent.setText(theDoctor.getEmail());
        lbl_TelContent.setText(theDoctor.getTel());
        lbl_Speciality_fidContent.setText(theDoctor.getSpeciality());
        lbl_EducationContent.setText(theDoctor.getEducation());
        lbl_MatabtelContent.setText(theDoctor.getMatabtel());
        lbl_MatabaddressContent.setText(theDoctor.getMatabaddress());
        lbl_PriceContent.setText(theDoctor.getPrice()+" ریال");
        Picasso.with(getActivity().getApplicationContext()).load(Constants.SITEURL+String.valueOf(theDoctor.getPhoto_flu())).into(lbl_Photo_fluContent);
        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersianCalendar now = new PersianCalendar();
                Log.d("Date Is:",String.valueOf(now.getPersianYear()));
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                                String date = "You picked the following date: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
                                MainActivity act=(MainActivity)getActivity();
                                act.SharedConf_year =year;
                                act.SharedConf_month =monthOfYear+1;
                                act.SharedConf_day =dayOfMonth;
                                act.SharedConf_doctorid =act.SharedConf_ItemID;
                                act.showFragment(DoctorplanFragment.class);
                                Log.d("Date Is:",date);
                            }
                        },now.getPersianYear(),
                        now.getPersianMonth(),
                        now.getPersianDay()
                );
                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_doctor_item, container, false);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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