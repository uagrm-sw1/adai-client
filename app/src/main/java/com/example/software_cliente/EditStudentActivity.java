package com.example.software_cliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.software_cliente.Response.Student;
import com.example.software_cliente.Interface.RetrofitServices;
import com.example.software_cliente.Utils.StudentAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditStudentActivity extends AppCompatActivity {

    @BindView(R.id.name_student_edit_text_input)
    TextInputEditText name_student_edit_text_input;
    @BindView(R.id.last_name_student_edit_text_input)
    TextInputEditText last_name_student_edit_text_input;
    @BindView(R.id.birthday_student_edit_text_input)
    TextInputEditText birthday_student_edit_text_input;
    @BindView(R.id.male_student_edit_radio_button)
    RadioButton male_student_edit_radio_button;
    @BindView(R.id.female_student_edit_radio_button)
    RadioButton female_student_edit_radio_button;

    Calendar calendar;
    DatePickerDialog.OnDateSetListener datePickerListener;

    final String TAG = EditStudentActivity.class.getSimpleName();
    final String baseUrl = "http://ec2-3-134-80-247.us-east-2.compute.amazonaws.com/";
    RetrofitServices services;
    Student student;
    private int idStudent;
    private String token = "";
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);
        ButterKnife.bind(this);

        SpannableStringBuilder str = new SpannableStringBuilder("EDITAR INFORMACIÓN");
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 18, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorBackground)));

        preferences = getSharedPreferences("Session", MODE_PRIVATE);

        idStudent = getIntent().getExtras().getInt("idStudent");
        token = preferences.getString("token", null);

        name_student_edit_text_input.setText(getIntent().getExtras().getString("name"));
        last_name_student_edit_text_input.setText(getIntent().getExtras().getString("lastName"));
        String birthday = getIntent().getExtras().getString("birthday");
        birthday_student_edit_text_input.setText(
                birthday.substring(8) + " " + getMonth(Integer.valueOf(birthday.substring(5, 7)) - 1) + " " + birthday.substring(0, 4));
        boolean male = getIntent().getExtras().getBoolean("gender");
        if (male)
            male_student_edit_radio_button.setChecked(true);
        else
            female_student_edit_radio_button.setChecked(true);

        calendar = Calendar.getInstance();
        calendar.set(Integer.valueOf(birthday.substring(0, 4)), Integer.valueOf(birthday.substring(5, 7)) - 1, Integer.valueOf(birthday.substring(8)));

        datePickerListener = (datePicker, year, month, day) -> {
            calendar.set(year, month, day);
            birthday_student_edit_text_input.setText(day + " " + getMonth(month) + " " + year);
        };

        student = new Student();
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

    @OnClick(R.id.birthday_student_edit_text_input)
    public void onClickBirthday(View v) {
        Calendar limit = Calendar.getInstance();
        limit.set(limit.get(Calendar.YEAR) - 4, limit.get(Calendar.MONTH), limit.get(Calendar.DAY_OF_MONTH));

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, datePickerListener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.getDatePicker().setMaxDate(limit.getTimeInMillis());
        datePickerDialog.show();
    }

    @OnClick(R.id.save_changes_student_button)
    public void onClickSaveChanges(View v) {
        if (areFieldsFill()) {
            student.setName(name_student_edit_text_input.getText().toString());
            student.setLastName(last_name_student_edit_text_input.getText().toString());
            student.setBirthday(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            student.setGender(male_student_edit_radio_button.isChecked());

            Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
            services = retrofit.create(RetrofitServices.class);
            Call<Student> call = services.editStudent("token " + token, idStudent, student);
            try {
                Response<Student> response = call.execute();
                if (response.isSuccessful()) {
                    Intent intent = new Intent(this, StudentListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                }
            } catch (IOException e) {
                Log.i(TAG, "Error: " + e.getMessage());
            }

        } else {
            Toast.makeText(this, "Please fill the fields.", Toast.LENGTH_LONG).show();
        }
    }

    private String getMonth(int monthNumber) {
        switch (monthNumber) {
            case 0:
                return "January";
            case 1:
                return "February";
            case 2:
                return "March";
            case 3:
                return "April";
            case 4:
                return "May";
            case 5:
                return "June";
            case 6:
                return "July";
            case 7:
                return "August";
            case 8:
                return "September";
            case 9:
                return "October";
            case 10:
                return "November";
            case 11:
                return "December";
            default:
                return "";
        }
    }

    private boolean areFieldsFill() {
        if (name_student_edit_text_input.getText().toString().equals(""))
            return false;
        if (last_name_student_edit_text_input.getText().toString().equals(""))
            return false;
        if (birthday_student_edit_text_input.getText().toString().equals(""))
            return false;
        return true;
    }
}
