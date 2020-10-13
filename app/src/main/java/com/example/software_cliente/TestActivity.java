package com.example.software_cliente;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.software_cliente.Interface.RetrofitServices;
import com.example.software_cliente.Response.Answer;
import com.example.software_cliente.Response.Exam;
import com.example.software_cliente.Response.ExerciseExam;
import com.example.software_cliente.Response.Exercise;
import com.example.software_cliente.Response.InitialExam;
import com.example.software_cliente.Response.Note;
import com.example.software_cliente.Response.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.multiple_scroll_view)
    ScrollView multiple_scroll_view;
    @BindView(R.id.multiple_question_text_view)
    TextView multiple_question_text_view;
    @BindView(R.id.answer_buttons_linear_layout)
    LinearLayout answer_buttons_linear_layout;
    @BindView(R.id.answer_images_linear_layout)
    LinearLayout answer_images_linear_layout;
    @BindView(R.id.first_answer_button)
    Button first_answer_button;
    @BindView(R.id.second_answer_button)
    Button second_answer_button;
    @BindView(R.id.third_answer_button)
    Button third_answer_button;
    @BindView(R.id.first_answer_image_button)
    ImageButton first_answer_image_button;
    @BindView(R.id.second_answer_image_button)
    ImageButton second_answer_image_button;
    @BindView(R.id.third_answer_image_button)
    ImageButton third_answer_image_button;
    @BindView(R.id.voice_linear_layout)
    LinearLayout voice_linear_layout;
    @BindView(R.id.voice_question_text_view)
    TextView voice_question_text_view;
    @BindView(R.id.paint_linear_layout)
    LinearLayout paint_linear_layout;
    @BindView(R.id.paint_text_view)
    TextView paint_text_view;
    @BindView(R.id.speech_button)
    Button speech_button;

    final int RESULT_SPEECH = 1;
    private String textAudio = "";
    boolean doubleBackToExitPressedOnce = false;
    String type;

    final String TAG = TestActivity.class.getSimpleName();
    final String baseUrl = "http://ec2-3-21-164-122.us-east-2.compute.amazonaws.com/api/";
    RetrofitServices services;
    List<InitialExam> initialExams;
    List<Exam> exams;
    Exam exam;
    List<ExerciseExam> exerciseExams;
    Exercise exercise;
    List<Answer> answers;
    private String token = "";
    SharedPreferences preferences;
    Student student;
    private int idStudent;
    private int idLesson;
    private int idCourse;
    private int idSubject;

    private int index = -1;
    private boolean[] correct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        type = getIntent().getExtras().getString("type");
        preferences = getSharedPreferences("Session", MODE_PRIVATE);

        idStudent = getIntent().getExtras().getInt("idStudent");
        token = preferences.getString("token", null);

        if (type.equals("initial")) {
            SpannableStringBuilder str = new SpannableStringBuilder("EXAMEN INICIAL");
            str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 14, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            getSupportActionBar().setTitle(str);

            student = new Student();
            student.setName(getIntent().getExtras().getString("name"));
            student.setLastName(getIntent().getExtras().getString("lastName"));
            student.setBirthday(getIntent().getExtras().getString("birthday"));
            student.setGender(getIntent().getExtras().getBoolean("gender"));
            student.setInitialExam(true);

            Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
            services = retrofit.create(RetrofitServices.class);
            Call<List<InitialExam>> call = services.getInitialExamExercise();
            call.enqueue(new Callback<List<InitialExam>>() {
                @Override
                public void onResponse(Call<List<InitialExam>> call, Response<List<InitialExam>> response) {
                    if (response.isSuccessful()) {
                        initialExams = response.body();
                        getQuestion();
                    } else {
                        Toast.makeText(TestActivity.this, "Get initial exam failed. Try again please", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<InitialExam>> call, Throwable t) {
                    Log.i(TAG, "Error: " + t.getMessage());
                }
            });

        } else {
            idLesson = getIntent().getExtras().getInt("idLesson");
            idCourse = getIntent().getExtras().getInt("idCourse");
            idSubject = getIntent().getExtras().getInt("idSubject");

            Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
            services = retrofit.create(RetrofitServices.class);
            Call<List<Exam>> callExam = services.getExams();
            callExam.enqueue(new Callback<List<Exam>>() {
                @Override
                public void onResponse(Call<List<Exam>> call, Response<List<Exam>> response) {
                    if (response.isSuccessful()) {
                        exams = response.body();
                        exam = new Exam();
                        for (Exam examSelect : exams) {
                            if (examSelect.getLessonId() == idLesson)
                                exam = examSelect;
                        }

                        SpannableStringBuilder str = new SpannableStringBuilder(exam.getTitle());
                        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, exam.getTitle().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        getSupportActionBar().setTitle(str);

                    } else {
                        Toast.makeText(TestActivity.this, "Get exam failed. Try again please", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Exam>> call, Throwable t) {
                    Log.i(TAG, "Error: " + t.getMessage());
                }
            });

            retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
            services = retrofit.create(RetrofitServices.class);
            Call<List<ExerciseExam>> call = services.getExerciseExam();
            call.enqueue(new Callback<List<ExerciseExam>>() {
                @Override
                public void onResponse(Call<List<ExerciseExam>> call, Response<List<ExerciseExam>> response) {
                    if (response.isSuccessful()) {
                        exerciseExams = response.body();
                        selectExercise();
                        getQuestionExam();
                    } else {
                        Toast.makeText(TestActivity.this, "Get initial exam failed. Try again please", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<ExerciseExam>> call, Throwable t) {
                    Log.i(TAG, "Error: " + t.getMessage());
                }
            });
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorBackground)));
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
                    textAudio = text.get(0).toUpperCase();
                    if (textAudio.equals("AH") || textAudio.equals("EH") || textAudio.equals("OH") || textAudio.equals("UN") || textAudio.equals("US"))
                        textAudio = textAudio.substring(0, 1);
                    if (textAudio.equals("Y"))
                        textAudio = "I";
                    if (textAudio.equals("HE") || textAudio.equals("ES"))
                        textAudio = "E";
                    if (textAudio.equals("Ü") || textAudio.equals("UH"))
                        textAudio = "U";
                    if (answers.get(0).getContent().equals(textAudio)) {
                        Toast.makeText(getApplicationContext(), "Bien hecho!", Toast.LENGTH_SHORT).show();
                        if (type.equals("initial"))
                            getQuestion();
                        else
                            getQuestionExam();
                    } else {
                        correct[index] = false;
                        Toast.makeText(getApplicationContext(), "Inténtalo otra vez.", Toast.LENGTH_SHORT).show();
                    }
                    textAudio = "";
                }
                break;
        }
    }

    private void getQuestion() {
        if (!initialExams.isEmpty()) {
            InitialExam initialExam = initialExams.remove(0);

            Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
            services = retrofit.create(RetrofitServices.class);
            Call<Exercise> call = services.getExercise(initialExam.getExerciseId());
            call.enqueue(new Callback<Exercise>() {
                @Override
                public void onResponse(Call<Exercise> call, Response<Exercise> response) {
                    if (response.isSuccessful()) {
                        exercise = response.body();
                        setQuestion();
                    } else {
                        Toast.makeText(TestActivity.this, "Get exercise failed. Try again please", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Exercise> call, Throwable t) {
                    Log.i(TAG, "Error: " + t.getMessage());
                }
            });
        } else {
            Toast.makeText(TestActivity.this, "Has finalizado el Examen Inicial.", Toast.LENGTH_LONG).show();

            Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
            services = retrofit.create(RetrofitServices.class);
            Call<Student> call = services.editStudent("token " + token, idStudent, student);
            try {
                Response<Student> response = call.execute();
                if (response.isSuccessful()) {
                    Calendar calendar = Calendar.getInstance();
                    int age = calendar.get(Calendar.YEAR) - student.getYear();
                    int idCourse = 0;
                    if (age == 4 || age == 5)
                        idCourse = 1;
                    if (age == 6)
                        idCourse = 2;
                    if (age == 7)
                        idCourse = 3;
                    if (age == 8)
                        idCourse = 4;
                    if (age == 9)
                        idCourse = 5;
                    if (age == 10)
                        idCourse = 6;

                    Intent intent = new Intent(this, AreaListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("idStudent", idStudent);
                    intent.putExtra("idCourse", idCourse);
                    startActivity(intent);
                }
            } catch (IOException e) {
                Log.i(TAG, "Error: " + e.getMessage());
            }
        }
    }

    private void getQuestionExam() {
        if (!exerciseExams.isEmpty()) {
            index++;
            ExerciseExam exerciseExam = exerciseExams.remove(0);

            Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
            services = retrofit.create(RetrofitServices.class);
            Call<Exercise> call = services.getExercise(exerciseExam.getExerciseId());
            call.enqueue(new Callback<Exercise>() {
                @Override
                public void onResponse(Call<Exercise> call, Response<Exercise> response) {
                    if (response.isSuccessful()) {
                        exercise = response.body();
                        setQuestion();
                    } else {
                        Toast.makeText(TestActivity.this, "Get exercise failed. Try again please", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Exercise> call, Throwable t) {
                    Log.i(TAG, "Error: " + t.getMessage());
                }
            });
        } else {
            Toast.makeText(TestActivity.this, "Has finalizado el Examen.", Toast.LENGTH_LONG).show();

            int correctAnswer = 0;
            for (int i = 0; i < correct.length; i++) {
                if (correct[i])
                    correctAnswer++;
            }
            float noteExam = (100 / correct.length) * correctAnswer;
            Log.i(TAG, "NOTE: "+noteExam);

            Note note = new Note();
            note.setNote(noteExam);
            note.setLessonId(idLesson);
            note.setStudentId(idStudent);

            Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
            services = retrofit.create(RetrofitServices.class);
            Call<Note> call = services.createNote(note);
            try {
                Response<Note> response = call.execute();
                if (response.isSuccessful()) {
                    Intent intent = new Intent(this, AreaListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("idStudent", idStudent);
                    intent.putExtra("idCourse", idCourse);
                    intent.putExtra("idSubject", idStudent);
                    startActivity(intent);
                }
            } catch (IOException e) {
                Log.i(TAG, "Error: " + e.getMessage());
            }
        }
    }

    private void setQuestion() {
        if (exercise.getType().equals("selector")) {
            voice_linear_layout.setVisibility(View.GONE);
            paint_linear_layout.setVisibility(View.GONE);
            multiple_scroll_view.setVisibility(View.VISIBLE);

            multiple_question_text_view.setText(exercise.getQuestion());

            getAnswers();
        }
        if (exercise.getType().equals("audio")) {
            paint_linear_layout.setVisibility(View.GONE);
            multiple_scroll_view.setVisibility(View.GONE);
            voice_linear_layout.setVisibility(View.VISIBLE);

            voice_question_text_view.setText(exercise.getQuestion());

            getAnswers();
        }
        if (exercise.getType().equals("pintura")) {
            multiple_scroll_view.setVisibility(View.GONE);
            voice_linear_layout.setVisibility(View.GONE);
            paint_linear_layout.setVisibility(View.VISIBLE);
        }
    }

    private void getAnswers() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        services = retrofit.create(RetrofitServices.class);
        Call<List<Answer>> call = services.getAnswer();
        call.enqueue(new Callback<List<Answer>>() {
            @Override
            public void onResponse(Call<List<Answer>> call, Response<List<Answer>> response) {
                if (response.isSuccessful()) {
                    answers = response.body();
                    selectAnswers();
                    setAnswers();
                } else {
                    Toast.makeText(TestActivity.this, "Get answers failed. Try again please", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Answer>> call, Throwable t) {
                Log.i(TAG, "Error: " + t.getMessage());
            }
        });
    }

    private void selectAnswers() {
        LinkedList<Answer> answersSelected = new LinkedList<>();
        for (Answer answer : answers) {
            if (answer.getExerciseId() != exercise.getId())
                answersSelected.add(answer);
        }
        for (Answer answer : answersSelected) {
            answers.remove(answer);
        }
    }

    private void setAnswers() {
        if (exercise.getType().equals("selector")) {
            String init = "";
            if (answers.get(0).getContent().length() > 4)
                init = answers.get(0).getContent().substring(0, 4);
            if (init.equals("http")) {
                answer_buttons_linear_layout.setVisibility(View.GONE);
                answer_images_linear_layout.setVisibility(View.VISIBLE);
                Glide.with(this).load(answers.get(0).getContent()).into(first_answer_image_button);
                Glide.with(this).load(answers.get(1).getContent()).into(second_answer_image_button);
                if (answers.size() == 3)
                    Glide.with(this).load(answers.get(2).getContent()).into(third_answer_image_button);
                else
                    Glide.with(this).load("https://upload.wikimedia.org/wikipedia/commons/4/4f/No_%28single%29_logo.png").into(third_answer_image_button);
            } else {
                answer_buttons_linear_layout.setVisibility(View.VISIBLE);
                answer_images_linear_layout.setVisibility(View.GONE);
                first_answer_button.setText(answers.get(0).getContent());
                second_answer_button.setText(answers.get(1).getContent());
                if (answers.size() == 3)
                    third_answer_button.setText(answers.get(2).getContent());
                else
                    third_answer_button.setText("Ninguno");
            }
        }
        if (exercise.getType().equals("pintura")) {
        }
    }

    @OnClick(R.id.first_answer_button)
    public void onClickFirstAnswer(View v) {
        if (answers.get(0).isCorrect()) {
            Toast.makeText(getApplicationContext(), "Bien hecho!", Toast.LENGTH_SHORT).show();
            if (type.equals("initial"))
                getQuestion();
            else
                getQuestionExam();
        } else {
            correct[index] = false;
            Toast.makeText(getApplicationContext(), "Inténtalo otra vez.", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.second_answer_button)
    public void onClickSecondAnswer(View v) {
        if (answers.get(1).isCorrect()) {
            Toast.makeText(getApplicationContext(), "Bien hecho!", Toast.LENGTH_SHORT).show();
            if (type.equals("initial"))
                getQuestion();
            else
                getQuestionExam();
        } else {
            correct[index] = false;
            Toast.makeText(getApplicationContext(), "Inténtalo otra vez.", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.third_answer_button)
    public void onClickThirdAnswer(View v) {
        if (answers.size() == 3 && answers.get(2).isCorrect()) {
            Toast.makeText(getApplicationContext(), "Bien hecho!", Toast.LENGTH_SHORT).show();
            if (type.equals("initial"))
                getQuestion();
            else
                getQuestionExam();
        } else {
            correct[index] = false;
            Toast.makeText(getApplicationContext(), "Inténtalo otra vez.", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.first_answer_image_button)
    public void onClickFirstAnswerImage(View v) {
        if (answers.get(0).isCorrect()) {
            Toast.makeText(getApplicationContext(), "Bien hecho!", Toast.LENGTH_SHORT).show();
            if (type.equals("initial"))
                getQuestion();
            else
                getQuestionExam();
        } else {
            correct[index] = false;
            Toast.makeText(getApplicationContext(), "Inténtalo otra vez.", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.second_answer_image_button)
    public void onClickSecondAnswerImage(View v) {
        if (answers.get(1).isCorrect()) {
            Toast.makeText(getApplicationContext(), "Bien hecho!", Toast.LENGTH_SHORT).show();
            getQuestion();
        } else {
            correct[index] = false;
            Toast.makeText(getApplicationContext(), "Inténtalo otra vez.", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.third_answer_image_button)
    public void onClickThirdAnswerImage(View v) {
        if (answers.size() == 3 && answers.get(2).isCorrect()) {
            Toast.makeText(getApplicationContext(), "Bien hecho!", Toast.LENGTH_SHORT).show();
            if (type.equals("initial"))
                getQuestion();
            else
                getQuestionExam();
        } else {
            correct[index] = false;
            Toast.makeText(getApplicationContext(), "Inténtalo otra vez.", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.paint_text_view)
    public void onClickPaint(View v) {
        if (type.equals("initial"))
            getQuestion();
        else
            getQuestionExam();
    }

    private void selectExercise() {
        LinkedList<ExerciseExam> exerciseExamSelected = new LinkedList<>();
        for (ExerciseExam exerciseExam : exerciseExams) {
            if (exerciseExam.getExamId() != exam.getId())
                exerciseExamSelected.add(exerciseExam);
        }
        for (ExerciseExam exerciseExam : exerciseExamSelected) {
            exerciseExams.remove(exerciseExam);
        }
        correct = new boolean[exerciseExams.size()];
        for (int i = 0; i < correct.length; i++)
            correct[i] = true;
    }
}
