package com.windfallsheng.myrxjava2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = MainActivity.class.getSimpleName();
    public TextView tvGoTOCreate;
    public TextView tvGoTOMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvGoTOCreate = findViewById(R.id.textview_go_to_create);
        tvGoTOMap = findViewById(R.id.textview_go_to_map);

        tvGoTOCreate.setOnClickListener(this);
        tvGoTOMap.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textview_go_to_create:
                CreateActivity.start(this);
                break;
                case R.id.textview_go_to_map:
                MapActivity.start(this);
                break;
            default:
                break;
        }
    }
}
