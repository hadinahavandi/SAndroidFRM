package ir.sweetsoft.ges;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import calendar.CivilDate;
import common.BaseFileUsageActivity;
import common.Exceptions.InvalidInputException;
import common.Exceptions.ItemExistsException;
import common.SweetDate;
import common.SweetDisplayScaler;
import common.SweetFile;
import ir.sweetsoft.ges.Exceptions.noSheetException;
import ir.sweetsoft.ges.Model.Cow;
import ir.sweetsoft.ges.Model.Herd;
import jxl.read.biff.BiffException;

public class CowManActivity extends BaseFileUsageActivity {
    protected boolean mTwoPane;
    private float FontSizePercent=3.5f;
    protected float TopIconsMarginPercent=0.6f;
    protected ItemDetailFragment LastFragment=null;
    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_HERD_POSITION = "herd_id";
    protected String SelectedHerdCode=null;
    protected int SelectedHerdPosition=-1;
    protected ItemListActivity.SimpleItemRecyclerViewAdapter recyclerViewAdapter = null;
    private Spinner HerdSelect;
    private TextView HerdSelectLabel;
    protected int ButtonSize=0;

    public Boolean getHeifer() {
        return isHeifer;
    }

    private Boolean isHeifer=false;

    public void setIsHeifer(Boolean theIsHeifer)
    {
        isHeifer=theIsHeifer;
        refreshCowList();
        Add(false);

    }

    protected int DefaultLyout=R.layout.activity_item_list;
    ImageView saver;
    ImageView BtnExport;
    ImageView BtnImport;
    ImageView BtnAdd;
    ImageView BtnDelete;
    ImageView BtnAboutUS;
    //    protected boolean Loading=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(DefaultLyout);

        if(shouldAskPermission())
            requestFileAccessPermission(10);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        saver = (ImageView) findViewById(R.id.save);
        BtnExport = (ImageView) findViewById(R.id.btn_export);
        BtnImport = (ImageView) findViewById(R.id.btn_import);
        BtnAdd = (ImageView) findViewById(R.id.btn_add);
        BtnDelete = (ImageView) findViewById(R.id.btn_delete);
        BtnAboutUS = (ImageView) findViewById(R.id.btn_aboutus);
        SweetDisplayScaler scaler=new SweetDisplayScaler(this);
        ButtonSize=Math.min(scaler.HeightPercentToPixel(10),scaler.WidthPercentToPixel(10));
//        int ImportHeights=Math.min(scaler.HeightPercentToPixel(10),scaler.WidthPercentToPixel(10));
        saver.getLayoutParams().width=ButtonSize;
        saver.getLayoutParams().height=ButtonSize;
        BtnExport.getLayoutParams().width=ButtonSize;
        BtnExport.getLayoutParams().height=ButtonSize;
        BtnImport.getLayoutParams().width=ButtonSize;
        BtnImport.getLayoutParams().height=ButtonSize;
        BtnAdd.getLayoutParams().width=ButtonSize;
        BtnAdd.getLayoutParams().height=ButtonSize;
        BtnDelete.getLayoutParams().width=ButtonSize;
        BtnDelete.getLayoutParams().height=ButtonSize;
        BtnAboutUS.getLayoutParams().width=ButtonSize;
        BtnAboutUS.getLayoutParams().height=ButtonSize;

        ((RelativeLayout.LayoutParams)saver.getLayoutParams()).rightMargin=scaler.WidthPercentToPixel(TopIconsMarginPercent);
        ((RelativeLayout.LayoutParams)BtnExport.getLayoutParams()).rightMargin=scaler.WidthPercentToPixel(TopIconsMarginPercent);
        ((RelativeLayout.LayoutParams)BtnImport.getLayoutParams()).rightMargin=scaler.WidthPercentToPixel(TopIconsMarginPercent);
        ((RelativeLayout.LayoutParams)BtnAdd.getLayoutParams()).rightMargin=scaler.WidthPercentToPixel(TopIconsMarginPercent);
        ((RelativeLayout.LayoutParams)BtnDelete.getLayoutParams()).rightMargin=scaler.WidthPercentToPixel(TopIconsMarginPercent);
        ((RelativeLayout.LayoutParams)BtnAboutUS.getLayoutParams()).rightMargin=scaler.WidthPercentToPixel(TopIconsMarginPercent);
        saver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    if(LastFragment!=null)
                        LastFragment.Save(true);
                }
                catch (InvalidInputException ex)
                {
                    showAlert("Error",ex.getMessage(),null,true);
                }
            }
        });
        BtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    if(LastFragment!=null)
                        LastFragment.Delete();
                }
                catch (Exception ex)
                {
                    showAlert("Error",ex.getMessage(),null,true);
                }
            }
        });
        BtnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    File AppDataPath=new File(Environment.getExternalStorageDirectory().getPath()+"/ArianDeltaGene/");
                    AppDataPath.mkdir();
                    File ExportPath=new File(AppDataPath.getAbsolutePath()+"/"+SelectedHerdCode+"/");
                    ExportPath.mkdir();

                    new ExcelAdapter(CowManActivity.this).makeExcelFromHerd(SelectedHerdCode, ExportPath.getAbsolutePath()+"/"+ SweetDate.Date2String("-")+".xls");
