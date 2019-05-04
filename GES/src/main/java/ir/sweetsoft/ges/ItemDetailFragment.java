package ir.sweetsoft.ges;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.activeandroid.query.Select;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import common.Exceptions.InvalidInputException;
import common.SweetDisplayScaler;
import ir.sweetsoft.ges.Model.Cow;
import ir.sweetsoft.ges.Model.Herd;
import ir.sweetsoft.ges.Model.HerdFile;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */

public class ItemDetailFragment extends Fragment {

    private float FontSizePercent=3.5f;
    private float MediumFontSizePercent=2.0f;
    private float SmallFontSizePercent=2.9f;
    private float CodeFontSizePercent=3.8f;
    private float CharacterImageSizePercent=4;
    private float ItemWidthPercent=15;
    private float ItemLabelPercent=10;
    private float BigItemWidthPercent=25;
    private float BigItemLabelPercent=11;
    private float CodeWidthPercent=28;
    private float CodeLabelPercent=18;

    private EditText txt_Code;
    private EditText txt_Description;
    private TextView lbl_Code;
    private TextView lbl_Description;
    private RowView Row_sire;
    private RowView Row_mgs;
    private RowView Row_mmgs;
    private RowView Row_ls;
    private RowView Row_st;
    private RowView Row_sr;
    private RowView Row_bd;
    private RowView Row_df;
    private RowView Row_ra;
    private RowView Row_rw;
    private RowView Row_sv;
    private RowView Row_rv;
    private RowView Row_fa;
    private RowView Row_fu;
    private RowView Row_uh;
    private RowView Row_uw;
    private RowView Row_uc;
    private RowView Row_ud;
    private RowView Row_tp;
    private RowView Row_tl;
    private RowView Row_rtp;
    ImageView CharacterImage;
    TextView[] Labels;
    EditText[] Inputs;
    RelativeLayout[] Columns;
    private HerdFile FragmentHerdFile=null;
    private Boolean FragmentIsHeifer=false;
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */

    /**
     * The dummy content this fragment is presenting.
     */
    private Cow mItem;
    CowManActivity ContainerActivity;
    private boolean deleted = false;

