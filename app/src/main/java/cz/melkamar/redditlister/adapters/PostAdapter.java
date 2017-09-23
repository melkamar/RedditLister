package cz.melkamar.redditlister.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cz.melkamar.redditlister.R;
import cz.melkamar.redditlister.model.ExternalPost;
import cz.melkamar.redditlister.model.Post;
import cz.melkamar.redditlister.model.SelfPost;
import cz.melkamar.redditlister.viewholders.ExternPostViewHolder;
import cz.melkamar.redditlister.viewholders.PostViewHolder;
import cz.melkamar.redditlister.viewholders.SelfpostViewHolder;

import java.util.List;

/**
 * Created by Martin Melka (martin.melka@gmail.com) on 17.09.2017 12:38.
 */

/**
 *
 */
public class PostAdapter extends RecyclerView.Adapter<PostViewHolder>
        implements ExternPostViewHolder.ExternpostClickListener, SelfpostViewHolder.SelfpostClickListener {
    List<Post> data;
    final private ListItemClickListener clickListener;

    public PostAdapter(List<Post> data, ListItemClickListener clickListener) {
        this.data = data;
        this.clickListener = clickListener;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case R.id.post_self:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_row_selfpost, parent, false);
                return new SelfpostViewHolder(itemView, this);

            case R.id.post_external:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_row_externpost, parent, false);
                return new ExternPostViewHolder(itemView, this);

            default:
                throw new RuntimeException("no type");
        }
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        holder.bindItem(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();
    }

    @Override
    public void onSelfPostClick(int index) {
        clickListener.onSelfPostTitleClick((SelfPost) data.get(index));
    }

    @Override
    public void onExternPostClick(int index) {
        clickListener.onExtPostTitleClick((ExternalPost) data.get(index));
    }

    public void swap(List<Post> newData) {
        if (newData == null || newData.isEmpty()) return;
        data.clear();
        data.addAll(newData);
        this.notifyDataSetChanged();
    }

    public interface ListItemClickListener {
        void onSelfPostTitleClick(SelfPost post);

        void onExtPostTitleClick(ExternalPost post);
    }


}