//                    if(AddedRows>0)
                        showAlert("Exported","Data Exported Successfully.",null,true);
//                    else
//                        showAlert("No Data","There is no cow in this herd.",null,true);
                }
                catch (Exception ex)
                {
                    showAlert("Error","Unexpected Error Occurred.\n"+ex.getMessage(),null,true);
                }

            }
        });
        BtnImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileOpenDialog(Constants.REQUEST_IMPORT,"application/vnd.ms-excel");

            }
        });
        BtnAboutUS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AboutIntent=new Intent(CowManActivity.this,AboutUsActivity.class);
                startActivity(AboutIntent);
            }
        });
        setSupportActionBar(toolbar);
        toolbar.setTitle("");

        getSupportActionBar().setDisplayShowTitleEnabled(false);


        loadHerdSelect();
//        toolbar.setTitle(getTitle());
        BtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Add(true);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }
    private void Add(Boolean ForceSaveLast)
    {
        if(SelectedHerdCode==null || SelectedHerdCode.length()==0 || SelectedHerdCode.equals("+"))
        {

        }
        else
        {
            LoadItem(BtnAdd,ForceSaveLast);
        }
    }
    protected void RefreshData()
    {
        LastFragment=null;
        List<Cow> Cows=new Select().from(Cow.class).orderBy("code").where("herd_fid= ? AND is_filled=?",SelectedHerdCode,true).execute();
        for(Cow theCow:Cows)
            theCow.clear();
        refreshCowList();
        Add(false);

    }
    private void ImportData(String Path)
    {
        try
        {
            int AddedRows=new ExcelAdapter(CowManActivity.this).importDataExcel(SelectedHerdCode, Path);
            if(AddedRows>0)
                showAlert("Imported","Data imported successfully.",null,true);
            refreshCowList();

        }
        catch (SQLiteConstraintException ex)
        {
            showAlert("Error","Invalid Input data"+ex.getMessage(),null,true);
        }
        catch (noSheetException ex)
        {
            showAlert("Error","There is no sheet in the Excel File.",null,true);
        }
        catch (ItemExistsException ex)
        {
            showAlert("Error","Repetitive Data Exists in the Excel File.",null,true);
        }
        catch (BiffException ex)
        {
            showAlert("Error","Input file is invalid,only XLS files are supported.\n"+ex.getMessage(),null,true);
        }
        catch (ArrayIndexOutOfBoundsException ex)
        {
            showAlert("Error","Some rows has no cow code.\n"+ex.getMessage(),null,true);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            showAlert("Error","Unexpected Error Occurred\n"+ex.getClass().toString()+" "+ex.getMessage(),null,true);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("CODE",requestCode+"");
        if ((requestCode == Constants.REQUEST_IMPORT && resultCode==RESULT_OK)) {
            try {
                Uri selectedFile = data.getData();
                Log.d("selectedFile", selectedFile + "");
                String Path= SweetFile.getPath(this,selectedFile);
                Log.d("Path", Path + "");
                ImportData(Path);
            }
            catch (SecurityException ex) {
                showAlert("Security Error", ex.getClass()+ " " +ex.getMessage(), null, true);
                ex.printStackTrace();
            }
            catch (Exception ex) {
                showAlert("Error", ex.getClass()+ " " +ex.getMessage(), null, true);
                ex.printStackTrace();
            }
        }



    }
    private void loadHerdSelect()
    {
        final SweetDisplayScaler scaler=new SweetDisplayScaler(this);
        HerdSelect = findViewById(R.id.herdselect);
        HerdSelectLabel = findViewById(R.id.lblHerdID);
        HerdSelectLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX,scaler.WidthPercentToPixel(FontSizePercent));
        List<String> HerdCodes = new ArrayList<>();
        final List<Herd> Herds= new Select().from(Herd.class).execute();
        for (Herd hrd:Herds) {
            HerdCodes.add(hrd.Code);
        }
        HerdCodes.add("+");
        Bundle arguments = new Bundle();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, HerdCodes)
        {
            public View getView(int position, View convertView,ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTextSize(TypedValue.COMPLEX_UNIT_PX,scaler.WidthPercentToPixel(FontSizePercent));
                return v;

            }

            public View getDropDownView(int position, View convertView,ViewGroup parent) {

                View v = super.getDropDownView(position, convertView,parent);
                ((TextView) v).setTextSize(TypedValue.COMPLEX_UNIT_PX,scaler.WidthPercentToPixel(FontSizePercent));

                return v;

            }
        };
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        HerdSelect.setAdapter(dataAdapter);
            Integer HerdPosition=getIntent().getIntExtra(CowManActivity.ARG_HERD_POSITION,-1);
            Log.d("HerdPOS",HerdPosition+"");
            if(HerdPosition>0)
                HerdSelect.setSelection(HerdPosition);

        HerdSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                Log.d("Herd:",position+" "+Herds.size());
                if(Herds.size()>0)
                    LoadItem(HerdSelect,false);
                if(position==Herds.size())
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CowManActivity.this);
                    builder.setTitle("Please Insert Herd ID:");
                    final EditText input = new EditText(CowManActivity.this);
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    builder.setView(input);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String HerdID = input.getText().toString().toUpperCase().trim();
                            Herd hd=new Herd();
                            hd.Code=HerdID;
                            hd.save();
                            loadHerdSelect();
                            HerdSelect.setSelection(position);
                        }
                    });
                    builder.show();
                }
                else
                {
                    String theSelectedHerdCode = parent.getItemAtPosition(position).toString();
//                    if(!Loading)
                    {
                        SelectedHerdCode=theSelectedHerdCode.toUpperCase().trim();
                        refreshCowList();
                    }
//                    else
//                        Loading=false;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    protected List<Cow> getHerdCows()
    {
        if(SelectedHerdCode==null)
            return new ArrayList<Cow>();
        List<Cow> Cows=new Select().from(Cow.class).orderBy("code").where("herd_fid= ? AND isheifer = ?",SelectedHerdCode,getHeifer()).execute();
        return Cows;
    }
    public void refreshCowList()
    {
        Log.d("Refreshing","Start");
        if(recyclerViewAdapter!=null)
        {
            Log.d("Refreshing","Inside");
            Log.d("Refreshing",getHerdCows().size()+"");
            recyclerViewAdapter.setDataset(getHerdCows());
            recyclerViewAdapter.notifyDataSetChanged();
        }

    }

    public void LoadItem(View view,Boolean ForceSaveLast) {

        try {
            Cow item=null;
            if(view!=null && view.getClass()!= FloatingActionButton.class)
                item = (Cow) view.getTag();
            if(LastFragment!=null)
                LastFragment.Save(ForceSaveLast);

            if(recyclerViewAdapter!=null)
                refreshCowList();
            long ItemID=-1;
            if(item!=null)
                ItemID=item.getId();

            if (mTwoPane) {
                Bundle arguments = new Bundle();
                if(item!=null)
                    arguments.putLong(CowManActivity.ARG_ITEM_ID, ItemID);
                ItemDetailFragment fragment = new ItemDetailFragment();
                LastFragment=fragment;
                fragment.setArguments(arguments);
                this.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit();
            } else {
                Context context = view.getContext();
                Intent intent = new Intent(context, ItemDetailActivity.class);
                intent.putExtra(CowManActivity.ARG_ITEM_ID, ItemID);
                intent.putExtra(CowManActivity.ARG_HERD_POSITION, HerdSelect.getSelectedItemPosition());
                context.startActivity(intent);


            }
        }
        catch (InvalidInputException ex)
        {
            showAlert("Error",ex.getMessage(),null,true);
        }
        catch (Exception ex)
        {

        }

    }
}
