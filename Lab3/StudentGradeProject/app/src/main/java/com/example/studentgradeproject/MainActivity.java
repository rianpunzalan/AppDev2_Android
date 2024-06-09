package com.example.studentgradeproject;

import static java.security.AccessController.getContext;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private TextView studentName_textView;
    private TextView studentGradeAverage_textView;
    private Button displayStudentGradeAvg_Button;
    private Button nextStudent_Button;
    private Button previousStudent_Button;

    private static final String KEY_INDEX = "index";
    private static final String TAG = "Student Grade Project";
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

        Grade[] Grades = {  new Grade(1,"Graham","Bill",69,70,98,80,90 ),
                            new Grade(2,"Sanchez","Jim",88,72,90,83,93 ),
                            new Grade(3,"White","Peter",85,80,45,93,70 ),
                            new Grade(4,"Phelp","David",70,60,60,90,70 ),
                            new Grade(5,"Lewis","Sheila",50,76,87,59,72),
                            new Grade(6,"James","Thomas",89,99,97,98,99) };

        DecimalFormat df = new DecimalFormat("#.##");
        studentName_textView = (TextView) findViewById(R.id.studentName_textView);
        studentGradeAverage_textView = (TextView) findViewById(R.id.studentGradeAverage_textView);

        displayStudentGradeAvg_Button = (Button) findViewById(R.id.displayStudentGradeAvg_Button);
        displayStudentGradeAvg_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double studentGrade = Grades[currentIndex].Calculate_GradeAverage();
                String studentGradeAverage_String = df.format(studentGrade)+"%";
                studentGradeAverage_textView.setText(studentGradeAverage_String);
                String Toast_String = "";
                if(studentGrade>=60){
                    Toast_String = "Pass ";
                }
                else{
                    Toast_String = "Fail ";
                }
                Toast_String += "with grade of "+studentGradeAverage_String;

                Toast.makeText(MainActivity.this, Toast_String, Toast.LENGTH_SHORT).show();

            }
        });

        nextStudent_Button = (Button) findViewById(R.id.nextStudent_Button);
        nextStudent_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex +1) % Grades.length;
                String studentName_String = Grades[currentIndex].getStudent_lname() + " " + Grades[currentIndex].getStudent_fname();
                studentName_textView.setText(studentName_String);
                studentGradeAverage_textView.setText(" ");
            }
        });
        previousStudent_Button = (Button) findViewById(R.id.previousStudent_Button);
        previousStudent_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentIndex == 0){
                    currentIndex = Grades.length - 1;
                }else{
                    currentIndex = (currentIndex -1) % Grades.length;
                }

                String studentName_String = Grades[currentIndex].getStudent_lname() + " " + Grades[currentIndex].getStudent_fname();
                studentName_textView.setText(studentName_String);
                studentGradeAverage_textView.setText(" ");
            }
        });
        if(savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_INDEX);
            String studentName_String = Grades[currentIndex].getStudent_lname() + " " + Grades[currentIndex].getStudent_fname();
            String studentGradeAverage_String = df.format(Double.valueOf(Grades[currentIndex].Calculate_GradeAverage()))+"%";
            studentName_textView.setText(studentName_String);
            studentGradeAverage_textView.setText(studentGradeAverage_String);
        }
        else{
            String studentName_String = Grades[currentIndex].getStudent_lname() + " " + Grades[currentIndex].getStudent_fname();
            studentName_textView.setText(studentName_String);
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