package com.example.software_cliente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.software_cliente.Utils.StudentAdapter;

import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StudentListActivity extends AppCompatActivity {

    @BindView(R.id.student_list_recycler_view)
    RecyclerView student_list_recycler_view;

    String[] names = new String[]{"Elian Delgadillo", "Rodrigo Torrez", "Ilich Choque"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        ButterKnife.bind(this);

        StudentAdapter studentAdapter = new StudentAdapter(this, names);
        student_list_recycler_view.setAdapter(studentAdapter);
        student_list_recycler_view.setLayoutManager(new LinearLayoutManager(this));
    }

    @OnClick(R.id.add_student_button)
    public void onClickAddStudent(View v) {
        Intent intent = new Intent(this, RegisterStudentActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.record_student_button)
    public void onClickRecordStudent(View v) {
        Intent intent = new Intent(this, StudentRecordActivity.class);
        startActivity(intent);
    }
}
