package com.example.courseproject;



import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_INDEX = "index";
    private static final String TAG = "Course Project";
    private TextView courseTextView;
    private TextView courseTotalFeesTextView;
    private Button btnCalculateTotalFees;
    private Button btnNext;
    private Button button_course_details;
    private int currentIndex = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);



        Course.credits = 3;
        Course[] CourseList = {

                new Course("MIS 101","Intro. to Info Systems",140,Course.credits),
                new Course("MIS 301","Systems Analysis",35,Course.credits),
                new Course("MIS 441","Database Management",12,Course.credits),
                new Course("CS 155","Programming in C++",90,Course.credits),
                new Course("MIS 451","Web-Based Systems",30,Course.credits),
                new Course("MIS 551","Advanced Web",30,Course.credits),
                new Course("MIS 651","Advanced Java",30,Course.credits)
        };

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnCalculateTotalFees = (Button) findViewById(R.id.btnCalculateTotalFees);
        btnCalculateTotalFees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                courseTotalFeesTextView = (TextView) findViewById(R.id.courseTotalFeesTextView);
                String totalfeesString ="Total Course Fees is: " + CourseList[currentIndex].CalculateTotalFees();
                courseTotalFeesTextView.setText(totalfeesString);
                Toast.makeText(MainActivity.this, totalfeesString, Toast.LENGTH_SHORT).show();
            }
        });
        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex = (currentIndex +1) % CourseList.length;
                courseTextView = (TextView) findViewById(R.id.courseTextView);
                String courseString = CourseList[currentIndex].getcourse_no()+ " | " + CourseList[currentIndex].getcourse_name();
                courseTextView.setText(courseString);
            }
        });

        button_course_details = (Button) findViewById(R.id.button_course_detail);
        button_course_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //Single parameter way
                /* Intent intent = new Intent(MainActivity.this, CourseActivity.class);
                String courseID = CourseList[currentIndex].getcourse_no();
                intent.putExtra(EXTRA_COURSE_NO, courseID);
                startActivity(intent);*/

                String courseID = CourseList[currentIndex].getcourse_no();
                String courseName = CourseList[currentIndex].getcourse_name();
                int courseMaxEnrl = CourseList[currentIndex].getmax_enrl();
                int courseCredits = Course.credits;
                Intent intent = CourseActivity.newIntent(MainActivity.this,
                        courseID,
                        courseName,
                        String.valueOf(courseMaxEnrl),
                        String.valueOf(courseCredits)
                );
                startActivity(intent);
            }
        });

        if(savedInstanceState != null){
            currentIndex = savedInstanceState.getInt(KEY_INDEX);
            courseTotalFeesTextView = (TextView) findViewById(R.id.courseTotalFeesTextView);
            String totalfeesString ="Total Course Fees is: " + CourseList[currentIndex].CalculateTotalFees();
            courseTotalFeesTextView.setText(totalfeesString);

            courseTextView = (TextView) findViewById(R.id.courseTextView);
            String courseString = CourseList[currentIndex].getcourse_no()+ " | " + CourseList[currentIndex].getcourse_name();
            courseTextView.setText(courseString);


        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        Log.d(TAG, "onSaveIntanceState() called");
        System.out.println("store mCurrentIndex: "+currentIndex);
        savedInstanceState.putInt(KEY_INDEX, currentIndex);
        super.onSaveInstanceState(savedInstanceState);
    }

    ActivityResultLauncher<Intent> startActivityIntent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>(){
                @Override
            public void onActivityResult(ActivityResult result){
                if(result.getResultCode()!= Activity.RESULT_OK) //when the Activity fails
                {
                    return;
                }
                else
                {
                    Course courseUpdateInfo = CourseActivity.decodedMessageCourseUpdateResult(result.getData());
                    CourseList[currentIndex].setCourse_no(courseUpdateInfo.getCourse_no());
                }
            }}
    );
    /*
        ActivityResultLauncher<Intent> startActivityIntent = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        new ActivityResultCallback<ActivityREsult>() {
            @Override
            public void onActivityResult(ActivityResult result){
                if(result.getResultCode()!= Activity.RESULT_OK) //when the Activity fails
                {
                    return;
                }
                else
                {
                    Course courseUpdateInfo = CourseActivity.decodedMessageCourseUpdateResult(result.getData());
                    all_courses[currentIndex].setCourse_no(courseUpdateInfo.getCourse_no());
                }
            }
        }
    */


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