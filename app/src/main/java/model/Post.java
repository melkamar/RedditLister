package model;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Martin Melka (martin.melka@gmail.com) on 17.09.2017 12:34.
 */

public abstract class Post implements Parcelable {
    String title;
    String url;

    public Post(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public abstract int getType();

}
