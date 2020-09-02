package com.example.software_cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AreaListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_list);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.math_image_view)
    public void onClickMath(View v) {
        Intent intent = new Intent(this, AreaActivity.class);
        intent.putExtra("area", "Math");
        startActivity(intent);
    }

    @OnClick(R.id.writing_image_view)
    public void onClickWriting(View v) {
        Intent intent = new Intent(this, AreaActivity.class);
        intent.putExtra("area", "Writing");
        startActivity(intent);
    }

    @OnClick(R.id.reading_image_view)
    public void onClickReading(View v) {
        Intent intent = new Intent(this, AreaActivity.class);
        intent.putExtra("area", "Reading");
        startActivity(intent);
    }

    @OnClick(R.id.art_image_view)
    public void onClickArt(View v) {
        Intent intent = new Intent(this, AreaActivity.class);
        intent.putExtra("area", "Art");
        startActivity(intent);
    }
}
