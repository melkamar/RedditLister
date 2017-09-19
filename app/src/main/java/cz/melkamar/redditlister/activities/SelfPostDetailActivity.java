package cz.melkamar.redditlister.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import cz.melkamar.redditlister.R;
import model.SelfPost;

public class SelfPostDetailActivity extends AppCompatActivity {

    TextView title;
    TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_post_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        title = (TextView) findViewById(R.id.tv_title);
        content = (TextView) findViewById(R.id.tv_content);

        final SelfPost post = getIntent().getExtras().getParcelable("post");

        title.setText(post.getTitle());
        content.setText(post.getContent());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, post.getTitle(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
