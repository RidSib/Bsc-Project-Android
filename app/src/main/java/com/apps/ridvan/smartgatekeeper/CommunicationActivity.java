package com.apps.ridvan.smartgatekeeper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.apps.ridvan.smartgatekeeper.model.Function;
import com.apps.ridvan.smartgatekeeper.model.FunctionListData;
import com.apps.ridvan.smartgatekeeper.utils.HttpHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CommunicationActivity extends AppCompatActivity {
    GlobalValues gValues;
    VideoView streamView;
    MediaController mediaController;
    private Semaphore mutex = new Semaphore(1);
    private boolean isSuccessful;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication);
        gValues = (GlobalValues) getApplication();
        streamView = (VideoView) findViewById(R.id.streamview);
        playStream();

    }

    private void playStream() {
        String url = gValues.getUrl().substring(0, gValues.getUrl().lastIndexOf(":") + 1);
        url += "8090/";
        url = url.replace("https", "http");
        Uri UriSrc = Uri.parse(url);
        streamView.setVideoURI(UriSrc);
        mediaController = new MediaController(this);
        streamView.setMediaController(mediaController);
        streamView.start();
        RelativeLayout parentLayout = (RelativeLayout) findViewById(R.id.stream_form);
        isSuccessful = false;
        FunctionListData fList = gValues.getFunctionList();
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.stream_linear_form);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ArrayList<Button> buttonList = new ArrayList<>();
        for (final Function function : fList.getFunctions()) {
            if (function.getType() == 2)
                continue;
            Button b = new Button(this);
            b.setLayoutParams(params);
            b.setText(function.getName());
            linearLayout.addView(b);
            buttonList.add(b);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    functionCall(function);
                }
            });
        }
        Button back = (Button) findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                functionCall(null);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        streamView.stopPlayback();
    }

    private boolean functionCall(Function function) {
        try {
            if(function == null){
                Intent intent = new Intent(CommunicationActivity.this, FunctionsActivity.class);
                startActivity(intent);
            }
            mutex.acquire();
            FunctionActivationTask activation = new FunctionActivationTask();
            activation.execute(gValues.getUrl(), gValues.getLogin(), gValues.getPassword(), function.getId() + "");
            mutex.acquire();
            if (isSuccessful) {
                Toast.makeText(this, "Function " + function.getName() + " has activated!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Function " + function.getName() + " has failed!", Toast.LENGTH_SHORT).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mutex.release();
        return false;
    }

    private class FunctionActivationTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            OkHttpClient client = HttpHelper.getUnsafeOkHttpClient();
            String json = "{\"login\": \"" + params[1] + "\", \"password\": \"" + params[2] + ", \"function\": " + params[3] + "}";
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
            Request request = new Request.Builder()
                    .url(params[0])
                    .post(body)
                    .build();
            System.out.println(request.toString());
            System.out.println(json);
            try (Response response = client.newCall(request).execute()) {
                isSuccessful = true;
                mutex.release();
                return response.isSuccessful();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mutex.release();
            return false;
        }
    }
}