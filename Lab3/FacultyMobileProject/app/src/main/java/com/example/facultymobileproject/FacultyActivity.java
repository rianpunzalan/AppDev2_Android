package com.example.facultymobileproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class FacultyActivity extends AppCompatActivity {

    private EditText edit_f_id;
    private EditText edit_f_lname;
    private EditText edit_f_fname;
    private EditText edit_f_salary;
    private EditText edit_f_bonus;
    private TextView textview_calculated_bonus;

    private Button button_update_faculty;
    private static final String EXTRA_F_ID = "com.example.facultyproject.f_Id";
    private static final String EXTRA_F_LNAME = "com.example.facultyproject.f_Lname";
    private static final String EXTRA_F_FNAME = "com.example.facultyproject.f_Fname";
    private static final String EXTRA_F_SALARY = "com.example.facultyproject.f_Salary";
    private static final String EXTRA_F_BONUS = "com.example.facultyproject.f_bonusRate";


    private String f_id_retrieve;
    private String f_lname_retrieve;
    private String f_fname_retrieve;

    private double f_salary_retrieve;
    private double f_bonus_rate_retrieve;
    DecimalFormat df = new DecimalFormat("0.00");
    public static Intent newIntent(Context packageContext,
                                   String f_Id,
                                   String f_Lname,
                                   String f_Fname,
                                   double f_Salary,
                                   double f_bonusRate)
    {
        Intent intent = new Intent(packageContext, FacultyActivity.class);
        intent.putExtra(EXTRA_F_ID, f_Id);
        intent.putExtra(EXTRA_F_LNAME, f_Lname);
        intent.putExtra(EXTRA_F_FNAME, f_Fname);
        intent.putExtra(EXTRA_F_SALARY,f_Salary);
        intent.putExtra(EXTRA_F_BONUS,f_bonusRate);

        return intent;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_faculty);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        f_id_retrieve = getIntent().getStringExtra(EXTRA_F_ID);
        f_lname_retrieve = getIntent().getStringExtra(EXTRA_F_FNAME);
        f_fname_retrieve = getIntent().getStringExtra(EXTRA_F_LNAME);
        f_salary_retrieve = getIntent().getDoubleExtra(EXTRA_F_SALARY,0);
        f_bonus_rate_retrieve = getIntent().getDoubleExtra(EXTRA_F_BONUS,0);

        edit_f_id = findViewById(R.id.edit_f_id);
        edit_f_id.setText(f_id_retrieve);

        edit_f_lname= findViewById(R.id.edit_f_lname);
        edit_f_lname.setText(f_lname_retrieve);

        edit_f_fname= findViewById(R.id.edit_f_fname);
        edit_f_fname.setText(f_fname_retrieve);

        edit_f_salary= findViewById(R.id.edit_f_salary);
        edit_f_salary.setText(String.valueOf(f_salary_retrieve));

        edit_f_bonus= findViewById(R.id.edit_f_bonus);
        edit_f_bonus.setText(String.valueOf(f_bonus_rate_retrieve));

        textview_calculated_bonus= findViewById(R.id.textview_calculated_bonus);


        button_update_faculty = findViewById(R.id.button_update_faculty);
        button_update_faculty.setOnClickListener(view -> {

            setFacultyUpdateResult(
                    edit_f_id.getText().toString(),
                    edit_f_lname.getText().toString(),
                    edit_f_fname.getText().toString(),
                    Double.parseDouble(edit_f_salary.getText().toString()),
                    Double.parseDouble(edit_f_bonus.getText().toString())
            );
            Faculty temp_faculty = new Faculty( edit_f_id.getText().toString(),
                    edit_f_lname.getText().toString(),
                    edit_f_fname.getText().toString(),
                    Double.parseDouble(edit_f_salary.getText().toString()),
                    Double.parseDouble(edit_f_bonus.getText().toString()));
            String Bonus_String= "Faculty Amount Bonus: "+df.format(temp_faculty.calculate_Bonus()) + "$";

            textview_calculated_bonus.setText(Bonus_String);
        });


    }


    public void setFacultyUpdateResult(String f_Id,String f_Lname,String f_Fname,double f_Salary,double f_bonusRate)
    {
        Intent dataIntent = new Intent();
        dataIntent.putExtra(EXTRA_F_ID, f_Id);
        dataIntent.putExtra(EXTRA_F_LNAME, f_Lname);
        dataIntent.putExtra(EXTRA_F_FNAME, f_Fname);
        dataIntent.putExtra(EXTRA_F_SALARY,f_Salary);
        dataIntent.putExtra(EXTRA_F_BONUS,f_bonusRate);

        setResult(RESULT_OK, dataIntent);
    }

    public static Faculty sendMessageFacultyUpdateResult(Intent resultIntent) {

        return new Faculty( resultIntent.getStringExtra(EXTRA_F_ID),
                resultIntent.getStringExtra(EXTRA_F_LNAME),
                resultIntent.getStringExtra(EXTRA_F_FNAME),
                resultIntent.getDoubleExtra(EXTRA_F_SALARY,0),
                resultIntent.getDoubleExtra(EXTRA_F_BONUS,0));
    }


}