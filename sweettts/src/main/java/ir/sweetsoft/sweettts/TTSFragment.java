package ir.sweetsoft.sweettts;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagOptionSingleton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import christophedelory.playlist.Playlist;
import christophedelory.playlist.SpecificPlaylist;
import christophedelory.playlist.SpecificPlaylistFactory;

public class TTSFragment extends Fragment implements TextToSpeech.OnInitListener{

    private TextToSpeech tts;
    private Button btnSpeak;
    private String LastFolderPath="";
    private List<String> LastFilesList= new ArrayList<>();
    private String LastString="";
    private String LastTitle="";
    private String LastUtteranceId="";
    private int SavedFileCount=0;
    private int FailedFileCount=0;
    private EditText txtText,txtTitle;

    private OnFragmentInteractionListener mListener;

    public TTSFragment() {
    }
    public static TTSFragment newInstance(String param1, String param2) {
        TTSFragment fragment = new TTSFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tts = new TextToSpeech(getActivity(), this);
        btnSpeak=getActivity().findViewById(ir.sweetsoft.sweettts.R.id.btnspeack);
        txtText =  getActivity().findViewById(ir.sweetsoft.sweettts.R.id.txttext);
        txtTitle = getActivity().findViewById(ir.sweetsoft.sweettts.R.id.txttitle);
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakAll();
            }
        });

    }
    public static boolean isAppAvailable(Context context, String appName)
    {
        PackageManager pm = context.getPackageManager();
        try
        {
            pm.getPackageInfo(appName, PackageManager.GET_ACTIVITIES);
            return true;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            return false;
        }
    }
    private void share(int i)
    {

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        File file = new File(LastFilesList.get(i));
        Uri uri = Uri.fromFile(file);
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("audio/*");
        share.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(share,
                LastTitle));
    }
//    void intentMessageTelegram()
//    {
//        for(int i=0;i<1 && i<LastFilesList.size();i++)
//        {
//            share(i);
//        }
//    }
    private int getStandardPartition(String text,int StartIndex)
    {

        int charindex=StartIndex;
        for(int index=0;index<15;index++)
        {
            int tmpcharindex= text.indexOf(".",charindex+1);
            if(tmpcharindex>=0)
                charindex=tmpcharindex;
        }
        return charindex;
    }
    private String getTime()
    {

        Calendar stc = Calendar.getInstance();
        String dayLongName = stc.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        String monthLongName = stc.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());;
        String date=dayLongName+" "+monthLongName+" "+stc.get(Calendar.DAY_OF_MONTH)+" "+stc.get(Calendar.YEAR)+" "+stc.get(Calendar.HOUR_OF_DAY)+":"+stc.get(Calendar.MINUTE)+" ";

        return date;
    }
    private void speakAll()
    {
        LastFilesList=new ArrayList<>();
        String text = txtText.getText().toString();
        String title=txtTitle.getText().toString();
        SavedFileCount=0;
        FailedFileCount=0;
        LastString=title+"\r\n"+text;
        LastTitle=title;
        text=text.replace("-\r\n","");
        text=text.replace("\n"," ");
        text=text.replace("\r","");
//        text=text.replace("-","");
        text = text.replaceAll("\\[.*?]", " [ one of references ] ");
        int StartIndex=0;
        int LastIndex=-1;
        int ContextIndex=0;

        do{

            ContextIndex++;
            if(StartIndex!=0)
                LastIndex=StartIndex;
            StartIndex=getStandardPartition(text,StartIndex);
            Log.d("Index",StartIndex+" ");

            if((LastIndex!=StartIndex && LastIndex>=-1 && StartIndex>0)||(ContextIndex==1 && StartIndex==0))
            {
                String StandardText=text;
//                Log.d("SS",StartIndex+":"+LastIndex);
                boolean isLastPart=false;
                if(StartIndex>0)
                {
                    if(text.indexOf(".",StartIndex+1)<0) {
                        StandardText = text.substring(LastIndex + 1);
                        isLastPart=true;
                    }
                    else
                        StandardText=text.substring(LastIndex+1,StartIndex+1);
                }
                String NewText="<<Start Of the document>>";
                if(ContextIndex!=1)
                {
                    NewText+="\r\nPart "+ContextIndex;
                    if(isLastPart)
                        NewText+="(this is the last part)";
                }
                else
                {
                    NewText+="\r\nTitle: "+title.replace(":","-");
                    NewText+="\r\nCreation Time: "+getTime();
                    NewText+="\r\nFirst Part";
                }
                NewText+="\r\n"+StandardText;
                NewText+="\r\n<<End of the document>>";
                if(isLastPart)
                    NewText+="\r\nthis file is made with sweet text to speech app.\r\nfor more information please visit our website:\r\nwww.sweetsoft.ir\r\n";
//                intentMessageTelegram(NewText);
                speakOut(title.replace(":","-")+"/",title.replace(":","-")+ContextIndex,NewText);

            }
        }while (StartIndex>0 && StartIndex<text.length() && LastIndex<StartIndex );
        updateProgress();

        btnSpeak.setEnabled(false);
    }
    private void updateProgress()
    {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                btnSpeak.setText("Saving("+SavedFileCount+"/"+LastFilesList.size()+")");
            }
        });
    }
    private void speakOut(String DirectoryName,String FileName,String Text) {

        LastFolderPath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/SweetTTS/"+DirectoryName;
        LastUtteranceId=this.hashCode() +DirectoryName +FileName;
        File f=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/SweetTTS/");
        f.mkdir();
        File f2=new File(LastFolderPath);
        f2.mkdir();
        String FilePath=LastFolderPath+FileName+".mp3";
        LastFilesList.add(FileName);
        File theFile=new File(FilePath);
        Bundle bundle=new Bundle();
        tts.setSpeechRate(0.83f);
        tts.synthesizeToFile(Text, bundle, theFile, LastUtteranceId);
        writeToFile(LastFolderPath,FileName+".txt",Text,getActivity().getApplicationContext());

        Toast.makeText(getActivity(),"File Generation Started!",Toast.LENGTH_LONG).show();
    }
    public void onUtteranceCompleted(String utteranceId) {
        Log.i("Completed!", utteranceId); //utteranceId == "SOME MESSAGE"

        try
        {
//            intentMessageTelegram();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    enableSaveButton();
                    Toast.makeText(getActivity(),"File Generation Completed!",Toast.LENGTH_LONG).show();
                }
            });
            openFolder(LastFolderPath);


