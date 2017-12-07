package com.example.santiagoalbertokirk.cleanroom.draw;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.santiagoalbertokirk.cleanroom.R;

public class MapActivity extends AppCompatActivity {

    public static final String X_POSITION = "xposition";
    public static final String Y_POSITION = "yposition";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Float posX = getIntent().getFloatExtra(X_POSITION, 0);
        Float posY = getIntent().getFloatExtra(Y_POSITION, 0);
        setContentView(R.layout.activity_draw);
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.drawContainer);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        ImageView imageView = (ImageView)findViewById(R.id.image_background);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        params.width = width;
        params.height = width;
        imageView.setLayoutParams(params);
        DrawMapView mapView = new DrawMapView(this, width, Math.round(posX), Math.round(posY), 10);
        layout.addView(mapView);
    }
}