    public ItemDetailFragment() {
    }
    private Handler mHandler= new Handler();
    private Integer getEditTextInt(EditText view) {
        return Integer.parseInt(view.getText().toString().equals("") ? "-1" : view.getText().toString());
    }
    private Integer getEditTextIntAndCheck(EditText view,int MinValue,int MaxValue) throws InvalidInputException {
        int Value = getEditTextInt(view);
        if (Value > 0 && (Value < MinValue || Value > MaxValue))
            throw new InvalidInputException("Invalid Input Data,Numbers Should be between 5 and 99 and LS Should be between 1 and 19 ", view.getId() + "");
        return Value;
    }
    private Integer getEditTextIntAndCheck(EditText view) throws InvalidInputException {
        return  getEditTextIntAndCheck(view,5,99);
    }
    public void Save(Boolean ForceSave) throws InvalidInputException {
        Log.d("Saving", "Start");
        if (deleted)
            return;
        Log.d("Saving", "Starts");
        if (mItem == null)
            mItem = new Cow();
        String CowCode = txt_Code.getText().toString().toUpperCase().trim();
        String Sire = Row_sire.getInput().getText().toString().toUpperCase().trim();
        String MMGS = Row_mmgs.getInput().getText().toString().toUpperCase().trim();
        String MGS = Row_mgs.getInput().getText().toString().toUpperCase().trim();
        Log.d("Saving", FragmentHerdFile.Herd.Code);
        List<Cow> cows = new Select().from(Cow.class).where("code = ? and herdfile_fid = ?", CowCode, FragmentHerdFile.getId()).execute();
        Log.d("Saving", "Started somes");
        if ((!CowCode.equals(mItem.CowCode+"")) && cows != null && cows.size() > 0)// Adding New Item or Changing It's CowID And It Exists Before
            ContainerActivity.showAlert("Error", "Error: Cow ID Exists.", null, true);
        else if(CowCode.trim().length()>0) {
            Log.d("Saving", "hi"+getEditTextIntAndCheck(Row_st.getInput()));
            mItem.HerdFile = FragmentHerdFile;
            mItem.ls = getEditTextIntAndCheck(Row_ls.getInput(),1,19);
            mItem.st = getEditTextIntAndCheck(Row_st.getInput());
            mItem.sr = getEditTextIntAndCheck(Row_sr.getInput());
            mItem.bd = getEditTextIntAndCheck(Row_bd.getInput());
            mItem.df = getEditTextIntAndCheck(Row_df.getInput());
            mItem.ra = getEditTextIntAndCheck(Row_ra.getInput());
            mItem.ra = getEditTextIntAndCheck(Row_ra.getInput());
            mItem.rw = getEditTextIntAndCheck(Row_rw.getInput());
            mItem.sv = getEditTextIntAndCheck(Row_sv.getInput());
            mItem.rv = getEditTextIntAndCheck(Row_rv.getInput());
            mItem.fa = getEditTextIntAndCheck(Row_fa.getInput());
            mItem.fu = getEditTextIntAndCheck(Row_fu.getInput());
            mItem.uh = getEditTextIntAndCheck(Row_uh.getInput());
            mItem.uw = getEditTextIntAndCheck(Row_uw.getInput());
            mItem.uc = getEditTextIntAndCheck(Row_uc.getInput());
            mItem.ud = getEditTextIntAndCheck(Row_ud.getInput());
            mItem.tp = getEditTextIntAndCheck(Row_tp.getInput());
            mItem.tl = getEditTextIntAndCheck(Row_tl.getInput());
            mItem.rtp = getEditTextIntAndCheck(Row_rtp.getInput());
            mItem.Description = txt_Description.getText().toString();
            Log.d("Saving", "hi");
            mItem.sire = Sire;
            mItem.mgs = MGS;
            mItem.mmgs = MMGS;
            Log.d("Saving", "hiss");
            mItem.IsHeifer = FragmentIsHeifer;
            Log.d("Saving", "his");
            mItem.CowCode = CowCode;
            Log.d("Saving", "him");
            Log.d("Saving", "Complating"+mItem.CowCode);
            if (mItem.CowCode.length() > 0)
                mItem.SaveData();
            CowManActivity activity = (CowManActivity) this.getActivity();
            activity.LastFragment = null;
            activity.LoadItem(txt_Code,false);//Just a view
        }
        else if(ForceSave)
        {
            Log.d("Saving", "Error");
            throw new InvalidInputException("Invalid Input Data,Cow ID is Required", "CowID");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContainerActivity = ((CowManActivity) getActivity());
        FragmentHerdFile=ContainerActivity.SelectedHerdFile;
        FragmentIsHeifer=ContainerActivity.getHeifer();
        if (getArguments().containsKey(CowManActivity.ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            long ItemID = getArguments().getLong(CowManActivity.ARG_ITEM_ID);
            if (ItemID > 0)
                mItem = Cow.load(Cow.class, ItemID);
//            if (appBarLayout != null && mItem!) {
//                appBarLayout.setTitle(mItem.CowCode);
//            }
        }
    }

    private void changeVisiblities() {
        int Visiblity = ContainerActivity.getHeifer() ? View.GONE : View.VISIBLE;
        Row_fu.setVisibility(Visiblity);
        Row_uh.setVisibility(Visiblity);
        Row_uw.setVisibility(Visiblity);
        Row_uc.setVisibility(Visiblity);
        Row_ud.setVisibility(Visiblity);
        Row_tp.setVisibility(Visiblity);
        Row_tl.setVisibility(Visiblity);
        Row_rtp.setVisibility(Visiblity);
        Row_ls.setVisibility(Visiblity);
        lbl_Code.setText(ContainerActivity.getHeifer() ? "Heifer ID" : "Cow ID");

    }
    public void Delete()
    {
        if (mItem != null) {
            mItem.delete();
            ItemListActivity act = (ItemListActivity) getActivity();
            act.LastFragment=null;
            act.LoadItem(null,false);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        Columns = new RelativeLayout[3];
        View rootView = inflater.inflate(R.layout.item_detail, container, false);
        Columns[0] = (RelativeLayout) rootView.findViewById(R.id.column1);
        Columns[1] = (RelativeLayout)rootView.findViewById(R.id.column2);
        Columns[2] = (RelativeLayout)rootView.findViewById(R.id.column3);
        txt_Code=(EditText)rootView.findViewById(R.id.item_txt_code) ;
        CharacterImage=rootView.findViewById(R.id.character) ;
        lbl_Code=(TextView) rootView.findViewById(R.id.item_lbl_code) ;
        txt_Description=(EditText)rootView.findViewById(R.id.txt_description) ;
        lbl_Description=(TextView) rootView.findViewById(R.id.lbl_description) ;
        Row_sire=(RowView)rootView.findViewById(R.id.sire);
        Row_mgs=(RowView)rootView.findViewById(R.id.mgs);
        Row_mmgs=(RowView)rootView.findViewById(R.id.mmgs);
        Row_ls=(RowView) inflater.inflate(R.layout.item_rowlayout,container,false);
        Row_st=(RowView) inflater.inflate(R.layout.item_rowlayout,container,false);
        Row_sr=(RowView) inflater.inflate(R.layout.item_rowlayout,container,false);
        Row_bd=(RowView) inflater.inflate(R.layout.item_rowlayout,container,false);
        Row_df=(RowView) inflater.inflate(R.layout.item_rowlayout,container,false);
        Row_ra=(RowView) inflater.inflate(R.layout.item_rowlayout,container,false);
        Row_rw=(RowView) inflater.inflate(R.layout.item_rowlayout,container,false);
        Row_sv=(RowView) inflater.inflate(R.layout.item_rowlayout,container,false);
        Row_rv=(RowView) inflater.inflate(R.layout.item_rowlayout,container,false);
        Row_fa=(RowView) inflater.inflate(R.layout.item_rowlayout,container,false);
        Row_fu=(RowView) inflater.inflate(R.layout.item_rowlayout,container,false);
        Row_uh=(RowView) inflater.inflate(R.layout.item_rowlayout,container,false);
        Row_uw=(RowView) inflater.inflate(R.layout.item_rowlayout,container,false);
        Row_uc=(RowView) inflater.inflate(R.layout.item_rowlayout,container,false);
        Row_ud=(RowView) inflater.inflate(R.layout.item_rowlayout,container,false);
        Row_tp=(RowView) inflater.inflate(R.layout.item_rowlayout,container,false);
        Row_tl=(RowView) inflater.inflate(R.layout.item_rowlayout,container,false);
        Row_rtp=(RowView) inflater.inflate(R.layout.item_rowlayout,container,false);

        Row_sire.getLabel().setText("sire".toUpperCase());
        Row_mgs.getLabel().setText("mgs".toUpperCase());
        Row_mmgs.getLabel().setText("mmgs".toUpperCase());
        Row_ls.getLabel().setText("ls".toUpperCase());
        Row_st.getLabel().setText("st".toUpperCase());
        Row_sr.getLabel().setText("sr".toUpperCase());
        Row_bd.getLabel().setText("bd".toUpperCase());
        Row_df.getLabel().setText("df".toUpperCase());
        Row_ra.getLabel().setText("ra".toUpperCase());
        Row_rw.getLabel().setText("rw".toUpperCase());
        Row_sv.getLabel().setText("sv".toUpperCase());
        Row_rv.getLabel().setText("rv".toUpperCase());
        Row_fa.getLabel().setText("fa".toUpperCase());
        Row_fu.getLabel().setText("fu".toUpperCase());
        Row_uh.getLabel().setText("uh".toUpperCase());
        Row_uw.getLabel().setText("uw".toUpperCase());
        Row_uc.getLabel().setText("uc".toUpperCase());
        Row_ud.getLabel().setText("ud".toUpperCase());
        Row_tp.getLabel().setText("ftp".toUpperCase());
        Row_tl.getLabel().setText("tl".toUpperCase());
        Row_rtp.getLabel().setText("rtp".toUpperCase());



        SweetDisplayScaler scaler=new SweetDisplayScaler(getActivity());
        txt_Code.getLayoutParams().width=scaler.WidthPercentToPixel(CodeWidthPercent);
        txt_Code.setBackgroundResource(R.drawable.forthedittext);
        txt_Code.setInputType(InputType.TYPE_CLASS_NUMBER);
        CharacterImage.getLayoutParams().width=scaler.WidthPercentToPixel(CharacterImageSizePercent);
        CharacterImage.getLayoutParams().height=scaler.WidthPercentToPixel(CharacterImageSizePercent);
        CharacterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_Code.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                Row_sire.getInput().setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                Row_mgs.getInput().setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                Row_mmgs.getInput().setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
            }
        });
        txt_Code.setTextSize(TypedValue.COMPLEX_UNIT_PX,scaler.WidthPercentToPixel(CodeFontSizePercent));
        lbl_Code.getLayoutParams().width=scaler.WidthPercentToPixel(CodeLabelPercent);
        lbl_Code.setTextSize(TypedValue.COMPLEX_UNIT_PX,scaler.WidthPercentToPixel(CodeFontSizePercent));
        txt_Description.setTextSize(TypedValue.COMPLEX_UNIT_PX,scaler.WidthPercentToPixel(FontSizePercent));
        lbl_Description.setTextSize(TypedValue.COMPLEX_UNIT_PX,scaler.WidthPercentToPixel(FontSizePercent));
        RowView[] Rows={Row_st,Row_sr,Row_bd,Row_df,Row_ra,Row_rw,Row_sv,Row_rv,Row_fa,Row_fu,Row_uh,Row_uw,Row_uc,Row_ud,Row_tp,Row_tl,Row_rtp,Row_ls};

        int ColumnCount=3;
        for(int i=0;i<Rows.length;i=i+ColumnCount)
            for(int j=0;j<ColumnCount && i+j<Rows.length;j++) {
                Rows[i + j].setId(View.generateViewId());
                Columns[j].addView(Rows[i + j]);
                Rows[i + j].setFilters(3, 1, 100);
                Rows[i + j].getLabel().getLayoutParams().width = scaler.WidthPercentToPixel(ItemLabelPercent);
                Rows[i + j].getLabel().setTextSize(TypedValue.COMPLEX_UNIT_PX, scaler.WidthPercentToPixel(FontSizePercent));
                Rows[i + j].getInput().getLayoutParams().width = scaler.WidthPercentToPixel(ItemWidthPercent);
                Rows[i + j].getInput().setTextSize(TypedValue.COMPLEX_UNIT_PX, scaler.WidthPercentToPixel(FontSizePercent));

                ((RelativeLayout.LayoutParams) Rows[i + j].getLayoutParams()).topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
                if (i > 0)
                    ((RelativeLayout.LayoutParams) Rows[i + j].getLayoutParams()).addRule(RelativeLayout.BELOW, Rows[i + j - ColumnCount].getId());
                int EndOfMovingForward=Rows.length-1;//LS Sire MGS and MMGS
                if(ContainerActivity.getHeifer())
                    EndOfMovingForward=EndOfMovingForward-5;//Only Cow Fields
                if (i + j > 0 && i+j<EndOfMovingForward) {
                    final EditText CurrentField = Rows[i + j].getInput();
                    final EditText PreviousField = Rows[i + j - 1].getInput();
                    PreviousField.setNextFocusForwardId(CurrentField.getId());
                    PreviousField.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if(PreviousField.getText().toString().length()==2)
                                CurrentField.requestFocus();

                        }
                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                }
            }

