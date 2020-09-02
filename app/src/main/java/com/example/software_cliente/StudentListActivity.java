package com.example.software_cliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.software_cliente.Response.Student;
import com.example.software_cliente.Interface.RetrofitServices;
import com.example.software_cliente.Utils.StudentAdapter;

import java.io.IOException;
import java.util.LinkedList;
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

    @BindView(R.id.loading_image)
    ImageView loading_image;
    @BindView(R.id.student_list_recycler_view)
    RecyclerView student_list_recycler_view;

    final String TAG = StudentListActivity.class.getSimpleName();
    final String baseUrl = "http://ec2-3-134-80-247.us-east-2.compute.amazonaws.com/";
    RetrofitServices services;
    private int id;
    private String token = "";
    boolean doubleBackToExitPressedOnce = false;
    List<Student> students;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        ButterKnife.bind(this);

        Glide.with(getBaseContext()).load(R.drawable.jar_loading).into(loading_image);

        preferences = getSharedPreferences("Session", MODE_PRIVATE);

        id = preferences.getInt("id", 0);
        token = preferences.getString("token", null);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        services = retrofit.create(RetrofitServices.class);
        Call<List<Student>> call = services.getStudents("token " + token);
        call.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                if (response.isSuccessful()) {
                    students = response.body();
                    selectStudents();
                    StudentAdapter studentAdapter = new StudentAdapter(StudentListActivity.this, students);
                    loading_image.setVisibility(View.GONE);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout_item) {
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.add_student_button)
    public void onClickAddStudent(View v) {
        Intent intent = new Intent(this, RegisterStudentActivity.class);
        intent.putExtra("first", false);
        startActivity(intent);
    }

    @OnClick(R.id.record_student_button)
    public void onClickRecordStudent(View v) {
        Intent intent = new Intent(this, StudentRecordActivity.class);
        startActivity(intent);
    }

    private void selectStudents() {
        LinkedList<Student> studentsSelected = new LinkedList<>();
        for (Student student : students) {
            if (student.getUserId() != id)
                studentsSelected.add(student);
        }
        for (Student student : studentsSelected) {
            students.remove(student);
        }
    }

    private void logout() {
        preferences.edit().clear().apply();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}
