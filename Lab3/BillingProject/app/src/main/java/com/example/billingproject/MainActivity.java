package com.example.billingproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText client_id_editText;
    private EditText client_name_editText;
    private EditText prd_name_editText;
    private EditText prd_price_editText;
    private EditText prd_qty_editText;
    private TextView ViewBillingTotal_textView;
    private Button TotalInputBilling_Button;
    private Button TotalRecordBilling_Button;
    private Button Prev_Billing_Button;
    private Button Next_Billing_Button;

    private static final String KEY_INDEX = "index";
    private static final String TAG = "Billing Project";
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

        Billing[] Billings =
        {
            new Billing(105, "Johnston Jane", "Chair", 99.99, 2),
            new Billing(108, "Fikhali Samuel", "Table", 139.99, 1),
            new Billing(113, "Samson Amina", "KeyUSB", 14.99, 2)
        };
        DecimalFormat df = new DecimalFormat("#.##");
        client_id_editText = (EditText) findViewById(R.id.client_id_editText);
        client_name_editText = (EditText) findViewById(R.id.client_name_editText);
        prd_name_editText = (EditText) findViewById(R.id.prd_Name_editText);
        prd_price_editText = (EditText) findViewById(R.id.prd_Price_editText);
        prd_qty_editText = (EditText) findViewById(R.id.prd_Qty_editText);

        ViewBillingTotal_textView = (TextView) findViewById(R.id.viewTotalBilling_textView);

        TotalInputBilling_Button = (Button) findViewById(R.id.TotalInputBilling_Button);
        TotalInputBilling_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Billing Input_Bill = new Billing(Integer.parseInt(client_id_editText.getText().toString()),client_name_editText.getText().toString(),prd_name_editText.getText().toString(),Double.parseDouble(prd_price_editText.getText().toString()),Integer.parseInt(prd_qty_editText.getText().toString()));
                double total_billing = Input_Bill.CalculateBilling();
                String Billing_String = "Client: "  + String.valueOf(Input_Bill.getClient_id()) + ", "
                                                    + Input_Bill.getClient_name()+ ", "+
                                        "Product: " + Input_Bill.getPrd_Name() + " is "
                                                    + df.format(total_billing) + "$";
                ViewBillingTotal_textView.setText(Billing_String);
                Toast.makeText(MainActivity.this, Billing_String, Toast.LENGTH_SHORT).show();

            }
        });
        TotalRecordBilling_Button = (Button) findViewById(R.id.TotalRecordBilling_Button);
        TotalRecordBilling_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                client_id_editText.setText("");
                client_name_editText.setText("");
                prd_name_editText.setText("");
                prd_price_editText.setText("");
                prd_qty_editText.setText("");

                double total_billing = Billings[currentIndex].CalculateBilling();
                String Billing_String = "Client: "  + String.valueOf(Billings[currentIndex].getClient_id()) + ", "
                        + Billings[currentIndex].getClient_name()+ ", "+
                        "Product: " + Billings[currentIndex].getPrd_Name() + " is "
                        + df.format(total_billing) + "$";
                ViewBillingTotal_textView.setText(Billing_String);
                Toast.makeText(MainActivity.this, Billing_String, Toast.LENGTH_SHORT).show();


            }
        });
        Prev_Billing_Button = (Button) findViewById(R.id.Prev_Billing_Button);
        Prev_Billing_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentIndex == 0){
                    currentIndex = Billings.length - 1;
                }else{
                    currentIndex = (currentIndex -1) % Billings.length;
                }

                TotalRecordBilling_Button.performClick();
            }
        });
        Next_Billing_Button = (Button) findViewById(R.id.Next_Billing_Button);
        Next_Billing_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex +1) % Billings.length;
                TotalRecordBilling_Button.performClick();
            }
        });
        if(savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_INDEX);
            double total_billing = Billings[currentIndex].CalculateBilling();
            String Billing_String = "Client: "  + String.valueOf(Billings[currentIndex].getClient_id()) + ", "
                    + Billings[currentIndex].getClient_name()+ ", "+
                    "Product: " + Billings[currentIndex].getPrd_Name() + " is "
                    + df.format(total_billing) + "$";
            ViewBillingTotal_textView.setText(Billing_String);
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