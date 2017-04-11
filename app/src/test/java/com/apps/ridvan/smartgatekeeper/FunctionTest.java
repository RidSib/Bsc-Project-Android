package com.apps.ridvan.smartgatekeeper;

import com.apps.ridvan.smartgatekeeper.utils.HttpHelper;

import org.junit.Test;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class FunctionTest {

    static final String urlStr = "192.168.43.148";
    static final String email = "rasibic@uclan.ac.uk";
    static final String password = "test";

    @Test
    public void successfulFunction() throws Exception {
        OkHttpClient client = HttpHelper.getUnsafeOkHttpClient();
        String json = "{\"login\": \"" + email + "\", \"password\": \"" + password + ", \"function\": 0}";
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        String url = "https://"+urlStr+":5000/api/json/activity";
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            assert (response.isSuccessful());
        }
    }

}