package com.apps.ridvan.smartgatekeeper;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.ridvan.smartgatekeeper.model.Function;
import com.apps.ridvan.smartgatekeeper.model.FunctionListData;
import com.apps.ridvan.smartgatekeeper.utils.HttpHelper;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FunctionsActivity extends AppCompatActivity {

    private GlobalValues gValues;
    private Semaphore mutex = new Semaphore(1);
    private boolean isSuccessful;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_functions);
        gValues = (GlobalValues) getApplication();
        FunctionListData fList = gValues.getFunctionList();
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main_functions_form);
        isSuccessful = false;


        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ArrayList<Button> buttonList = new ArrayList<>();
        for (final Function function : fList.getFunctions()) {
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
    }

    private boolean functionCall(Function function) {
        try {
            mutex.acquire();
            FunctionActivationTask activation = new FunctionActivationTask();
            activation.execute(gValues.getUrl(), gValues.getLogin(), gValues.getPassword(), function.getId()+"");
            mutex.acquire();
            if(isSuccessful){
                Toast.makeText(this, "Function "+function.getName()+" has activated!", Toast.LENGTH_SHORT).show();
                if(function.getType()==2){
                    Intent intent = new Intent(FunctionsActivity.this, CommunicationActivity.class);
                    startActivity(intent);
                }
            } else {
                Toast.makeText(this, "Function "+function.getName()+" has failed!", Toast.LENGTH_SHORT).show();
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
                isSuccessful=true;
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
