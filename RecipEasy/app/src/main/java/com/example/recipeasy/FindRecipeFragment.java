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

public class FindRecipeFragment extends Fragment {

    private FindRecipeViewModel mViewModel;
    RecyclerView recyclerView;
    NestedScrollView nestedScrollView;
    RecipeCardAdapter recipeCardAdapter;
    private static final String TAG = "FindRecipeFragment";
    public static FindRecipeFragment newInstance() {
        return new FindRecipeFragment();
    }

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

        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            //Log.d(TAG,String.valueOf(recipeCardAdapter.getItemCount()));
            if (!v.canScrollVertically(1000)) {
                recyclerView.post(new Runnable() {
                    public void run() {
                        recipeCardAdapter.repopulateRecyclerView(10);
                    }
                });


            }
        });
        //Log.d(TAG,String.valueOf(v));
        recyclerView.setAdapter(recipeCardAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public int checkFragmentPosition(){

        return 0;
    }



}