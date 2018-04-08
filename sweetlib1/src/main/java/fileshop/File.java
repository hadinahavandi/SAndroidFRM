package fileshop;

import android.app.Activity;
import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import common.Message;
import common.RemoteClass;
import common.SweetDeviceManager;
import ir.sweetsoft.sweetlibone.Activities.Constants;

public class File extends RemoteClass {
	public File(Activity activity){super(activity);}

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getThumbnail_flu() {
		return thumbnail_flu;
	}

	public void setThumbnail_flu(String thumbnail_flu) {
		this.thumbnail_flu = thumbnail_flu;
	}

	public String getAdd_date() {
		return add_date;
	}

	public void setAdd_date(String add_date) {
		this.add_date = add_date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getFilecount() {
		return filecount;
	}

	public void setFilecount(String filecount) {
		this.filecount = filecount;
	}

	public String getRole_systemuser_fid() {
		return role_systemuser_fid;
	}

	public void setRole_systemuser_fid(String role_systemuser_fid) {
		this.role_systemuser_fid = role_systemuser_fid;
	}

	private long id;
	private String file_flu;
	private String title;
	private String thumbnail_flu;
	private String add_date;
	private String description;
	private String price;
	private String filecount;
	private String role_systemuser_fid;
	private String filetype_fid;

    public boolean isPurchased() {
        return isPurchased;
    }

    public void setPurchased(boolean purchased) {
        isPurchased = purchased;
    }

    private boolean isPurchased;

	public String getFiletype_fid() {
		return filetype_fid;
	}

	public void setFiletype_fid(String filetype_fid) {
		this.filetype_fid = filetype_fid;
	}

	public void getAll(List<File> Files){
		try {
			String DeviceID= SweetDeviceManager.getDeviceID(this.getActivity().getApplicationContext());
			String URL= Constants.SITEURL + "json/fa/fileshop/filelist.jsp";
			URL+="?deviceid="+DeviceID;
			Log.d("FLURL",URL);
			JsonReader reader=getReader(URL,false,null);
			if(reader.hasNext()) {
			reader.beginArray(); 
			while (reader.hasNext())
			Files.add(getObject(reader));
		reader.endArray();
		}
		return;
		}catch (IOException e) {
		e.printStackTrace();
		}
		return;
	}
	public Message buy(long FileID,String UserName,String Password)
	{
        try {
            String DeviceID= SweetDeviceManager.getDeviceID(this.getActivity().getApplicationContext());
            String URL= Constants.SITEURL + "json/fa/fileshop/filelist.jsp";
            URL+="?deviceid="+DeviceID;
            URL+="&service=buy";
            URL+="&fileid="+FileID;
            URL+="&username="+UserName;
            URL+="&password="+Password;
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
	public void getCatFiles(long catID,List<File> Files){
		try {
			String DeviceID= SweetDeviceManager.getDeviceID(this.getActivity().getApplicationContext());
			String URL= Constants.SITEURL + "json/fa/fileshop/filelist.jsp?catid="+catID;
			URL+="&deviceid="+DeviceID;
			Log.d("URLCat",URL);
			JsonReader reader=getReader(URL,false,null);
			if(reader.hasNext()) {
				reader.beginArray();
				while (reader.hasNext())
					Files.add(getObject(reader));
				reader.endArray();
			}
			return;
		}catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
	public File getOne(long Id,String UserName,String Password)
	{
		try {
			String DeviceID = SweetDeviceManager.getDeviceID(this.getActivity().getApplicationContext());
			String URL=Constants.SITEURL + "json/fa/fileshop/file.jsp";
			URL+="?deviceid="+DeviceID+"&id="+String.valueOf(Id);
            URL+="&username="+UserName;
            URL+="&password="+Password;
			Log.d("UR:::",URL);
			JsonReader reader=getReader(URL,false,null);
			return getObject(reader);
		}catch (IOException e) {
		e.printStackTrace();
		}
		return null;
	}
	private File getObject(JsonReader reader) throws IOException {
		reader.beginObject();
		File theFile =new File(getActivity());
		while (reader.hasNext()) {
			String key = reader.nextName();
			if (key.equals("id")) {theFile.setId(reader.nextInt());}
			else if (key.equals("file_flu")) {theFile.setFile_flu(reader.nextString());}
			else if (key.equals("title")) {theFile.setTitle(reader.nextString());}
			else if (key.equals("thumbnail_flu")) {theFile.setThumbnail_flu(reader.nextString());}
			else if (key.equals("add_date")) {theFile.setAdd_date(reader.nextString());}
			else if (key.equals("description")) {theFile.setDescription(reader.nextString());}
			else if (key.equals("price")) {theFile.setPrice(reader.nextString());}
			else if (key.equals("filecount")) {theFile.setFilecount(reader.nextString());}
			else if (key.equals("filetype_fid")) {theFile.setFiletype_fid(reader.nextString());}
			else if (key.equals("role_systemuser_fid")) {theFile.setRole_systemuser_fid(reader.nextString());}
            else if (key.equals("ispurchased")) {
               theFile.setPurchased(reader.nextBoolean());
            }
		}
		reader.endObject();
		return theFile;
	}
	public Message Save()
	{
	try {
			String PageURL=Constants.SITEURL + "json/fa/fileshop/managefile.jsp";
			String Data = "action=btnSave_Click";
					Data+="&id=" + String.valueOf(id);
					Data+="&file_flu=" + String.valueOf(file_flu);
					Data+="&title=" + String.valueOf(title);
					Data+="&thumbnail_flu=" + String.valueOf(thumbnail_flu);
					Data+="&add_date=" + String.valueOf(add_date);
					Data+="&description=" + String.valueOf(description);
					Data+="&price=" + String.valueOf(price);
					Data+="&filecount=" + String.valueOf(filecount);
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