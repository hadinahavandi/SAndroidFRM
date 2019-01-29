package ocms;
import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.mojtaba.materialdatetimepicker.utils.PersianCalendar;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.yarolegovich.lovelydialog.LovelyProgressDialog;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;
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
    private int PHOTO_ROW_SIZE=3;
    cn.jzvd.JZVideoPlayerStandard vv;
    private LovelyProgressDialog pDialog;
    ProgressBar WaitBar;
    public DoctorItemFragment() {
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANSansMobile.ttf");

        WaitBar=getActivity().findViewById(R.id.progressbar);
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
        btnReserve.setTypeface(face);
        SweetDisplayScaler scaler=new SweetDisplayScaler(getActivity());
        lbl_Photo_fluContent.getLayoutParams().height=scaler.HeightPercentToPixel(50);
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

    private int getFileType(String FilePath)
    {
        if(FilePath.indexOf('.')<0)
            return 3;
        String Format=FilePath.substring(FilePath.indexOf('.')).trim().toLowerCase();
        if(Format.equals(".jpg") || Format.equals(".png") || Format.equals(".tiff") || Format.equals(".jpeg") || Format.equals(".gif"))
            return 1;//Image
        else if(Format.equals(".mkv") || Format.equals(".mp4") || Format.equals(".3gp") || Format.equals(".avi") || Format.equals(".mpeg") || Format.equals(".mpg"))
            return 2;//video
        else
            return 3;
    }
    private void ReloadData() {
        final SweetDisplayScaler scaler=new SweetDisplayScaler(getActivity());

        vv=getActivity().findViewById(R.id.filevideo);
        vv.getLayoutParams().height=scaler.HeightPercentToPixel(40);

        RelativeLayout MainLayout=getActivity().findViewById(R.id.mainlayout);
        MainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vv!=null && vv.getVisibility()==View.VISIBLE)
                {
                    vv.release();
                    vv.setVisibility(View.GONE);
                }
            }
        });
        RelativeLayout LastLL=null;
        RelativeLayout LastRowLL=null;
        final Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.parseColor("#ff0099cc"))
                .borderWidthDp(3)
                .cornerRadiusDp(20)
                .oval(false)
                .build();
        long LastViewID=-1;
        for(int i=0;i<theDoctorFiles.size();i++)
        {
            Log.d("DoctorFileID",String.valueOf(theDoctorFiles.get(i).getId()));
            RelativeLayout ll=new RelativeLayout(getActivity());
//            ll.setOrientation(LinearLayout.HORIZONTAL);
//            ll.setGravity(Gravity.RIGHT);
            ll.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            ImageView imgFile=new ImageView(getActivity());
            imgFile.setImageResource(R.drawable.server);
            imgFile.setScaleType(ImageView.ScaleType.CENTER_CROP);
            TextView tvTitle=new TextView(getActivity());
            tvTitle.setText(theDoctorFiles.get(i).getDescription());

            Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANSansMobile.ttf");
            tvTitle.setTypeface(face);
            imgFile.setId(View.generateViewId());
            ll.addView(imgFile);

            tvTitle.setVisibility(View.GONE);
            RelativeLayout.LayoutParams imgLP= (RelativeLayout.LayoutParams) imgFile.getLayoutParams();
            imgLP.width=scaler.WidthPercentToPixel(30);
            imgLP.height=scaler.WidthPercentToPixel(30);
            ll.addView(tvTitle);
            RelativeLayout.LayoutParams TitleParams= (RelativeLayout.LayoutParams) tvTitle.getLayoutParams();
            TitleParams.addRule(RelativeLayout.BELOW,imgFile.getId());



            MainLayout.addView(ll);
            RelativeLayout.LayoutParams llp= (RelativeLayout.LayoutParams) ll.getLayoutParams();
            llp.setMargins(scaler.WidthPercentToPixel(1),scaler.WidthPercentToPixel(1),scaler.WidthPercentToPixel(1),scaler.WidthPercentToPixel(1));
            if(LastRowLL==null)
                llp.addRule(RelativeLayout.BELOW,lbl_PriceContent.getId());
            else
                llp.addRule(RelativeLayout.BELOW,LastRowLL.getId());

            if(i%PHOTO_ROW_SIZE==0)
                llp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,1);
            else
            {
                llp.addRule(RelativeLayout.LEFT_OF,LastLL.getId());

            }
            if((i+1)%PHOTO_ROW_SIZE==0){
                llp.addRule(RelativeLayout.ALIGN_PARENT_LEFT,1);
                LastRowLL=ll;
            }

//            llp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,1);
            ll.setId(View.generateViewId());
            final String FileURL=theDoctorFiles.get(i).getFile_flu();
            if(getFileType(FileURL)==1)
                Picasso.with(getActivity()).load(Constants.SITEURL+"/"+FileURL).fit().transform(transformation).into(imgFile);
            else if(getFileType(FileURL)==2)
                imgFile.setImageResource(R.drawable.defaultvideo);
            imgFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try
                    {
                    if(getFileType(FileURL)==1) {//Image

//                        fileImage.setCornerRadius(3,3,3,3);
                        if(FileURL.trim().length()>3) {
                            Log.d("Image","Displaying Image File");
                            final ImageView fileImage = getActivity().findViewById(R.id.fileimage);
//                            fileImage.setVisibility(View.VISIBLE);
                            Picasso.with(getActivity()).load(Constants.SITEURL + "/" + FileURL).into(fileImage);
                            fileImage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    fileImage.setImageResource(R.drawable.empty);
                                }
                            });
//                            fileImage.bringToFront();

                            vv.setVisibility(View.GONE);
                        }
                        else
                        {

                            Log.d("Image","Not Displaying Image File Width Size:"+FileURL.trim().length());
                        }
                    }
                    else if(getFileType(FileURL)==2) {//Video
                        String url = Constants.SITEURL + "/" + FileURL;
                        new DownloadFileFromURL().execute(url);

//                        final cn.jzvd.JZVideoPlayerStandard vvv=vv;
//                        vv.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                vvv.setVisibility(View.GONE);
//                            }
//                        });
                        final ImageView fileImage = getActivity().findViewById(R.id.fileimage);
                        fileImage.setImageDrawable(null);
                    }

                    }catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            });
            ll.setId(View.generateViewId());
            LastLL=ll;
        }
        if(LastLL!=null)
            ((RelativeLayout.LayoutParams)(btnReserve.getLayoutParams())).addRule(RelativeLayout.BELOW,LastLL.getId());
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

        if(String.valueOf(theDoctor.getPhoto_flu()).trim().length()>3)
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
    /**
     * Background Async Task to download file
     * */
    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            WaitBar.setVisibility(View.VISIBLE);
            WaitBar.bringToFront();
//            showDialog(progress_bar_type);
        }

        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();

                // this will be useful so that you can show a tipical 0-100%
                // progress bar
                int lenghtOfFile = conection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(),
                        8192);

                // Output stream
                OutputStream output = new FileOutputStream(Environment
                        .getExternalStorageDirectory().toString()
                        + "/.ocmsvid.mp4");
                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        /**
         * Updating progress bar
         * */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
//            pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {

            WaitBar.setVisibility(View.GONE);
            vv.setUp(Environment
                            .getExternalStorageDirectory().toString()
                            + "/.ocmsvid.mp4",
                    JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,
                    "");
            vv.bringToFront();
            vv.setVisibility(View.VISIBLE);
            // dismiss the dialog after the file was downloaded
//            dismissDialog(progress_bar_type);

        }

    }
}

