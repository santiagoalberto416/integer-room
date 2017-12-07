package com.example.santiagoalbertokirk.cleanroom;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.santiagoalbertokirk.cleanroom.data.GetRoomInterface;
import com.example.santiagoalbertokirk.cleanroom.data.Module;
import com.example.santiagoalbertokirk.cleanroom.data.Sensor;
import com.example.santiagoalbertokirk.cleanroom.data.SingleRoomResponse;
import com.example.santiagoalbertokirk.cleanroom.draw.MapActivity;
import com.example.santiagoalbertokirk.cleanroom.fragment.MeditorOfSensorFragment;
import com.example.santiagoalbertokirk.cleanroom.fragment.ModelViewSensor;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SingleRoomActivity extends AppCompatActivity {

    public static final String ID_ROOM = "idroom";
    public TextView mNameRoom;
    public TextView mSubtitleText;
    public ScreenSlidePagerAdapter mPagerAdapter;
    private int moduleSelected = 0;
    private ViewPager mPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_room);
        mSubtitleText = (TextView)findViewById(R.id.subtitle_text);
        mNameRoom = (TextView)findViewById(R.id.name_room);
        int id = getIntent().getIntExtra(ID_ROOM, 0);
        if(id!=0){
            getRooms(id);
        }
        findViewById(R.id.ubication).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SingleRoomActivity.this, MapActivity.class);
                SingleRoomActivity.this.startActivity(intent);
            }
        });

        // Instantiate a ViewPager and a PagerAdapter.

        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mPagerAdapter);
    }

    public void getRooms(final int id){
        if(!SharedUtils.getInstance().isValidURL(SharedUtils.getInstance().getIpDefault(this))){
            Toast.makeText(SingleRoomActivity.this, "Url invalida intenta otra", Toast.LENGTH_LONG);
            return;
        }
        Gson gsonBuilder = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        Retrofit retrofitBuilder = new Retrofit.Builder()
                .baseUrl(SharedUtils.getInstance().getIpDefault(this))
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
                .build();
        GetRoomInterface restClient = retrofitBuilder.create(GetRoomInterface.class);
        Call<SingleRoomResponse> call = restClient.getRoom(id);
        call.enqueue(new Callback<SingleRoomResponse>() {
            @Override
            public void onResponse(Call<SingleRoomResponse> call, Response<SingleRoomResponse> response) {
                if(response.code()==200){
                    SingleRoomResponse data = response.body();
                    if(data!=null){
                        // specify an adapter (see also next example)
                        setValues(data);
                        // for default we show the module number 1
                        mPagerAdapter.refreshData(data, moduleSelected);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getRooms(id);
                            }
                        }, 5000);

                    }
                }
            }
            @Override
            public void onFailure(Call<SingleRoomResponse> call, Throwable t) {
                Toast.makeText(SingleRoomActivity.this, "Servicio no disponible por ahora", Toast.LENGTH_LONG);
            }
        });
    }

    private void setValues(SingleRoomResponse response) {
        mNameRoom.setText(response.getName());
        mSubtitleText.setText(response.getDescription());
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

            List<Fragment> fragments = new ArrayList<>();

            public ScreenSlidePagerAdapter(FragmentManager fm) {
                super(fm);
            }

            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            public void refreshData(final SingleRoomResponse data, final int module){
                findViewById(R.id.change_module).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showModuleSelect(data.getModules());
                    }
                });
                findViewById(R.id.ubication).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SingleRoomActivity.this, MapActivity.class);
                        intent.putExtra(MapActivity.X_POSITION, Float.parseFloat(data.getModules().get(module).getXPos()));
                        intent.putExtra(MapActivity.Y_POSITION,  Float.parseFloat(data.getModules().get(module).getYPos()));
                        SingleRoomActivity.this.startActivity(intent);
                    }
                });

                List<String> types = new ArrayList<>();
                List<ModelViewSensor> sensorsModels = new ArrayList<>();
                for(Sensor sensor : data.getModules().get(module).getSensors()){
                    if(!types.contains(sensor.getType())){
                        types.add(sensor.getType());
                    }
                }

                // Create models for fragments

                for(int i = 0 ; i < types.size(); i++) {
                    float value = 0;

                    for (Sensor sensor : data.getModules().get(module).getSensors()) {
                        if(!types.get(i).equals(sensor.getType())){
                            value = value + sensor.getCurrentValue();
                        }
                    }
                    value = value / types.size();
                    ModelViewSensor modelViewSensor = new ModelViewSensor(types.get(i), value);
                    sensorsModels.add(modelViewSensor);
                }

                // Refresh or create a fragment

                if(fragments.size()==0) {
                    for (ModelViewSensor sensor : sensorsModels) {
                        fragments.add(MeditorOfSensorFragment.newInstance(sensor));
                    }
                }else {
                    for(int i = 0; i < fragments.size(); i++){
                        ((MeditorOfSensorFragment)fragments.get(i)).setValueToMeter(sensorsModels.get(i).getValue());
                    }
                }
                notifyDataSetChanged();
            }

        }


        public void showModuleSelect(List<Module> modules){
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(SingleRoomActivity.this);
            builderSingle.setTitle("Select One Module:");

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SingleRoomActivity.this, android.R.layout.select_dialog_singlechoice);
            for(Module module : modules){
                arrayAdapter.add(module.getName());
            }

            builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String strName = arrayAdapter.getItem(which);
                    moduleSelected = which;
                    AlertDialog.Builder builderInner = new AlertDialog.Builder(SingleRoomActivity.this);
                    builderInner.setMessage(strName);
                    builderInner.setTitle("Your Selected Item is");
                    builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,int which) {
                            dialog.dismiss();
                        }
                    });
                    builderInner.show();
                }
            });
            builderSingle.show();
        }


}
