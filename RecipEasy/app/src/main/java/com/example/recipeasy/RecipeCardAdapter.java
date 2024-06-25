package com.example.recipeasy;

import static java.util.concurrent.Executors.newFixedThreadPool;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.StrictMode;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;

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
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RecipeCardAdapter  extends RecyclerView.Adapter<RecipeCardViewHolder> implements Executor {
    ArrayList<Meal> Meals = new ArrayList<>();
    //private final AsyncListDiffer<ArrayList<Meal>> mDiffer = new AsyncListDiffer<ArrayList<Meal>>(this, DIFF_CALLBACK);
    Boolean search_result;
    private static final String TAG = "RecipeCardAdapter";
    Context context;

    public RecipeCardAdapter(Context context) {
        this.context = context;
        repopulateRecyclerView(2,"");
    }

    @NonNull
    @Override
    public RecipeCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context
                = parent.getContext();
        LayoutInflater inflater
                = LayoutInflater.from(context);
        View recipeView
                = inflater
                .inflate(R.layout.fragment_recipe_preview,
                        parent, false);
        return new RecipeCardViewHolder(recipeView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeCardViewHolder viewHolder,final int position) {
        viewHolder.temp_meal = Meals.get(position);
        viewHolder.textview_strMeal.setText(Meals.get(viewHolder.getBindingAdapterPosition()).getStrMeal());
        viewHolder.idMeal = Meals.get(viewHolder.getBindingAdapterPosition()).getIdMeal();
        execute(new Runnable() {
                    @Override
                    public void run() {
                        viewHolder.imageview_strMealThumb.setImageBitmap(getImageBitmap(Meals.get(viewHolder.getBindingAdapterPosition()).getStrThumb()));
                    }
                });
    }

    @Override
    public int getItemCount() {
        return Meals.size();
    }



    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public void RecipeGenerator(String url_API) {
        boolean random_generate = false;
        if (url_API.isEmpty()){
            url_API = "https://www.themealdb.com/api/json/v1/1/random.php";
            random_generate = true;
        }

            URL url;
                    try {
                        url = new URL(url_API);
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }

                    HttpURLConnection urlConnection = null;
                    try {
                        urlConnection = (HttpURLConnection) url.openConnection();
                        urlConnection.setRequestProperty("accept", "application/json");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    ArrayList<Meal> meal_ArrayList = new ArrayList<>();

                    try {
                        InputStream input = new BufferedInputStream(urlConnection.getInputStream());
                        InputStreamReader inputStreamReader = new InputStreamReader(input, StandardCharsets.UTF_8);
                        JsonReader reader = new JsonReader(inputStreamReader);
                        reader.beginObject();
                        String name = reader.nextName();

                        reader.beginArray();
                        while (reader.hasNext()) {
                            Meal temp_meal = new Meal();
                            reader.beginObject();

                            ArrayList<String> ingredient_name = new ArrayList<>();
                            ArrayList<String> ingredient_measure = new ArrayList<>();

                            while (reader.hasNext()) {
                                name = reader.nextName();
                                //Log.d(TAG, name + " " + reader.peek().toString());
                                if (name.equals("idMeal")) {
                                    temp_meal.setIdMeal(reader.nextString());
                                } else if (name.equals("strMeal")) {
                                    temp_meal.setStrMeal(reader.nextString());
                                } else if (name.equals("strArea") && reader.peek() != JsonToken.NULL) {
                                    temp_meal.setStrArea(reader.nextString());
                                } else if (name.equals("strCategory") && reader.peek() != JsonToken.NULL) {
                                    temp_meal.setStrCategory(reader.nextString());
                                } else if (name.equals("strInstructions") && reader.peek() != JsonToken.NULL) {
                                    temp_meal.setStrInstructions(reader.nextString());
                                } else if (name.equals("strMealThumb") && reader.peek() != JsonToken.NULL) {
                                    temp_meal.setStrMealThumb(reader.nextString());
                                } else if (name.equals("strTags") && reader.peek() != JsonToken.NULL) {
                                    temp_meal.setStrTags(reader.nextString());
                                } else if (name.equals("strYoutube") && reader.peek() != JsonToken.NULL) {
                                    temp_meal.setStrYoutube(reader.nextString());
                                } else if (name.matches("strIngredient(.*)") && (reader.peek() != JsonToken.NULL)) {
                                    ingredient_name.add(reader.nextString());
                                } else if (name.matches("strMeasure(.*)") && (reader.peek() != JsonToken.NULL)) {
                                    ingredient_measure.add(reader.nextString());
                                } else {
                                    reader.skipValue();
                                }
                            }

                            HashMap<String, String> ingredients = new LinkedHashMap<>();
                            ingredient_name.forEach((ingr_name) -> {
                                if (!ingr_name.isEmpty()) {
                                    ingredients.put(ingr_name, ingredient_measure.get(ingredient_name.indexOf(ingr_name)));
                                }
                            });

                            temp_meal.setStrIngredient(ingredients);
                            reader.endObject();
                            ingredients.clear();
                            meal_ArrayList.add(new Meal(temp_meal));
                            //Log.d(TAG,temp_meal.toString());



                        }

                        reader.endArray();
                        reader.endObject();

                        if(random_generate){
                            Meals.addAll(meal_ArrayList);
                        }
                        else{
                            Meals.clear();
                            notifyItemRangeRemoved(0,getItemCount());
                            //recipeCardAdapter.notifyDataSetChanged();
                            Meals.addAll(meal_ArrayList);

                            //Log.d(TAG,meal_ArrayList.toString());
                            //Log.d(TAG,Meals.toString());
                        }
                        Log.d(TAG, String.valueOf(Meals.size()));

                        //textview_strMeal.setText(temp_meal.getStrMeal());
                        //textview_strIngredients.setText(temp_meal.getStrIngredient_formatted());
                        // imageview_strMealThumb.setImageBitmap(getImageBitmap(temp_meal.getStrThumb()));
                        input.close();
                        inputStreamReader.close();
                        reader.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                        //textview_my_recipes_fragment.setText("No recipe found");
                    } finally {

                        urlConnection.disconnect();
                    }


    }
    private Bitmap getImageBitmap(String url) {

        Bitmap bm = null;
        HttpURLConnection conn =null;
        try {
            URL aURL = new URL(url);
            conn = (HttpURLConnection) aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            int nh = (int) ( bm.getHeight() * (512.0 / bm.getWidth()) );
            bm = Bitmap.createScaledBitmap(bm, 512, nh, true);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e(TAG, "Error getting bitmap", e);
        } finally {

                conn.disconnect();

        }
        return bm;
    }

    public void repopulateRecyclerView(int count,String url_string){
        ArrayList<Thread> threads = new ArrayList<>();
        int initialSize = getItemCount();
        if(url_string.isEmpty()){
            for(int recipe_count=0;recipe_count<count; recipe_count++){
                threads.add(new Thread(new Runnable() {
                    @Override
                    public void run() {
                        RecipeGenerator("");
                    }
                }));
                threads.get(threads.size()-1).start();
            }
            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            notifyItemRangeInserted(initialSize, getItemCount()-1);
            search_result = false;
        }
        else{
            Thread search_thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    RecipeGenerator(url_string);
                }
            });
            search_thread.start();
            try {
                search_thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            notifyItemRangeInserted(initialSize,getItemCount());
            search_result=true;
        }


    }



    @Override
    public void execute(Runnable command) {
        Thread thread =new Thread(command);
        try {
            thread.start();
            thread.join();
        }catch(IllegalThreadStateException ex) {
                Log.d(TAG,ex.toString());// restore interrupt state
            }
        catch ( InterruptedException ex){
            Log.d(TAG,ex.toString());
        }
    }




    /*
    *
        String random_url_API = "https://www.themealdb.com/api/json/v1/1/random.php";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Log.d(TAG,random_url_API);
        URL url = null;
        try {
            url = new URL(random_url_API);
            //url = new URL(url_API+idMeal);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("accept", "application/json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try {
            ArrayList<Meal> meal_ArrayList = new ArrayList<>();

    InputStream input = new BufferedInputStream(urlConnection.getInputStream());
    JsonReader reader = new JsonReader(new InputStreamReader(input, StandardCharsets.UTF_8));
            reader.beginObject();
    String name = reader.nextName();
            reader.beginArray();
    Meal temp_meal = new Meal();
            while(reader.hasNext()) {
        reader.beginObject();

        ArrayList<String> ingredient_name = new ArrayList<>();
        ArrayList<String> ingredient_measure = new ArrayList<>();
        while (reader.hasNext()) {

            name = reader.nextName();
            Log.d(TAG, name+" "+reader.peek().toString());
            if (name.equals("idMeal")) {
                temp_meal.setIdMeal(reader.nextString());
            }
            else if (name.equals("strMeal")) {
                temp_meal.setStrMeal(reader.nextString());
            }
            else if (name.equals("strArea") && reader.peek() != JsonToken.NULL) {
                temp_meal.setStrArea(reader.nextString());
            }
            else if (name.equals("strCategory") && reader.peek() != JsonToken.NULL) {
                temp_meal.setStrCategory(reader.nextString());
            }
            else if (name.equals("strInstructions")&& reader.peek() != JsonToken.NULL) {
                temp_meal.setStrInstructions(reader.nextString());
            }
            else if (name.equals("strMealThumb")&& reader.peek() != JsonToken.NULL) {
                temp_meal.setStrMealThumb(reader.nextString());
            }
            else if (name.equals("strTags")&& reader.peek() != JsonToken.NULL) {
                temp_meal.setStrTags(reader.nextString());
            }
            else if (name.equals("strYoutube")&& reader.peek() != JsonToken.NULL) {
                temp_meal.setStrYoutube(reader.nextString());
            }
            else if (name.matches("strIngredient(.*)") && (reader.peek() != JsonToken.NULL)) {
                ingredient_name.add(reader.nextString());
            }
            else if (name.matches("strMeasure(.*)") && (reader.peek() != JsonToken.NULL)) {
                ingredient_measure.add(reader.nextString());
            }
            else{
                reader.skipValue();
            }
        }
        HashMap<String,String> ingredients = new LinkedHashMap<>();
        ingredient_name.forEach((ingr_name)->{
            //Log.d(TAG,ingr_name);
            if(!ingr_name.isEmpty()){
                ingredients.put(ingr_name,ingredient_measure.get(ingredient_name.indexOf(ingr_name)));
            }
        });

        temp_meal.setStrIngredient(ingredients);
        reader.endObject();
    }
            reader.endArray();
            reader.endObject();

            Meals.add(temp_meal);
            //textview_strMeal.setText(temp_meal.getStrMeal());
            //textview_strIngredients.setText(temp_meal.getStrIngredient_formatted());
           // imageview_strMealThumb.setImageBitmap(getImageBitmap(temp_meal.getStrThumb()));

}
        catch (IOException e) {
        throw new RuntimeException(e);
        //textview_my_recipes_fragment.setText("No recipe found");

        } finally {
                urlConnection.disconnect();
        }
     */

}
