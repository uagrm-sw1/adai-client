package com.example.software_cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterStudentActivity extends AppCompatActivity {

    @BindView(R.id.birthday_student_text_input)
    TextInputEditText birthday_text_input;

    Calendar calendar;
    DatePickerDialog.OnDateSetListener datePickerListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);
        ButterKnife.bind(this);

        calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR) - 4, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerListener = (datePicker, year, month, day) -> {
            calendar.set(year, month, day);
            birthday_text_input.setText(getMonth(month) + " " + day + " " + year);
        };
    }

    @OnClick({R.id.birthday_student_text_input})
    public void onClickBirthday(View v) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, datePickerListener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }

    @OnClick(R.id.register_student_button)
    public void onClickRegister(View v) {
        Intent intent = new Intent(this, StudentListActivity.class);
        startActivity(intent);
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
}
