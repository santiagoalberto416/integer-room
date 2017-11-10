package com.example.santiagoalbertokirk.cleanroom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.santiagoalbertokirk.cleanroom.adapter.RoomsAdapter;
import com.example.santiagoalbertokirk.cleanroom.data.GetRoomsInterface;
import com.example.santiagoalbertokirk.cleanroom.data.Room;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RoomsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);

        if (getSupportActionBar()!= null) {
            getSupportActionBar().setTitle("Rooms");
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_rooms);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        getRooms();


    }

    public void getRooms(){
        if(!SharedUtils.getInstance().isValidURL(SharedUtils.getInstance().getIpDefault(this))){
            Toast.makeText(RoomsActivity.this, "Url invalida intenta otra", Toast.LENGTH_LONG);
            return;
        }
        Gson gsonBuilder = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        Retrofit retrofitBuilder = new Retrofit.Builder()
                .baseUrl(SharedUtils.getInstance().getIpDefault(this))
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
                .build();
        GetRoomsInterface restClient = retrofitBuilder.create(GetRoomsInterface.class);
        Call<Room[]> call = restClient.getRooms();
        call.enqueue(new Callback<Room[]>() {
            @Override
            public void onResponse(Call<Room[]> call, Response<Room[]> response) {
                if(response.code()==200){
                    Room[] data = response.body();
                    if(data!=null && data.length > 0){
                        // specify an adapter (see also next example)
                        mAdapter = new RoomsAdapter(data, RoomsActivity.this);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                }
            }
            @Override
            public void onFailure(Call<Room[]> call, Throwable t) {
                Toast.makeText(RoomsActivity.this, "Servicio no disponible por ahora", Toast.LENGTH_LONG);
            }
        });
    }
}
