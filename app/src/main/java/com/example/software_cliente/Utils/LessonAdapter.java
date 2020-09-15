package com.example.software_cliente.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.software_cliente.R;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.MyViewHolder> {

    private Context context;
    private String[] titles = new String[]{"NÚMEROS", "SUMA", "RESTA", "MULTIPLICACIÓN", "DIVISIÓN"};
    private boolean[] completed = new boolean[]{true, true, false, false, false};

    public LessonAdapter(Context context) {
        this.context = context;
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
        holder.lesson_title_text_view.setText(titles[position]);

        if (completed[position]) {
            holder.complete_image_view.setVisibility(View.VISIBLE);
            holder.lesson_linear_layout.setBackground(context.getResources().getDrawable(R.drawable.oval_lesson_complete));
            holder.lesson_number_text_view.setTextColor(context.getResources().getColor(R.color.colorText));
            holder.lesson_title_text_view.setTextColor(context.getResources().getColor(R.color.colorText));
        }
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView lesson_number_text_view;
        TextView lesson_title_text_view;
        LinearLayout lesson_linear_layout;
        ImageView complete_image_view;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            lesson_number_text_view = itemView.findViewById(R.id.lesson_number_text_view);
            lesson_title_text_view = itemView.findViewById(R.id.lesson_title_text_view);
            lesson_linear_layout = itemView.findViewById(R.id.lesson_linear_layout);
            complete_image_view = itemView.findViewById(R.id.complete_image_view);
        }
    }
}
