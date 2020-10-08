package com.example.software_cliente;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.multiple_scroll_view)
    ScrollView multiple_scroll_view;
    @BindView(R.id.voice_linear_layout)
    LinearLayout voice_linear_layout;
    @BindView(R.id.paint_linear_layout)
    LinearLayout paint_linear_layout;
    @BindView(R.id.speech_button)
    Button speech_button;

    final int RESULT_SPEECH = 1;
    boolean doubleBackToExitPressedOnce = false;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        type = getIntent().getExtras().getString("type");

        SpannableStringBuilder str = new SpannableStringBuilder("EXAMEN: LECCIÓN X");
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 17, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorBackground)));

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

    @OnClick(R.id.speech_button)
    public void onClickSpeechButton(View v) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        try {
            startActivityForResult(intent, RESULT_SPEECH);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Your device is not support text to speech.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_SPEECH:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Log.i("Texto: ", text.get(0));
                }
                break;
        }
    }
}
