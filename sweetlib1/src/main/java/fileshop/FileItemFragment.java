package fileshop;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.io.IOException;

import common.Message;
import common.SweetDisplayScaler;
import ir.sweetsoft.sweetlibone.Activities.Constants;
import ir.sweetsoft.sweetlibone.Activities.MainActivity;
import ir.sweetsoft.sweetlibone.R;
import layout.MenuFragment;
import layout.PurchaseFragment;

public class FileItemFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private File theFile;
    //	private TextView lbl_File_fluContent;
//	private TextView lbl_File_fluCaption;
    private TextView lbl_TitleContent;
    private TextView lbl_TitleCaption;
    private ImageView lbl_Thumbnail_fluContent;
    private TextView lbl_Add_dateContent;
    private TextView lbl_Add_dateCaption;
    private TextView lbl_DescriptionContent;
    private TextView lbl_DescriptionCaption;
    private TextView lbl_PriceContent;
    private TextView lbl_PriceCaption;
    private TextView lbl_FilecountContent;
    private TextView lbl_FilecountCaption;
    private TextView lbl_Role_systemuser_fidContent;
    private TextView lbl_Role_systemuser_fidCaption;
    MainActivity theActivity;
    private ImageView buyButton;
    private ImageView playButton;
    private ImageView fastforwardButton;
    private ImageView backwardButton;
    private ImageView pauseButton;
    MediaPlayer mediaPlayer;

    public FileItemFragment() {
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANSansMobile.ttf");
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                theActivity = ((MainActivity) getActivity());
                final String UserName = theActivity.getSharedPreferences(Constants.GENERAL_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.USERNAME, "0");
                final String Password = theActivity.getSharedPreferences(Constants.GENERAL_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.PASSWORD, "0");
                theFile = new File(getActivity()).getOne(theActivity.SharedConf_ItemID, UserName, Password);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ReloadData();
                    }
                });
            }
        });
        mediaPlayer = new MediaPlayer();
        lbl_TitleContent = (TextView) getActivity().findViewById(R.id.lbl_title_content);
        lbl_TitleCaption = (TextView) getActivity().findViewById(R.id.lbl_title_caption);
        lbl_Thumbnail_fluContent = (ImageView) getActivity().findViewById(R.id.lbl_thumbnail_flu_content);
        lbl_Add_dateContent = (TextView) getActivity().findViewById(R.id.lbl_add_date_content);
        lbl_Add_dateCaption = (TextView) getActivity().findViewById(R.id.lbl_add_date_caption);
        lbl_DescriptionContent = (TextView) getActivity().findViewById(R.id.lbl_description_content);
        lbl_DescriptionCaption = (TextView) getActivity().findViewById(R.id.lbl_description_caption);
        lbl_PriceContent = (TextView) getActivity().findViewById(R.id.lbl_price_content);
        lbl_PriceCaption = (TextView) getActivity().findViewById(R.id.lbl_price_caption);
        lbl_FilecountContent = (TextView) getActivity().findViewById(R.id.lbl_filecount_content);
        lbl_FilecountCaption = (TextView) getActivity().findViewById(R.id.lbl_filecount_caption);
        lbl_Role_systemuser_fidContent = (TextView) getActivity().findViewById(R.id.lbl_role_systemuser_fid_content);
        lbl_Role_systemuser_fidCaption = (TextView) getActivity().findViewById(R.id.lbl_role_systemuser_fid_caption);
        buyButton = (ImageView) getActivity().findViewById(R.id.buybutton);
        playButton = (ImageView) getActivity().findViewById(R.id.playbutton);
        pauseButton = (ImageView) getActivity().findViewById(R.id.pausebutton);
        fastforwardButton = (ImageView) getActivity().findViewById(R.id.fastforwardbutton);
        backwardButton = (ImageView) getActivity().findViewById(R.id.backwardbutton);
        lbl_TitleContent.setTypeface(face);
        lbl_TitleCaption.setTypeface(face);
        lbl_Add_dateContent.setTypeface(face);
        lbl_Add_dateCaption.setTypeface(face);
        lbl_DescriptionContent.setTypeface(face);
        lbl_DescriptionCaption.setTypeface(face);
        lbl_PriceContent.setTypeface(face);
        lbl_PriceCaption.setTypeface(face);
        lbl_FilecountContent.setTypeface(face);
        lbl_FilecountCaption.setTypeface(face);
        lbl_Role_systemuser_fidContent.setTypeface(face);
        SweetDisplayScaler scaler = new SweetDisplayScaler(getActivity());
        int width = scaler.WidthPercentToPixel(15);
        buyButton.getLayoutParams().width = width;
        buyButton.getLayoutParams().height = width;
        playButton.getLayoutParams().width = width;
        playButton.getLayoutParams().height = width;
        pauseButton.getLayoutParams().width = width;
        pauseButton.getLayoutParams().height = width;
        fastforwardButton.getLayoutParams().width = width;
        fastforwardButton.getLayoutParams().height = width;
        backwardButton.getLayoutParams().width = width;
        backwardButton.getLayoutParams().height = width;
        lbl_Role_systemuser_fidCaption.setTypeface(face);
    }

    private void ReloadData() {
//	lbl_File_fluContent.setText(theFile.getFile_flu());
        lbl_TitleContent.setText(theFile.getTitle());
        SweetDisplayScaler scaler = new SweetDisplayScaler(getActivity());
        Picasso.with(getContext()).load(Constants.SITEURL + theFile.getThumbnail_flu()).resize(scaler.WidthPercentToPixel(50), scaler.WidthPercentToPixel(50)).into(lbl_Thumbnail_fluContent);
        lbl_Add_dateContent.setText(theFile.getAdd_date());
        lbl_DescriptionContent.setText(theFile.getDescription());
        lbl_PriceContent.setText(theFile.getPrice() + " ریال");
        lbl_FilecountContent.setText(theFile.getFilecount());
        lbl_Role_systemuser_fidContent.setText(theFile.getRole_systemuser_fid());
        if (theFile.isPurchased()) {
            playButton.setVisibility(View.VISIBLE);
            backwardButton.setVisibility(View.VISIBLE);
            fastforwardButton.setVisibility(View.VISIBLE);
            playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    theActivity = ((MainActivity) getActivity());
                    playButton.setVisibility(View.GONE);
                    pauseButton.setVisibility(View.VISIBLE);
                    try {
                        if(mediaPlayer.isPlaying())
                            mediaPlayer.pause();
                        else if(mediaPlayer.getCurrentPosition()>0)
                            mediaPlayer.start();
                        else
                        {
                            mediaPlayer.setDataSource(Constants.SITEURL + theFile.getFile_flu());
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            pauseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        mediaPlayer.pause();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    pauseButton.setVisibility(View.GONE);
                    playButton.setVisibility(View.VISIBLE);
                }
            });
            fastforwardButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int Duration=mediaPlayer.getDuration();
                        int seeked=mediaPlayer.getCurrentPosition();
                        mediaPlayer.seekTo(Math.min(seeked+15000,Duration));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            backwardButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int seeked=mediaPlayer.getCurrentPosition();
                        mediaPlayer.seekTo(Math.max(seeked-15000,0));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        } else {
            buyButton.setVisibility(View.VISIBLE);
            buyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    theActivity = ((MainActivity) getActivity());
                    final File f = new File(theActivity);
                    final String UserName = theActivity.getSharedPreferences(Constants.GENERAL_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.USERNAME, "0");
                    final String Password = theActivity.getSharedPreferences(Constants.GENERAL_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.PASSWORD, "0");
                    new LovelyStandardDialog(theActivity)
                            .setButtonsColor(Color.parseColor("#FFC01059"))
                            .setTitle("نحوه پرداخت")
                            .setMessage("")
                            .setPositiveButton("کسر از اعتبار موجود", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    BuyFromAccount(f, UserName, Password);

                                }
                            })
                            .setNegativeButton("انتقال به درگاه بانکی", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    PurchaseFragment frg = (PurchaseFragment) theActivity.showFragment(PurchaseFragment.class);
                                    frg.setThePurchaseCompleteListener(new PurchaseFragment.onPurchaseCompleteListener() {
                                        @Override
                                        public void OnPurchaseComplete() {
                                            theActivity.showFragment(FileFragment.class);
                                            BuyFromAccount(f, UserName, Password);
                                        }
                                    });
                                    Log.d("File Price", theFile.getPrice());
                                    frg.Pay(Integer.parseInt(theFile.getPrice()));

                                }
                            })
                            .show();


                }
            });
        }


    }

    private void BuyFromAccount(final File f, final String UserName, final String Password) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final Message msg = f.buy(theActivity.SharedConf_ItemID, UserName, Password);
                theActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new LovelyStandardDialog(theActivity)
                                .setButtonsColor(Color.parseColor("#FFC01059"))
                                .setTitle("پیام")
                                .setMessage(msg.getMessageText())
                                .setPositiveButton(android.R.string.ok, null)
                                .show();

                    }
                });
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_file_item, container, false);
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