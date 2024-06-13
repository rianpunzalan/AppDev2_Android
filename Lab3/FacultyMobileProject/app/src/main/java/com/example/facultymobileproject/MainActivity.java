package com.example.facultymobileproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
    private Button button_faculty_details;
    private Button button_all_faculties;
    private static final String KEY_INDEX = "key";
    private static final String TAG = "Faculty Mobile Project";
    private static final String KEY_ARRAY_LIST = "array_list";
    private int currentIndex = 0;
    DecimalFormat df = new DecimalFormat("0.00");
    ArrayList<Faculty> Faculties = new ArrayList<>();


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



        Faculties.add(new Faculty("101","Robertson", "Myra",60000.00,2.50));
        Faculties.add(new Faculty("212","Smith","Neal",40000.00,3.00));
        Faculties.add(new Faculty("315","Arlec", "Lisa", 55000.00, 1.50));
        Faculties.add(new Faculty("857","Fillipo", "Paul", 30000.00, 5.00));
        Faculties.add(new Faculty("370","Denkan", "Anais", 95000.00, 1.50));


        textView_f_id = findViewById(R.id.textView_f_id);
        textView_f_lname = findViewById(R.id.textView_f_lname);
        textView_f_fname = findViewById(R.id.textView_f_fname);
        textView_f_salary = findViewById(R.id.textView_f_salary);
        textView_f_bonus = findViewById(R.id.textView_f_bonus);
        textView_calcBonus = findViewById(R.id.textView_calcBonus);

        button_prev_faculty = findViewById(R.id.button_prev_faculty);
        button_prev_faculty.setOnClickListener(v -> {
            if(currentIndex == 0){
                currentIndex = Faculties.size() - 1;
            }else{
                currentIndex = (currentIndex -1) % Faculties.size();
            }

            textView_f_id.setText("Faculty No: " +Faculties.get(currentIndex).getF_Id());
            textView_f_lname.setText("Faculty LName: " +Faculties.get(currentIndex).getF_Lname());
            textView_f_fname.setText("Faculty FName: " +Faculties.get(currentIndex).getF_Fname());
            textView_f_salary.setText("Faculty Salary: " +df.format(Faculties.get(currentIndex).getF_Salary()) + "$");
            textView_f_bonus.setText("Faculty Rate Bonus: " +df.format(Faculties.get(currentIndex).getF_bonusRate()));
            textView_calcBonus.setText("Faculty Amount Bonus: "+df.format(Faculties.get(currentIndex).calculate_Bonus()) + "$");

        });

        button_next_faculty = (Button) findViewById(R.id.button_next_faculty);
        button_next_faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex +1) % Faculties.size();

                textView_f_id.setText("Faculty No: " +Faculties.get(currentIndex).getF_Id());
                textView_f_lname.setText("Faculty LName: " +Faculties.get(currentIndex).getF_Lname());
                textView_f_fname.setText("Faculty FName: " +Faculties.get(currentIndex).getF_Fname());
                textView_f_salary.setText("Faculty Salary: " +df.format(Faculties.get(currentIndex).getF_Salary()) + "$");
                textView_f_bonus.setText("Faculty Rate Bonus: " +df.format(Faculties.get(currentIndex).getF_bonusRate()));
                textView_calcBonus.setText("Faculty Amount Bonus: "+df.format(Faculties.get(currentIndex).calculate_Bonus()) + "$");
            }
        });

        button_faculty_details = findViewById(R.id.button_faculty_details);
        button_faculty_details.setOnClickListener(view -> {

            String f_Id = Faculties.get(currentIndex).getF_Id();
            String f_Lname = Faculties.get(currentIndex).getF_Lname();
            String f_Fname = Faculties.get(currentIndex).getF_Fname();
            double f_Salary = Faculties.get(currentIndex).getF_Salary();
            double f_bonusRate = Faculties.get(currentIndex).getF_bonusRate();

            Intent intent = FacultyActivity.newIntent(MainActivity.this,
                    f_Id,
                    f_Lname,
                    f_Fname,
                    f_Salary,
                    f_bonusRate);

            startActivityIntent.launch(intent);
        });
        button_all_faculties = findViewById(R.id.button_all_faculties);
        button_all_faculties.setOnClickListener(view ->{
           Intent intent = new Intent(this,AllFacultiesActivity.class);
            startActivity(intent);
        });
        /*button_calculated_bonus = (Button) findViewById(R.id.button_calculated_bonus);
        button_calculated_bonus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_calcBonus.setText("Faculty Amount Bonus: "+df.format( FacultiesLinked.get(Faculty_ArrayList.get(currentIndex)).calculate_Bonus()) + "$");
            }
        });*/

        if(savedInstanceState != null){
            Faculties.clear();
            Faculties.addAll(savedInstanceState.getParcelableArrayList(KEY_ARRAY_LIST));
            currentIndex = savedInstanceState.getInt(KEY_INDEX);

            textView_f_id.setText("Faculty No: " +Faculties.get(currentIndex).getF_Id());
            textView_f_lname.setText("Faculty LName: " +Faculties.get(currentIndex).getF_Lname());
            textView_f_fname.setText("Faculty FName: " +Faculties.get(currentIndex).getF_Fname());
            textView_f_salary.setText("Faculty Salary: " +df.format(Faculties.get(currentIndex).getF_Salary()) + "$");
            textView_f_bonus.setText("Faculty Rate Bonus: " +df.format(Faculties.get(currentIndex).getF_bonusRate()));
            textView_calcBonus.setText("Faculty Amount Bonus: "+df.format(Faculties.get(currentIndex).calculate_Bonus()) + "$");
        }
        else{
            textView_f_id.setText("Faculty No: " +Faculties.get(0).getF_Id());
            textView_f_lname.setText("Faculty LName: " +Faculties.get(0).getF_Lname());
            textView_f_fname.setText("Faculty FName: " +Faculties.get(0).getF_Fname());
            textView_f_salary.setText("Faculty Salary: " +df.format(Faculties.get(0).getF_Salary()) + "$");
            textView_f_bonus.setText("Faculty Rate Bonus: " +df.format(Faculties.get(0).getF_bonusRate()));
            textView_calcBonus.setText("Faculty Amount Bonus: "+df.format(Faculties.get(currentIndex).calculate_Bonus()) + "$");
        }


    }//onCreate end

    ActivityResultLauncher<Intent> startActivityIntent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode()!= MainActivity.RESULT_OK)
                {
                    Log.d(TAG, String.valueOf(result.getResultCode()));
                }
                else{
                    if(result.getData()==null){
                        Log.d(TAG, "result.getData() is null");
                        return;
                    }
                    Faculty facultyUpdatedInfo = FacultyActivity.sendMessageFacultyUpdateResult(result.getData());
                    Faculties.set(currentIndex, facultyUpdatedInfo);
                    textView_f_id.setText("Faculty No: " +Faculties.get(currentIndex).getF_Id());
                    textView_f_lname.setText("Faculty LName: " +Faculties.get(currentIndex).getF_Lname());
                    textView_f_fname.setText("Faculty FName: " +Faculties.get(currentIndex).getF_Fname());
                    textView_f_salary.setText("Faculty Salary: " +df.format(Faculties.get(currentIndex).getF_Salary()) + "$");
                    textView_f_bonus.setText("Faculty Rate Bonus: " +df.format(Faculties.get(currentIndex).getF_bonusRate()));

                    double f_amount_bonus = Faculties.get(currentIndex).calculate_Bonus();
                    String Bonus_String = "Faculty Amount Bonus: "+df.format(f_amount_bonus) + "$";

                    textView_calcBonus.setText(Bonus_String);
                }
            }
    );


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        Log.d(TAG, "onSaveIntanceState() called");
        System.out.println("store CurrentIndex: "+currentIndex);
        savedInstanceState.putInt(KEY_INDEX, currentIndex);

        Log.d(TAG, "store ArrayList: "+Faculties);
        savedInstanceState.putParcelableArrayList(KEY_ARRAY_LIST, Faculties);

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