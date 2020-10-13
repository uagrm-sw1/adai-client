package com.example.software_cliente.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.software_cliente.Interface.RetrofitServices;
import com.example.software_cliente.R;
import com.example.software_cliente.Response.Lesson;
import com.example.software_cliente.Response.Note;
import com.example.software_cliente.TestActivity;
import java.util.List;


public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.MyViewHolder> {

    private Context context;
    private List<Lesson> lessons;
    private int idStudent;

    boolean[] completed;

    public LessonAdapter(Context context, List<Lesson> lessons, boolean[] completed, int idStudent) {
        this.context = context;
        this.lessons = lessons;
        this.idStudent = idStudent;
        this.completed = completed;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.lesson_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonAdapter.MyViewHolder holder, int position) {
        holder.lesson_number_text_view.setText("TEMA " + (position + 1));
        holder.lesson_title_text_view.setText(lessons.get(position).getTitle());

        if (completed[position]) {
            holder.complete_image_view.setVisibility(View.VISIBLE);
            holder.lesson_linear_layout.setBackground(context.getResources().getDrawable(R.drawable.oval_lesson_complete));
            holder.lesson_number_text_view.setTextColor(context.getResources().getColor(R.color.colorText));
            holder.lesson_title_text_view.setTextColor(context.getResources().getColor(R.color.colorText));
        }
        holder.lesson = lessons.get(position);
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView lesson_number_text_view;
        TextView lesson_title_text_view;
        LinearLayout lesson_linear_layout;
        ImageView complete_image_view;
        Lesson lesson;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            lesson_number_text_view = itemView.findViewById(R.id.lesson_number_text_view);
            lesson_title_text_view = itemView.findViewById(R.id.lesson_title_text_view);
            lesson_linear_layout = itemView.findViewById(R.id.lesson_linear_layout);
            complete_image_view = itemView.findViewById(R.id.complete_image_view);

            lesson_linear_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TestActivity.class);
                    intent.putExtra("type", "exam");
                    intent.putExtra("idStudent", idStudent);
                    intent.putExtra("idLesson", lesson.getId());
                    intent.putExtra("idCourse", lesson.getCourseId());
                    intent.putExtra("idSubject", lesson.getSubjectId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
