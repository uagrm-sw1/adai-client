package com.example.software_cliente.Adapters;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.software_cliente.Interface.RetrofitServices;
import com.example.software_cliente.R;
import com.example.software_cliente.Response.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.MyViewHolder> {

    Context context;
    List<Student> students;

    final String baseUrl = "http://ec2-3-21-164-122.us-east-2.compute.amazonaws.com/api/";
    RetrofitServices services;

    public RecordAdapter(Context context, List<Student> students) {
        this.context = context;
        this.students = students;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name_student_text_view.setText(students.get(position).getName() + " " + students.get(position).getLastName());
        holder.average_text_view.setText("" + students.get(position).getAverage());
        holder.student = students.get(position);

        boolean isExpanded = students.get(position).expanded;
        holder.expandable_constraint_layout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.view_details_text_view.setText(isExpanded ? Html.fromHtml(context.getResources().getString(R.string.hide_details)) :
                Html.fromHtml(context.getResources().getString(R.string.view_details)));
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name_student_text_view;
        TextView view_details_text_view;
        ConstraintLayout expandable_constraint_layout;
        TextView average_text_view;
        Student student;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name_student_text_view = itemView.findViewById(R.id.name_student_text_view);
            view_details_text_view = itemView.findViewById(R.id.view_details_text_view);
            view_details_text_view.setText(Html.fromHtml(context.getResources().getString(R.string.sign_up)));
            expandable_constraint_layout = itemView.findViewById(R.id.expandable_constraint_layout);
            average_text_view = itemView.findViewById(R.id.average_text_view);
            view_details_text_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    students.get(getAdapterPosition()).expanded = !students.get(getAdapterPosition()).expanded;
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
