package com.example.software_cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.software_cliente.Response.User;
import com.example.software_cliente.Interface.RetrofitServices;
import com.example.software_cliente.Response.LoginResponse;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.email_text_input)
    TextInputEditText email_text_input;
    @BindView(R.id.password_text_input)
    TextInputEditText password_text_input;
    @BindView(R.id.sign_up_text_view)
    TextView sign_up_text_view;
    @BindView(R.id.forgot_password_text_view)
    TextView forgot_password_text_view;

    final String TAG = RegisterActivity.class.getSimpleName();
    final String baseUrl = "http://ec2-3-134-80-247.us-east-2.compute.amazonaws.com/";
    RetrofitServices services;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        sign_up_text_view.setText(Html.fromHtml(getResources().getString(R.string.sign_up)));
        forgot_password_text_view.setText(Html.fromHtml(getResources().getString(R.string.forgot_password)));

        user = new User();
    }

    @OnClick(R.id.sign_in_button)
    public void onClickSignIn(View v) {
        user.setEmail(email_text_input.getText().toString());
        user.setPassword(password_text_input.getText().toString());

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        services = retrofit.create(RetrofitServices.class);
        Call<LoginResponse> call = services.login(user);
        //Sync
        try{
            Response<LoginResponse> response = call.execute();
            if (response.isSuccessful()) {
                Intent intent = new Intent(this, StudentListActivity.class);
                intent.putExtra("token", response.body().getToken());
                startActivity(intent);
            } else {
                password_text_input.setText("");
                Toast.makeText(this, "Incorrect email or password.", Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            Log.i(TAG, "Error: " + e.getMessage());
        }
        //Async
        /*call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body() != null) {
                    Intent intent = new Intent(getBaseContext(), StudentListActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getBaseContext(), "Incorrect email or password.", Toast.LENGTH_LONG).show();
                    password_text_input.setText("");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.i(TAG, "Error: " + t.getMessage());
            }
        });*/
    }

    @OnClick(R.id.sign_up_text_view)
    public void onClickSignUp(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
