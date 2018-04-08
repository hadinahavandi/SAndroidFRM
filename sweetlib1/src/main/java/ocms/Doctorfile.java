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

public class Doctorfile extends RemoteClass{
	public Doctorfile(Activity activity){super(activity);}
	private long id;
	private String file_flu;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFile_flu() {
		return file_flu;
	}

	public void setFile_flu(String file_flu) {
		this.file_flu = file_flu;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDoctor_fid() {
		return doctor_fid;
	}

	public void setDoctor_fid(String doctor_fid) {
		this.doctor_fid = doctor_fid;
	}

	public String getRole_systemuser_fid() {
		return role_systemuser_fid;
	}

	public void setRole_systemuser_fid(String role_systemuser_fid) {
		this.role_systemuser_fid = role_systemuser_fid;
	}

	private String description;
	private String doctor_fid;
	private String role_systemuser_fid;
	public void getAll(List<Doctorfile> Doctorfiles){
		try {
			String DeviceID= SweetDeviceManager.getDeviceID(this.getActivity().getApplicationContext());
			String URL= Constants.SITEURL + "json/fa/ocms/doctorfilelist.jsp";
			URL+="?deviceid="+DeviceID;
			JsonReader reader=getReader(URL,false,null);
			if(reader.hasNext()) {
			reader.beginArray(); 
			while (reader.hasNext())
			Doctorfiles.add(getObject(reader));
		reader.endArray();
		}
		return;
		}catch (IOException e) {
		e.printStackTrace();
		}
		return;
	}
	public void getAll(List<Doctorfile> Doctorfiles,long DoctorID){
		try {
			String DeviceID= SweetDeviceManager.getDeviceID(this.getActivity().getApplicationContext());
			String URL= Constants.SITEURL + "json/fa/ocms/doctorfilelist.jsp";
			URL+="?deviceid="+DeviceID;
			URL+="&doctorid="+DoctorID;
            Log.d("DoctorFileGetURL",URL);
			JsonReader reader=getReader(URL,false,null);
			if(reader.hasNext())
			{
                Log.d("Adding","DoctorFile");
				reader.beginArray();
				while (reader.hasNext())
                {
                    Log.d("Adding","DoctorFile");
                    Doctorfiles.add(getObject(reader));
                }
				reader.endArray();
			}
			return;
		}catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
	public Doctorfile getOne(long Id)
	{
		try {
			String DeviceID = SweetDeviceManager.getDeviceID(this.getActivity().getApplicationContext());
			String URL=Constants.SITEURL + "json/fa/ocms/doctorfile.jsp";
			URL+="?deviceid="+DeviceID+"&id="+String.valueOf(Id);
			JsonReader reader=getReader(URL,false,null);
			return getObject(reader);
		}catch (IOException e) {
		e.printStackTrace();
		}
		return null;
	}
	private Doctorfile getObject(JsonReader reader) throws IOException {
				reader.beginObject();
				Doctorfile theDoctorfile =new Doctorfile(getActivity());
				while (reader.hasNext()) {
					String key = reader.nextName();
					if (key.equals("id")) {theDoctorfile.setId(reader.nextInt());}
					else if (key.equals("file_flu")) {theDoctorfile.setFile_flu(reader.nextString());}
					else if (key.equals("description")) {theDoctorfile.setDescription(reader.nextString());}
					else if (key.equals("doctor_fid")) {theDoctorfile.setDoctor_fid(reader.nextString());}
					else if (key.equals("role_systemuser_fid")) {theDoctorfile.setRole_systemuser_fid(reader.nextString());}
				}
			reader.endObject();
				return theDoctorfile;
	}
	public Message Save()
	{
	try {
			String PageURL=Constants.SITEURL + "json/fa/ocms/managedoctorfile.jsp";
			String Data = "action=btnSave_Click";
					Data+="&id=" + String.valueOf(id);
					Data+="&file_flu=" + String.valueOf(file_flu);
					Data+="&description=" + String.valueOf(description);
					Data+="&doctor_fid=" + String.valueOf(doctor_fid);
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