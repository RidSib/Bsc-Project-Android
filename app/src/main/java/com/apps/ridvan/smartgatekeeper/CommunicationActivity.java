package com.apps.ridvan.smartgatekeeper;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.VideoView;

public class CommunicationActivity extends AppCompatActivity {
    final static String RTSP_URL = "http://192.168.43.148:8090/";

    VideoView streamView;
    MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication);

        streamView = (VideoView)findViewById(R.id.streamview);
        playStream();

    }

    private void playStream(){
        Uri UriSrc = Uri.parse(RTSP_URL);
            streamView.setVideoURI(UriSrc);
            mediaController = new MediaController(this);
            streamView.setMediaController(mediaController);
            streamView.start();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        streamView.stopPlayback();
    }
}