            Row_sire.setFilters(14,-1,-1);
            Row_mgs.setFilters(14,-1,-1);
            Row_mmgs.setFilters(14,-1,-1);
        Row_ls.setFilters(2,1,19);
            Row_sire.getInput().setBackgroundResource(R.drawable.secondedittext);

        Row_ls.getInput().setBackgroundResource(R.drawable.thirdedittext);
        Row_mgs.getInput().setBackgroundResource(R.drawable.secondedittext);
        Row_mmgs.getInput().setBackgroundResource(R.drawable.secondedittext);

        int padding_in_px = (int) (4 * getResources().getDisplayMetrics().density + 0.5f);
        Row_ls.getInput().setPadding(padding_in_px,padding_in_px,padding_in_px,padding_in_px);
        Row_mgs.getInput().setPadding(0,padding_in_px,0,padding_in_px);
        Row_mmgs.getInput().setPadding(0,padding_in_px,0,padding_in_px);
        Row_sire.getInput().setPadding(0,padding_in_px,0,padding_in_px);

        Row_sire.getLabel().getLayoutParams().width = scaler.WidthPercentToPixel(BigItemLabelPercent);
        Row_sire.getInput().getLayoutParams().width = scaler.WidthPercentToPixel(BigItemWidthPercent);
        Row_mgs.getLabel().getLayoutParams().width = scaler.WidthPercentToPixel(BigItemLabelPercent);
        Row_mgs.getInput().getLayoutParams().width = scaler.WidthPercentToPixel(BigItemWidthPercent);
        Row_mmgs.getLabel().getLayoutParams().width = scaler.WidthPercentToPixel(BigItemLabelPercent);
        Row_mmgs.getInput().getLayoutParams().width = scaler.WidthPercentToPixel(BigItemWidthPercent);
        lbl_Description.getLayoutParams().width = scaler.WidthPercentToPixel(BigItemLabelPercent);
        txt_Description.getLayoutParams().width = scaler.WidthPercentToPixel(BigItemWidthPercent);

