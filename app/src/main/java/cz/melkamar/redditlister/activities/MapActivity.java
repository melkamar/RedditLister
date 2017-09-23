package cz.melkamar.redditlister.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import cz.melkamar.redditlister.R;

import java.util.Random;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    MapFragment mapFragment;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        findViewById(R.id.btn_random_marker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (map == null) return;

                Random rnd = new Random();
                map.addMarker(new MarkerOptions().position(new LatLng(rnd.nextDouble() * 360 - 180,
                                                                      rnd.nextDouble() * 180 - 90))
                .title("Some fancy marker "+rnd.nextInt(100)));

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.addMarker(new MarkerOptions()
                              .position(new LatLng(1, 2))
                              .title("My custom place bro"));
    }
}
