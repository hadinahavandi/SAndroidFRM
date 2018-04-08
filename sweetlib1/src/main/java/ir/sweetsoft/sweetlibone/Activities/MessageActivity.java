package ir.sweetsoft.sweetlibone.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ir.sweetsoft.sweetlibone.R;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Intent i=new Intent(MessageActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}
