package ocms;

import android.app.Activity;
import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import common.Message;
import common.RemoteClass;
import common.SweetDeviceManager;
import ir.sweetsoft.sweetlibone.Activities.Constants;

public class Finance extends RemoteClass{
	public Finance(Activity activity){super(activity);}

	public long getUserBalance(String Username,String Password){
		try {
			String URL=Constants.SITEURL + "json/fa/finance/userbalance.jsp?service=getbalance";
			URL+="&username="+Username;
			URL+="&password="+Password;
			Log.d("Tran",URL);
			long balance=0;
			JsonReader reader=getReader(URL,false,null);
//			Log.d("JSPN",reader.toString());
			if(reader.hasNext()) {
			reader.beginArray();
				while (reader.hasNext()) {
					reader.beginObject();

					while (reader.hasNext()) {
						String key = reader.nextName();
						Log.d("Name", key);
						if (key.equals("balance")) {
							balance = reader.nextLong();
						}
					}
					reader.endObject();
				}
		reader.endArray();
		}
		return balance;
		}catch (IOException e) {
		e.printStackTrace();
		}
		return -1;
	}


}