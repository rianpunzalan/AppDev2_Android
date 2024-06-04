package com.example.temperatureproject;

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

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText Temperature_editText;
    private TextView ConvertedTemperature_textView;
    private Button Convert_Button;
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
        Temperature_editText = (EditText) findViewById(R.id.Temperature_editText);
        ConvertedTemperature_textView = (TextView) findViewById(R.id.ConvertedTemperature_textView);
        Convert_Button = (Button) findViewById(R.id.Convert_Button);
        Convert_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DecimalFormat df = new DecimalFormat("0.00");
                Double Temperature = Double.parseDouble(Temperature_editText.getText().toString());
                Double Result = (Temperature-32) * 5/9;
                String Converted = "Temperature in Celsius is " + df.format(Result) + " degrees.";
                ConvertedTemperature_textView.setText(Converted);
            }
        });
    }
}