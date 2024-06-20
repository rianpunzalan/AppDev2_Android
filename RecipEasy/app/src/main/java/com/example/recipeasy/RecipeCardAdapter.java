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

    private static final String TAG = "RecipeCardAdapter";
    Context context;

    public RecipeCardAdapter(Context context) {
        this.context = context;
        repopulateRecyclerView(10);
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

    /*public static final DiffUtil.ItemCallback<Meals> DIFF_CALLBACK = new DiffUtil.ItemCallback<Meals>() {
        @Override
        public boolean areItemsTheSame(
                @NonNull Meal oldMeal, @NonNull Meal newMeal) {
            // User properties may have changed if reloaded from the DB, but ID is fixed
            return oldMeal.getIdMeal().equals(newMeal.getIdMeal());
        }

        @Override
        public boolean areContentsTheSame(
                @NonNull Meal oldMeal, @NonNull Meal newMeal) {
            // NOTE: if you use equals, your object must properly override Object#equals()
            // Incorrectly returning false here will result in too many animations.
            return oldMeal.equals(newMeal);
        }
    };

    public void submitList() {

        mDiffer.submitList(randomRecipeGenerator(5));
    }
*/


    @Override
    public int getItemCount() {
        return Meals.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }




    public void randomRecipeGenerator() {

        ArrayList<Meal> meal_ArrayList = new ArrayList<>();

                    String random_url_API = "https://www.themealdb.com/api/json/v1/1/random.php";
                    //Log.d(TAG, random_url_API);

                    URL url;
                    try {
                        url = new URL(random_url_API);
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


                    Meal temp_meal;
                    try {
                        InputStream input = new BufferedInputStream(urlConnection.getInputStream());
                        JsonReader reader = new JsonReader(new InputStreamReader(input, StandardCharsets.UTF_8));
                        reader.beginObject();
                        String name = reader.nextName();
                        reader.beginArray();
                        temp_meal = new Meal();
                        while (reader.hasNext()) {
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
                        }
                        reader.endArray();
                        reader.endObject();

                        //meal_ArrayList.add(temp_meal);
                        //textview_strMeal.setText(temp_meal.getStrMeal());
                        //textview_strIngredients.setText(temp_meal.getStrIngredient_formatted());
                        // imageview_strMealThumb.setImageBitmap(getImageBitmap(temp_meal.getStrThumb()));

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                        //textview_my_recipes_fragment.setText("No recipe found");
                    } finally {
                        urlConnection.disconnect();
                    }

                    Meals.add(temp_meal);
        //Log.d(TAG, String.valueOf(thread.isAlive()));
       // StrictMode.setThreadPolicy(old_policy);

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

    public void repopulateRecyclerView(int count){
        ArrayList<Runnable> jobs = new ArrayList<Runnable>();

        for(int recipe_count=0;recipe_count<count; recipe_count++){
            jobs.add(() -> randomRecipeGenerator());
            notifyItemInserted(getItemCount()-1);
        }
        ArrayList<Thread> threads = new ArrayList<>();
        jobs.forEach(runnable -> {
            threads.add(new Thread(runnable));
            threads.get(threads.size()-1).start();
        });

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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
