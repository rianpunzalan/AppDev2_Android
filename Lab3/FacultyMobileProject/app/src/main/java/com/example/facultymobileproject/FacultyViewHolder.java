package com.example.facultymobileproject;

import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class FacultyViewHolder extends RecyclerView.ViewHolder {
    TextView textview_f_bonus_holder;
    TextView textview_f_id_holder;
    TextView textview_f_lname_holder;
    TextView textview_f_fname_holder;
    TextView textview_f_salary_holder;

    Layout layout_frame;
    View view;

    public FacultyViewHolder(@NonNull View itemView) {
        super(itemView);
        textview_f_bonus_holder = (TextView)itemView.findViewById(R.id.textview_f_bonus_holder);
        textview_f_id_holder =(TextView) itemView.findViewById(R.id.textview_f_id_holder);
        textview_f_lname_holder = (TextView)itemView.findViewById(R.id.textview_f_lname_holder);
        textview_f_fname_holder = (TextView)itemView.findViewById(R.id.textview_f_fname_holder);
        textview_f_salary_holder = (TextView)itemView.findViewById(R.id.textview_f_salary_holder);
        view = itemView;


    }
}