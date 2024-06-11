package com.example.courseproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CourseActivity extends AppCompatActivity {

    private EditText edit_course_no;
    private EditText edit_course_name;
    private EditText edit_course_max_enrl;
    private EditText edit_course_credits;
    private Button button_course_update;
    private String courseIDRetrieve;
    private String courseNameRetrieve;
    private int courseMaxEnrlRetrieve;
    private int courseCreditsRetrieve;

    private static final String EXTRA_COURSE_NO = "com.example.courseproject.course_no";
    private static final String EXTRA_COURSE_NAME = "com.example.courseproject.course_name";
    private static final String EXTRA_COURSE_MAX_ENRL = "com.example.courseproject.max_enrl";
    private static final String EXTRA_COURSE_CREDITS = "com.example.courseproject.credits";

    public static Intent newIntent(Context packageContext, String courseID,String courseName,int courseMaxEnrl,int courseCredits)
    {
        Intent intent = new Intent(packageContext, CourseActivity.class);
        intent.putExtra(EXTRA_COURSE_NO, courseID);
        intent.putExtra(EXTRA_COURSE_NAME, courseName);
        intent.putExtra(EXTRA_COURSE_MAX_ENRL, courseMaxEnrl);
        intent.putExtra(EXTRA_COURSE_CREDITS, courseCredits);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_course);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //continue hereee
        courseIDRetrieve = getIntent().getStringExtra(EXTRA_COURSE_NO);
        courseNameRetrieve = getIntent().getStringExtra(EXTRA_COURSE_NAME);
        courseMaxEnrlRetrieve = getIntent().getIntExtra(EXTRA_COURSE_MAX_ENRL,0);
        courseCreditsRetrieve = getIntent().getIntExtra(EXTRA_COURSE_CREDITS,0);

        edit_course_no = (EditText) findViewById(R.id.edit_course_no);
        edit_course_no.setText(courseIDRetrieve);
        edit_course_name = (EditText) findViewById(R.id.edit_course_name);
        edit_course_name.setText(courseNameRetrieve);
        edit_course_max_enrl = (EditText) findViewById(R.id.edit_course_max_enrl);
        edit_course_max_enrl.setText(String.valueOf(courseMaxEnrlRetrieve));
        edit_course_credits = (EditText) findViewById(R.id.edit_course_credits);
        edit_course_credits.setText(String.valueOf(courseCreditsRetrieve));

        edit_course_name = (EditText) findViewById(R.id.edit_course_name);
        edit_course_max_enrl = (EditText) findViewById(R.id.edit_course_max_enrl);
        edit_course_credits = (EditText) findViewById(R.id.edit_course_credits);
        button_course_update = (Button) findViewById(R.id.button_course_update);

        button_course_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCourseUpdateResult ( edit_course_no.getText().toString(),
                                        edit_course_name.getText().toString(),
                                        Integer.parseInt(edit_course_max_enrl.getText().toString()),
                                        Integer.parseInt(edit_course_credits.getText().toString()));
            }
        });

        //use Intent Class to transfer variables from MainActivity to CourseActivity
        //used when you expect no result data from child activity

        //startActivity(intent)
        //used when there is only unidirectional data transfer(one way)
        //startActivityIntent.launch(intent);

    }

    public void setCourseUpdateResult(String course_no, String course_name, int course_max_enrl, int credits)
    {
        Intent dataIntent = new Intent();
        dataIntent.putExtra(EXTRA_COURSE_NO,course_no);
        dataIntent.putExtra(EXTRA_COURSE_NAME,course_name);
        dataIntent.putExtra(EXTRA_COURSE_MAX_ENRL,course_max_enrl);
        dataIntent.putExtra(EXTRA_COURSE_CREDITS,credits);
        setResult(RESULT_OK, dataIntent);
    }

    public static Course sendMessageCourseUpdateResult(Intent resultIntent)
    {
        Course courseUpdateInfo = new Course();
        courseUpdateInfo.setcourse_no(resultIntent.getStringExtra(EXTRA_COURSE_NO));
        courseUpdateInfo.setcourse_name(resultIntent.getStringExtra(EXTRA_COURSE_NAME));
        courseUpdateInfo.setmax_enrl(resultIntent.getIntExtra(EXTRA_COURSE_MAX_ENRL,0));
        courseUpdateInfo.credits = resultIntent.getIntExtra(EXTRA_COURSE_CREDITS,0);


        return courseUpdateInfo;
    }


    //used when there is a bidirectional data transfer(two way)
    /*ActivityResultLauncher<Intent> startActivityIntent = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        new ActivityCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o){
                //Decoding Result coming from child Activity CourseActivity
            }
        }
    );*/

    //Decoding extra parameters in parent MainActivity
    /*
    public static Course decodedMessageCourseUpdateResult(Intent resultIntent){
        Course courseUpdateInfo = new Course();
        courseUpdateInfo.setCourse_no(resultIntent.getStringExtra(EXTRA_COURSE_NO));
    }
    * */
}