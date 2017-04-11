package com.apps.ridvan.smartgatekeeper;

import com.apps.ridvan.smartgatekeeper.model.FunctionListData;
import com.apps.ridvan.smartgatekeeper.utils.HttpHelper;
import com.google.gson.Gson;

import org.junit.Test;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LoginTest {

    static final String urlStr = "192.168.43.148";
    static final String email = "rasibic@uclan.ac.uk";
    static final String fEmail = "xxx@uclan.ac.uk";
    static final String password = "test";

    @Test
    public void successfulLogin() throws Exception {
        OkHttpClient client = HttpHelper.getUnsafeOkHttpClient();
        String json = "{\"login\": \"" + email + "\", \"password\": \"" + password + "\"}";
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        String url = "https://"+urlStr+":5000/api/json/login";
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            assert(response.isSuccessful());
            Gson gson = new Gson();
            String respondBody = response.body().string();
            FunctionListData fList = (gson.fromJson(respondBody, FunctionListData.class));
            assertNotEquals(null, fList);
        }
    }

    @Test
    public void failedLogin() throws Exception {
        OkHttpClient client = HttpHelper.getUnsafeOkHttpClient();
        String json = "{\"login\": \"" + fEmail + "\", \"password\": \"" + password + "\"}";
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        String url = "https://"+urlStr+":5000/api/json/login";
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        FunctionListData fList = null;
        try (Response response = client.newCall(request).execute()) {
            assert(response.isSuccessful());
            Gson gson = new Gson();
            String respondBody = response.body().string();
             fList = (gson.fromJson(respondBody, FunctionListData.class));
            assertEquals(null, fList);
        } catch (Exception e){
            assertEquals(null, fList);
        }
    }
}