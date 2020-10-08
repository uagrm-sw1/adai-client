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

import com.example.software_cliente.Adapters.RecordAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentRecordActivity extends AppCompatActivity {

    @BindView(R.id.student_record_list_recycler_view)
    RecyclerView student_record_list_recycler_view;

    String[] names = new String[]{"Elian Delgadillo", "Rodrigo Torrez", "Ilich Choque"};
    boolean[] expanded = new boolean[]{false, false, false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_record);
        ButterKnife.bind(this);

        SpannableStringBuilder str = new SpannableStringBuilder("VER HISTORIAL");
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorBackground)));

        RecordAdapter recordAdapter = new RecordAdapter(this, names, expanded);
        student_record_list_recycler_view.setAdapter(recordAdapter);
        student_record_list_recycler_view.setLayoutManager(new LinearLayoutManager(this));
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
}
