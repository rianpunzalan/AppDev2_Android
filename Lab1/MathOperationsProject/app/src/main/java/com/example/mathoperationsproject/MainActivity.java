package com.example.mathoperationsproject;

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

    private EditText XeditText;
    private EditText YeditText;
    private TextView Addition_textView;
    private TextView Subtraction_textView;
    private TextView Multiplication_textView;
    private TextView Division_textView;
    private Button Calculate_Button;
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

        XeditText = (EditText) findViewById(R.id.XeditText);
        YeditText = (EditText) findViewById(R.id.YeditText);

        Addition_textView = (TextView) findViewById(R.id.Addition_textView);
        Subtraction_textView = (TextView) findViewById(R.id.Subtraction_textView);
        Multiplication_textView = (TextView) findViewById(R.id.Multiplication_textView);
        Division_textView = (TextView) findViewById(R.id.Division_textView);

        Calculate_Button = (Button) findViewById(R.id.Calculate_Button);
        Calculate_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DecimalFormat df = new DecimalFormat("0.00");
                Double X_num = Double.parseDouble(XeditText.getText().toString());
                Double Y_num = Double.parseDouble(YeditText.getText().toString());
                String add = "Sum of X and y is: "+(X_num+Y_num);
                String sub = "Difference X and Y is: "+(Y_num-X_num);
                String multi = "Product of X and Y is: "+(X_num*Y_num);
                String div = "Quotient of X and Y is: "+df.format(X_num/Y_num);

                Addition_textView.setText(add);
                Subtraction_textView.setText(sub);
                Multiplication_textView.setText(multi);
                Division_textView.setText(div);

            }
        });
    }
}