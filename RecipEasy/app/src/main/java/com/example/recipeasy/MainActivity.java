package com.example.recipeasy;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener{

    private BottomNavigationView bottom_nav_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottom_nav_menu = findViewById(R.id.bottom_nav_menu);
        bottom_nav_menu.setOnItemSelectedListener(this);
        bottom_nav_menu.setSelectedItemId(R.id.find);

    }

    FindRecipeFragment find_recipe_fragment = new FindRecipeFragment();
    BookmarksFragment bookmarks_fragment = new BookmarksFragment();
    MyRecipesFragment my_recipes_fragment = new MyRecipesFragment();
    ProfileFragment profile_fragment = new ProfileFragment();
    RecipeCardFragment recipe_card_fragment = new RecipeCardFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==R.id.find) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, find_recipe_fragment)
                    .commit();
            return true;
        }
        else if (item.getItemId()==R.id.bookmarks) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, bookmarks_fragment)
                    .commit();
            return true;
        }
        else if (item.getItemId()==R.id.my_recipes) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, my_recipes_fragment)
                        .commit();
                return true;
        }
        else if (item.getItemId()==R.id.profile) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, profile_fragment)
                    .commit();
            return true;
        }
        return false;
    }
}