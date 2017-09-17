package cz.melkamar.redditlister.activities;

import adapters.PostAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.*;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import cz.melkamar.redditlister.R;
import cz.melkamar.redditlister.util.RedditJsonParser;
import cz.melkamar.redditlister.util.RefreshATask;
import model.Post;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RefreshATask.RefreshTaskListener,
        PostAdapter.ListItemClickListener {

    TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

//        ButterKnife.setDebug(true);
//        ButterKnife.bind(this);
//        Log.w("onCreate", "" + content);
//        content = findViewById(R.id.tv_content);

        ArrayList<Post> data = new ArrayList<>();
        for (int i = 0; i < 100; i++)
            data.add(new Post("" + i, null));


        RecyclerView rv = findViewById(R.id.rv_content);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(manager);

        DividerItemDecoration divider = new DividerItemDecoration(rv.getContext(), manager.getOrientation());
        rv.addItemDecoration(divider);

        rv.setAdapter(new PostAdapter(data, this));
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
    public void onFinished(String responseBody) {
        try {
            String[] titles = RedditJsonParser.parseJson(responseBody);
            content.setText("");
            for (String str : titles) {
                if (content.getText().length() != 0) {
                    content.append("\n---\n");
                }
                content.append(str);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            content.setText("Failed parsing or something.");
        }
    }


    Toast toast = null;
    @Override
    public void onListItemClick(Post post) {
        if (toast!=null){
            toast.cancel();
        }

        toast = Toast.makeText(this, post.getTitle(), Toast.LENGTH_SHORT);
        toast.show();
    }
}
