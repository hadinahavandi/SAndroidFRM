package ir.sweetsoft.sweettts;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Locale;

public class TTSFragment extends Fragment implements TextToSpeech.OnInitListener{

    private TextToSpeech tts;
    private Button btnSpeak;
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
        btnSpeak=(Button)getActivity().findViewById(ir.sweetsoft.sweettts.R.id.btnspeack);
        txtText = (EditText) getActivity().findViewById(ir.sweetsoft.sweettts.R.id.txttext);
        txtTitle = (EditText) getActivity().findViewById(ir.sweetsoft.sweettts.R.id.txttitle);
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakAll();
            }
        });

    }

    private int getStandardPartition(String text,int StartIndex)
    {

        int charindex=StartIndex;
        for(int index=0;index<10;index++)
        {
            int tmpcharindex= text.indexOf(".",charindex+1);
            if(tmpcharindex>=0)
                charindex=tmpcharindex;
        }
        return charindex;
    }
    private void speakAll()
    {
        String text = txtText.getText().toString();
        String title=txtTitle.getText().toString();
        text=text.replace("-\r\n","");
        text=text.replace("\n"," ");
        text=text.replace("\r","");
        text=text.replace("-","");
        text = text.replaceAll("\\[.*?]", " [ one of references ] ");
        int StartIndex=0;
        int LastIndex=-1;
        int ContextIndex=0;
        while (StartIndex>=0 && StartIndex<text.length() && LastIndex<StartIndex)
        {
            ContextIndex++;
            if(StartIndex!=0)
                LastIndex=StartIndex;
            StartIndex=getStandardPartition(text,StartIndex);

            if(LastIndex!=StartIndex && LastIndex>=-1 && StartIndex>0)
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
                String NewText="Start Of the document\r\ntitle: "+title.replace(":","-");
                if(ContextIndex!=1)
                {

                    NewText+="\r\npart "+ContextIndex;
                    if(isLastPart)
                        NewText+="(this is the last part)";
                }
                NewText+="\r\n"+StandardText;
                NewText+="\r\nend of the document. \r\n this file is made with sweet text to speech app.\r\n for more information please visit our website:\r\nwww.sweetsoft.ir       \r\n";
                speakOut(title.replace(":","-")+ContextIndex,NewText);

            }
        }

    }
    private void speakOut(String FileName,String Text) {


        String utteranceId=this.hashCode() + "";
        File f=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/SweetTTS/");
        f.mkdir();
        File theFile=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/SweetTTS/"+FileName+".mp3");
        Bundle bundle=new Bundle();
        //tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
        tts.setSpeechRate(0.83f);
        tts.synthesizeToFile(Text, bundle, theFile, utteranceId);
        writeToFile(Environment.getExternalStorageDirectory().getAbsolutePath()+"/SweetTTS/",FileName+".txt",Text,getActivity().getApplicationContext());

        Toast.makeText(getActivity(),"File Generation Started!",Toast.LENGTH_LONG).show();
    }
    private void writeToFile(String FilePath,String FileName,String data,Context context) {
        final File file = new File(FilePath, FileName);

        // Save your stream, don't forget to flush() it before closing it.

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
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
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
