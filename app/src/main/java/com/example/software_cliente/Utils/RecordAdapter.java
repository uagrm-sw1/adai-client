package com.example.software_cliente.Utils;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.software_cliente.R;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.MyViewHolder> {

    Context context;
    String[] names;
    boolean[] expanded;

    public RecordAdapter(Context context, String[] names, boolean[] expanded) {
        this.context = context;
        this.names = names;
        this.expanded = expanded;
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
        holder.name_student_text_view.setText(names[position]);

        boolean isExpanded = expanded[position];
        holder.expandable_constraint_layout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.view_details_text_view.setText(isExpanded ? Html.fromHtml(context.getResources().getString(R.string.hide_details)) :
                Html.fromHtml(context.getResources().getString(R.string.view_details)));
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name_student_text_view;
        TextView view_details_text_view;
        ConstraintLayout expandable_constraint_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name_student_text_view = itemView.findViewById(R.id.name_student_text_view);
            view_details_text_view = itemView.findViewById(R.id.view_details_text_view);
            view_details_text_view.setText(Html.fromHtml(context.getResources().getString(R.string.sign_up)));
            expandable_constraint_layout = itemView.findViewById(R.id.expandable_constraint_layout);
            view_details_text_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    expanded[getAdapterPosition()] = !expanded[getAdapterPosition()];
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
