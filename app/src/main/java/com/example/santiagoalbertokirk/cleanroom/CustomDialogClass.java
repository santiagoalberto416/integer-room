package com.example.santiagoalbertokirk.cleanroom;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by santiagoalbertokirk on 21/10/17.
 */

public class CustomDialogClass extends Dialog implements
        android.view.View.OnClickListener {

    public Activity mActivity;
    public Dialog mDialog;
    public Button mAccept, mCancel;
    public EditText mEditText;

    public CustomDialogClass(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.mActivity = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        mAccept = (Button) findViewById(R.id.btn_yes);
        mCancel = (Button) findViewById(R.id.btn_no);
        mEditText = (EditText)findViewById(R.id.ip_edit);
        mAccept.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        mEditText.setText(SharedUtils.getInstance().getRawIP(mActivity));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                SharedUtils.getInstance().setIpDefault(mActivity, mEditText.getText().toString());
                break;
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
