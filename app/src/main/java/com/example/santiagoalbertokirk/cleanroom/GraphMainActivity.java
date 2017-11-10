package com.example.santiagoalbertokirk.cleanroom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.anastr.speedviewlib.SpeedView;

public class GraphMainActivity extends AppCompatActivity {

    private SpeedView mMainGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_main);
    }
}
