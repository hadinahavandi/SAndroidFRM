package ir.sweetsoft.sweettts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.speech.tts.Voice;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
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
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import christophedelory.playlist.Playlist;
import christophedelory.playlist.SpecificPlaylist;
import christophedelory.playlist.SpecificPlaylistFactory;

public class TTSFragment extends Fragment implements TextToSpeech.OnInitListener{

    private TextToSpeech tts;
    private Button btnSpeak;
    private Button btnSave;
    private Button btnClear;
    private String LastFolderPath="";
    private List<String> LastFilesList= new ArrayList<>();
    private String LastString="";
    private String LastTitle="";
    private String LastUtteranceId="";
    private int SavedFileCount=0;
    private int FailedFileCount=0;
    private boolean LastJobIsFile=true;
    private Voice DefaultVoice=null;

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
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int font_size=Integer.parseInt(prefs.getString("font_size", "10"));
        tts = new TextToSpeech(getActivity(), this);
        btnSpeak=getActivity().findViewById(R.id.btnspeak);
        btnSave=getActivity().findViewById(R.id.btnsave);
        btnClear=getActivity().findViewById(R.id.btn_clear);
        txtText =  getActivity().findViewById(ir.sweetsoft.sweettts.R.id.txttext);
        txtText.setTextSize(TypedValue.COMPLEX_UNIT_SP,font_size);
        txtTitle = getActivity().findViewById(ir.sweetsoft.sweettts.R.id.txttitle);
        txtTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,font_size+1);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakAll(true);
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtText.setText("");
                txtTitle.setText("");
            }
        });
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakAll(false);
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
    private int getStandardPartition(String text,int StartIndex,int PageSize)
    {
        int charindex= text.indexOf(".",StartIndex+PageSize+1);
        if(charindex==-1)
            return text.length();
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
    private void speakAll(boolean saveFile)
    {

        LastJobIsFile=saveFile;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int page_size=Integer.parseInt(prefs.getString("page_size", "2000"));
        boolean SingleLine=prefs.getBoolean("single_line_mode", false);
        boolean remove_all_parenthesis=prefs.getBoolean("remove_all_parenthesis", false);
        boolean remove_year_parenthesis=prefs.getBoolean("remove_year_parenthesis", false);
        String VoiceName=prefs.getString("default_voice", "");
        Set<Voice> voices=tts.getVoices();
        for(Voice voice:voices)
        {
            if(voice.getName().equals(VoiceName)) {
                DefaultVoice = voice;
                Log.d("Found", VoiceName);
            }
        }
        if(DefaultVoice!=null)
            tts.setVoice(DefaultVoice);

        LastFilesList=new ArrayList<>();
        String text = txtText.getText().toString();
        String title=txtTitle.getText().toString();
        SavedFileCount=0;
        FailedFileCount=0;
        LastString=title+"\r\n"+text;
        LastTitle=title;
        text=text.replace("-\r\n","");
        text=text.replace("-\n","");
        text=text.replace("-\r","");
        if(SingleLine)
        {
            text=text.replace("\r\n\r\n",".");
            text=text.replace("\n\n",".");
            text=text.replace("\r\r",".");

            text=text.replace("\r\n"," ");
            text=text.replace("\n"," ");
            text=text.replace("\r"," ");
        }
        text = text.replaceAll("\\[[^\\[\\]]*\\]", " [ one of references ] ");
        if(remove_all_parenthesis)
            text = text.replaceAll("\\([^\\(\\)]*\\)", " (one of notes) ");
        if(remove_year_parenthesis)
            text = text.replaceAll("\\([^\\(\\)]*\\d{4}[^\\(\\)]*\\)", " (one of references) ");
        txtText.setText(text);
        int StartIndex=0;
        int LastIndex=-1;
        int ContextIndex=0;
        if(!saveFile)
        {
            updateProgress();
            btnSpeak.setEnabled(false);
            btnSave.setEnabled(false);
            speakOut(text);
        }
        else
        {
            do{

                ContextIndex++;
                if(StartIndex!=0)
                    LastIndex=StartIndex;
                StartIndex=getStandardPartition(text,StartIndex,page_size);
//                Log.d("Index",StartIndex+" ");

                if((LastIndex!=StartIndex && LastIndex>=-1 && StartIndex>0)||(ContextIndex==1 && StartIndex==0))
                {
                    String StandardText=text;
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
                    String NewText="Start Of the document";
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
                    NewText+="\r\nEnd of the document";
                    if(isLastPart)
                        NewText+="\r\nthis file is made with sweet text to speech app.\r\nfor more information please visit our website:\r\nwww.sweetsoft.ir\r\n";
//                intentMessageTelegram(NewText);
                    Save(title.replace(":","-")+"/",title.replace(":","-")+ContextIndex,NewText);

                }
            }while (StartIndex>0 && StartIndex<text.length() && LastIndex<StartIndex );
            updateProgress();

            btnSpeak.setEnabled(false);
            btnSave.setEnabled(false);
        }

    }
    private void updateProgress()
    {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if(LastJobIsFile)
                    btnSave.setText("Saving("+SavedFileCount+"/"+LastFilesList.size()+")");
                else
                    btnSpeak.setText("Speaking");
            }
        });
    }
    private void Save(String DirectoryName,String FileName,String Text) {

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
//        tts.setSpeechRate(0.83f);
        tts.synthesizeToFile(Text, bundle, theFile, LastUtteranceId);
        writeToFile(LastFolderPath,FileName+".txt",Text,getActivity().getApplicationContext());

        Toast.makeText(getActivity(),"File Generation Started!",Toast.LENGTH_LONG).show();
    }
    private void speakOut(String Text) {

        LastUtteranceId=this.hashCode()+"TTS";
//        tts.setSpeechRate(0.83f);
        tts.speak(Text,TextToSpeech.QUEUE_FLUSH,new Bundle(),LastUtteranceId);
    }
    public void onUtteranceCompleted(String utteranceId) {
        Log.i("Completed!", utteranceId); //utteranceId == "SOME MESSAGE"

        try
        {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    enableSaveButton();
//                    if(LastJobIsFile)
//                        Toast.makeText(getActivity(),"File Generation Completed!",Toast.LENGTH_LONG).show();
//                    else
//                        Toast.makeText(getActivity(),"Speaking Completed!",Toast.LENGTH_LONG).show();
                }
            });
            if(LastJobIsFile)
                openFolder(LastFolderPath);
        }catch (Exception ex){ex.printStackTrace();}

    }
    private void enableSaveButton()
    {

        btnSpeak.setText("Speak");
        btnSpeak.setEnabled(true);

        btnSave.setText("Save");
        btnSave.setEnabled(true);
    }
    public void openFolder(String Path)
    {
        Uri selectedUri = Uri.parse(Path);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(selectedUri, "resource/folder");

        startActivity(intent);
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
//            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
//            String VoiceName=prefs.getString("default_voice", "");
//            Log.d("DefaultVoice",VoiceName);
            Set<Voice> voices=tts.getVoices();
            List<Voice> VoiceList=new ArrayList<>();
            for(Voice voice:voices)
            {
//                Log.d("Locale:",voice.getLocale().getCountry()+"-"+voice.getLocale().getDisplayLanguage()+"-"+voice.getLocale().getISO3Language()+"-"+voice.getLocale().getISO3Country());
                if(voice.getLocale().getISO3Language().toLowerCase().equals("eng") && (voice.getLocale().getISO3Country().toLowerCase().equals("usa") || voice.getLocale().getISO3Country().toLowerCase().equals("gbr")))
                    VoiceList.add(voice);
//                if(voice.getName().equals(VoiceName)) {
//                    DefaultVoice = voice;
//
//                    Log.d("Found", VoiceName);
//                }
            }
            if(getActivity()!=null)
                ((MainActivity)getActivity()).setVoiceList(VoiceList);

//            if(DefaultVoice!=null)
//                tts.setVoice(DefaultVoice);

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
                                    if(LastJobIsFile)
                                        Toast.makeText(getActivity(),"File Generation Failed!",Toast.LENGTH_LONG).show();
                                    else
                                        Toast.makeText(getActivity(),"Speaking Failed!",Toast.LENGTH_LONG).show();
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
