package cz.melkamar.redditlister.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Martin Melka (martin.melka@gmail.com) on 16.09.2017 10:54.
 */

public class RedditJsonParser {
    public static String[] parseJson(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray posts = jsonObject.getJSONObject("data").getJSONArray("children");

        String[] result = new String[posts.length()];

        for (int i = 0; i < posts.length(); i++) {
            JSONObject postData = posts.getJSONObject(i).getJSONObject("data");
            result[i] = postData.getString("title");
        }

        return result;
    }
}
