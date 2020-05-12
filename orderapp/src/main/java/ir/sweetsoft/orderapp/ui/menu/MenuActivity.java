package ir.sweetsoft.orderapp.ui.menu;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import common.BaseAppCompatActivity;
import common.DBTools;
import common.ExcelReader;
import common.ExcelWriter;
import common.SweetDisplayScaler;
import common.SweetFile;
import common.SweetFonts;
import ir.sweetsoft.orderapp.Model.Customer;
import ir.sweetsoft.orderapp.Model.Factor;
import ir.sweetsoft.orderapp.Model.FactorProduct;
import ir.sweetsoft.orderapp.Model.Parameter;
import ir.sweetsoft.orderapp.Model.Product;
import ir.sweetsoft.orderapp.PasswordActivity;
import ir.sweetsoft.orderapp.PasswordChangeActivity;
import ir.sweetsoft.orderapp.R;
import ir.sweetsoft.orderapp.ui.common.AboutDeveloperActivity;
import ir.sweetsoft.orderapp.ui.common.AboutUsActivity;
import ir.sweetsoft.orderapp.ui.customer.CustomerListActivity;
import ir.sweetsoft.orderapp.ui.factor.FactorListActivity;
import ir.sweetsoft.orderapp.ui.product.ProductListActivity;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

public class MenuActivity extends BaseAppCompatActivity {

    String BackupFilePath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/ATA/";
    String BackupFileName="arioazarbaijan.swt";
    String ProductBackupFileName="products.stx";

    ImageView btnProducts;
    ImageView btnCustomers;
    ImageView btnFactors;
    ImageView btnArchivedFactors;
    ImageView btnAboutUs;
    ImageView btnAboutDeveloper;
    ImageView btnBackup;
    ImageView btnRestore;
    ImageView btnExit;
    ImageView btnChangePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Typeface font=SweetFonts.getFont(this,SweetFonts.IranSans);
        SweetDisplayScaler scaler=new SweetDisplayScaler(this);
        int width=scaler.WidthPercentToPixel(30);
        int padding=scaler.WidthPercentToPixel(1);

