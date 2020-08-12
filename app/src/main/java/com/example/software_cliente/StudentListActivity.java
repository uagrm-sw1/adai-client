package com.example.software_cliente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.software_cliente.Response.Student;
import com.example.software_cliente.Interface.RetrofitServices;
import com.example.software_cliente.Utils.StudentAdapter;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StudentListActivity extends AppCompatActivity {

    @BindView(R.id.student_list_recycler_view)
    RecyclerView student_list_recycler_view;

    final String TAG = StudentListActivity.class.getSimpleName();
    final String baseUrl = "http://ec2-3-134-80-247.us-east-2.compute.amazonaws.com/";
    RetrofitServices services;
    private String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        ButterKnife.bind(this);

        token = getIntent().getExtras().getString("token");

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        services = retrofit.create(RetrofitServices.class);
        Call<List<Student>> call = services.getStudents("token " + token);
        call.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                if (response.isSuccessful()) {
                    StudentAdapter studentAdapter = new StudentAdapter(StudentListActivity.this, response.body(), token);
                    student_list_recycler_view.setAdapter(studentAdapter);
                    student_list_recycler_view.setLayoutManager(new LinearLayoutManager(StudentListActivity.this));
                } else {
                    Toast.makeText(StudentListActivity.this, "Get student failed. Try again please", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                Log.i(TAG, "Error: " + t.getMessage());
            }
        });
    }

    @OnClick(R.id.add_student_button)
    public void onClickAddStudent(View v) {
        Intent intent = new Intent(this, RegisterStudentActivity.class);
        intent.putExtra("token", token);
        startActivity(intent);
    }

    @OnClick(R.id.record_student_button)
    public void onClickRecordStudent(View v) {
        Intent intent = new Intent(this, StudentRecordActivity.class);
        startActivity(intent);
    }

    private final class StudentList extends AsyncTask<Context, Integer, String> {
        @Override
        protected String doInBackground(Context... contexts) {
            Context context = contexts[0];
            Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
            services = retrofit.create(RetrofitServices.class);
            Call<List<Student>> call = services.getStudents("token " + token);
            try {
                Response<List<Student>> response = call.execute();
                if (response.isSuccessful()) {
                    StudentAdapter studentAdapter = new StudentAdapter(StudentListActivity.this, response.body(), token);
                    student_list_recycler_view.setAdapter(studentAdapter);
                    student_list_recycler_view.setLayoutManager(new LinearLayoutManager(StudentListActivity.this));
                } else {
                    Toast.makeText(StudentListActivity.this, "Get student failed. Try again please", Toast.LENGTH_LONG).show();
                }
            } catch (IOException e) {
                Log.i(TAG, "Error: " + e.getMessage());
            }
            return "OK";
        }
    }
}
