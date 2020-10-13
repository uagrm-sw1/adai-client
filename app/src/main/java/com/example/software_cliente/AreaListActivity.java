package com.example.software_cliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.software_cliente.Interface.RetrofitServices;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AreaListActivity extends AppCompatActivity {

    @BindView(R.id.math_linear_layout)
    LinearLayout math_linear_layout;
    @BindView(R.id.reading_linear_layout)
    LinearLayout reading_linear_layout;
    @BindView(R.id.art_linear_layout)
    LinearLayout art_linear_layout;
    @BindView(R.id.writing_linear_layout)
    LinearLayout writing_linear_layout;

    final String TAG = AreaListActivity.class.getSimpleName();
    final String baseUrl = "http://ec2-3-21-164-122.us-east-2.compute.amazonaws.com/api/";
    RetrofitServices services;

    private int width;
    private int height;
    private int idStudent;
    private int idCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_list);
        ButterKnife.bind(this);

        SpannableStringBuilder str = new SpannableStringBuilder("ESCOGER MATERIA");
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 15, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorBackground)));

        width = getResources().getDisplayMetrics().widthPixels;
        height = getResources().getDisplayMetrics().heightPixels;

        ViewGroup.LayoutParams paramsMath = math_linear_layout.getLayoutParams();
        paramsMath.width = width * 2 / 5;
        paramsMath.height = height * 2 / 5;
        math_linear_layout.setLayoutParams(paramsMath);
        ViewGroup.LayoutParams paramsReading = reading_linear_layout.getLayoutParams();
        paramsReading.width = width * 2 / 5;
        paramsReading.height = height * 2 / 5;
        reading_linear_layout.setLayoutParams(paramsReading);
        ViewGroup.LayoutParams paramsArt = art_linear_layout.getLayoutParams();
        paramsArt.width = width * 2 / 5;
        paramsArt.height = height * 2 / 5;
        art_linear_layout.setLayoutParams(paramsArt);
        ViewGroup.LayoutParams paramsWriting = writing_linear_layout.getLayoutParams();
        paramsWriting.width = width * 2 / 5;
        paramsWriting.height = height * 2 / 5;
        writing_linear_layout.setLayoutParams(paramsWriting);

        idStudent = getIntent().getExtras().getInt("idStudent");
        idCourse = getIntent().getExtras().getInt("idCourse");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, StudentListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.math_linear_layout)
    public void onClickMath(View v) {
        Intent intent = new Intent(this, AreaActivity.class);
        intent.putExtra("idSubject", 3);
        intent.putExtra("idStudent", idStudent);
        intent.putExtra("idCourse", idCourse);
        startActivity(intent);
    }

    @OnClick(R.id.writing_linear_layout)
    public void onClickWriting(View v) {
        Intent intent = new Intent(this, AreaActivity.class);
        intent.putExtra("idSubject", 2);
        intent.putExtra("idStudent", idStudent);
        intent.putExtra("idCourse", idCourse);
        startActivity(intent);
    }

    @OnClick(R.id.reading_linear_layout)
    public void onClickReading(View v) {
        Intent intent = new Intent(this, AreaActivity.class);
        intent.putExtra("idSubject", 1);
        intent.putExtra("idStudent", idStudent);
        intent.putExtra("idCourse", idCourse);
        startActivity(intent);
    }

    @OnClick(R.id.art_linear_layout)
    public void onClickArt(View v) {
        Intent intent = new Intent(this, AreaActivity.class);
        intent.putExtra("idSubject", 4);
        intent.putExtra("idStudent", idStudent);
        intent.putExtra("idCourse", idCourse);
        startActivity(intent);
    }
}
