package viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import model.Post;

/**
 * Created by Martin Melka (martin.melka@gmail.com) on 18.09.2017 23:57.
 */

public abstract class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public PostViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindItem(Post post);
}
