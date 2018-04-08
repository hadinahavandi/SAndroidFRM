package ir.sweetsoft.sweetlibone.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController.MediaPlayerControl;

import ir.sweetsoft.sweetlibone.R;

public class MusicPlayerActivity extends AppCompatActivity  implements MediaPlayerControl{
    private MusicController controller;
    private boolean paused=false, playbackPaused=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_player);
        setController();
    }
    private void setController(){
        controller = new MusicController(this);
        controller.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                playNext();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                playPrev();
            }
        });
        controller.setMediaPlayer(this);
//        controller.setAnchorView(findViewById(R.id.song_list));
        controller.setEnabled(true);
    }
    @Override
    public void start() {

    }

    @Override
    public void pause() {

    }
    @Override
    protected void onPause(){
        super.onPause();
        paused=true;
    }
    @Override
    protected void onResume(){
        super.onResume();
        if(paused){
            setController();
            paused=false;
        }
    }
    @Override
    protected void onStop() {
        controller.hide();
        super.onStop();
    }

    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        return 0;
    }

    @Override
    public void seekTo(int pos) {

    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return false;
    }

    @Override
    public boolean canSeekBackward() {
        return false;
    }

    @Override
    public boolean canSeekForward() {
        return false;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }
}
