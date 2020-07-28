package com.example.software_cliente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.software_cliente.Utils.RecordAdapter;

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

        RecordAdapter recordAdapter = new RecordAdapter(this, names, expanded);
        student_record_list_recycler_view.setAdapter(recordAdapter);
        student_record_list_recycler_view.setLayoutManager(new LinearLayoutManager(this));
    }
}
