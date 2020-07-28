package com.example.software_cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);
        ButterKnife.bind(this);

        name_student_edit_text_input.setText("Elian");
        last_name_student_edit_text_input.setText("Delgadillo");
        birthday_student_edit_text_input.setText("March 21 2005");
        male_student_edit_radio_button.setChecked(true);
    }

    @OnClick(R.id.delete_student_button)
    public void onClickDeleteStudent(View v) {
        Intent intent = new Intent(this, StudentListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.save_changes_student_button)
    public void onClickSaveChanges(View v) {
        Intent intent = new Intent(this, StudentListActivity.class);
        startActivity(intent);
    }
}
