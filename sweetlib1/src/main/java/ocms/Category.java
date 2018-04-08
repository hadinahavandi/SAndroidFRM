package ocms;
import android.util.JsonReader;

import java.io.IOException;

import common.SweetDeviceManager;
import common.RemoteClass;
import common.Message;
import ir.sweetsoft.sweetlibone.Activities.Constants;

import java.util.List;
import android.app.Activity;
import android.util.Log;

public class Category extends RemoteClass{
	public Category(Activity activity){super(activity);}
	private long id;
	private String title;
	private String latintitle;
	private String category_fid;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLatintitle() {
		return latintitle;
	}

	public void setLatintitle(String latintitle) {
		this.latintitle = latintitle;
	}

	public String getCategory_fid() {
		return category_fid;
	}

	public void setCategory_fid(String category_fid) {
		this.category_fid = category_fid;
	}

	public void getAll(List<Category> Categorys){
		try {
			String DeviceID= SweetDeviceManager.getDeviceID(this.getActivity().getApplicationContext());
			String URL= Constants.SITEURL + "json/fa/common/categorylist.jsp";
			URL+="?deviceid="+DeviceID;
			Log.d("URL",URL);
			JsonReader reader=getReader(URL,false,null);
			if(reader.hasNext()) {
			reader.beginArray(); 
			while (reader.hasNext())
			Categorys.add(getObject(reader));
		reader.endArray();
		}
		return;
		}catch (IOException e) {
		e.printStackTrace();
		}
		return;
	}
	public Category getOne(long Id)
	{
		try {
			String DeviceID = SweetDeviceManager.getDeviceID(this.getActivity().getApplicationContext());
			String URL=Constants.SITEURL + "json/fa/common/category.jsp";
			URL+="?deviceid="+DeviceID+"&id="+String.valueOf(Id);
			JsonReader reader=getReader(URL,false,null);
			return getObject(reader);
		}catch (IOException e) {
		e.printStackTrace();
		}
		return null;
	}
	private Category getObject(JsonReader reader) throws IOException {
				reader.beginObject();
				Category theCategory =new Category(getActivity());
				while (reader.hasNext()) {
					String key = reader.nextName();
					if (key.equals("id")) {theCategory.setId(reader.nextInt());}
					else if (key.equals("title")) {theCategory.setTitle(reader.nextString());}
					else if (key.equals("latintitle")) {theCategory.setLatintitle(reader.nextString());}
					else if (key.equals("category_fid")) {theCategory.setCategory_fid(reader.nextString());}
				}
			reader.endObject();
				return theCategory;
	}
	public Message Save()
	{
	try {
			String PageURL=Constants.SITEURL + "json/fa/common/managecategory.jsp";
			String Data = "action=btnSave_Click";
					Data+="&id=" + String.valueOf(id);
					Data+="&title=" + String.valueOf(title);
					Data+="&latintitle=" + String.valueOf(latintitle);
					Data+="&category_fid=" + String.valueOf(category_fid);
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