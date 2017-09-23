package cz.melkamar.redditlister.activities;

import adapters.PostAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity implements RefreshATask.RefreshTaskListener,
        PostAdapter.ListItemClickListener {

    RecyclerView rv;
    PostAdapter postAdapter;
    Toast toast = null;
    ArrayList<Post> posts;

    private boolean nowRefreshing = false; // Is content currently being refreshed? = is ASyncTask running?

    /*
     * Used for handling preferences.
     *
     * selfPostsIncludedInList marks whether selfposts are currently shown or not.
     * If this activity is resumed and value of this variable is different to that in SharedPreferences, that
     * means that the user has changed this setting and we should re-poll the posts list.
     */
    private SharedPreferences prefMgr;
    boolean selfPostsIncludedInList = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * Magic lines to make modern toolbar work.
         */
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        prefMgr = PreferenceManager.getDefaultSharedPreferences(this);

        /*
         * Setting up RecyclerView, assigning an adapter to it.
         */
        rv = findViewById(R.id.rv_content);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(manager);

        DividerItemDecoration divider = new DividerItemDecoration(rv.getContext(), manager.getOrientation());
        rv.addItemDecoration(divider);

        postAdapter = new PostAdapter(new ArrayList<Post>(0), this);
        rv.setAdapter(postAdapter);

        /*
         * Restoring content, or fetching it from the internet if nothing was saved.
         */
        Log.d("savedState:", savedInstanceState + "");
        if (savedInstanceState == null) {
            refreshContent();
        } else {
            posts = savedInstanceState.getParcelableArrayList("posts");
            Log.d("sSaved posts: ", posts == null ? "null" : posts.toString());
            if (posts != null && !posts.isEmpty()) {
                postAdapter.swap(posts);
                selfPostsIncludedInList = savedInstanceState.getBoolean("selfposts_included");
            } else {
                Log.d("sSaved posts: ", "empty, refreshing...");
                refreshContent();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*
         * If we are not currently refreshing content, compare if selfposts are shown to whether they should be shown.
         */
        if (!nowRefreshing) {
            boolean inclSelfposts = prefMgr.getBoolean("include_selfposts", true);
            if (inclSelfposts != selfPostsIncludedInList) {
                refreshContent();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("onSaveInstanceState", "Saving posts: " + (posts == null ? "null" : posts.size()));
        outState.putParcelableArrayList("posts", posts);
        outState.putBoolean("selfposts_included", selfPostsIncludedInList);
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
                Toast.makeText(this, "Made by Martin Melka.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refreshContent() {
//        new RefreshATask(this).execute("https://www.reddit.com/.json");
        nowRefreshing = true;
        new RefreshATask(this).execute("https://www.reddit.com/r/androiddev/.json");
    }

    @Override
    public void onRefreshFinished(String responseBody) {
        try {

            Post[] posts = RedditJsonParser.parseJson(responseBody);

            ArrayList<Post> postsList = new ArrayList<>(Arrays.asList(posts));
            boolean inclSelfposts = prefMgr.getBoolean("include_selfposts", true);
            selfPostsIncludedInList = inclSelfposts;

            if (!inclSelfposts) {
                ListIterator<Post> iter = postsList.listIterator();
                while (iter.hasNext()) {
                    Post post = iter.next();
                    if (post.getType() == R.id.post_self) {
                        iter.remove();
                    }

                }
            }

            postAdapter.swap(postsList);
            this.posts = postsList;
            ((LinearLayoutManager) rv.getLayoutManager()).scrollToPositionWithOffset(0, 0);

            nowRefreshing = false;
        } catch (JSONException e) {
            e.printStackTrace();
            showSnackbar("Failed parsing or something.");
        }
    }


    protected void showSnackbar(String text) {
        if (toast != null) {
            toast.cancel();
        }

        Snackbar.make(findViewById(R.id.rv_content), text, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onSelfPostTitleClick(SelfPost post) {
        Intent intent = new Intent(this, SelfPostDetailActivity.class);
        intent.putExtra("post", post);
        startActivity(intent);
    }

    @Override
    public void onExtPostTitleClick(ExternalPost post) {
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(post.getUrl()));
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        } else {
//            showSnackbar("Sorry, no app installed for opening webpages.");
//        }

        Intent intent = new Intent(this, SelfPostDetailActivity.class);
        intent.putExtra("post", "something");
        startActivity(intent);
    }
}
