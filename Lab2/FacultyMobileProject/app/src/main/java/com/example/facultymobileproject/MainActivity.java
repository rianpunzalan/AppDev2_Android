package com.example.facultymobileproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private TextView textView_f_id;
    private TextView textView_f_lname;
    private TextView textView_f_fname;
    private TextView textView_f_salary;
    private TextView textView_f_bonus;
    private TextView textView_calcBonus;
    private Button button_prev_faculty;
    private Button button_next_faculty;
    private Button button_calculated_bonus;
    private static final String KEY_INDEX = "key";
    private static final String TAG = "Faculty Mobile Project";
    private int currentIndex = 0;

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

        Map<String,Faculty> Faculties = new HashMap<String,Faculty>();


        Faculties.put("101",new Faculty("101","Robertson", "Myra",60000.00,2.50));
        Faculties.put("212",new Faculty("212","Smith","Neal",40000.00,3.00));
        Faculties.put("315",new Faculty("315","Arlec", "Lisa", 55000.00, 1.50));
        Faculties.put("857",new Faculty("857","Fillipo", "Paul", 30000.00, 5.00));
        Faculties.put("370",new Faculty("370","Denkan", "Anais", 95000.00, 1.50));
        HashMap<String,Faculty> FacultiesLinked = new LinkedHashMap<>(Faculties);
        ArrayList<String> Faculty_ArrayList = new ArrayList<>(FacultiesLinked.keySet());


        DecimalFormat df = new DecimalFormat("0.00");
        textView_f_id = (TextView) findViewById(R.id.textView_f_id);
        textView_f_lname = (TextView) findViewById(R.id.textView_f_lname);
        textView_f_fname = (TextView) findViewById(R.id.textView_f_fname);
        textView_f_salary = (TextView) findViewById(R.id.textView_f_salary);
        textView_f_bonus = (TextView) findViewById(R.id.textView_f_bonus);
        textView_calcBonus = (TextView) findViewById(R.id.textView_calcBonus);


        button_prev_faculty = (Button) findViewById(R.id.button_prev_faculty);
        button_prev_faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentIndex == 0){
                    currentIndex = Faculty_ArrayList.size() - 1;
                }else{
                    currentIndex = (currentIndex -1) % Faculty_ArrayList.size();
                }

                Faculty temp_faculty =  new Faculty(FacultiesLinked.get(Faculty_ArrayList.get(currentIndex)));
                textView_f_id.setText("Faculty No: " +temp_faculty.getF_Id());
                textView_f_lname.setText("Faculty LName: " +temp_faculty.getF_Lname());
                textView_f_fname.setText("Faculty FName: " +temp_faculty.getF_Fname());
                textView_f_salary.setText("Faculty Salary: " +df.format(temp_faculty.getF_Salary()) + "$");
                textView_f_bonus.setText("Faculty Rate Bonus: " +df.format(temp_faculty.getF_bonusRate()));
                textView_calcBonus.setText(" ");

            }
        });
        button_next_faculty = (Button) findViewById(R.id.button_next_faculty);
        button_next_faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    currentIndex = (currentIndex +1) % Faculty_ArrayList.size();
                    Faculty temp_faculty =  new Faculty(FacultiesLinked.get(Faculty_ArrayList.get(currentIndex)));
                textView_f_id.setText("Faculty No: " +temp_faculty.getF_Id());
                textView_f_lname.setText("Faculty LName: " +temp_faculty.getF_Lname());
                textView_f_fname.setText("Faculty FName: " +temp_faculty.getF_Fname());
                textView_f_salary.setText("Faculty Salary: " +df.format(temp_faculty.getF_Salary()) + "$");
                textView_f_bonus.setText("Faculty Rate Bonus: " +df.format(temp_faculty.getF_bonusRate()));
                textView_calcBonus.setText(" ");
            }
        });
        button_calculated_bonus = (Button) findViewById(R.id.button_calculated_bonus);
        button_calculated_bonus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_calcBonus.setText("Faculty Amount Bonus: "+df.format( FacultiesLinked.get(Faculty_ArrayList.get(currentIndex)).calculate_Bonus()) + "$");
            }
        });

        if(savedInstanceState != null){
            currentIndex = savedInstanceState.getInt(KEY_INDEX);
            Faculty temp_faculty =  new Faculty(FacultiesLinked.get(Faculty_ArrayList.get(currentIndex)));
            textView_f_id.setText("Faculty No: " +temp_faculty.getF_Id());
            textView_f_lname.setText("Faculty LName: " +temp_faculty.getF_Lname());
            textView_f_fname.setText("Faculty FName: " +temp_faculty.getF_Fname());
            textView_f_salary.setText("Faculty Salary: " +df.format(temp_faculty.getF_Salary()) + "$");
            textView_f_bonus.setText("Faculty Rate Bonus: " +df.format(temp_faculty.getF_bonusRate()));
        }
        else{
            Faculty temp_faculty = new Faculty(FacultiesLinked.get(Faculty_ArrayList.get(0)));
            textView_f_id.setText("Faculty No: " +temp_faculty.getF_Id());
            textView_f_lname.setText("Faculty LName: " +temp_faculty.getF_Lname());
            textView_f_fname.setText("Faculty FName: " +temp_faculty.getF_Fname());
            textView_f_salary.setText("Faculty Salary: " +df.format(temp_faculty.getF_Salary()) + "$");
            textView_f_bonus.setText("Faculty Rate Bonus: " +df.format(temp_faculty.getF_bonusRate()));
            //textView_calcBonus.setText(" ");
        }


    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        Log.d(TAG, "onSaveIntanceState() called");
        System.out.println("store CurrentIndex: "+currentIndex);
        savedInstanceState.putInt(KEY_INDEX, currentIndex);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onStart(){
        super.onStart();

        Log.d(TAG, "onStart() called");
    }


    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called");
    }
    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() called");
    }
    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }


}