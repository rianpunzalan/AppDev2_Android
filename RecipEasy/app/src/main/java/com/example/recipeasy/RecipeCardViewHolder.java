package com.example.recipeasy;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RecipeCardViewHolder extends RecyclerView.ViewHolder {
    ImageView imageview_strMealThumb;
    TextView textview_strMeal;
    RecipeCardViewHolder mViewModel;
    private static final String TAG = "RecipeCardViewHolder";
    private static final String ID_MEAL = "idMeal";
    private static final String MEAL_OBJECT = "mealsObject";
    private static final String EXTRA_MEAL = "com.example.recipeasy.Meal";
    Meal temp_meal;
    String idMeal;
    View view;

    public RecipeCardViewHolder(@NonNull View itemView) {
        //setAdapter() research this
        super(itemView);
        imageview_strMealThumb = itemView.findViewById(R.id.imageview_strMealThumb);
        textview_strMeal =itemView.findViewById(R.id.textview_strMeal);
        view = itemView;

        view.setOnClickListener(v ->{
            Intent intent = new Intent(view.getContext(),RecipePageActivity.class);
            intent.putExtra(ID_MEAL,idMeal);
            startActivity(view.getContext(),intent,null);
            //Toast.makeText(view.getContext(), textview_f_id_holder.getText().toString(), Toast.LENGTH_SHORT).show();
        });
    }


}