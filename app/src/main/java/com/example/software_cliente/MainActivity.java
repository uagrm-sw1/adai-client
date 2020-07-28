package com.example.software_cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.sign_up_text_view)
    TextView sign_up_text_view;
    @BindView(R.id.forgot_password_text_view)
    TextView forgot_password_text_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        sign_up_text_view.setText(Html.fromHtml(getResources().getString(R.string.sign_up)));
        forgot_password_text_view.setText(Html.fromHtml(getResources().getString(R.string.forgot_password)));
    }

    @OnClick(R.id.sign_in_button)
    public void onClickSignIn(View v) {
        Intent intent = new Intent(this, StudentListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.sign_up_text_view)
    public void onClickSignUp(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
