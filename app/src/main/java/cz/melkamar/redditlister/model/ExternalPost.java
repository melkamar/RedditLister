package cz.melkamar.redditlister.model;

import android.os.Parcel;
import cz.melkamar.redditlister.R;

/**
 * Created by Martin Melka (martin.melka@gmail.com) on 18.09.2017 23:49.
 */

public class ExternalPost extends Post {
    public ExternalPost(String title, String url) {
        super(title, url);
    }

    protected ExternalPost(Parcel in) {
        super(in.readString(), in.readString());
    }

    @Override
    public int getType() {
        return R.id.post_external;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(url);
    }

    public static final Creator<ExternalPost> CREATOR = new Creator<ExternalPost>() {
        @Override
        public ExternalPost createFromParcel(Parcel in) {
            return new ExternalPost(in);
        }

        @Override
        public ExternalPost[] newArray(int size) {
            return new ExternalPost[size];
        }
    };
}
