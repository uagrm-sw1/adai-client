package com.example.software_cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AreaActivity extends AppCompatActivity {

    @BindView(R.id.area_name_text_view)
    TextView area_name_text_view;

    private String area = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);
        ButterKnife.bind(this);

        area = getIntent().getExtras().getString("area");
        area_name_text_view.setText(area);
    }
}
