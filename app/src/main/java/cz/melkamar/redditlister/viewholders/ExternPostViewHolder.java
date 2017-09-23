package cz.melkamar.redditlister.viewholders;

import android.view.View;
import android.widget.TextView;
import cz.melkamar.redditlister.R;
import cz.melkamar.redditlister.model.ExternalPost;
import cz.melkamar.redditlister.model.Post;

/**
 * Created by Martin Melka (martin.melka@gmail.com) on 19.09.2017 0:02.
 */

public class ExternPostViewHolder extends PostViewHolder {
    TextView title;
    TextView url;
    ExternpostClickListener listener;


    public ExternPostViewHolder(View itemView, ExternpostClickListener listener) {
        super(itemView);
        title = itemView.findViewById(R.id.recycler_tv_title);
        url = itemView.findViewById(R.id.tv_url);
        this.listener = listener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void bindItem(Post post) {
        ExternalPost externalPost = (ExternalPost) post;
        title.setText(externalPost.getTitle());
        url.setText("some url");
    }

    @Override
    public void onClick(View view) {
        listener.onExternPostClick(getAdapterPosition());
    }

    public interface ExternpostClickListener {
        void onExternPostClick(int index);
    }
}
