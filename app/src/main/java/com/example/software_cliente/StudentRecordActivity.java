package com.example.software_cliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.software_cliente.Adapters.RecordAdapter;
import com.example.software_cliente.Adapters.StudentAdapter;
import com.example.software_cliente.Interface.RetrofitServices;
import com.example.software_cliente.Response.Student;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StudentRecordActivity extends AppCompatActivity {

    @BindView(R.id.student_record_list_recycler_view)
    RecyclerView student_record_list_recycler_view;

    final String TAG = StudentListActivity.class.getSimpleName();
    final String baseUrl = "http://ec2-3-21-164-122.us-east-2.compute.amazonaws.com/api/";
    RetrofitServices services;
    private int id;
    private String token = "";
    List<Student> students;
    SharedPreferences preferences;

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

        preferences = getSharedPreferences("Session", MODE_PRIVATE);

        id = preferences.getInt("id", 0);
        token = preferences.getString("token", null);
        Log.i(TAG, ""+id);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        services = retrofit.create(RetrofitServices.class);
        Call<List<Student>> call = services.getStudents("token " + token);
        call.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                if (response.isSuccessful()) {
                    students = response.body();
                    selectStudents();
                    RecordAdapter recordAdapter = new RecordAdapter(StudentRecordActivity.this, students);
                    student_record_list_recycler_view.setAdapter(recordAdapter);
                    student_record_list_recycler_view.setLayoutManager(new LinearLayoutManager(StudentRecordActivity.this));
                } else {
                    Toast.makeText(StudentRecordActivity.this, "Get student failed. Try again please", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                Log.i(TAG, "Error: " + t.getMessage());
            }
        });
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

    private void selectStudents() {
        LinkedList<Student> studentsSelected = new LinkedList<>();
        for (Student student : students) {
            Log.i(TAG, ""+student.getAverage());
            if (student.getUserId() != id)
                studentsSelected.add(student);
        }
        for (Student student : studentsSelected) {
            students.remove(student);
        }
    }
}
