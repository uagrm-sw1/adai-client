package com.example.software_cliente.Utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.software_cliente.AreaListActivity;
import com.example.software_cliente.Response.Student;
import com.example.software_cliente.EditStudentActivity;
import com.example.software_cliente.R;

import java.util.LinkedList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder> {

    Context context;
    List<Student> students;

    public StudentAdapter(Context context, List<Student> students) {
        this.context = context;
        this.students = students;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.student_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name_student_text_view.setText(students.get(position).getName() + " " + students.get(position).getLastName());
        holder.student = students.get(position);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name_student_text_view;
        Button edit_button;

        Student student;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name_student_text_view = itemView.findViewById(R.id.name_student_text_view);
            edit_button = itemView.findViewById(R.id.edit_button);
            name_student_text_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AreaListActivity.class);
                    context.startActivity(intent);
                }
            });
            edit_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EditStudentActivity.class);
                    intent.putExtra("idStudent", student.getId());
                    intent.putExtra("name", student.getName());
                    intent.putExtra("lastName", student.getLastName());
                    intent.putExtra("birthday", student.getBirthday());
                    intent.putExtra("gender", student.getGender());
                    context.startActivity(intent);
                }
            });
        }
    }
}
