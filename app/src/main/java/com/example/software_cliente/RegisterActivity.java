package com.example.software_cliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.software_cliente.Response.Tutor;
import com.example.software_cliente.Response.User;
import com.example.software_cliente.Interface.RetrofitServices;
import com.example.software_cliente.Response.LoginResponse;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.name_tutor_text_input)
    TextInputEditText name_tutor_text_input;
    @BindView(R.id.last_name_tutor_text_input)
    TextInputEditText last_name_tutor_text_input;
    @BindView(R.id.birthday_tutor_text_input)
    TextInputEditText birthday_text_input;
    @BindView(R.id.male_tutor_radio_button)
    RadioButton male_tutor_radio_button;
    @BindView(R.id.female_tutor_radio_button)
    RadioButton female_tutor_radio_button;
    @BindView(R.id.email_tutor_text_input)
    TextInputEditText email_tutor_text_input;
    @BindView(R.id.password_tutor_text_input)
    TextInputEditText password_tutor_text_input;
    @BindView(R.id.confirm_password_tutor_text_input)
    TextInputEditText confirm_password_tutor_text_input;

    Calendar calendar;
    DatePickerDialog.OnDateSetListener datePickerListener;

    final String TAG = RegisterActivity.class.getSimpleName();
    final String baseUrl = "http://ec2-3-134-80-247.us-east-2.compute.amazonaws.com/";
    RetrofitServices services;
    Tutor tutor;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        SpannableStringBuilder str = new SpannableStringBuilder("REGISTRAR DATOS");
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 15, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorBackground)));

        preferences = getSharedPreferences("Session", MODE_PRIVATE);

        calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR) - 18, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerListener = (datePicker, year, month, day) -> {
            calendar.set(year, month, day);
            birthday_text_input.setText(day + " " + getMonth(month) + " " + year);
        };
        male_tutor_radio_button.setChecked(true);

        tutor = new Tutor();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.birthday_tutor_text_input)
    public void onClickBirthday(View v) {
        Calendar limit = Calendar.getInstance();
        limit.set(limit.get(Calendar.YEAR) - 4, limit.get(Calendar.MONTH), limit.get(Calendar.DAY_OF_MONTH));

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, datePickerListener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.getDatePicker().setMaxDate(limit.getTimeInMillis());
        datePickerDialog.show();
    }

    @OnClick(R.id.register_tutor_button)
    public void onClickRegister(View v) {
        if (areFieldsFill()) {
            if (password_tutor_text_input.getText().toString().equals(confirm_password_tutor_text_input.getText().toString())) {
                tutor.setName(name_tutor_text_input.getText().toString());
                tutor.setLastName(last_name_tutor_text_input.getText().toString());
                tutor.setBirthday(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                tutor.setGender(male_tutor_radio_button.isChecked());
                tutor.setEmail(email_tutor_text_input.getText().toString());
                tutor.setPassword(password_tutor_text_input.getText().toString());

                Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
                services = retrofit.create(RetrofitServices.class);
                Call<Tutor> call = services.register(tutor);
                try {
                    Response<Tutor> response = call.execute();
                    if (response.isSuccessful()) {
                        User user = new User();
                        user.setEmail(tutor.getEmail());
                        user.setPassword(tutor.getPassword());
                        login(user);
                    } else {
                        password_tutor_text_input.setText("");
                        confirm_password_tutor_text_input.setText("");
                        Toast.makeText(this, "User registration failed. Try again please", Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    Log.i(TAG, "Error: " + e.getMessage());
                }
            } else {
                confirm_password_tutor_text_input.setText("");
                Toast.makeText(this, "Password are not matching.", Toast.LENGTH_LONG).show();
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
        if (name_tutor_text_input.getText().toString().equals(""))
            return false;
        if (last_name_tutor_text_input.getText().toString().equals(""))
            return false;
        if (email_tutor_text_input.getText().toString().equals(""))
            return false;
        if (password_tutor_text_input.getText().toString().equals(""))
            return false;
        if (confirm_password_tutor_text_input.getText().toString().equals(""))
            return false;
        return true;
    }

    private void login(User user) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        services = retrofit.create(RetrofitServices.class);
        Call<LoginResponse> call = services.login(user);
        try{
            Response<LoginResponse> response = call.execute();
            if (response.isSuccessful()) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("id", response.body().getId());
                editor.putString("token", response.body().getToken());
                editor.commit();

                Intent intent = new Intent(this, RegisterStudentActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("first", true);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Incorrect email or password.", Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            Log.i(TAG, "Error: " + e.getMessage());
        }
    }
}
