package com.windfallsheng.myrxjava2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public TextView tvGoToCreate, tvGoToTramsform, tvGoToCombine, tvGoToFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvGoToCreate = findViewById(R.id.textview_go_to_create);
        tvGoToTramsform = findViewById(R.id.textview_go_to_transform);
        tvGoToCombine = findViewById(R.id.textview_go_to_combine);
        tvGoToFilter = findViewById(R.id.textview_go_to_filter);

        tvGoToCreate.setOnClickListener(this);
        tvGoToTramsform.setOnClickListener(this);
        tvGoToCombine.setOnClickListener(this);
        tvGoToFilter.setOnClickListener(this);

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
                case R.id.textview_go_to_filter:
                FilterActivity.start(this);
                break;
            default:
                break;
        }
    }
}
