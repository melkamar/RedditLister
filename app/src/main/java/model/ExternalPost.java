package model;

import cz.melkamar.redditlister.R;

/**
 * Created by Martin Melka (martin.melka@gmail.com) on 18.09.2017 23:49.
 */

public class ExternalPost extends Post {
    public ExternalPost(String title, String url) {
        super(title, url);
    }

    @Override
    public int getType() {
        return R.id.post_external;
    }
}
