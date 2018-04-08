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

public class Doctorplan extends RemoteClass{
	public Doctorplan(Activity activity){super(activity);}
	private long id;

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	private int price;

	public int getOff() {
		return off;
	}

	public void setOff(int off) {
		this.off = off;
	}

	private int off;
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getDoctor_fid() {
		return doctor_fid;
	}

	public void setDoctor_fid(String doctor_fid) {
		this.doctor_fid = doctor_fid;
	}

	private String start_time;
	private String end_time;
	private String doctor_fid;
	public void getAll(List<Doctorplan> Doctorplans){
		try {
			String DeviceID= SweetDeviceManager.getDeviceID(this.getActivity().getApplicationContext());
			String URL= Constants.SITEURL + "json/fa/ocms/doctorplanlist.jsp";
			URL+="?deviceid="+DeviceID;
			JsonReader reader=getReader(URL,false,null);
			if(reader.hasNext()) {
			reader.beginArray(); 
			while (reader.hasNext())
			Doctorplans.add(getObject(reader));
		reader.endArray();
		}
		return;
		}catch (IOException e) {
		e.printStackTrace();
		}
		return;
	}

    public Message reserve(long DoctorPlanID,String UserName,String Password,int PresenceTypeID){
        try {
            String DeviceID= SweetDeviceManager.getDeviceID(this.getActivity().getApplicationContext());
            String URL= Constants.SITEURL + "json/fa/ocms/doctorplanlist.jsp";
            URL+="?deviceid="+DeviceID;
            URL+="&service=reserve";
            URL+="&doctorplanid="+DoctorPlanID;
			URL+="&username="+UserName;
			URL+="&password="+Password;
            URL+="&presencetypeid="+PresenceTypeID;
            Log.d("URL:",URL);
            JsonReader reader=getReader(URL,false,null);
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
	public void getAll(long DoctorID,int Year,int Month,int Day,List<Doctorplan> Doctorplans){
		try {
			String DeviceID= SweetDeviceManager.getDeviceID(this.getActivity().getApplicationContext());
			String URL= Constants.SITEURL + "json/fa/ocms/doctorplanlist.jsp";
			URL+="?deviceid="+DeviceID;
            URL+="&service=getdoctorfreeplans";
			URL+="&y="+Year;
			URL+="&m="+Month;
			URL+="&d="+Day;
            URL+="&doctorid="+DoctorID;
            Log.d("URL:",URL);
            JsonReader reader=getReader(URL,false,null);
			if(reader.hasNext()) {
				reader.beginArray();
				while (reader.hasNext())
					Doctorplans.add(getObject(reader));
				reader.endArray();
			}
			return;
		}catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
	public Doctorplan getOne(long Id)
	{
		try {
			String DeviceID = SweetDeviceManager.getDeviceID(this.getActivity().getApplicationContext());
			String URL=Constants.SITEURL + "json/fa/ocms/doctorplan.jsp";
			URL+="?deviceid="+DeviceID+"&id="+String.valueOf(Id);
			JsonReader reader=getReader(URL,false,null);
			return getObject(reader);
		}catch (IOException e) {
		e.printStackTrace();
		}
		return null;
	}
	private Doctorplan getObject(JsonReader reader) throws IOException {
				reader.beginObject();
				Doctorplan theDoctorplan =new Doctorplan(getActivity());
				while (reader.hasNext()) {
					String key = reader.nextName();
					if (key.equals("id")) {theDoctorplan.setId(reader.nextInt());}
					else if (key.equals("start_time")) {theDoctorplan.setStart_time(reader.nextString());}
					else if (key.equals("end_time")) {theDoctorplan.setEnd_time(reader.nextString());}
					else if (key.equals("doctor_fid")) {theDoctorplan.setDoctor_fid(reader.nextString());}
					else if (key.equals("price")) {theDoctorplan.setPrice(reader.nextInt());}
					else if (key.equals("off")) {theDoctorplan.setOff(reader.nextInt());}
				}
			reader.endObject();
				return theDoctorplan;
	}
	public Message Save()
	{
	try {
			String PageURL=Constants.SITEURL + "json/fa/ocms/managedoctorplan.jsp";
			String Data = "action=btnSave_Click";
					Data+="&id=" + String.valueOf(id);
					Data+="&start_time=" + String.valueOf(start_time);
					Data+="&end_time=" + String.valueOf(end_time);
					Data+="&doctor_fid=" + String.valueOf(doctor_fid);
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