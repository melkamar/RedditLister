package adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cz.melkamar.redditlister.R;
import model.Post;
import viewholders.ExternPostViewHolder;
import viewholders.PostViewHolder;
import viewholders.SelfpostViewHolder;

import java.util.List;

/**
 * Created by Martin Melka (martin.melka@gmail.com) on 17.09.2017 12:38.
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
    public void onSelfPostClick() {
        clickListener.onSelfPostClick();
    }

    @Override
    public void onExternPostClick() {
        clickListener.onExternPostClick();
    }

    public void swap(List<Post> newData) {
        data.clear();
        data.addAll(newData);
        this.notifyDataSetChanged();
    }

    public interface ListItemClickListener extends
            ExternPostViewHolder.ExternpostClickListener,
            SelfpostViewHolder.SelfpostClickListener {
    }


}
