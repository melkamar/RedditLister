package cz.melkamar.redditlister.model;

import android.os.Parcel;
import android.os.Parcelable;
import cz.melkamar.redditlister.R;

/**
 * Created by Martin Melka (martin.melka@gmail.com) on 18.09.2017 23:48.
 */

public class SelfPost extends Post implements Parcelable {
    private String content;

    public SelfPost(String title, String url, String content) {
        super(title, url);
        this.content = content;
    }

    protected SelfPost(Parcel in) {
        super(in.readString(), in.readString());
        content = in.readString();
    }

    public static final Creator<SelfPost> CREATOR = new Creator<SelfPost>() {
        @Override
        public SelfPost createFromParcel(Parcel in) {
            return new SelfPost(in);
        }

        @Override
        public SelfPost[] newArray(int size) {
            return new SelfPost[size];
        }
    };

    @Override
    public int getType() {
        return R.id.post_self;
    }

    public String getContent() {
        return content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.title);
        parcel.writeString(this.url);
        parcel.writeString(content);
    }
}
