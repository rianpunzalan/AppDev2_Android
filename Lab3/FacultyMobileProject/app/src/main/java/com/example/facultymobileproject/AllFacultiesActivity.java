package com.example.facultymobileproject;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class AllFacultiesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    AllFacultiesAdapter adapter;
    RecyclerView recyclerView;

    ArrayList<Faculty> Faculties = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_faculties);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Faculties.add(new Faculty("101", "Robertson", "Myra", 60000.00, 2.50));
        Faculties.add(new Faculty("212", "Smith", "Neal", 40000.00, 3.00));
        Faculties.add(new Faculty("315", "Arlec", "Lisa", 55000.00, 1.50));
        Faculties.add(new Faculty("857", "Fillipo", "Paul", 30000.00, 5.00));
        Faculties.add(new Faculty("370", "Denkan", "Anais", 95000.00, 1.50));
        Faculties.add(new Faculty("1011", "Hutchinson", "Lorelai", 60000.00, 2.50));
        Faculties.add(new Faculty("2121", "House", "Israel", 40000.00, 3.00));
        Faculties.add(new Faculty("3151", "Salinas", "Blaine", 55000.00, 1.50));
        Faculties.add(new Faculty("8571", "Blair", "Aiyana", 30000.00, 5.00));
        Faculties.add(new Faculty("3701", "Cuevas", "Dominik", 95000.00, 1.50));
        Faculties.add(new Faculty("1012", "Shepard", "Sidney", 60000.00, 2.50));
        Faculties.add(new Faculty("2122", "French", "Annabella", 40000.00, 3.00));
        Faculties.add(new Faculty("3152", "Tapia", "Miranda", 55000.00, 1.50));
        Faculties.add(new Faculty("8572", "Davenport", "Adrienne", 30000.00, 5.00));
        Faculties.add(new Faculty("3702", "Boyd", "Davon", 95000.00, 1.50));
        recyclerView = (RecyclerView) findViewById(R.id.view_recycler);


        adapter = new AllFacultiesAdapter(Faculties, getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(AllFacultiesActivity.this));



    }
    @Override
    public boolean onNavigationItemSelected (@NonNull MenuItem item){
        return false;
    }
}