        lbl_Description.setTextSize(TypedValue.COMPLEX_UNIT_PX,scaler.WidthPercentToPixel(SmallFontSizePercent));
        txt_Description.setTextSize(TypedValue.COMPLEX_UNIT_PX,scaler.WidthPercentToPixel(SmallFontSizePercent));
        Row_sire.getLabel().setTextSize(TypedValue.COMPLEX_UNIT_PX,scaler.WidthPercentToPixel(SmallFontSizePercent));
        Row_sire.getInput().setTextSize(TypedValue.COMPLEX_UNIT_PX,scaler.WidthPercentToPixel(SmallFontSizePercent));
        Row_mgs.getLabel().setTextSize(TypedValue.COMPLEX_UNIT_PX,scaler.WidthPercentToPixel(SmallFontSizePercent));
        Row_mgs.getInput().setTextSize(TypedValue.COMPLEX_UNIT_PX,scaler.WidthPercentToPixel(SmallFontSizePercent));
        Row_mmgs.getLabel().setTextSize(TypedValue.COMPLEX_UNIT_PX,scaler.WidthPercentToPixel(SmallFontSizePercent));
        Row_mmgs.getInput().setTextSize(TypedValue.COMPLEX_UNIT_PX,scaler.WidthPercentToPixel(SmallFontSizePercent));
        changeVisiblities();
        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            Row_rtp.getInput().setText((mItem.rtp == -1 ? "" : mItem.rtp.toString()));
            txt_Code.setText(mItem.CowCode+"");
            txt_Description.setText(mItem.Description);
            Row_sire.getInput().setText(mItem.sire);
            Row_mgs.getInput().setText(mItem.mgs);
            Row_mmgs.getInput().setText(mItem.mmgs);
            Row_ls.getInput().setText((mItem.ls == -10 ? "" : mItem.ls.toString()));
            Row_st.getInput().setText((mItem.st == -1 ? "" : mItem.st.toString()));
            Row_sr.getInput().setText((mItem.sr == -1 ? "" : mItem.sr.toString()));
            Row_bd.getInput().setText((mItem.bd == -1 ? "" : mItem.bd.toString()));
            Row_df.getInput().setText((mItem.df == -1 ? "" : mItem.df.toString()));
            Row_ra.getInput().setText((mItem.ra == -1 ? "" : mItem.ra.toString()));
            Row_rw.getInput().setText((mItem.rw == -1 ? "" : mItem.rw.toString()));
            Row_sv.getInput().setText((mItem.sv == -1 ? "" : mItem.sv.toString()));
            Row_rv.getInput().setText((mItem.rv == -1 ? "" : mItem.rv.toString()));
            Row_fa.getInput().setText((mItem.fa == -1 ? "" : mItem.fa.toString()));
            Row_fu.getInput().setText((mItem.fu == -1 ? "" : mItem.fu.toString()));
            Row_uh.getInput().setText((mItem.uh == -1 ? "" : mItem.uh.toString()));
            Row_uw.getInput().setText((mItem.uw == -1 ? "" : mItem.uw.toString()));
            Row_uc.getInput().setText((mItem.uc == -1 ? "" : mItem.uc.toString()));
            Row_ud.getInput().setText((mItem.ud == -1 ? "" : mItem.ud.toString()));
            Row_tp.getInput().setText((mItem.tp == -1 ? "" : mItem.tp.toString()));
            Row_tl.getInput().setText((mItem.tl == -1 ? "" : mItem.tl.toString()));

        }
        mHandler.post(
                new Runnable()
                {
                    public void run()
                    {
                        txt_Code.requestFocus();
                    }
                }
        );

        return rootView;
    }
}
