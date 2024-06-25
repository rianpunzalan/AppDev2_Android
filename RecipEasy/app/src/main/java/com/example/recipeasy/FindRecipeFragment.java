package com.example.recipeasy;

import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;

import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Toast;

public class FindRecipeFragment extends Fragment {

    private FindRecipeViewModel mViewModel;
    RecyclerView recyclerView;
    NestedScrollView nestedScrollView;
    RecipeCardAdapter recipeCardAdapter;
    private static final String TAG = "FindRecipeFragment";

    SearchView searchView;
    RadioGroup filter_radioGroup;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find_recipe, container, false);
    }

    @Override
    public void onViewCreated( @NonNull View view,
                               @Nullable Bundle savedInstanceState ){
        recipeCardAdapter = new RecipeCardAdapter(getContext());
        recyclerView = view.findViewById(R.id.recycle_recipe);
        nestedScrollView = view.findViewById(R.id.nested_scrollview);
        searchView = view.findViewById(R.id.search_view);
        filter_radioGroup = view.findViewById(R.id.radio_group);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {

                int filter = filter_radioGroup.getCheckedRadioButtonId();
                String url_string ="";
                if(filter == R.id.radio_name){
                    if(query.isEmpty()){
                        url_string = "https://www.themealdb.com/api/json/v1/1/random.php";
                    }
                    else if(query.length()==1){
                        url_string ="https://www.themealdb.com/api/json/v1/1/search.php?f="+query;
                    }
                    else{
                        url_string ="https://www.themealdb.com/api/json/v1/1/search.php?s="+query;
                    }
                }
                else if(filter == R.id.radio_ingredient){
                    url_string ="https://www.themealdb.com/api/json/v1/1/filter.php?i="+query;
                }
                else if(filter == R.id.radio_category){
                    url_string ="https://www.themealdb.com/api/json/v1/1/filter.php?c="+query;
                }
                else if(filter == R.id.radio_cuisine_type){
                    url_string ="https://www.themealdb.com/api/json/v1/1/filter.php?a="+query;
                }


                Log.d(TAG, url_string);

                String finalUrl_string = url_string;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.post(new Runnable() {
                            public void run() {
                                recipeCardAdapter.repopulateRecyclerView(1, finalUrl_string);
                                recyclerView.getAdapter().notifyDataSetChanged();
                            }
                        });
                    }
                }).start();
                //recyclerView.removeAllViews();

                recyclerView.setAdapter(recipeCardAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                searchView.setQuery("", false); // clear the text
                searchView.setIconified(true); // close the search editor and make search icon again
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
//        searchView.setOnSearchClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d(TAG, searchView.toString());
//                }
//            }
//
//        );
        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            //Log.d(TAG,String.valueOf(recipeCardAdapter.getItemCount()));
            if (!v.canScrollVertically(500) && !recipeCardAdapter.search_result) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.post(new Runnable() {
                            public void run() {
                                recipeCardAdapter.repopulateRecyclerView(2,"");
                            }
                        });
                    }
                }).start();
                recyclerView.setAdapter(recipeCardAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });
        //Log.d(TAG,String.valueOf(v));
        recyclerView.setAdapter(recipeCardAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }





}