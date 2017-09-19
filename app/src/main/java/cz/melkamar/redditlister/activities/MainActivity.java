package cz.melkamar.redditlister.activities;

import adapters.PostAdapter;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import cz.melkamar.redditlister.R;
import cz.melkamar.redditlister.util.RedditJsonParser;
import cz.melkamar.redditlister.util.RefreshATask;
import model.ExternalPost;
import model.Post;
import model.SelfPost;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements RefreshATask.RefreshTaskListener,
        PostAdapter.ListItemClickListener {

    RecyclerView rv;
    PostAdapter postAdapter;
    Toast toast = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        rv = findViewById(R.id.rv_content);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(manager);

        DividerItemDecoration divider = new DividerItemDecoration(rv.getContext(), manager.getOrientation());
        rv.addItemDecoration(divider);

        postAdapter = new PostAdapter(new ArrayList<Post>(0), this);
        rv.setAdapter(postAdapter);
        refreshContent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_refresh:
                Toast.makeText(this, "Refreshing...", Toast.LENGTH_SHORT).show();
                refreshContent();
                break;
            case R.id.btn_about:
                Toast.makeText(this, "Made by zmrd", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refreshContent() {
        new RefreshATask(this).execute("https://www.reddit.com/.json");

    }

    @Override
    public void onRefreshFinished(String responseBody) {
        try {

            Post[] posts = RedditJsonParser.parseJson(responseBody);
            postAdapter.swap(Arrays.asList(posts));
        } catch (JSONException e) {
            e.printStackTrace();
            showToast("Failed parsing or something.");
        }
    }


    protected void showToast(String text) {
        if (toast != null) {
            toast.cancel();
        }

//        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();
        Snackbar.make(findViewById(R.id.rv_content), text, Snackbar.LENGTH_LONG);
//        toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
//        toast.show();
    }

    @Override
    public void onSelfPostTitleClick(SelfPost post) {
//        Intent intent = new Intent(this, SelfPostDetailActivity.class);
//        startActivity(intent);
        showToast(post.getTitle());
    }

    @Override
    public void onExtPostTitleClick(ExternalPost post) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(post.getUrl()));
        if (intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        } else {
            showToast("Sorry, no app installed for opening webpages.");
        }
    }
}
