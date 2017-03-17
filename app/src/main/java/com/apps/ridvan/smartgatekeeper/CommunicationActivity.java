package com.apps.ridvan.smartgatekeeper;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import java.util.HashMap;
import java.util.Map;

public class CommunicationActivity extends Activity implements MediaPlayer.OnPreparedListener, SurfaceHolder.Callback {
    private GlobalValues gValues;
    private MediaPlayer _mediaPlayer;
    private SurfaceHolder _surfaceHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gValues = (GlobalValues) getApplication();
        // Set up a full-screen black window.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.setBackgroundDrawableResource(android.R.color.black);

        setContentView(R.layout.activity_communication);

        // Configure the view that renders live video.
        SurfaceView surfaceView =
                (SurfaceView) findViewById(R.id.surfaceView);
        _surfaceHolder = surfaceView.getHolder();
        _surfaceHolder.addCallback(this);
        //_surfaceHolder.setFixedSize(320, 240);
    }

    @Override
    public void surfaceChanged(
            SurfaceHolder sh, int f, int w, int h) {}

    @Override
    public void surfaceCreated(SurfaceHolder sh) {
        _mediaPlayer = new MediaPlayer();
        _mediaPlayer.setDisplay(_surfaceHolder);

        Context context = getApplicationContext();
        String url = gValues.getUrl().substring(0,gValues.getUrl().lastIndexOf(":")+1);
        url+="8081/favicon.ico";

        url = url.replace("https", "http");
        Uri source = Uri.parse(url);
        System.out.println(url);
        try {
            // Specify the IP camera's URL and auth headers.
            _mediaPlayer.setDataSource(context, source);

            // Begin the process of setting up a video stream.
            _mediaPlayer.setOnPreparedListener(this);
            _mediaPlayer.prepareAsync();
        }
        catch (Exception e) {}
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder sh) {
        _mediaPlayer.release();
    }

    /*
MediaPlayer.OnPreparedListener
*/
    @Override
    public void onPrepared(MediaPlayer mp) {
        _mediaPlayer.start();
    }
}