        btnProducts=findViewById(R.id.btn_products);
        LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams) btnProducts.getLayoutParams();
        lp.width=width;
        lp.height=width;
        lp.leftMargin=padding;
        lp.rightMargin=padding;
        btnProducts.setLayoutParams(lp);
        btnCustomers=findViewById(R.id.btn_customers);
        btnCustomers.setLayoutParams(lp);
        btnFactors=findViewById(R.id.btn_factors);
        btnFactors.setLayoutParams(lp);
        btnArchivedFactors=findViewById(R.id.btn_archived_factors);
        btnArchivedFactors.setLayoutParams(lp);
        btnAboutUs=findViewById(R.id.btn_about_us);
        btnAboutUs.setLayoutParams(lp);
        btnAboutDeveloper=findViewById(R.id.btn_about_developer);
        btnAboutDeveloper.setLayoutParams(lp);
        btnBackup=findViewById(R.id.btn_makebackup);
        btnBackup.setLayoutParams(lp);
        btnRestore=findViewById(R.id.btn_restore_backup);
        btnRestore.setLayoutParams(lp);
        btnExit=findViewById(R.id.btn_exit);
        btnExit.setLayoutParams(lp);
        btnChangePassword=findViewById(R.id.btn_change_password);
        btnChangePassword.setLayoutParams(lp);

        btnProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MenuActivity.this, ProductListActivity.class);
                startActivity(i);
            }
        });

        btnCustomers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MenuActivity.this, CustomerListActivity.class);
                startActivity(i);
            }
        });

        btnFactors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MenuActivity.this, FactorListActivity.class);
                i.putExtra("is_archived",false);
                startActivity(i);
            }
        });
        btnArchivedFactors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MenuActivity.this, FactorListActivity.class);
                i.putExtra("is_archived",true);
                startActivity(i);
            }
        });
        btnAboutDeveloper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MenuActivity.this, AboutDeveloperActivity.class);
                startActivity(i);
            }
        });

        btnAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MenuActivity.this, AboutUsActivity.class);
                startActivity(i);
            }
        });
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MenuActivity.this, PasswordChangeActivity.class);
                startActivity(i);
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuActivity.this.finish();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuActivity.this.finish();
            }
        });

        btnBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doAllTablesHasRecord()) {
                    List<Product> products=new Select().from(Product.class).execute();
                    try {
                        ExcelWriter ew=new ExcelWriter(BackupFilePath+"/"+ProductBackupFileName);
                        String[] fields={"Name","Code","Description","Price","IsActive","Status"};
                        ew.appendListSheet("products",products,fields,"--null--");
                        ew.write();
                    } catch (IllegalAccessException | IOException | NoSuchFieldException | WriteException e) {
                        e.printStackTrace();
                    }
                    DBTools.Export(BackupFilePath, BackupFileName, getApplicationContext(), "sweetyorderapp.db");
                    showBackupMadeMessage();
                }
                else
                {
                    showEmptyTableExistsMessage();
                }
            }
        });
        btnRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LovelyStandardDialog(MenuActivity.this, LovelyStandardDialog.ButtonLayout.VERTICAL)
                        .setTitle("پیام")
                        .setIcon(R.drawable.info)
                        .setTopColorRes(R.color.blue)
                        .setMessage("لطفا روش خواندن بکاپ را انتخاب کنید.")
                        .setPositiveButton("بازگردانی خودکار", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MenuActivity.this.showBackupMenu(BackupFilePath+"/"+BackupFileName,BackupFilePath+"/"+ProductBackupFileName);
                            }
                        })

                        .setNegativeButton("انتخاب فایل بکاپ", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent()
                                        .setType("*/*")
                                        .setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent, "Select the Backup File"), 100);
                            }
                        })
                        .show();


            }
        });
    }

    private void importProductsFromExcel(String excelFilePath){
        ExcelReader er= null;
        int PRODUCT_COLUMNS=6;
        try {
            er = new ExcelReader(excelFilePath);
            List<List<String>> sht=er.getSheet(0,"--null--");
            int rowCount=sht.size();
            if(rowCount>0 && sht.get(0).size()>0){

                new Delete().from(FactorProduct.class).execute();
                new Delete().from(Product.class).execute();
                new Delete().from(Factor.class).execute();
                for(int row=0;row<rowCount;row++){
                    List<String> theRow=sht.get(row);
                    int colCount=theRow.size();
                    if(colCount==PRODUCT_COLUMNS){
                        Product p=new Product();
                        p.Name=theRow.get(0);
                        p.Code=theRow.get(1);
                        p.Description=theRow.get(2);
                        String priceStr=theRow.get(3);
                        if(priceStr==null)
                            p.Price=0;
                        else
                            p.Price=Integer.parseInt(priceStr.trim());
                        String IsActive=theRow.get(4);
                        if(IsActive==null)
                            p.IsActive=true;
                        else
                            p.IsActive=Boolean.parseBoolean(IsActive);
                        String statusStr=theRow.get(5);
                        if(statusStr==null)
                            p.Status=0;
                        else
                            p.Status=Integer.parseInt(statusStr.trim());
                        p.save();
                    }
                }
                new LovelyStandardDialog(this, LovelyStandardDialog.ButtonLayout.VERTICAL)
                        .setTitle("پیام")
                        .setIcon(R.drawable.succeed)
                        .setTopColorRes(R.color.green)
                        .setMessage("فایل بکاپ محصولات بازگردانی شد.")
                        .setPositiveButton("بستن",null)
                        .show();
            }
        } catch (IOException | BiffException e) {
            e.printStackTrace();
        }
    }
    public void showBackupMenu(String BackupFilePath,String ExcelFilePath){
        new LovelyStandardDialog(this, LovelyStandardDialog.ButtonLayout.VERTICAL)
                .setTitle("پیام")
                .setIcon(R.drawable.info)
                .setTopColorRes(R.color.blue)
                .setMessage("نوع فایل")
                .setPositiveButton("اطلاعات کامل", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        importBackup(BackupFilePath);
                    }
                })
                .setNegativeButton("محصولات", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        importProductsFromExcel(ExcelFilePath);
                    }
                })
                .show();
    }
    public void importBackup(String BackupFilePath)
    {
        try {
            DBTools.Import(BackupFilePath, getApplicationContext(), "sweetyorderapp.db");
            new LovelyStandardDialog(this, LovelyStandardDialog.ButtonLayout.VERTICAL)
                    .setTitle("پیام")
                    .setIcon(R.drawable.succeed)
                    .setTopColorRes(R.color.green)
                    .setMessage("فایل بکاپ بازگردانی شد،  برنامه بسته شده و دوباره اجرا خواهد شد.")
                    .setPositiveButton("بستن", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent mStartActivity = new Intent(getApplicationContext(), MenuActivity.class);
                            int mPendingIntentId = 123456;
                            PendingIntent mPendingIntent = PendingIntent.getActivity(getApplicationContext(), mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
                            AlarmManager mgr = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
                            System.exit(0);
                        }
                    })
                    .show();
        }
        catch (FileNotFoundException fex)
        {
            new LovelyStandardDialog(this, LovelyStandardDialog.ButtonLayout.VERTICAL)
                    .setTitle("پیام")
                    .setIcon(R.drawable.warning)
                    .setTopColorRes(R.color.Red)
                    .setMessage("فایل بکاپ پیدا نشد، لطفا نرم افزار یا روش دیگری را برای انتخاب فایل انتخاب کنید.")
                    .setPositiveButton("بستن", null)
                    .show();
        }
        catch (IOException fex)
        {
            new LovelyStandardDialog(this, LovelyStandardDialog.ButtonLayout.VERTICAL)
                    .setTitle("پیام")
                    .setIcon(R.drawable.warning)
                    .setTopColorRes(R.color.Red)
                    .setMessage("خطایی در خواندن فایل بکاپ به وجود آمد.")
                    .setPositiveButton("بستن", null)
                    .show();
        }
    }
    private boolean doAllTablesHasRecord()
    {
        return  doTableHasRecord(Parameter.class) && doTableHasRecord(Product.class)&& doTableHasRecord(Factor.class)&& doTableHasRecord(FactorProduct.class)&& doTableHasRecord(Customer.class);
    }
    private boolean doTableHasRecord(Class TableClass)
    {
        List records=new Select().from(TableClass).execute();
        return !(records==null || records.size()==0);
    }
    private void showEmptyTableExistsMessage()
    {
        new LovelyStandardDialog(MenuActivity.this, LovelyStandardDialog.ButtonLayout.VERTICAL)
                .setTitle("پیام")
                .setIcon(R.drawable.warning)
                .setTopColorRes(R.color.Red)
                .setMessage("برای بکاپ گرفتن باید حداقل یک فاکتور با یک محصول در داخل آن وجود داشته باشد.")
                .setPositiveButton("OK",null)
                .show();
    }
    private void showBackupMadeMessage()
    {
        new LovelyStandardDialog(MenuActivity.this, LovelyStandardDialog.ButtonLayout.VERTICAL)
                .setTitle("پیام")
                .setIcon(R.drawable.succeed)
                .setTopColorRes(R.color.green)
                .setMessage("فایل بکاپ در حافظه با نام " + BackupFileName + " ساخته شد")
                .setPositiveButton("OK",null)
                .show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {
            Uri selectedfile = data.getData();
            showBackupMenu(selectedfile.getPath(),selectedfile.getPath());

        }
    }
}