//            File file = new File(LastFolderPath+"playlist.asx");
//            SpecificPlaylist specificPlaylist = SpecificPlaylistFactory.getInstance().readFrom(file);
//            Playlist genericPlaylist = specificPlaylist.toPlaylist();
//            for(int i=0;i<LastFilesList.size();i++)
//            {
//
//            }
//            for(int i=0;i<LastFilesList.size();i++)
//            {
//
//                TagOptionSingleton.getInstance().setAndroid(true);
//                Log.d("Folder:",LastFolderPath);
//                File tempFile = new File(LastFolderPath, LastFilesList.get(i) + ".mp3");
//                AudioFile audioFile = AudioFileIO.read(tempFile);
//                Tag tag = audioFile.getTag();
//                tag.setField(FieldKey.ARTIST, "SweetTTS"); // when i open music app the artist is "unknown"
//                tag.setField(FieldKey.ALBUM_ARTIST, "SweetSoft"); // when i open music app the artist is "unknown"
//                tag.setField(FieldKey.ALBUM, LastTitle); // when i open music app the artist is "unknown"
//                tag.setField(FieldKey.TITLE, LastTitle); // when i open music app the artist is "unknown"
//                tag.setField(FieldKey.COMMENT, "This file is made with SweetTTS android app,for more info visit www.sweetsoft.ir"); // when i open music app the artist is "unknown"
//                audioFile.setTag(tag); // even without this i'm getting the same result
//                audioFile.commit();
//                AudioFileIO.write(audioFile);
//            }
        }catch (Exception ex){ex.printStackTrace();}

    }
    private void enableSaveButton()
    {

        btnSpeak.setText("Save");
        btnSpeak.setEnabled(true);
    }
    public void openFolder(String Path)
    {
        Uri selectedUri = Uri.parse(Path);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(selectedUri, "resource/folder");

        startActivity(intent);
//        if (intent.resolveActivityInfo(getActivity().getPackageManager(), 0) != null)
//        {
////            startActivity(intent);
//        }
//        else
//        {
//            // if you reach this place, it means there is no any file
//            // explorer app installed on your device
//        }
//        File file = new File(Path);
//
////        Log.d("path", file.toString());
//
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setDataAndType(Uri.fromFile(file), "*/*");
//        startActivity(intent);
    }
    private void writeToFile(String FilePath,String FileName,String data,Context context) {
        final File file = new File(FilePath, FileName);

        try
        {
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(data);

            myOutWriter.close();

            fOut.flush();
            fOut.close();
        }
        catch (IOException e)
        {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                tts.setOnUtteranceProgressListener(new UtteranceProgressListener()
                {
                    @Override
                    public void onDone(String utteranceId)
                    {
                        SavedFileCount++;
                        updateProgress();
                        if(LastUtteranceId.equals(utteranceId))
                            onUtteranceCompleted(utteranceId);
                    }

                    @Override
                    public void onError(String utteranceId)
                    {
                        FailedFileCount++;
                        try {

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    enableSaveButton();
                                    Toast.makeText(getActivity(),"File Generation Failed!",Toast.LENGTH_LONG).show();
                                }
                            });
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onStart(String utteranceId)
                    {
                    }
                });
//                speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
