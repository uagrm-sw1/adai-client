package com.example.software_cliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.software_cliente.Utils.LessonAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AreaActivity extends AppCompatActivity {

    @BindView(R.id.lesson_list_recycler_view)
    RecyclerView lesson_list_recycler_view;

    private String area = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);
        ButterKnife.bind(this);

        area = getIntent().getExtras().getString("area");

        SpannableStringBuilder str = new SpannableStringBuilder(area);
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, area.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorBackground)));

        LessonAdapter lessonAdapter = new LessonAdapter(this);
        lesson_list_recycler_view.setAdapter(lessonAdapter);
        lesson_list_recycler_view.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, AreaListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
