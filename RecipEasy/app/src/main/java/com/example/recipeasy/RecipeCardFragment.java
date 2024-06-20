package com.example.recipeasy;

import static android.content.Intent.getIntent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class RecipeCardFragment extends Fragment {

    private ImageView imageview_strMealThumb;
    private TextView textview_strMeal;
    private TextView textview_strIngredients;
    private RecipeCardViewHolder mViewModel;
    private static final String TAG = "RecipeCardFragment";
    private static final String ID_MEAL = "idMeal";
    private static final String MEAL_OBJECT = "mealsObject";
    public static RecipeCardFragment newInstance() {
        return new RecipeCardFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_recipe_card, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,@NonNull Bundle savedInstanceState){

        /*String idMeal ="0";
        String url_API = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=";

        imageview_strMealThumb = getView().findViewById(R.id.imageview_strMealThumb);
        textview_strMeal = getView().findViewById(R.id.textview_strMeal);
        textview_strIngredients =getView().findViewById(R.id.textview_strIngredients);

        Log.d(TAG,url_API+idMeal);

        textview_strMeal.setText(mealFragment.getStrMeal());
        textview_strIngredients.setText(mealFragment.getStrIngredient_formatted());
        imageview_strMealThumb.setImageBitmap(getImageBitmap(mealFragment.getStrThumb()));*/
    }

    private Bitmap getImageBitmap(String url) {

        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e(TAG, "Error getting bitmap", e);
        }
        return bm;
    }

}