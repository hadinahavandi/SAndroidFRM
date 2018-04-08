package common;

import android.app.Activity;
import android.util.JsonReader;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Will on 11/9/2017.
 */

public class RemoteClass {
    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    private Activity activity;
    public RemoteClass(Activity activity)
    {
        setActivity(activity);
    }

    public JsonReader getReader(String PageURL,boolean isPostMethod,String Data) throws IOException {
        URL httpbinEndpoint = null;
        try {
            httpbinEndpoint = new URL(PageURL);
            HttpURLConnection myConnection = null;
            myConnection = (HttpURLConnection) httpbinEndpoint.openConnection();
            if(!isPostMethod)
                myConnection.setRequestMethod("GET");
            else
                myConnection.setRequestMethod("POST");

            myConnection.setDoOutput(true);
            if(Data!=null)
                myConnection.getOutputStream().write(Data.getBytes());
            InputStream responseBody = myConnection.getInputStream();
            BufferedReader r = new BufferedReader(new InputStreamReader(responseBody));
//            StringBuilder total = new StringBuilder();
//            String line;
//            while ((line = r.readLine()) != null) {
//                total.append(line).append('\n');
//            }
//            Log.d("Totla",total.toString());
            JsonReader reader=new JsonReader(r);
            return  reader;
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        return null;
    }
}
