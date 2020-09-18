package com.example.software_cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import butterknife.BindView;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.multiple_scroll_view)
    ScrollView multiple_scroll_view;
    @BindView(R.id.voice_linear_layout)
    LinearLayout voice_linear_layout;
    @BindView(R.id.paint_linear_layout)
    LinearLayout paint_linear_layout;

    boolean doubleBackToExitPressedOnce = false;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        type = getIntent().getExtras().getString("type");

        SpannableStringBuilder str = new SpannableStringBuilder("EXAMEN: LECCIÓN X");
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 17, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorBackground)));

        multiple_scroll_view = findViewById(R.id.multiple_scroll_view);
        voice_linear_layout = findViewById(R.id.voice_linear_layout);
        paint_linear_layout = findViewById(R.id.paint_linear_layout);

        if (type.equals("multiple")) {
            voice_linear_layout.setVisibility(View.GONE);
            paint_linear_layout.setVisibility(View.GONE);
            multiple_scroll_view.setVisibility(View.VISIBLE);
        }
        if (type.equals("voice")) {
            paint_linear_layout.setVisibility(View.GONE);
            multiple_scroll_view.setVisibility(View.GONE);
            voice_linear_layout.setVisibility(View.VISIBLE);
        }
        if (type.equals("paint")) {
            multiple_scroll_view.setVisibility(View.GONE);
            voice_linear_layout.setVisibility(View.GONE);
            paint_linear_layout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
