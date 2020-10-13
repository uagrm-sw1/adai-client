package com.example.software_cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
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

    final String TAG = RegisterActivity.class.getSimpleName();
    final String baseUrl = "http://ec2-3-21-164-122.us-east-2.compute.amazonaws.com/api/";
    RetrofitServices services;
    User user;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getSupportActionBar().hide();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if (Build.VERSION.SDK_INT >= 23){
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            // Verify permissions
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //request for access
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                }
            }
        }

        preferences = getSharedPreferences("Session", MODE_PRIVATE);

        sign_up_text_view.setText(Html.fromHtml(getResources().getString(R.string.sign_up)));

        user = new User();

        validateSession();
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
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("id", response.body().getId());
                editor.putString("token", response.body().getToken());
                editor.commit();

                Intent intent = new Intent(this, StudentListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
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

    private void validateSession() {
        int id = preferences.getInt("id", 0);
        String token = preferences.getString("token", null);
        if (id > 0 && token != null) {
            Intent intent = new Intent(this, StudentListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }
    }
}
