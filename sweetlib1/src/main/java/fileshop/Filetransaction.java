package fileshop;

import android.app.Activity;
import android.util.JsonReader;

import java.io.IOException;
import java.util.List;

import common.Message;
import common.RemoteClass;
import common.SweetDeviceManager;
import ir.sweetsoft.sweetlibone.Activities.Constants;
import ir.sweetsoft.sweetlibone.Activities.MainActivity;
import ir.sweetsoft.sweetlibone.R;

public class Filetransaction extends RemoteClass {
	public Filetransaction(Activity activity){super(activity);}
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFile_fid() {
		return file_fid;
	}

	public void setFile_fid(String file_fid) {
		this.file_fid = file_fid;
	}

	public String getFinance_bankpaymentinfo_fid() {
		return finance_bankpaymentinfo_fid;
	}

	public void setFinance_bankpaymentinfo_fid(String finance_bankpaymentinfo_fid) {
		this.finance_bankpaymentinfo_fid = finance_bankpaymentinfo_fid;
	}

	private String file_fid;
	private String finance_bankpaymentinfo_fid;
	public void getAll(List<Filetransaction> Filetransactions){
		try {
			String DeviceID= SweetDeviceManager.getDeviceID(this.getActivity().getApplicationContext());
			String URL= Constants.SITEURL + "json/fa/fileshop/filetransactionlist.jsp";
			URL+="?deviceid="+DeviceID;
			JsonReader reader=getReader(URL,false,null);
			if(reader.hasNext()) {
			reader.beginArray(); 
			while (reader.hasNext())
			Filetransactions.add(getObject(reader));
		reader.endArray();
		}
		return;
		}catch (IOException e) {
		e.printStackTrace();
		}
		return;
	}
	public Filetransaction getOne(long Id)
	{
		try {
			String DeviceID = SweetDeviceManager.getDeviceID(this.getActivity().getApplicationContext());
			String URL=Constants.SITEURL + "json/fa/fileshop/filetransaction.jsp";
			URL+="?deviceid="+DeviceID+"&id="+String.valueOf(Id);
			JsonReader reader=getReader(URL,false,null);
			return getObject(reader);
		}catch (IOException e) {
		e.printStackTrace();
		}
		return null;
	}
	private Filetransaction getObject(JsonReader reader) throws IOException {
				reader.beginObject();
				Filetransaction theFiletransaction =new Filetransaction(getActivity());
				while (reader.hasNext()) {
					String key = reader.nextName();
					if (key.equals("id")) {theFiletransaction.setId(reader.nextInt());}
					else if (key.equals("file_fid")) {theFiletransaction.setFile_fid(reader.nextString());}
					else if (key.equals("finance_bankpaymentinfo_fid")) {theFiletransaction.setFinance_bankpaymentinfo_fid(reader.nextString());}
				}
			reader.endObject();
				return theFiletransaction;
	}
	public Message Save()
	{
	try {
			String PageURL=Constants.SITEURL + "json/fa/fileshop/managefiletransaction.jsp";
			String Data = "action=btnSave_Click";
					Data+="&id=" + String.valueOf(id);
					Data+="&file_fid=" + String.valueOf(file_fid);
					Data+="&finance_bankpaymentinfo_fid=" + String.valueOf(finance_bankpaymentinfo_fid);
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