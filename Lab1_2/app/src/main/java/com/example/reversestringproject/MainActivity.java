package com.example.reversestringproject;

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

    private EditText InputStringText;
    private TextView ReversedStringTextView;
    private Button ReverseStringButton;

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

        InputStringText= (EditText) findViewById(R.id.InputStringText);
        ReversedStringTextView = (TextView) findViewById(R.id.ReversedStringTextView);
        ReverseStringButton = (Button) findViewById(R.id.ReverseStringButton);
        ReverseStringButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reversedString = "";
                char[] charArray = InputStringText.getText().toString().toCharArray();
                for(int x=charArray.length-1; x>=0; x--){
                    reversedString+= String.valueOf(charArray[x]);
                }
                ReversedStringTextView.setText(reversedString);
            }
        });
    }


}