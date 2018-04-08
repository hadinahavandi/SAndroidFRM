package ocms;
import android.util.JsonReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import common.SweetDeviceManager;
import common.RemoteClass;
import common.Message;
import ir.sweetsoft.sweetlibone.Activities.Constants;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.util.Log;

public class Doctorreserve extends RemoteClass{
	public Doctorreserve(Activity activity){super(activity);}
	private long id;
	private String doctorplan_fid;
	private String financial_transaction_fid;
	private String presencetype_fid;
	private String StartTime;
    private String User_DoctorName;

    public String getUser_DoctorName() {
        return User_DoctorName;
    }

    public void setUser_DoctorName(String user_DoctorName) {
        User_DoctorName = user_DoctorName;
    }

    public String getUser_DoctorFamily() {
        return User_DoctorFamily;
    }

    public void setUser_DoctorFamily(String user_DoctorFamily) {
        User_DoctorFamily = user_DoctorFamily;
    }

    private String User_DoctorFamily;
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDoctorplan_fid() {
		return doctorplan_fid;
	}

	public void setDoctorplan_fid(String doctorplan_fid) {
		this.doctorplan_fid = doctorplan_fid;
	}

	public String getFinancial_transaction_fid() {
		return financial_transaction_fid;
	}

	public void setFinancial_transaction_fid(String financial_transaction_fid) {
		this.financial_transaction_fid = financial_transaction_fid;
	}

	public String getPresencetype_fid() {
		return presencetype_fid;
	}

	public void setPresencetype_fid(String presencetype_fid) {
		this.presencetype_fid = presencetype_fid;
	}

	public String getReserve_date() {
		return reserve_date;
	}

	public void setReserve_date(String reserve_date) {
		this.reserve_date = reserve_date;
	}

	public String getRole_systemuser_fid() {
		return role_systemuser_fid;
	}

	public void setRole_systemuser_fid(String role_systemuser_fid) {
		this.role_systemuser_fid = role_systemuser_fid;
	}

	private String reserve_date;
	private String role_systemuser_fid;
	public void getAll(List<Doctorreserve> Doctorreserves,String UserName,String Password){
		try {
			String DeviceID= SweetDeviceManager.getDeviceID(this.getActivity().getApplicationContext());
			String URL= Constants.SITEURL + "json/fa/ocms/userreservelist.jsp";
			URL+="?deviceid="+DeviceID;
            URL+="&service=getuserreserves";
			URL+="&username="+UserName;
			URL+="&password="+Password;
			Log.d("UserReservesURL",URL);
			JsonReader reader=getReader(URL,false,null);
			if(reader.hasNext()) {
				reader.beginArray();
				while (reader.hasNext())
					Doctorreserves.add(getObject(reader));
				reader.endArray();
			}
			return;
		}catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
	public Doctorreserve getOne(long Id)
	{
		try {
			String DeviceID = SweetDeviceManager.getDeviceID(this.getActivity().getApplicationContext());
			String URL=Constants.SITEURL + "json/fa/ocms/doctorreserve.jsp";
			URL+="?deviceid="+DeviceID+"&id="+String.valueOf(Id);
			JsonReader reader=getReader(URL,false,null);
			return getObject(reader);
		}catch (IOException e) {
		e.printStackTrace();
		}
		return null;
	}

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    private Doctorreserve getObject(JsonReader reader) throws IOException {
        reader.beginObject();
        Doctorreserve theDoctorreserve =new Doctorreserve(getActivity());
        while (reader.hasNext()) {
            String key = reader.nextName();
            if (key.equals("id")) {theDoctorreserve.setId(reader.nextInt());}
            else if (key.equals("doctorname")) {theDoctorreserve.setUser_DoctorName(reader.nextString());}
            else if (key.equals("doctorfamily")) {theDoctorreserve.setUser_DoctorFamily(reader.nextString());}
            else if (key.equals("presencetype_fid")) {theDoctorreserve.setPresencetype_fid(reader.nextString());}
            else if (key.equals("starttime")) {theDoctorreserve.setStartTime(reader.nextString());}
        }
        reader.endObject();
        return theDoctorreserve;
    }
	public Message Save()
	{
	try {
			String PageURL=Constants.SITEURL + "json/fa/ocms/managedoctorreserve.jsp";
			String Data = "action=btnSave_Click";
					Data+="&id=" + String.valueOf(id);
					Data+="&doctorplan_fid=" + String.valueOf(doctorplan_fid);
					Data+="&financial_transaction_fid=" + String.valueOf(financial_transaction_fid);
					Data+="&presencetype_fid=" + String.valueOf(presencetype_fid);
					Data+="&reserve_date=" + String.valueOf(reserve_date);
					Data+="&role_systemuser_fid=" + String.valueOf(role_systemuser_fid);
			JsonReader reader=getReader(PageURL,true,Data);        
       reader.beginObject();
			Message theMessage =new Message();
			while (reader.hasNext()) {
				String key = reader.nextName();
				if (key.equals("message")) {theMessage.setMessageText(reader.nextString());}
				else if (key.equals("messagetype")) {theMessage.setMessageType(reader.nextInt());}
			}
			reader.endObject();
			return theMessage;
		}catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}