package com.example.storyproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText Name_editText,Age_editText,City_editText,CollegeName_editText,Profession_editText,AnimalType_editText,PetName_editText;
    private TextView Story_textView;
    private Button DisplayStory_Button;

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


        Name_editText = (EditText) findViewById(R.id.Name_editText);
        Age_editText = (EditText) findViewById(R.id.Age_editText);
        City_editText = (EditText) findViewById(R.id.City_editText);
        CollegeName_editText = (EditText) findViewById(R.id.CollegeName_editText);
        Profession_editText = (EditText) findViewById(R.id.Profession_editText);
        AnimalType_editText = (EditText) findViewById(R.id.AnimalType_editText);
        PetName_editText = (EditText) findViewById(R.id.PetName_editText);
        Story_textView = (TextView) findViewById(R.id.Story_textView);

        DisplayStory_Button = (Button) findViewById(R.id.DisplayStory_Button);
        DisplayStory_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Story = "There once was a person named "+Name_editText.getText().toString()+" who lived in "+City_editText.getText().toString()+". " +
                        "At the age of "+Age_editText.getText().toString()+", "+Name_editText.getText().toString()+" went to college at "+CollegeName_editText.getText().toString()+". " +
                        Name_editText.getText().toString()+" graduated and went to work as a "+Profession_editText.getText().toString()+". " +
                        "Then, "+Name_editText.getText().toString()+" adopted a (n) "+AnimalType_editText.getText().toString()+" named "+PetName_editText.getText().toString()+". " +
                        "They both lived happily ever after!";

                Story_textView.setText(Story);
            }
        });




    }
}