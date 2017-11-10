package com.example.santiagoalbertokirk.cleanroom.fragment;

/**
 * Created by santiagoalbertokirk on 06/11/17.
 */

public class ModelViewSensor {

    private String title;

    private float value;

    public ModelViewSensor(String title, float value){
        this.title = title;
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

}

