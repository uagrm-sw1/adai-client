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
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.software_cliente.Adapters.LessonAdapter;
import com.example.software_cliente.Interface.RetrofitServices;
import com.example.software_cliente.Response.InitialExam;
import com.example.software_cliente.Response.Lesson;
import com.example.software_cliente.Response.Note;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AreaActivity extends AppCompatActivity {

    @BindView(R.id.lesson_list_recycler_view)
    RecyclerView lesson_list_recycler_view;

    final String TAG = AreaActivity.class.getSimpleName();
    final String baseUrl = "http://ec2-3-21-164-122.us-east-2.compute.amazonaws.com/api/";
    RetrofitServices services;
    List<Lesson> lessons;

    private String area = "";
    private int idStudent;
    private int idCourse;
    private int idSubject;

    List<Note> notes;
    boolean[] completed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);
        ButterKnife.bind(this);

        idSubject = getIntent().getExtras().getInt("idSubject");
        idStudent = getIntent().getExtras().getInt("idStudent");
        idCourse = getIntent().getExtras().getInt("idCourse");

        SpannableStringBuilder str = new SpannableStringBuilder(area);
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, area.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorBackground)));

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        services = retrofit.create(RetrofitServices.class);
        Call<List<Lesson>> call = services.getLessons();
        call.enqueue(new Callback<List<Lesson>>() {
            @Override
            public void onResponse(Call<List<Lesson>> call, Response<List<Lesson>> response) {
                if (response.isSuccessful()) {
                    lessons = response.body();
                    selectLessons();

                    completed = new boolean[lessons.size()];
                    for (int i = 0; i < completed.length; i++)
                        completed[i] = false;
                    getLessonComplete();

                } else {
                    Toast.makeText(AreaActivity.this, "Get initial exam failed. Try again please", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Lesson>> call, Throwable t) {
                Log.i(TAG, "Error: " + t.getMessage());
            }
        });
    }

    private void getLessonComplete() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        services = retrofit.create(RetrofitServices.class);
        Call<List<Note>> call = services.getNotes();
        call.enqueue(new Callback<List<Note>>() {
            @Override
            public void onResponse(Call<List<Note>> call, Response<List<Note>> response) {
                if (response.isSuccessful()) {
                    notes = response.body();
                    selectNotes();

                    LessonAdapter lessonAdapter = new LessonAdapter(AreaActivity.this, lessons, completed, idStudent);
                    lesson_list_recycler_view.setAdapter(lessonAdapter);
                    lesson_list_recycler_view.setLayoutManager(new LinearLayoutManager(AreaActivity.this));
                } else {
                    Toast.makeText(AreaActivity.this, "Get initial exam failed. Try again please", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Note>> call, Throwable t) {
                Log.i(LessonAdapter.class.getSimpleName(), "Error: " + t.getMessage());
            }
        });
    }

    private void selectNotes() {
        int index = 0;
        for (Lesson lesson : lessons) {
            for (Note note : notes) {
                if (lesson.getId() == note.getLessonId() && note.getStudentId() == idStudent)
                    completed[index] = true;
            }
            index++;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, AreaListActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("idStudent", idStudent);
                intent.putExtra("idCourse", idCourse);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void selectLessons() {
        LinkedList<Lesson> lessonsSelected = new LinkedList<>();
        for (Lesson lesson : lessons) {
            if (lesson.getCourseId() != idCourse || lesson.getSubjectId() != idSubject)
                lessonsSelected.add(lesson);
        }
        for (Lesson lesson : lessonsSelected) {
            lessons.remove(lesson);
        }
    }
}
