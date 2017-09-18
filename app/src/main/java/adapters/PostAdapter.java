package adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cz.melkamar.redditlister.R;
import model.Post;

import java.util.List;

/**
 * Created by Martin Melka (martin.melka@gmail.com) on 17.09.2017 12:38.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    List<Post> data;
    final private ListItemClickListener clickListener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_list_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = this.data.get(position);
        holder.tvTitle.setText(post.getTitle());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public PostAdapter(List<Post> data, ListItemClickListener clickListener) {
        this.data = data;
        this.clickListener = clickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvTitle = itemView.findViewById(R.id.recycler_tv_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            this.clickListener.onListItemClick(
//                    getAdapterPosition()
//            );
            clickedItem(getAdapterPosition());
        }
    }

    private void clickedItem(int index) {
        this.clickListener.onListItemClick(data.get(index));
    }

    public interface ListItemClickListener {
        void onListItemClick(Post post);
    }

    public void swap(List<Post> newData) {
        data.clear();
        data.addAll(newData);
        this.notifyDataSetChanged();
    }
}
