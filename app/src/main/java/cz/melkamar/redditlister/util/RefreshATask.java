package cz.melkamar.redditlister.util;

import android.os.AsyncTask;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * Created by Martin Melka (martin.melka@gmail.com) on 16.09.2017 10:53.
 */
public class RefreshATask extends AsyncTask<String, Void, String> {

    RefreshTaskListener listener = null;

    public RefreshATask(RefreshTaskListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... strings) {
        assert strings.length == 1;
        try {
            return getHttp(strings[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        listener.onRefreshFinished(s);
    }

    public interface RefreshTaskListener {
        void onRefreshFinished(String responseBody);
    }

    private static String getHttp(String url) throws IOException {
        Response response = new OkHttpClient().newCall(
                new Request.Builder().url(url).build()
        ).execute();

        return response.body().string();
    }
}
