package cz.melkamar.redditlister;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import cz.melkamar.redditlister.util.RedditJsonParser;
import cz.melkamar.redditlister.util.RefreshATask;
import org.json.JSONException;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity implements RefreshATask.RefreshTaskListener {

    @BindView(R.id.tv_content)
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
        content = findViewById(R.id.tv_content);
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
                if (content.getText().length() != 0){
                    content.append("\n---\n");
                }
                content.append(str);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            content.setText("Failed parsing or something.");
        }
    }
}
