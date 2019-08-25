package com.windfallsheng.myrxjava2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public TextView tvGoTOCreate, tvGoTOTramsform, getTvGoCombine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvGoTOCreate = findViewById(R.id.textview_go_to_create);
        tvGoTOTramsform = findViewById(R.id.textview_go_to_transform);
        getTvGoCombine = findViewById(R.id.textview_go_to_combine);

        tvGoTOCreate.setOnClickListener(this);
        tvGoTOTramsform.setOnClickListener(this);
        getTvGoCombine.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textview_go_to_create:
                CreateActivity.start(this);
                break;
            case R.id.textview_go_to_transform:
                TransformActivity.start(this);
                break;
            case R.id.textview_go_to_combine:
                CombineActivity.start(this);
                break;
            default:
                break;
        }
    }
}
