package com.apps.ridvan.smartgatekeeper;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import java.util.HashMap;
import java.util.Map;

public class CommunicationActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener, SurfaceHolder.Callback {
    private String RTSP_URL;
    private GlobalValues gValues;
    private MediaPlayer _mediaPlayer;
    private SurfaceHolder _surfaceHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gValues = (GlobalValues) getApplication();
        RTSP_URL = gValues.getUrl();
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
        _surfaceHolder.setFixedSize(320, 240);
    }

    private Map<String, String> getRtspHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        //String basicAuthValue = getBasicAuthValue(USERNAME, PASSWORD);
        //headers.put("Authorization", basicAuthValue);
        return headers;
    }

    private String getBasicAuthValue(String usr, String pwd) {
        String credentials = usr + ":" + pwd;
        int flags = Base64.URL_SAFE | Base64.NO_WRAP;
        byte[] bytes = credentials.getBytes();
        return "Basic " + Base64.encodeToString(bytes, flags);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        _mediaPlayer.start();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}