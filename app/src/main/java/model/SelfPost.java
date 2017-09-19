package model;

import cz.melkamar.redditlister.R;

/**
 * Created by Martin Melka (martin.melka@gmail.com) on 18.09.2017 23:48.
 */

public class SelfPost extends Post {
    public SelfPost(String title, String url) {
        super(title, url);
    }

    @Override
    public int getType() {
        return R.id.post_self;
    }


}
