package com.example.software_cliente.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.software_cliente.AreaListActivity;
import com.example.software_cliente.Interface.RetrofitServices;
import com.example.software_cliente.Response.Student;
import com.example.software_cliente.EditStudentActivity;
import com.example.software_cliente.R;
import com.example.software_cliente.StudentListActivity;
import com.example.software_cliente.TestActivity;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder> {

    final String baseUrl = "http://ec2-3-134-80-247.us-east-2.compute.amazonaws.com/";
    RetrofitServices services;
    private String token = "";
    private SharedPreferences preferences;

    Context context;
    List<Student> students;

    public StudentAdapter(Context context, List<Student> students) {
        this.context = context;
        this.students = students;
        preferences = context.getSharedPreferences("Session", MODE_PRIVATE);
        token = preferences.getString("token", null);
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
        holder.name_text_view.setText(students.get(position).getName() + " " + students.get(position).getLastName());
        holder.student = students.get(position);

        Calendar calendar = Calendar.getInstance();
        Student student = students.get(position);

        holder.age_text_view.setText((calendar.get(Calendar.YEAR) - student.getYear()) + " años");
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name_text_view;
        TextView age_text_view;
        Button edit_button;
        Button delete_button;
        Student student;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name_text_view = itemView.findViewById(R.id.name_text_view);
            age_text_view = itemView.findViewById(R.id.age_text_view);
            edit_button = itemView.findViewById(R.id.edit_button);
            delete_button = itemView.findViewById(R.id.delete_button);
            name_text_view.setOnClickListener(new View.OnClickListener() {
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
            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
                    services = retrofit.create(RetrofitServices.class);
                    Call<Student> call = services.deleteStudent("token " + token, student.getId());
                    call.enqueue(new Callback<Student>() {
                        @Override
                        public void onResponse(Call<Student> call, Response<Student> response) {
                            if (response.isSuccessful()) {
                                Intent intent = new Intent(context, StudentListActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                context.startActivity(intent);
                            } else {
                                Toast.makeText(context, "Delete student failed. Try again please", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Student> call, Throwable t) {
                            Log.i(StudentListActivity.class.getSimpleName(), "Error: " + t.getMessage());
                        }
                    });
                }
            });
        }
    }
}
