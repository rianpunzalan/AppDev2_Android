package com.example.facultymobileproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AllFacultiesAdapter extends RecyclerView.Adapter<FacultyViewHolder> {

    ArrayList<Faculty> Faculties = new ArrayList<>();

    Context context;

    public AllFacultiesAdapter(ArrayList<Faculty> Faculties, Context context) {
        this.Faculties = Faculties;
        this.context = context;

    }


    @NonNull
    @Override
    public FacultyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context
                = parent.getContext();
        LayoutInflater inflater
                = LayoutInflater.from(context);

        // Inflate the layout

        View facultyView
                = inflater
                .inflate(R.layout.activity_faculty_view_holder,
                        parent, false);

        FacultyViewHolder viewHolder = new FacultyViewHolder(facultyView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FacultyViewHolder viewHolder, int position) {
        //final index = viewHolder.getAdapterPosition();
        viewHolder.textview_f_bonus_holder.setText(String.valueOf(Faculties.get(position).getF_bonusRate()));
        viewHolder.textview_f_id_holder.setText(Faculties.get(position).getF_Id());
        viewHolder.textview_f_lname_holder.setText(Faculties.get(position).getF_Lname());
        viewHolder.textview_f_fname_holder.setText(Faculties.get(position).getF_Fname());
        viewHolder.textview_f_salary_holder.setText(String.valueOf(Faculties.get(position).getF_Salary()));


    }

    @Override
    public int getItemCount() {
        return Faculties.size();
    }

    @Override
    public void onAttachedToRecyclerView(
            RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
