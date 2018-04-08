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

public class Doctor extends RemoteClass{
	public Doctor(Activity activity){super(activity);}
	private long id;
	private String name;
	private String family;
	private String nezam_code;
	private String mellicode;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public String getNezam_code() {
		return nezam_code;
	}

	public void setNezam_code(String nezam_code) {
		this.nezam_code = nezam_code;
	}

	public String getMellicode() {
		return mellicode;
	}

	public void setMellicode(String mellicode) {
		this.mellicode = mellicode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getIsmale() {
		return ismale;
	}

	public void setIsmale(String ismale) {
		this.ismale = ismale;
	}

	public String getSpeciality_fid() {
		return speciality_fid;
	}

	public void setSpeciality_fid(String speciality_fid) {
		this.speciality_fid = speciality_fid;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getMatabtel() {
		return matabtel;
	}

	public void setMatabtel(String matabtel) {
		this.matabtel = matabtel;
	}

	public String getMatabaddress() {
		return matabaddress;
	}

	public void setMatabaddress(String matabaddress) {
		this.matabaddress = matabaddress;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getCommon_city_fid() {
		return common_city_fid;
	}

	public void setCommon_city_fid(String common_city_fid) {
		this.common_city_fid = common_city_fid;
	}

	public String getIsactiveonphone() {
		return isactiveonphone;
	}

	public void setIsactiveonphone(String isactiveonphone) {
		this.isactiveonphone = isactiveonphone;
	}

	public String getIsactiveonplace() {
		return isactiveonplace;
	}

	public void setIsactiveonplace(String isactiveonplace) {
		this.isactiveonplace = isactiveonplace;
	}

	public String getIsactiveonhome() {
		return isactiveonhome;
	}

	public void setIsactiveonhome(String isactiveonhome) {
		this.isactiveonhome = isactiveonhome;
	}

	public String getPhoto_flu() {
		return photo_flu;
	}

	public void setPhoto_flu(String photo_flu) {
		this.photo_flu = photo_flu;
	}

	public String getRole_systemuser_fid() {
		return role_systemuser_fid;
	}

	public void setRole_systemuser_fid(String role_systemuser_fid) {
		this.role_systemuser_fid = role_systemuser_fid;
	}

	private String mobile;
	private String email;
	private String tel;
	private String ismale;
	private String speciality_fid;
	private String speciality;
	private String education;
	private String matabtel;
	private String matabaddress;
	private String longitude;
	private String latitude;
	private String common_city_fid;
	private String isactiveonphone;
	private String isactiveonplace;

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	private String isactiveonhome;
	private String photo_flu;
	private String price;
	private String role_systemuser_fid;
	public void getAll(List<Doctor> Doctors){
		try {
			String DeviceID= SweetDeviceManager.getDeviceID(this.getActivity().getApplicationContext());
			String URL= Constants.SITEURL + "json/fa/ocms/doctorlist.jsp";
			URL+="?deviceid="+DeviceID;
			Log.d("Tran",URL);
			JsonReader reader=getReader(URL,false,null);
			if(reader.hasNext()) {
			reader.beginArray(); 
			while (reader.hasNext())
			Doctors.add(getObject(reader));
		reader.endArray();
		}
		return;
		}catch (IOException e) {
		e.printStackTrace();
		}
		return;
	}
	public void getAll(Long SpecialityID,int PresenceType,List<Doctor> Doctors){
		try {
			String DeviceID= SweetDeviceManager.getDeviceID(this.getActivity().getApplicationContext());
			String URL= Constants.SITEURL + "json/fa/ocms/doctorlist.jsp";
			URL+="?deviceid="+DeviceID;
            URL+="&specialityid="+String.valueOf(SpecialityID);
            URL+="&presencetypeid="+String.valueOf(PresenceType);
			Log.d("Tran",URL);
			JsonReader reader=getReader(URL,false,null);
			if(reader.hasNext()) {
				reader.beginArray();
				while (reader.hasNext())
					Doctors.add(getObject(reader));
				reader.endArray();
			}
			return;
		}catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
	public Doctor getOne(long Id)
	{
		try {
			String DeviceID = SweetDeviceManager.getDeviceID(this.getActivity().getApplicationContext());
			String URL=Constants.SITEURL + "json/fa/ocms/doctor.jsp";
			URL+="?deviceid="+DeviceID+"&id="+String.valueOf(Id);
			Log.d("URL::",URL);
			JsonReader reader=getReader(URL,false,null);
			return getObject(reader);
		}catch (IOException e) {
		e.printStackTrace();
		}
		return null;
	}

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getSpeciality() {
        return speciality;
    }

    private Doctor getObject(JsonReader reader) throws IOException {
				reader.beginObject();
				Doctor theDoctor =new Doctor(getActivity());
				while (reader.hasNext()) {
					String key = reader.nextName();
					Log.d("Name",key);
					if (key.equals("id")) {theDoctor.setId(reader.nextInt());}
					else if (key.equals("name")) {theDoctor.setName(reader.nextString());}
					else if (key.equals("family")) {theDoctor.setFamily(reader.nextString());}
					else if (key.equals("nezam_code")) {theDoctor.setNezam_code(reader.nextString());}
					else if (key.equals("mellicode")) {theDoctor.setMellicode(reader.nextString());}
					else if (key.equals("mobile")) {theDoctor.setMobile(reader.nextString());}
					else if (key.equals("email")) {theDoctor.setEmail(reader.nextString());}
					else if (key.equals("tel")) {theDoctor.setTel(reader.nextString());}
					else if (key.equals("ismale")) {theDoctor.setIsmale(reader.nextString());}
					else if (key.equals("speciality_fid")) {theDoctor.setSpeciality_fid(reader.nextString());}
                    else if (key.equals("speciality")) {theDoctor.setSpeciality(reader.nextString());}
					else if (key.equals("education")) {theDoctor.setEducation(reader.nextString());}
					else if (key.equals("matabtel")) {theDoctor.setMatabtel(reader.nextString());}
					else if (key.equals("matabaddress")) {theDoctor.setMatabaddress(reader.nextString());}
					else if (key.equals("longitude")) {theDoctor.setLongitude(reader.nextString());}
					else if (key.equals("latitude")) {theDoctor.setLatitude(reader.nextString());}
					else if (key.equals("common_city_fid")) {theDoctor.setCommon_city_fid(reader.nextString());}
					else if (key.equals("isactiveonphone")) {theDoctor.setIsactiveonphone(reader.nextString());}
					else if (key.equals("isactiveonplace")) {theDoctor.setIsactiveonplace(reader.nextString());}
					else if (key.equals("isactiveonhome")) {theDoctor.setIsactiveonhome(reader.nextString());}
					else if (key.equals("photo_flu")) {theDoctor.setPhoto_flu(reader.nextString());}
					else if (key.equals("price")) {theDoctor.setPrice(reader.nextString());}
					else if (key.equals("role_systemuser_fid")) {theDoctor.setRole_systemuser_fid(reader.nextString());}
				}
			reader.endObject();
		Log.d("Photo",theDoctor.getPhoto_flu());
				return theDoctor;
	}

	public Message Save()
	{
	try {
			String PageURL=Constants.SITEURL + "json/fa/ocms/managedoctor.jsp";
			String Data = "action=btnSave_Click";
					Data+="&id=" + String.valueOf(id);
					Data+="&name=" + String.valueOf(name);
					Data+="&family=" + String.valueOf(family);
					Data+="&nezam_code=" + String.valueOf(nezam_code);
					Data+="&mellicode=" + String.valueOf(mellicode);
					Data+="&mobile=" + String.valueOf(mobile);
					Data+="&email=" + String.valueOf(email);
					Data+="&tel=" + String.valueOf(tel);
					Data+="&ismale=" + String.valueOf(ismale);
					Data+="&speciality_fid=" + String.valueOf(speciality_fid);
					Data+="&education=" + String.valueOf(education);
					Data+="&matabtel=" + String.valueOf(matabtel);
					Data+="&matabaddress=" + String.valueOf(matabaddress);
					Data+="&longitude=" + String.valueOf(longitude);
					Data+="&latitude=" + String.valueOf(latitude);
					Data+="&common_city_fid=" + String.valueOf(common_city_fid);
					Data+="&isactiveonphone=" + String.valueOf(isactiveonphone);
					Data+="&isactiveonplace=" + String.valueOf(isactiveonplace);
					Data+="&isactiveonhome=" + String.valueOf(isactiveonhome);
					Data+="&photo_flu=" + String.valueOf(photo_flu);
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