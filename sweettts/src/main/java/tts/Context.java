package tts;

import common.RemoteClass;
import ir.sweetsoft.sweettts.Constants;

import java.util.List;

import android.app.Activity;
import android.util.Log;

import com.afollestad.bridge.Bridge;
import com.afollestad.bridge.BridgeException;
import com.afollestad.bridge.Request;
import com.afollestad.bridge.Response;

public class Context extends RemoteClass {
    public Context(Activity activity) {
        super(activity);
    }

    public Context() {
        super();
    }

    public long id;
    public String title;
    public String context;
    public String summary;
    public String category_fid;
    public String url;
    public String description;
    public String updated_at;
    public String created_at;

    public void getAll(long categoryID, List<Context> Contexts) {
        try {
            String URL = Constants.SITEURL + "api/tts/contexts/";
            URL += "?categoryid=" + String.valueOf(categoryID);
            Log.d("Category Get All URL", URL);
            Request request = Bridge.get(URL).throwIfNotSuccess().retries(5, 6000).request();
            Response response = request.response();
            if (response.isSuccess()) {
                Contexts.addAll(response.asClassList(Context.class));
//                Log.d("Size:",Contexts.size()+"");
            }
        } catch (BridgeException e) {
            e.printStackTrace();
        }
    }

    public void getAll(List<Context> Contexts) {
        try {
            String URL = Constants.SITEURL + "api/tts/contexts/";
            Request request = Bridge.get(URL).throwIfNotSuccess().retries(5, 6000).request();
            Response response = request.response();
            if (response.isSuccess()) {
                Contexts.addAll(response.asClassList(Context.class));
            }
        } catch (BridgeException e) {
            e.printStackTrace();
        }
    }

    public Context getOne(long Id) {
        try {

            String URL = Constants.SITEURL + "api/tts/contexts/" + String.valueOf(Id);
            Request request = Bridge.get(URL).throwIfNotSuccess().retries(5, 6000).request();
            Response response = request.response();
            if (response.isSuccess())
                return response.asClass(Context.class);
        } catch (BridgeException e) {
            e.printStackTrace();
        }
        return null;
    }

}