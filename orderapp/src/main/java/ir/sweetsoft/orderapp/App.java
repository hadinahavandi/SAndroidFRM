package ir.sweetsoft.orderapp;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.activeandroid.ActiveAndroid;


/**
 * Created by Nahavandi on 8/17/2016.
 */
import org.acra.*;
import org.acra.annotation.*;
import org.acra.config.CoreConfigurationBuilder;
import org.acra.data.StringFormat;
import org.acra.sender.HttpSender;

@AcraCore(buildConfigClass = BuildConfig.class)
@AcraHttpSender(uri = "https://anhd.ir/acra.php",
        basicAuthLogin = "hadi", // optional
        basicAuthPassword = "Persian1147%", // optional
        httpMethod = HttpSender.Method.POST)
public class App extends MultiDexApplication {
  @Override
  public void onCreate() {
    super.onCreate();
    ActiveAndroid.initialize(this);
    ActiveAndroid.getDatabase().disableWriteAheadLogging();
    CoreConfigurationBuilder builder = new CoreConfigurationBuilder(this);
    builder.setBuildConfigClass(BuildConfig.class).setReportFormat(StringFormat.JSON);
    ACRA.DEV_LOGGING = true;
    ACRA.init(this, builder);
  }
  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    // The following line triggers the initialization of ACRA
    ACRA.DEV_LOGGING = true;

    ACRA.init(this);
  }
}